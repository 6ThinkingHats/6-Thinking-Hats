<template>
  <div class="icon-box">
    <button @click="changeMic"><i class='bx bxs-microphone-off mic'></i></button>
    <button @click="changeVideo"><i class='bx bxs-video-off video'></i></button>
    <button @click="shareScreen"><i class='bx bx-window-open'></i></button>
    <button><i class='bx bx-radio-circle-marked'></i></button>
    <button><i class='bx bxs-smile'></i></button>
    <button><i class='bx bx-dots-vertical-rounded'></i></button>
    <button><i class='bx bx-link-alt' ></i></button>
    <button class="pre-btn" v-if="isConferencing"><i class='bx bx-chevron-left'></i></button>
    <button class="next-btn" v-if="isConferencing"><i class='bx bx-chevron-right' ></i></button>
    <button class="pass-btn" v-if="isConferencing">차례 넘기기</button>
    <button class="end-btn" @click="changeConference()">
      <span v-if="!isConferencing">회의 시작</span>
      <span v-else-if="isConferencing">회의 종료</span>
      </button>
    <button @click="outToMain()"><i class='bx bxs-x-circle'></i></button>
  </div>
</template>

<script>
export default {
  name: 'IconBar',
  components: {
	},
  props: {
    isConferencing: Boolean,
  },
	data: () => {
		return {
      
		}
	},
	computed: {
	},
	methods: {
    changeConference() {
      this.$emit('changeConferenceStatus')
    },
    outToMain() {
      alert('회의에서 나가시겠습니까?')
      this.$emit('leaveRoom')
      this.$router.push({name: 'home'})
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
    }
	},
}
</script>

<style scoped>
  * {
  box-sizing: border-box;
}

.icon-box {
  display: flex;
  align-items: center;
  gap: 4px;
}

button {
  border: none;
  background-color: rgb(18, 18, 18);
  width: 40px;
  height: 40px;
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

.pre-btn {
  border-radius: 50%;
}

.next-btn {
  border-radius: 50%;
}

.pass-btn, .end-btn {
  width: 160px;
  height: 48px;
  border-radius: 36px;
  color: white;
}
</style>