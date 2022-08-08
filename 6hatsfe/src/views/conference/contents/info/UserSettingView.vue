<template>
  <div class="user-setting-box">
    <p class="user-setting-word">회의 참가자 설정</p>
    <div class="hat-setting-box">
      <div class="hat-setting-row">
        <hat-select></hat-select>
        <hat-select></hat-select>
      </div>
      <div class="hat-setting-row">
        <hat-select></hat-select>
        <hat-select></hat-select>
      </div>
      <div class="hat-setting-row">
        <hat-select></hat-select>
        <hat-select></hat-select>
      </div>
      <div class="hat-setting-row">
        <hat-select></hat-select>
        <hat-select></hat-select>
      </div>
    </div>
  </div>
</template>

<script>
import HatSelect from '@/views/conference/contents/info/HatSelect.vue'

export default {
  name: 'UserSettingView',
  components: {
    HatSelect,
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

.user-setting-box, .hat-setting-box {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.hat-setting-row {
  display: flex;
  align-items: center;
}
</style>