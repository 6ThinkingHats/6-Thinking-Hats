<template>
  <div class="icon-box">
    <button v-if="(isConferencing && hatColor !== 'spectator') || !isConferencing"
    @click="changeMic" 
    :disabled="
    (isConferencing && hatColor !== 'blue-hat' && hatColor !== speechOrder[currentTurn] && hatMode === 'sixhats')
    || (isConferencing && hatMode === 'onehat' && speechOrder[currentTurn] === 'blue-hat' && hatColor !== 'blue-hat')">
    
    <i class='bx bxs-microphone-off mic' 
    :class="{'disabled' : 
    (isConferencing && hatColor !== 'blue-hat' && hatColor !== speechOrder[currentTurn] && hatMode === 'sixhats')
    || (isConferencing && hatMode === 'onehat' && speechOrder[currentTurn] === 'blue-hat' && hatColor !== 'blue-hat')}"></i>
    </button>

    <button v-if="(isConferencing && hatColor !== 'spectator') || !isConferencing"
    @click="changeVideo"><i class='bx bxs-video-off video'></i></button>

    <button v-if="(isConferencing && hatColor !== 'spectator') || !isConferencing"
    @click="shareScreen"><i class='bx bx-window-open'></i></button>

    <button v-if="isHost" @click="record"
    ><i class='bx bx-radio-circle-marked' :class="{'record-activated': isRecording}"></i></button>

    <button @click="menuModal"><i class='bx bx-dots-vertical-rounded'></i></button>

    <button @click="copyURL"><i class='bx bx-link-alt' ></i></button>

    <button class="pre-btn" v-if="isConferencing && hatColor == 'blue-hat'"
    @click="clickBackToPreTurn"
    :class="speechOrder[(currentTurn + 5) % 6]"><i class='bx bx-chevron-left'></i></button>

    <button class="next-btn" v-if="isConferencing && hatColor == 'blue-hat'"
    @click="clickPassTurn"
    :class="speechOrder[(currentTurn + 1) % 6]"><i class='bx bx-chevron-right'></i></button>

    <button class="pass-btn" 
    v-if="isConferencing && hatMode === 'sixhats' && speechOrder[currentTurn] === hatColor && hatColor !== 'blue-hat'" 
    :class="hatColor"
    @click="clickPassTurn">차례 넘기기</button>

    <button class="end-btn" @click="startConference()" 
    v-if="!isConferencing && isHost">
      <span>회의 시작</span>
    </button>

    <button class="end-btn" @click="endConference()" 
    v-if="hatColor == 'blue-hat' && isConferencing">
      <span>회의 종료</span>
    </button>

    <button @click="outToMain()"><i class='bx bxs-x-circle'></i></button>
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
export default {
  name: 'IconBar',
  components: {
	},
  props: {
    hatColor: String,
    isRecording: Boolean,
  },
	data: () => {
		return {
      
		}
	},
	computed: {
    ...mapGetters(['ideaMode', 'currentTurn', 'session', 'speechOrder', 
                    'users', 'hatMode', 'isHost', 'isConferencing',]),
	},
	methods: {
    ...mapActions(['passTurn', 'backToPreTurn', 'resetTurn', 'startTimer', 'resetTimer',
                    'changeUserHatColor',]),
    startConference() {
      // sixhats 모드, 6모자가 각 한 명 이상 있어야 시작 가능 
      if (this.hatMode === 'sixhats') {
        // 각 모자가 몇 명씩 있는지 계산
        let hats = ['red-hat', 'yellow-hat', 'green-hat', 'blue-hat', 'black-hat', 
                    'white-hat']
        let hatCnts = [0, 0, 0, 0, 0, 0]
        let randomHatCnt = 0
        hats.sort(() => Math.random() - 0.5)
  
        this.users.forEach(el => {
          if (el.hatColor === 'random-hat') {
            randomHatCnt += 1
          } else {
            const hatIdx = hats.indexOf(el.hatColor)
            hatCnts[hatIdx] += 1
          }
        });

        // 여섯 모자 모두 배정될 수 있는지 판별
        if (hatCnts.reduce((sum, value) => {
          if (value > 0) {
            return sum + 1
          } else {
            return sum
          }
        }, 0) + randomHatCnt >= 6) {
          
          // 랜덤 모자 배정
          for (let randomCnt = randomHatCnt; randomCnt > 0; randomCnt -= 1) {
            let targetColor = hats[hatCnts.indexOf(Math.min(...hatCnts.slice(0, 6)))]
            let userIdx = this.users.findIndex(userInfo => userInfo.hatColor === 'random-hat')
            hatCnts[hats.indexOf(targetColor)] += 1

            const sending = {user: this.users[userIdx], changedHat: targetColor}
            this.changeUserHatColor(sending)
            const jsonData = JSON.stringify(sending)
            this.session.signal({
              data: jsonData,
              type: 'change-hat-color'
            })
          }
          this.$emit('startConference')
        } else {
          alert('한 모자에 적어도 한 명이 배정될 수 있어야 합니다.')
        }
      } else {
        // onehat 모드, 파란 모자가 한 명 있어야 시작 가능
        let bluehatCnt = 0

        this.users.forEach(el => {
          if (el.hatColor === 'blue-hat') {
            bluehatCnt += 1
          }
        });

        if (bluehatCnt === 1) {
          this.$emit('startConference')
        } else {
          alert('파란 모자가 한 명 있어야 합니다.')
        }
      }
    },
    endConference() {
      this.$emit('endConference')
    },
    outToMain() {
      const answer = confirm('회의에서 나가시겠습니까?')
      if (answer) {
        this.$emit('leaveRoom')
        this.$router.push({name: 'LandingPage'})
      }
    },
    changeMic() {
      this.$emit('changeMic')
      const micIcon = document.querySelector('.mic')
      if (micIcon.classList.item(1) === 'bxs-microphone-off') {
        micIcon.classList.replace('bxs-microphone-off', 'bxs-microphone')
      } else if (micIcon.classList.item(1) === 'bxs-microphone') {
        micIcon.classList.replace('bxs-microphone', 'bxs-microphone-off')
      }
    },
    changeVideo() {
      this.$emit('changeVideo')
      const videoIcon = document.querySelector('.video')
      if (videoIcon.classList.item(1) === 'bxs-video-off') {
        videoIcon.classList.replace('bxs-video-off', 'bxs-video')
      } else if (videoIcon.classList.item(1) === 'bxs-video') {
        videoIcon.classList.replace('bxs-video', 'bxs-video-off')
      }
    },
    shareScreen() {
      this.$emit('shareScreen')
    },
    clickBackToPreTurn() {
      this.session.signal({
        type: 'back-to-pre-turn'
      })
    },
    clickPassTurn() {
      this.session.signal({
        type: 'pass-turn'
      })
    },
    menuModal() {
      this.$emit('menuModal')
    },
    record() {
      this.$emit('record')
    },
    copyURL() {
      let url = '';
      const textarea = document.createElement("textarea");
      document.body.appendChild(textarea);
      url = window.document.location.href;
      textarea.value = url;
      textarea.select();
      document.execCommand("copy");
      document.body.removeChild(textarea);
      alert("URL이 복사되었습니다.")
    },
	},
  created() {
    // 파란모자가 차례를 이전으로 돌릴 때
    this.session.on('signal:back-to-pre-turn', () => {
      this.backToPreTurn()
    })

    // 파란모자가 차례를 이후로 넘길 때
    this.session.on('signal:pass-turn', () => {
      this.passTurn()
    })
  }
}
</script>

<style scoped>
  * {
  box-sizing: border-box;
}

.icon-box {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-bottom: 12px;
}

button {
  border: none;
  background-color: rgb(18, 18, 18);
  width: 36px;
  height: 36px;
  display: flex;
  align-items: center;
  justify-content: center;
}

button:hover {
  cursor: pointer;
}

i {
  color: white;
  font-size: 24px;
}

.bxs-x-circle {
  color: rgb(223, 58, 58);
}

.pre-btn, .next-btn {
  border-radius: 50%;
  width: 32px;
  height: 32px;
}

.pass-btn, .end-btn {
  width: 132px;
  height: 40px;
  border-radius: 32px;
  color: white;
}

.end-btn {
  background-color: #4285F4;
}

.red-hat {
  background-color: #EA4335;
}

.yellow-hat {
  background-color: #FBBC05;
}

.green-hat {
  background-color: #34A853;
}

.blue-hat {
  background-color: #4285F4;
}

.white-hat {
  background-color: #FFFFFF;
  color: black;
}

.white-hat i {
  color: black;
}

.black-hat {
  background-color: #000000;
}

.disabled {
  color: #a1a1a1;
}

.record-activated {
  color: rgb(223, 58, 58);
}
</style>