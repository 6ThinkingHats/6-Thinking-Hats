<template>
  <div class="conference-page">
    <!-- 화면공유 -->
    <button @click="seeScreenShare" class="screen-share-btn"
    v-if="!seeScreen && !isMyScreenShared">
      <i class="screen-share-btn-icon bx bxs-caret-down-circle"></i>
    </button>
    <screen-share class="screen-share" v-if="seeScreen"
    @closeScreenShareModal="closeScreenShare"
    :screen-sub="screenSub"></screen-share>
    <div class="screen-shared" v-if="seeScreen || isMyScreenShared"></div>

    <div class="conference-body">
      <!-- 왼쪽 캠화면 + 모자 키워드 -->
      <div class="left-side">
        <role-keyword :hat-color="myHat" class="role-keyword"
        v-if="isConferencing"></role-keyword>
        <div class="cam-arrow-btn">
          <i @click="leftCamUp" v-show="leftCamStartIndex > 0" 
            class='bx bx-chevron-up cam-arrow-icon'></i>
        </div>
        <cam-screen v-if="!isConferencing || (isConferencing && myHat !== 'spectator')" :stream-manager="publisher"
        class="cam"></cam-screen>
        <div v-if="isConferencing && myHat === 'spectator'">
          <cam-screen v-for="sub in leftSubscribers.slice(leftCamStartIndex, leftCamEndIndex + 1)" 
          :key="sub.stream.connection.connectionId" :stream-manager="sub"
          class="cam"></cam-screen>
        </div>
        <div v-else>
          <cam-screen v-for="sub in leftSubscribers.slice(leftCamStartIndex, leftCamEndIndex)" 
          :key="sub.stream.connection.connectionId" :stream-manager="sub"
          class="cam"></cam-screen>
        </div>
        <div class="cam-arrow-btn">
          <i @click="leftCamDown" v-show="leftCamEndIndex < leftSubscribers.length" 
          class='bx bx-chevron-down cam-arrow-icon' ></i>
        </div>
      </div>

      <!-- 셋팅할 때 -->
      <div class="join-screen" v-if="!isConferencing">
        <info-box></info-box>
      </div>

      <!-- 회의 시작 후 -->
      <div class="in-conference-screen" v-else-if="isConferencing">
        <role-explain :hat-color="myHat"></role-explain>
        <opinion-box :hat-color="myHat"
        @updateSubject="updateSubject"
        :session="session"></opinion-box>
      </div>

      <!-- 오른쪽 캠화면 + 발언 순서 -->
      <div class="right-side">
        <speech-order class="speech-order" v-if="isConferencing"></speech-order>
        <div class="cam-arrow-btn">
          <i @click="rightCamUp" v-show="rightCamStartIndex > 0" 
            class='bx bx-chevron-up cam-arrow-icon' ></i>
        </div>
        <cam-screen v-for="sub in rightSubscribers.slice(rightCamStartIndex, rightCamEndIndex)" 
        :key="sub.stream.connection.connectionId" :stream-manager="sub"></cam-screen>
        <div class="cam-arrow-btn">
          <i @click="rightCamDown" v-show="rightCamEndIndex < rightSubscribers.length" 
          class='bx bx-chevron-down cam-arrow-icon' ></i>
        </div>
      </div>  
    </div>

    <!-- 아이콘바 -->
    <icon-bar 
    :hat-color="myHat"
    @startConference="popSubjectModal"
    @endConference="changeConf"
    @leaveRoom="leaveSession"
    @changeMic="changeMicrophone"
    @changeVideo="changeVideo"
    @shareScreen="shareScreen"
    @menuModal="menuModal"
    class="icon-bar"
    @record="recording"
    :isRecording="isRecording"
    ref="iconBar"></icon-bar>

    <!-- <button @click="testDown">test</button> -->
    
    <menu-modal
    class="menu-modal"
    v-if="seeMenu"
    @chatModal="chatModal"
    @userListModal="userListModal"></menu-modal>
    <chat-modal
    class="chat-modal"
    v-show="seeChat"
    ref="chatRef"
    @sendChat="sendChat"
    @closeChatModal="closeChatModal"></chat-modal>
    <user-list-modal
    class="user-list-modal"
    v-show="seeUserList"
    ref="userListRef"
    @closeUserListModal="closeUserListModal"></user-list-modal>

    <div v-if="subjectModalOpend" class="subject-modal">
      <p class="sub-modal-header">회의 주제를 입력해 주세요</p>
      <input type="text" class="sub-modal-input" v-model="subject">
      <div class="sub-btn">
        <button class="sub-confirm-btn" @click="changeConf"><span>시작</span></button>
        <button class="sub-reject-btn" @click="closeSubjectModal"><span>취소</span></button>
      </div>
    </div>

    <div v-if="subjectModalOpend" class="modal-bg"></div>

    <div v-if="isConferencing && myHat === speechOrder[currentTurn] && hatMode === 'sixhats'" 
          class="turn-alert-modal" :class="myHat">
      <span>당신의 차례입니다!</span>
    </div>

  </div>
</template>

<script>
import IconBar from '@/views/conference/footer/IconBar.vue'
import InfoBox from '@/views/conference/contents/info/InfoBox.vue'
import OpinionBox from '@/views/conference/contents/opinions/OpinionBox.vue'
import RoleExplain from '@/views/conference/header/RoleExplain.vue'
import RoleKeyword from '@/views/conference/header/RoleKeyword.vue'
import SpeechOrder from '@/views/conference/header/SpeechOrder.vue'
import CamScreen from '@/views/conference/user/CamScreen.vue'
import ScreenShare from '@/views/conference/ScreenShare.vue'
import axios from 'axios';
import { OpenVidu } from 'openvidu-browser';
import { mapActions, mapGetters } from 'vuex'
// import UserVideo from './components/UserVideo';
import MenuModal from '@/views/conference/modal/MenuModal.vue'
import ChatModal from '@/views/conference/modal/ChatModal.vue'
import UserListModal from '@/views/conference/modal/UserListModal.vue'

axios.defaults.headers.post['Content-Type'] = 'application/json';

// const OPENVIDU_SERVER_URL = "https://" + 'i7a709.p.ssafy.io' + ":4443";
const OPENVIDU_SERVER_URL = "https://" + 'i7a709.p.ssafy.io' + ":5000";
// const OPENVIDU_SERVER_URL = "https://" + location.hostname + ":4443";
const OPENVIDU_SERVER_SECRET = "MY_SECRET";

export default {
  name: 'ConferencePage',
  components: {
    IconBar,
    InfoBox,
    OpinionBox,
    RoleExplain,
    RoleKeyword,
    SpeechOrder,
    CamScreen,
    ScreenShare,
    MenuModal,
    ChatModal,
    UserListModal,
  },
  data: () => {
		return {
      OV: undefined,
      screenOV: undefined,
			session: undefined,
      screenSession: undefined,
			mainStreamManager: undefined,
			subscribers: [],
      screenPublisher: undefined,
      screenSub: undefined,
      recordingOV: undefined,
      recordingSession: undefined,
      isRecording: false,
      recordPublisher: undefined,
      recordingId: undefined,
      recordingURL: undefined,
      localRecorder: undefined,
      subjectModalOpend: false,
      subject: '',

			mySessionId: 'SessionAAAAAA',
      audio: false,
      video: false,
      isMyScreenShared: false,
      seeScreen: false,
      seeMenu: false,
      seeChat: false,
      seeUserList: false,
      leftCamStartIndex: 0,
      leftCamEndIndex: 2,
      rightCamStartIndex: 0,
      rightCamEndIndex: 3,
		}
	},
	computed: {
    ...mapGetters(['publisher', 'users', 'myHat', 'isHost', 'ideaMode', 'hatMode',
                    'speechOrder', 'currentTurn', 'baseTime', 'totalTime',
                    'confSubject', 'opinions', 'hostConnectionId', 'isConferencing',
                    'conferenceStatus', 'timer', 'name',]),
    leftSubscribers () {
      let leftSubscribers = []
      for (let idx in this.subscribers) {
        if (idx % 2 == 1) {
          leftSubscribers.push(this.subscribers[idx])
        }
      }
      return leftSubscribers
    },
    rightSubscribers () {
      let rightSubscribers = []
      for (let idx in this.subscribers) {
        if (idx % 2 == 0) {
          rightSubscribers.push(this.subscribers[idx])
        }
      }
      return rightSubscribers
    }
	},
	methods: {
    ...mapActions(['startTimer', 'resetTimer', 'resetTurn', 'setSession', 'addUser',
                    'changeUserHatColor', 'setMyHat', 'setPublisher', 'clearUsers',
                    'setMyName', 'removeUser', 'addOpinion', 'removeOpinion', 'setRole',
                    'initialSetting', 'setHostConnectionId', 'turnOffAudio', 'turnOnAudio',
                    'turnOffVideo', 'turnOnVideo', 'endConference', 'startConference',
                    'joinConferenceRoom', 'exitConferenceRoom', 'someoneTurnOffAudio',
                    'someoneTurnOnAudio', 'someoneTurnOffVideo', 'someoneTurnOnVideo',
                    'setConfSubject', 'clearOpinions',]),

    sendChat (chat) {
      this.session.signal({
        data: chat,
        type: 'chat'
      })
      .then(() => {
        console.log('Message successfully sent')
        this.$refs.chatRef.clearChat()
      })
      .catch(error => {
        console.error(error)
      })
    },
    menuModal () {
      this.seeMenu = !this.seeMenu
    },
    chatModal () {
      this.seeMenu = false
      this.seeChat = true
      let chatModal = document.querySelector('div.chat-modal')
      let userListModal = document.querySelector('div.user-list-modal')
      chatModal.style.zIndex = '3'
      userListModal.style.zIndex = '2'
    },
    userListModal () {
      this.seeMenu = false
      this.seeUserList = true
      let chatModal = document.querySelector('div.chat-modal')
      let userListModal = document.querySelector('div.user-list-modal')
      chatModal.style.zIndex = '2'
      userListModal.style.zIndex = '3'
    },
    closeChatModal () {
      this.seeChat = !this.seeChat
    },
    closeUserListModal () {
      this.seeUserList = false
    },
    popSubjectModal() {
      this.subjectModalOpend = true
    },
    closeSubjectModal() {
      this.subjectModalOpend = false
    },
    changeConf() {
      if (this.isConferencing) {
        // 회의 종료할 때
        this.session.signal({
          to: [],
          type: 'changeConf'
        })
        .then(() => {
          console.log('conference just ended!');
        })

      } else {

        // 회의 시작할 때
        if (this.subject) {
          this.session.signal({
            to: [],
            type: 'changeConf'
          })
          .then(() => {
            console.log('conference just started!');
          })
          const subjectData = {content: this.subject, category: 'subject'}
          const jsonSubjectData = JSON.stringify(subjectData)
          this.session.signal({
            data: jsonSubjectData,
            type: 'update-subject'
          })
          this.subject = ''
        } else {
          alert('안건을 입력해주세요!')
        }
      }
    },
    joinSession () {
			// --- Get an OpenVidu object ---
			this.OV = new OpenVidu();

			// --- Init a session ---
			this.session = this.OV.initSession();
      
      this.setSession(this.session)

			// --- Specify the actions when events take place in the session ---

      this.session.on('signal:chat', (event) => {
        this.$refs.chatRef.receiveChat(event)
      })

			// On every new Stream received...
			this.session.on('streamCreated', ({ stream }) => {
        if (stream.typeOfVideo === 'SCREEN') {
          const screen = this.session.subscribe(stream)
          this.screenSub = screen
        }
        if (stream.typeOfVideo === 'CAMERA') {
          const subscriber = this.session.subscribe(stream);
          subscriber.hatColor = 'spectator'
          this.subscribers.push(subscriber)
        }
			});

			// On every Stream destroyed...
			this.session.on('streamDestroyed', ({ stream }) => {
        if (stream.typeOfVideo === 'SCREEN') {
          this.screenSub = undefined
        }
				if (stream.typeOfVideo === 'CAMERA') {
          const index = this.subscribers.indexOf(stream.streamManager, 0);
          if (index >= 0) {
            this.subscribers.splice(index, 1);
          }
        }
			});

			// On every asynchronous exception...
			this.session.on('exception', ({ exception }) => {
				console.warn(exception);
			});
      
      this.session.on('connectionCreated', ({ connection }) => {
        if (this.isHost) {

          if (this.users.length < 12) {
            const name = JSON.parse(connection.data).clientData
            const userInfo = { hatColor: 'spectator', 
                              connectionId: connection.connectionId,
                              userName: name,
                              isHost: false,
                              camOn: false,
                              micOn: false }
            this.addUser(userInfo)
          }

          const settingData = { users: this.users,
                                ideaMode: this.ideaMode,
                                hatMode: this.hatMode,
                                speechOrder: this.speechOrder,
                                currentTurn: this.currentTurn,
                                baseTime: this.baseTime,
                                totalTime: this.totalTime,
                                confSubject: this.confSubject,
                                opinions: this.opinions,
                                hostConnectionId: this.hostConnectionId,
                                conferenceStatus: this.conferenceStatus,
                                timer: this.timer }
          const jsonSettingData = JSON.stringify(settingData)
          
          this.session.signal({
            data: jsonSettingData,
            type: 'initial-setting'
          })
        }
      })

      this.session.on('connectionDestroyed', ({ connection }) => {
        if (this.hostConnectionId !== connection.connectionId) {
          let idx = this.users.findIndex(userInfo => {
          if (userInfo.connectionId === connection.connectionId) {
            return true
          }
        })
         if (idx > -1) {
           if (this.isConferencing && this.users[idx].hatColor !== 'spectator') {
             // 회의 종료 시
            this.resetTimer()
            this.endConference()

            // 관전자일 때 회의가 끝나면 다시 카메라 복원
            if (this.myHat === 'spectator') {
              this.session.publish(this.publisher)
            }
          }
          this.removeUser(idx)
         }
        } else {
          this.leaveSession()
        }
      })

      this.session.on('publisherStartSpeaking', ({ connection }) => {
        this.session.signal({
          data: JSON.stringify({
            connectionId: connection.connectionId,
            streamId: connection.stream.streamId
          }),
          type: 'start-speaking'
        })
      })

      this.session.on('publisherStopSpeaking', ({ connection }) => {
        this.session.signal({
          data: JSON.stringify({
            connectionId: connection.connectionId,
            streamId: connection.stream.streamId
          }),
          type: 'stop-speaking'
        })
      })
			// --- Connect to the session with a valid user token ---

			// 'getToken' method is simulating what your server-side should do.
			// 'token' parameter should be retrieved and returned by your own backend
			this.getToken(this.mySessionId).then(token => {
				this.session.connect(token, { clientData: this.name })
					.then(() => {
            
            this.setMyName(this.name)
						// --- Get your own camera stream with the desired properties ---

						let publisher = this.OV.initPublisher(undefined, {
							audioSource: undefined, // The source of audio. If undefined default microphone
							videoSource: undefined, // The source of video. If undefined default webcam
							publishAudio: false,  	// Whether you want to start publishing with your audio unmuted or not
							publishVideo: false,  	// Whether you want to start publishing with your video enabled or not
							resolution: '640x480',  // The resolution of your video
							frameRate: 30,			// The frame rate of your video
							insertMode: 'APPEND',	// How the video is inserted in the target element 'video-container'
							mirror: false       	// Whether to mirror your local video or not
						});

						this.mainStreamManager = publisher;
						this.setPublisher(publisher)
            
						// --- Publish your stream ---

            // 호스트 판별
            if (this.subscribers.length === 0) {
              this.setRole('host')
              this.setHostConnectionId(publisher.stream.session.connection.connectionId)
            }

            const userInfo = { hatColor: 'spectator', connectionId: publisher.stream.session.connection.connectionId,
                              userName: this.name, isHost: this.isHost, camOn: false, micOn: false }
            this.addUser(userInfo)
						if (!this.isConferencing) {
              this.session.publish(this.publisher);
            }
					})
					.catch(error => {
						console.log('There was an error connecting to the session:', error.code, error.message);
					});
			});
			window.addEventListener('beforeunload', this.leaveSession)
		},

		leaveSession () {
      // --- Leave the session by calling 'disconnect' method over the Session object ---
      this.resetTimer()
      this.endConference().then(() => {
        if (this.session) this.session.disconnect();
        if (this.screenSession) this.screenSession.disconnect()
        if (this.recordingSession) this.recordingSession.disconnect()

        this.session = undefined;
        this.setSession(undefined)
        this.mainStreamManager = undefined;
        this.setPublisher(undefined);
        this.subscribers = [];
        this.OV = undefined;
        this.screenSession = undefined
        this.recordingSession = undefined
        this.isMyScreenShared = false
        this.clearUsers()
        this.setRole('particitant')
        this.setHostConnectionId(undefined)
        this.exitConferenceRoom()
        this.$router.push({name: 'LandingPage'})

        this.leftCamStartIndex = 0
        this.leftCamEndIndex = 2
        this.rightCamStartIndex = 0
        this.rightCamEndIndex = 3
        this.clearOpinions()
      })

			window.removeEventListener('beforeunload', this.leaveSession);
		},

		updateMainVideoStreamManager (stream) {
			if (this.mainStreamManager === stream) return;
			this.mainStreamManager = stream;
		},

		/**
		 * --------------------------
		 * SERVER-SIDE RESPONSIBILITY
		 * --------------------------
		 * These methods retrieve the mandatory user token from OpenVidu Server.
		 * This behavior MUST BE IN YOUR SERVER-SIDE IN PRODUCTION (by using
		 * the API REST, openvidu-java-client or openvidu-node-client):
		 *   1) Initialize a Session in OpenVidu Server	(POST /openvidu/api/sessions)
		 *   2) Create a Connection in OpenVidu Server (POST /openvidu/api/sessions/<SESSION_ID>/connection)
		 *   3) The Connection.token must be consumed in Session.connect() method
		 */

		getToken (mySessionId) {
			return this.createSession(mySessionId).then(sessionId => this.createToken(sessionId));
		},

		// See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-session
		createSession (sessionId) {
			return new Promise((resolve, reject) => {
				axios
					.post(`${OPENVIDU_SERVER_URL}/openvidu/api/sessions`, JSON.stringify({
						customSessionId: sessionId,
					}), {
						auth: {
							username: 'OPENVIDUAPP',
							password: OPENVIDU_SERVER_SECRET,
						},
					})
					.then(response => response.data)
					.then(data => resolve(data.id))
					.catch(error => {
						if (error.response.status === 409) {
							resolve(sessionId);
						} else {
							console.warn(`No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}`);
							if (window.confirm(`No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${OPENVIDU_SERVER_URL}"`)) {
								location.assign(`${OPENVIDU_SERVER_URL}/accept-certificate`);
							}
							reject(error.response);
						}
					});
			});
		},

		// See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-connection
		createToken (sessionId) {
			return new Promise((resolve, reject) => {
				axios
					.post(`${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${sessionId}/connection`, {}, {
						auth: {
							username: 'OPENVIDUAPP',
							password: OPENVIDU_SERVER_SECRET,
						},
					})
					.then(response => response.data)
					.then(data => resolve(data.token))
					.catch(error => reject(error.response));
			});
		},

    changeMicrophone() {
      this.audio = !this.audio
      if (this.audio) {
        this.turnOnAudio()
        this.session.signal({
          data: this.publisher.stream.session.connection.connectionId,
          type: 'turn-on-audio'
        })
      } else {
        this.turnOffAudio()
        this.session.signal({
          data: this.publisher.stream.session.connection.connectionId,
          type: 'turn-off-audio'
        }) 
      }
    },

    changeVideo(){
      this.video = !this.video
      if (this.video) {
        this.turnOnVideo()
        this.session.signal({
          data: this.publisher.stream.session.connection.connectionId,
          type: 'turn-on-video'
        })
      } else {
        this.turnOffVideo()
        this.session.signal({
          data: this.publisher.stream.session.connection.connectionId,
          type: 'turn-off-video'
        })
      }
    },
    
    shareScreen() {
      this.screenOV = new OpenVidu()
      this.screenSession = this.screenOV.initSession()

      this.getToken(this.mySessionId).then(token => {
				this.screenSession.connect(token)
					.then(() => {

						// --- Get your own camera stream with the desired properties ---

            this.screenPublisher = this.screenOV.initPublisher(undefined, { videoSource: 'screen', publishAudio: false})
            this.screenPublisher.once('accessAllowed', () => {
              this.isMyScreenShared = true
              this.screenPublisher.stream.getMediaStream().getVideoTracks()[0].addEventListener('ended', () => {
                this.screenSession.unpublish(this.screenPublisher)
                this.screenSession.disconnect()
                this.screenPublisher = undefined
                this.screenSub = undefined
                this.screenOV = undefined
                this.isMyScreenShared = false
              })
            })
            this.screenSession.publish(this.screenPublisher)

					})
					.catch(error => {
						console.log('There was an error connecting to the session:', error.code, error.message);
					});
			});    
    },

    seeScreenShare() {
      this.seeScreen = true
    },

    closeScreenShare() {
      this.seeScreen = false
    },
    recording() {
      if (this.isRecording) {
        // 녹화 중이었을 때 녹화 끄기
        this.recordingStop()
        this.isRecording = false
        this.recordingSession.unpublish(this.recordPublisher)
        this.recordingSession.disconnect()
        this.recordPublisher = undefined
        this.recordingOV = undefined
      } else {
        // 녹화 중이 아니었을 때 녹화 시작하기
        this.recordingOV = new OpenVidu()
        this.recordingSession = this.recordingOV.initSession()

        this.getToken(this.mySessionId + 'A').then(token => {
        //녹화용 세션 아이디(기존 세션 아이디에 A 붙인 것)
				this.recordingSession.connect(token)
					.then(() => {

            this.recordPublisher = this.recordingOV.initPublisher(undefined, { videoSource: 'screen', publishAudio: false})

            this.recordPublisher.once('accessAllowed', () => {
              this.isRecording = true
              
              this.recordPublisher.stream.getMediaStream().getVideoTracks()[0].addEventListener('ended', () => {
                this.recordingStop()
                this.recordingSession.unpublish(this.recordPublisher)
                this.recordingSession.disconnect()
                this.recordPublisher = undefined
                this.recordingOV = undefined
                this.isRecording = false
              })
            })
            this.recordingSession.publish(this.recordPublisher)

          })
          .then(() => {
            this.recordingStart()
          })
					// .catch(error => {
					// 	console.log('There was an error connecting to the session:', error.code, error.message);
					// });
        });    
      }
    },
    recordingStart () {
      axios
        .post(
          `${OPENVIDU_SERVER_URL}/openvidu/api/recordings/start`,
          {
            session: this.mySessionId + 'A',
            // session: 녹화용 세션 아이디(기존 세션 아이디에 A 붙인 것)
          },
          {
            auth: {
              username: "OPENVIDUAPP",
              password: OPENVIDU_SERVER_SECRET,
            },
          }
        )
        .then((res) => {
            console.log(res);
            this.recordingId = res.data.id;
            console.log(this.recordingId);
        })
    },
    recordingStop () {
      console.log(this.recordingId);
      axios
        .post(
          `${OPENVIDU_SERVER_URL}/openvidu/api/recordings/stop/${this.recordingId}`,
          {},
          {
            auth: {
              username: "OPENVIDUAPP",
              password: OPENVIDU_SERVER_SECRET,
            },
          }
        )
        .then((res) => {
          console.log(res);
          console.log(res.data.url);
          this.recordingUrl = res.data.url;
        })
    },
    testDown() {
      this.localRecorder.download()
    },
    leftCamUp() {
      this.leftCamStartIndex -= 1
      this.leftCamEndIndex -= 1
    },
    leftCamDown() {
      this.leftCamStartIndex += 1
      this.leftCamEndIndex += 1 
    },
    rightCamUp() {
      this.rightCamStartIndex -= 1
      this.rightCamEndIndex -= 1
    },
    rightCamDown() {
      this.rightCamStartIndex += 1
      this.rightCamEndIndex += 1
    },
	},
  watch: {
    // 회의화면에서 뒤로가기 했을 때 회의 나가기
    $route(to, from) {
      if (to.path !== from.path) {
        this.leaveSession()
      }
    }
  },
  created() {
    console.log(this.$route.params.sessionCode);
		this.mySessionId = this.$route.params.sessionCode;
    this.joinSession()

    // 회의를 시작하거나 종료할 때 신호를 받고 실행됨
    this.session.on('signal:changeConf', () => {
      this.resetTurn()
      if (this.isConferencing) {
        // 회의 종료 시
        this.resetTimer()
        this.endConference()

        // 관전자일 때 회의가 끝나면 다시 카메라 복원
        if (this.myHat === 'spectator') {
          this.session.publish(this.publisher)
        }
      } else {

        // 회의 시작 시
        this.subjectModalOpend = false
        this.startTimer()
        this.startConference()
        // 회의 시작시 무조건 오디오 끄기
        if (this.audio) {
          this.$refs.iconBar.changeMic()
        }

        // 관전자일 때 회의가 시작되면 카메라 끄고 캠 화면 없앰
        if (this.myHat === 'spectator') {
          // this.turnOffVideo()
          if (this.video) {
            this.$refs.iconBar.changeVideo()
          }
          this.session.unpublish(this.publisher)
        }
      }
    })

    // 유저들의 모자 색을 바꿀 때 실행됨
    this.session.on('signal:change-hat-color', event => {
      const data = JSON.parse(event.data)
      this.changeUserHatColor(data)
      if (this.publisher.stream.session.connection.connectionId === data.user.connectionId) {
        this.setMyHat(data.changedHat)
      }
    })

    // 의견창구에서 의견을 지울 때 실행됨
    this.session.on('signal:delete-opinion', ({data}) => {
      this.removeOpinion(Number(data))
    })
    this.session.on('signal:initial-setting', ({data}) => {
      // users, ideaMode, hatMode, speechOrder, currentTurn, baseTime, 
      // totalTime, timer, confSubject, opinions
      if (!this.isHost) {
        const settingData = JSON.parse(data)
        if (settingData.users.length === 12) {
          console.log('12명');
          let idx = settingData.users.findIndex(userInfo => {
            if (userInfo.connectionId === this.publisher.stream.session.connection.connectionId) {
              return true
            }
          })
          
          if (idx === -1) {
            alert('12명 까지만 입장할 수 있습니다.')
            this.leaveSession()
          } else {
            this.initialSetting(settingData)
          }
        } else {
            this.initialSetting(settingData)
        }
      }
    })

    this.session.on('signal:turn-on-audio', ({data}) => {
      this.someoneTurnOnAudio(data)
    })

    this.session.on('signal:turn-off-audio', ({data}) => {
      this.someoneTurnOffAudio(data)
    })

    this.session.on('signal:turn-on-video', ({data}) => {
      this.someoneTurnOnVideo(data)
    })

    this.session.on('signal:turn-off-video', ({data}) => {
      this.someoneTurnOffVideo(data)
    })

    this.session.on('signal:kick-user', ({data}) => {
      if (this.users[data]['connectionId'] == this.publisher.stream.session.connection.connectionId) {
        this.leaveSession()
        .then(() => {
          alert('당신은 강퇴당했습니다.')
        })
      }
    })

    this.joinConferenceRoom()

    this.session.on('signal:start-speaking', ({data}) => {
      let speakingData = JSON.parse(data)
      if (this.subscribers.length == 0) {
        let publisherCam = document.querySelector('#local-video-undefined')
        if (publisherCam == null) {
          publisherCam = document.querySelector('#local-video-' + speakingData.streamId)
        }
        publisherCam.classList.add('highlight')
      } else if (this.subscribers.length == 1) {
        if (this.subscribers[0].stream.connection.connectionId == speakingData.connectionId) {
          let subcriberCam = document.querySelector('#remote-video-' + speakingData.streamId)
          subcriberCam.classList.add('highlight')
        } else {
          let publisherCam = document.querySelector('#local-video-undefined')
          if (publisherCam == null) {
            publisherCam = document.querySelector('#local-video-' + speakingData.streamId)
          }
          publisherCam.classList.add('highlight')
        }
      } else {
        let isSubscriber = false
        for (let idx in this.subscribers) {
          if (this.subscribers[idx].stream.connection.connectionId == speakingData.connectionId) {
            isSubscriber = true
            let currentSubscriber = this.subscribers[idx]
            this.subscribers.splice(idx, 1)
            this.subscribers.splice(0, 0, currentSubscriber)
            if (idx == 0) {
              let subcriberCam = document.querySelector('#remote-video-' + speakingData.streamId)
              subcriberCam.classList.add('highlight')
            } else {
              setTimeout(() => {
                let subcriberCam = document.querySelector('#remote-video-' + speakingData.streamId)
                subcriberCam.classList.add('highlight')
              }, 200);
            }
            break
          }
        }
        if (!isSubscriber) {
          let publisherCam = document.querySelector('#local-video-undefined')
          if (publisherCam == null) {
            publisherCam = document.querySelector('#local-video-' + speakingData.streamId)
          }
          publisherCam.classList.add('highlight')
        }
      }
    })

    this.session.on('signal:stop-speaking', ({data}) => {
      let speakingData = JSON.parse(data)
      let isSubscriber = false
      for (let idx in this.subscribers) {
        if (this.subscribers[idx].stream.connection.connectionId == speakingData.connectionId) {
          isSubscriber = true
          let subcriberCam = document.querySelector('#remote-video-' + speakingData.streamId)
          subcriberCam.classList.remove('highlight')
          break
        }
      }
      if (!isSubscriber) {
        let publisherCam = document.querySelector('#local-video-undefined')
        if (publisherCam == null) {
          publisherCam = document.querySelector('#local-video-' + speakingData.streamId)
        }
        publisherCam.classList.remove('highlight')
      }
    })

    // 주제가 변화될 때
    this.session.on('signal:update-subject', event => {
      const subjectData = JSON.parse(event.data)
      this.addOpinion(subjectData)
      this.setConfSubject(subjectData.content)
    })
    
    // 타이머를 실행할 때
    this.session.on('signal:start-timer', () => {
      this.startTimer()
    })
    
    // 타이머를 멈출 때
    this.session.on('signal:stop-timer', () => {
      this.stopTimer()
    })

    // 타이머를 재설정할 때
    this.session.on('signal:reset-timer', () => {
      this.resetTimer()
    })

    // 의견창구에 의견을 보낼 때 실행됨
    this.session.on('signal:send-opinion', ({data}) => {
      const opinionData = JSON.parse(data)
      this.addOpinion(opinionData).then(() => {
        const opScroll = document.querySelector('.opinion-contents')
        opScroll.scrollTop = opScroll.scrollHeight
      })
    })
  }
}
</script>

<style scoped>
  html, body, #app {
    width: 100%;
    height: 100%;
  }

  .conference-page {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: space-around;
    background-color: #121212;
    width: 100%;
    height: 100%;
  }

  .screen-share {
    position: absolute;
    top: 0px;
  }

  .screen-share-btn-icon {
    color: white;
    font-size: 24px;
  }

  .screen-share-btn {
    border: none;
    background-color: #121212;
    margin-top: 10px;
  }

  .screen-share-btn:hover {
    cursor: pointer;
  }

  .screen-shared{
    height: 50px;
  }

  .in-conference-screen {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 8px;
  }

  .conference-body {
    display: flex;
    align-items: center;
    justify-content: space-evenly;
    width: 100%;
    height: 100%;
  }

  .left-side {
    display: flex;
    flex-direction: column;
  }

  .right-side {
    display: flex;
    flex-direction: column;
    
  }

  .left-side, .right-side {
    align-self: flex-start;
    align-items: center;
    width: 15.3646vw;
  }

  .cam-arrow-btn {
    width: 40px;
    height: 40px;
  }

  .cam-arrow-icon {
    font-size: 40px;
    color: rgb(116, 116, 116);
  }

  .cam-arrow-icon:hover {
    cursor: pointer;
    color: white;
  }

  .icon-bar {
    align-self: bottom;
  }

  .menu-modal {
    position: absolute;
    bottom: 60px;
    z-index: 1;
  }

  .chat-modal {
    position: absolute;
    bottom: 60px;
  }

  .user-list-modal {
    position: absolute;
    bottom: 60px;
  }

  .cam {
    flex-shrink: 1;
  }

  .subject-modal {
    display: flex;
    flex-direction: column;
    justify-content: space-around;
    background-color: #F6F6F6;
    position: absolute;
    width: 500px;
    height: 200px;
    border-radius: 14px;
    padding: 12px;
    z-index: 1000;
  }

  .sub-modal-input {
    border-radius: 10px;
    border: 1px solid;
    padding: 2px;
  }

  .sub-btn {
    align-self: center;
    display: flex;
    width: 150px;
    justify-content: space-between;
  }

  .sub-confirm-btn, .sub-reject-btn {
    width: 50px;
    border-radius: 20px;
    border: none;
    color: white;
    padding: 4px;
  }

  .sub-confirm-btn {
    background-color: #4285F4;
  }

  .sub-reject-btn {
    background-color: #EA4335;
  }

  .modal-bg {
    width: 100%;
    height: 100%;
    position: fixed;
    background-color: rgba(0, 0, 0, 0.3);
    z-index: 999;
  }

  .sub-modal-invisible {
    display: none;
  }

  .turn-alert-modal {
    position: absolute;
    border-radius: 10px;
    width: 200px;
    top: 180px;
    padding: 12px;
    z-index: 1000;
    animation-name: turnAlert;
    animation-duration: 1.8s;
    animation-fill-mode: forwards;
  }

   .turn-alert-modal span {
    color: white;
   }

   @keyframes turnAlert {
    0% {
      top: 0px;
      opacity: 0;
    }
    90% {
      top: 180px;
      opacity: 0.9;
    }
    100% {
      opacity: 0;
      visibility: hidden;
    }
   }

  .white-hat {
    background-color: white;
  }

  .white-hat span {
    color: black;
  }

  .red-hat {
    background-color: #EA4335;
    color: white;
  }

  .yellow-hat {
    background-color: #FBBC05;
    color: white;
  }

  .black-hat {
    background-color: black;
    color: white;
  }

  .green-hat {
    background-color: #34A853;
    color: white;
  }

  .blue-hat {
    background-color: #4285F4;
    color: white;
  }
</style>