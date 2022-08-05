<template>
  <div class="opinion-box">
    <div class="subject-box">
      <div class="timer-box">
        <i class="timer-icon"></i>
        <div class="time">
  
        </div>
      </div>
      <div class="subject-content">
  
      </div>
      <button class="subject-btn">수정</button>
    </div>
    <div class="opinion-content-box">
      <div class="opinion-contents">
        <opinion-item></opinion-item>
      </div>
      <div class="input-box">
        <input type="text" class="input-box-content">
        <i class="send-icon"></i>
      </div>
    </div>
  </div>
</template>

<script>
import OpinionItem from '@/views/conference/contents/opinions/OpinionItem.vue'

export default {
  name: 'OpinionBox',
  components: {
    OpinionItem,
  },
  data: () => {
		return {
		}
	},
	computed: {
	},
	methods: {
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
  border-radius: 14px;
  height: 52px;
  width: 1080px;
  margin-bottom: 12px;
  margin-left: auto;
  margin-right: auto;
}

.timer-box {
  display: flex;
  gap: 4px;
  margin-left: 8px;
}

.subject-btn {
  border: none;
  background-color: #4285F4;
  color: white;
  border-radius: 36px;
  width: 64px;
  height: 36px;
  margin-right: 8px;
}

.subject-btn:hover {
  cursor: pointer;
}

.opinion-content-box {
  display: flex;
  flex-direction: column;
  background-color: #F6F6F6;
  width: 1080px;
  height: 565px;
  border-radius: 14px;
  margin-left: auto;
  margin-right: auto;
}

.opinion-contents {
  display: flex;
  flex-direction: column;
}

.input-box {
  display: flex;
  width: 1020px;
  height: 48px;
  border: black solid 1px;
  border-radius: 14px;
  align-items: center;
  background-color: white;
}

.input-box-content {
  border: none;
  width: 980px;
  height: 36px;
  margin-left: 10px;
}

.input-box-content:focus {
  outline: none;
}
</style>