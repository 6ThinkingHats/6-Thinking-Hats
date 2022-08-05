<template>
  <div class="user-modal-box">
    <div class="user-modal-header">
      <div class="header-content">
        <i class="user-icon"></i>
        <span class="header-word">방 참가자 목록</span>
      </div>
      <i class="close-icon"></i>
    </div>
    <div class="user-modal-body">
      <ul class="user-list">
        <li class="user-info">
          <div class="user-name-authority">
            <span class="user-name"></span>
            <i class="user-authority"></i>
          </div>
          <div class="user-info-icon">
            <i class="user-cam"></i>
            <i class="user-mic"></i>
            <div class="user-hat-status"></div>
            <i class="user-explusion"></i>
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  name: 'UserListModal',
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

<style>

</style>