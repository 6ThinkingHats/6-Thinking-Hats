<template>
  <div class="opinion-box">
    <div class="subject-box">

      <!-- 타이머 -->
      <div class="timer-box">
        <i class="timer-icon bx bx-stopwatch"></i>
        <div class="time">
          {{ minutes }} : {{ seconds }}
        </div>
        <i class='bx bx-play' @click="clickStartTimer"
        v-if="hatColor === 'blue-hat' && !timer"></i>
        <i class='bx bx-pause' @click="clickStopTimer"
        v-if="hatColor === 'blue-hat' && timer"></i>
        <i class='bx bx-revision' @click="clickResetTimer"
        v-if="hatColor === 'blue-hat'"></i>
      </div>

      <!-- 회의 주제 변경 인풋, 데이터 창 -->
      <div class="subject-content" :class="{'other-hat-sub-content': hatColor !== 'blue-hat'}">
        <span v-if="!subUpdating">{{ confSubject }}</span>
        <input class="sub-input" 
        v-else-if="subUpdating" type="text">
      </div>

      <!-- 회의 주제 변경 버튼 -->
      <div class="subject-btn" v-if="hatColor === 'blue-hat'">
        <button class="sub-update-btn" v-if="!subUpdating"
        @click="updateToggle">
        <span v-if="!confSubject">입력</span>
        <span v-else-if="confSubject">수정</span></button>
        <button class="sub-update-confirm-btn"
        v-if="subUpdating"
        @click="updateSubject">확인</button>
        <button class="sub-update-cancel-btn"
        v-if="subUpdating"
        @click="updateToggle">취소</button>
      </div>

    </div>

    <!-- 의견 창구 -->
    <div class="opinion-content-box">
      <div class="opinion-contents">
        <opinion-item v-for="(op, idx) in opinions" :key="`message-${idx}`"
        :opinion="op" :opinion-index="idx"></opinion-item>
      </div>

      <!-- 의견 창구 입력 창 sixhats 모드 & onehat 모드 -->
      <div class="input-box" 
      v-if="(hatMode === 'sixhats' && (hatColor === speechOrder[currentTurn] || hatColor === 'blue-hat'))
            || (hatMode === 'onehat' && hatColor !== 'spectator' && speechOrder[currentTurn] !== 'blue-hat')
            || (hatMode === 'onehat' && hatColor === 'blue-hat' )">
        <input class="input-box-content" v-model="opinion" @keyup.enter="sendOpinion(opinion)">
        <i class='bx bxs-send' @click="sendOpinion(opinion)"></i>
      </div>
    </div>
  </div>
</template>

<script>
import OpinionItem from '@/views/conference/contents/opinions/OpinionItem.vue'
import { mapActions, mapGetters } from 'vuex'

export default {
  name: 'OpinionBox',
  components: {
    OpinionItem,
  },
  props: {
    hatColor: String,
  },
  data: () => {
		return {
      opinion: '',
      subUpdating: false,
		}
	},
	computed: {
    ...mapGetters(['minutes', 'seconds', 'confSubject', 'session', 'opinions',
                    'publisher', 'myName', 'speechOrder', 'currentTurn',
                    'hatMode', 'timer',]),
	},
	methods: {
    ...mapActions(['startTimer', 'stopTimer', 'resetTimer', 'setConfSubject', 'addOpinion',]),
    updateToggle() {
      this.subUpdating = !this.subUpdating
    },
    updateSubject() {
      const changedSub = document.querySelector('.sub-input').value
      this.subUpdating = false
      let subjectData = { content: changedSub,
                          category: 'subject'}
      const jsonSubjectData = JSON.stringify(subjectData)
      this.session.signal({
        data: jsonSubjectData,  
        to: [],
        type: 'update-subject' 
      })
      .then(() => {
          console.log('Message successfully sent');
      })
      .catch(error => {
          console.error(error);
      });
    },
    clickStartTimer() {
      this.session.signal({
        type: 'start-timer'
      })
    },
    clickStopTimer() {
      this.session.signal({
        type: 'stop-timer'
      })
    },
    clickResetTimer() {
      this.session.signal({
        type: 'reset-timer'
      })
    },
    sendOpinion(op) {
      if (op) {
        let opinionData = {}
        if (this.hatMode === 'sixhats' || this.hatColor === 'blue-hat' ) {
          opinionData = { userName: this.myName, 
                          content: this.opinion, 
                          connectionId: this.publisher.stream.session.connection.connectionId,
                          hatColor: this.hatColor,
                          category: 'opinion'}
        } else {
          opinionData = { userName: this.myName, 
                          content: this.opinion, 
                          connectionId: this.publisher.stream.session.connection.connectionId,
                          hatColor: this.speechOrder[this.currentTurn],
                          category: 'opinion'}
        }
        const jsonOpinionData = JSON.stringify(opinionData)
        this.session.signal({
          data: jsonOpinionData,
          type: 'send-opinion'
        })

        this.opinion = ''
      }
    },
	},
}
</script>

<style scoped>
* {
  box-sizing: border-box;
}

.subject-box {
  display: flex;
  justify-content: space-between;
  background-color: #F6F6F6;
  align-items: center;
  border-radius: 0.6510vw;
  height: 4.9889vh;
  width: 66.4063vw;
  margin-bottom: 8px;
  margin-left: auto;
  margin-right: auto;
}

.timer-box {
  display: flex;
  align-items: center;
  gap: 4px;
  margin-left: 8px;
}

.time {
  font-size: 0.9115vw;
}

.timer-icon {
  font-size: 2.0833vw;
}

.other-hat-sub-content {
  margin-right: 32.5521vw;
}

.sub-input {
  border: none;
  height: 3.8803vh;
}

.subject-btn {
  display: flex;
  justify-content: space-between;
}

.sub-update-btn, .sub-update-confirm-btn, .sub-update-cancel-btn {
  border: none;
  color: white;
  border-radius: 2.3438vw;
  width: 2.8646vw;
  height: 3.3259vh;
  margin-right: 4px;
  font-size: 0.7813vw;
  padding: 4px;
}

.sub-update-btn:hover, .sub-update-confirm-btn:hover, .sub-update-cancel-btn:hover {
  cursor: pointer;
}

.sub-update-btn {
  background-color: #4285F4;
}

.sub-update-confirm-btn {
  background-color: #34A853;
}

.sub-update-cancel-btn {
  background-color: #EA4335;
}

.opinion-content-box {
  background-color: #F6F6F6;
  width: 66.4063vw;
  height: 66.5188vh;
  border-radius: 0.9115vw;
  margin-left: auto;
  margin-right: auto;
  padding: 12px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
}

.opinion-contents {
  width: 65.1042vw;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 8px;
  overflow: auto;
  margin-bottom: 8px;
}

.opinion-contents::-webkit-scrollbar {
  width: 10px;
}

.opinion-contents::-webkit-scrollbar-thumb {
  background-color: rgb(41, 41, 41);
  border-radius: 10px
}

.opinion-contents::-webkit-scrollbar-track {
  background-color: rgb(209, 209, 209);
  border-radius: 10px
}

.input-box {
  display: flex;
  width: 65.1042vw;
  min-height: 4.9889vh;
  border: black solid 1px;
  border-radius: 0.7813vw;
  align-items: center;
  background-color: white;
}

.input-box-content {
  border: none;
  width: 62.5000vw;
  height: 3.3259vh;
  margin-left: 10px;
  position: relative;
  top: 2px;
  bottom: 4px;
  overflow: auto;
}

.input-box-content:focus {
  outline: none;
}

.bxs-send:hover {
  cursor: pointer;
}

.bx-play, .bx-pause {
  font-size: 1.5625vw;
}

.bx-revision {
  font-size: 1.3021vw;
}

.bx-play:hover, .bx-pause:hover, .bx-revision:hover {
  cursor: pointer;
}

</style>