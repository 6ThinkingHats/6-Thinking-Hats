<template>
  <div class="speech-order-box">
    <div class="white-hat order"></div>
    <div class="red-hat order"></div>
    <div class="yellow-hat order"></div>
    <div class="black-hat order"></div>
    <div class="green-hat cnt-order"></div>
    <div class="blue-hat order"></div>
  </div>
</template>

<script>
export default {
  name: 'SpeechOrder',
  components: {
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

.speech-order-box {
  display: flex;
  align-items: center;
  gap: 12px;
}

.order {
  width: 24px;
  height: 24px;
  border-radius: 50%;
}

.cnt-order {
  width: 60px;
  height: 60px;
  border-radius: 50%;
}

.white-hat {
  background-color: white;
}

.red-hat {
  background-color: #EA4335;
}

.yellow-hat {
  background-color: #FBBC05;
}

.black-hat {
  background-color: black;
}

.green-hat {
  background-color: #34A853;
}

.blue-hat {
  background-color: #4285F4;
}
</style>