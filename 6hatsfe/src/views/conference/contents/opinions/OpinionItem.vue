<template>
  <div v-if="opinion.category === 'opinion'" class="opinion-item-box" :class="opinion.hatColor">
    <!-- 의견일 때 -->
    <img v-if="opinion.hatColor === 'red-hat'" src="@/assets/redhat_circle.png" alt="" class="opinion-item-hatface">
    <img v-else-if="opinion.hatColor === 'yellow-hat'" src="@/assets/yellowhat_circle.png" alt="" class="opinion-item-hatface">
    <img v-else-if="opinion.hatColor === 'green-hat'" src="@/assets/greenhat_circle.png" alt="" class="opinion-item-hatface">
    <img v-else-if="opinion.hatColor === 'blue-hat'" src="@/assets/bluehat_circle.png" alt="" class="opinion-item-hatface">
    <img v-else-if="opinion.hatColor === 'black-hat'" src="@/assets/blackhat_circle.png" alt="" class="opinion-item-hatface">
    <img v-else-if="opinion.hatColor === 'white-hat'" src="@/assets/whitehat_border_circle.png" alt="" class="opinion-item-hatface">
    <div class="user-info">
      <p class="username">{{ opinion.userName }}</p>
      <p v-if="opinion.hatColor === 'red-hat'" class="hatname">빨간모자</p>
      <p v-else-if="opinion.hatColor === 'yellow-hat'" class="hatname">노란모자</p>
      <p v-else-if="opinion.hatColor === 'green-hat'" class="hatname">초록모자</p>
      <p v-else-if="opinion.hatColor === 'blue-hat'" class="hatname">파란모자</p>
      <p v-else-if="opinion.hatColor === 'white-hat'" class="hatname">하얀모자</p>
      <p v-else-if="opinion.hatColor === 'black-hat'" class="hatname">검은모자</p>
    </div>
    <span class="opinion-word">{{ opinion.content }}</span>
    <i class='bx bx-x delete-icon' v-if="myHat === 'blue-hat'"
    @click="clickDeleteOpinion(opinionIndex)"></i>
  </div>

  <!-- 주제일 때 -->
  <div class="subject-word" v-else-if="opinion.category === 'subject'">
    <span>회의 주제: </span><strong>{{ opinion.content }}</strong>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
export default {
  name: 'OpinionItem',
  components: {
	},
  props: {
    opinion: Object,
    opinionIndex: Number,
  },
	data: () => {
		return {
		}
	},
	computed: {
    ...mapGetters(['myHat', 'session',]),
	},
	methods: {
    clickDeleteOpinion(opIdx) {
      if (opIdx > -1) {
        this.session.signal({
          data: String(opIdx),
          type: 'delete-opinion',
        })
      }
    },
  }
}
</script>

<style scoped>
* {
  box-sizing: border-box;
}

p {
  margin: 0;
}

.subject-word {
  margin-left: auto;
  margin-right: auto;
}

.opinion-item-box {
  display: flex;
  align-items: center;
  width: 62.5000vw;
  gap: 8px;
  border-radius: 3.2552vw;
  padding: 2px;
}

.opinion-item-hatface {
  width: 2.6042vw;
  height: 2.6042vw;
  margin-left: 4px;
}

.user-info {
  margin-right: 8px;
}

.username, .hatname {
  font-size: 0.9115vw;
  min-width: 3.3854vw;
}

.opinion-word {
  padding: 4px;
}

.delete-icon {
  font-size: 24px;
  color: rgb(240, 63, 63);
  margin-left: auto;
  margin-right: 12px;
}

.delete-icon:hover {
  cursor: pointer;
}

.red-hat {
  background-color: #FFDCD9;
  color: black;
}

.yellow-hat {
  background-color: #FFF1CA;
  color: black;
}

.green-hat {
  background-color: #CFF3D9;
  color: black;
}

.blue-hat {
  background-color: #CEE0FF;
  color: black;
}

.white-hat {
  background-color: #FFFFFF;
  color: black;
}

.black-hat {
  background-color: #8E8E8E;
  color: black;
}

</style>