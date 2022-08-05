<template>
  <div class="opinion-item-box">
    <img src="" alt="" class="opinion-item-hatface">
    <div class="user-info">
      <p class="username">권수린</p>
      <p class="hatname">초록모자</p>
    </div>
    <span class="opinion-word">라면</span>
  </div>
</template>

<script>
export default {
  name: 'OpinionItem',
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

p {
  margin: 0;
}

.opinion-item-box {
  display: flex;
  align-items: center;
  gap: 8px;
  background-color: #CFF3D9;
  border-radius: 50px;
  width: 1020px;
  height: 52px;
}

.opinion-item-hatface {
  width: 44px;
  height: 44px;
  margin-left: 4px;
}

.username {
  font-size: 14px;
}

.hatname {
  margin-right: 14px;
  font-size: 16px;
}
</style>