<template>
  <div class="mode-setting-box">
    <p class="mode-setting-word">회의 모드 설정</p>

    <p class="hat-type-setting-word">모자 종류</p>
    <div class="hat-type-setting">
      <div v-if="isHost">
        <input type="radio" name="hat-type" id="sixhats" :checked="hatMode === 'sixhats'"
        @click="clickChangeHatMode('sixhats')" class="sixhats-mode-btn">
        <label for="sixhats" class="sixhats-label">
          <span v-if="hatMode === 'sixhats'" class="checked-hat-mode"></span>
          <img src="@/assets/sixhats_mode.png" alt="" class="sixhats-img host-img">
        </label>
        <input type="radio" name="hat-type" id="onehat" :checked="hatMode === 'onehat'"
        @click="clickChangeHatMode('onehat')" class="onehat-mode-btn">
        <label for="onehat" class="onehat-label">
          <span v-if="hatMode === 'onehat'" class="checked-hat-mode"></span>
          <img src="@/assets/onehat_mode.png" alt="" class="onehat-img host-img">
        </label>
      </div>

      <div class="users-hat-mode" v-else>
        <div>
          <span v-if="hatMode === 'sixhats'" class="checked-hat-mode"></span>
          <img src="@/assets/sixhats_mode.png" alt="" class="sixhats-img" :class="{'hat-mode-checked': hatMode === 'sixhats'}">
        </div>
        <div>
          <span v-if="hatMode === 'onehat'" class="checked-hat-mode"></span>
          <img src="@/assets/onehat_mode.png" alt="" class="onehat-img" :class="{'hat-mode-checked': hatMode === 'onehat'}">
        </div>
      </div>
    </div>

    <div class="idea-type-setting">
      <p class="idea-type-setting-word">회의 모드</p>
      <div v-if="isHost">
        <input type="radio" name="idea-type" id="suggest"
        @click="clickChangeIdeaMode('ideaSuggest')" 
        class="suggest-mode-btn"
        :checked="ideaMode === 'ideaSuggest'">
        <label for="suggest" class="host-suggest-label"
        :class="{'selected-option': ideaMode === 'ideaSuggest'}">아이디어 제안</label>
        <input type="radio" name="idea-type" id="judge"
        @click="clickChangeIdeaMode('ideaJudge')" 
        class="judge-mode-btn"
        :checked="ideaMode === 'ideaJudge'">
        <label for="judge" class="host-judge-label"
        :class="{'selected-option': ideaMode === 'ideaJudge'}">아이디어 검증</label>
      </div>

      <div v-else>
        <span :class="{'selected-option': ideaMode === 'ideaSuggest'}"
        class="suggest-label">아이디어 제안</span>
        <span :class="{'selected-option': ideaMode === 'ideaJudge'}"
        class="judge-label">아이디어 검증</span>
      </div>
    </div>
    
    <div class="timer-setting">
      <p class="timer-word">타이머</p>
      <div v-if="isHost">
        <select class="host-timer-select" name="timer" id="time-select" @change="timeSettingChange($event)">
          <option value="1" :selected="timeSetting === 1">1 minute</option>
          <option value="3" :selected="timeSetting === 3">3 minute</option>
          <option value="5" :selected="timeSetting === 5">5 minute</option>
          <option value="7" :selected="timeSetting === 7">7 minute</option>
          <option value="10" :selected="timeSetting === 10">10 minute</option>
        </select>
      </div>

      <div v-else>
        <span class="timer-select">{{ timeSetting }} minute</span>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'

export default {
  name: 'ModeSettingView',
  components: {
	},
	data: () => {
		return {
		}
	},
	computed: {
    ...mapGetters(['session', 'ideaMode', 'hatMode', 'baseTime', 'isHost',]),
    timeSetting() {
      return parseInt(this.baseTime / 60)
    }
	},
	methods: {
    ...mapActions(['changeIdeaMode', 'setTime', 'changeHatMode', 'changeSpeechOrder']),
    clickChangeIdeaMode(selected) {
      this.session.signal({
        data: selected,
        type: 'change-idea-mode'
      })
    },
    clickChangeHatMode(selected) {
      this.session.signal({
        data: selected,
        type: 'change-hat-mode'
      })
    },
    timeSettingChange(event) {
      this.session.signal({
        data: event.target.value,
        type: 'time-setting'
      })
    },
	},
  created() {
    this.session.on('signal:change-idea-mode', (event) => {
      this.changeIdeaMode(event.data)
      this.changeSpeechOrder(event.data)
    })

    this.session.on('signal:time-setting', (event) => {
      this.setTime(Number(event.data))
    })

    this.session.on('signal:change-hat-mode', (event) => {
      this.changeHatMode(event.data)
    })
  }
}
</script>

<style scoped>
.mode-setting-word {
  font-size: 20px;
  font-weight: bold;
}

.checked-hat-mode {
  position: absolute;
  width: 8.5938vw;
  height: 10.4167vw;
  border-radius: 0.6510vw;
  background-color: rgba(182, 182, 182, 0.2);
}

.idea-type-setting {
  margin-bottom: 12px;
}

.hat-type-setting-word, .idea-type-setting-word, .timer-word {
  font-size: 16px;
  font-weight: bold;
}

.selected-option {
  background-color: rgba(182, 182, 182, 0.2);
}

.sixhats-mode-btn, .onehat-mode-btn {
  display: none;
}

.hat-type-setting {
  margin-bottom: 12px;
}

.host-img:hover {
  cursor: pointer;
}

.sixhats-img, .onehat-img {
  border: 1px solid rgba(160, 160, 160, 0.5);
  width: 8.5938vw;
  height: 10.4167vw;
  border-radius: 0.6510vw;
  margin-right: 8px;
}

.users-hat-mode {
  display: flex;
}

.suggest-label, .host-suggest-label {
  margin-right: 8px;
}

.suggest-label, .judge-label, .host-suggest-label, .host-judge-label {
  font-size: 14px;
  padding: 4px;
  border: 1px solid rgba(160, 160, 160, 0.5);
  border-radius: 10px;
}

.suggest-mode-btn, .judge-mode-btn {
  display: none;
}

.host-suggest-label:hover, .host-judge-label:hover {
  cursor: pointer;
}

.host-timer-select, .timer-select {
  width: 92px;
  border: 1px solid rgba(160, 160, 160, 0.5);
  border-radius: 8px;
  padding-top: 4px;
  padding-bottom: 4px;
}

.timer-select {
  padding: 4px 16px;
  background-color: white;
  border-radius: 10px;
}

</style>