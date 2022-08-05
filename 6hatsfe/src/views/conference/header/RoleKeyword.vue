<template>
  <div class="role-keyword-box">
    <img src="" alt="" class="role-keyword-face">
    <div class="role-keyword-list">
      <p class="role-keyword">창의적</p>
      <p class="role-keyword">혁신적</p>
      <p class="role-keyword">확산적</p>
    </div>
  </div>
</template>

<script>
export default {
  name: 'RoleKeyword',
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

.role-keyword-box {
  width: 132px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.role-keyword-face {
  width: 72px;
  height: 72px;
}

.role-keyword {
  font-size: 14px;
  margin: 0;
  padding: 2px;
  color: black;
}
</style>