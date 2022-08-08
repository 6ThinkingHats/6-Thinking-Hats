<template>
  <div class="hat-select-box">
    <img src="" alt="" class="hat-img">
    <div class="user-list">
      <user-list-item></user-list-item>
    </div>
  </div>
</template>

<script>
import UserListItem from '@/views/conference/contents/info/UserListItem.vue'

export default {
  name: 'HatSelect',
  components: {
    UserListItem,
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

.hat-select-box {
  display: flex;
  align-items: center;
  gap: 28px;
}

.hat-img {
  width: 52px;
  height: 52px;
}

.user-list {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 200px;
  height: 40px;
  background-color: #FFDCD9;
  border-radius: 14px;
}
</style>