<template>
  <div class="chat-modal-box">
    <div class="chat-modal-header">
      <div class="header-content">
        <i class="header-content-icon"></i>
        <span class="header-content-word">채팅</span>
      </div>
      <i class="close-icon"></i>
    </div>
    <div class="chat-modal-body">
      <div class="body-content">
        <div class="body-content-header">
          <span class="user-name"></span>
          <span class="time"></span>
        </div>
        <p class="chat-word"></p>
      </div>
    </div>
    <div class="chat-modal-input-box">
      <input type="text">
      <button class="send-btn"><i></i></button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ChatModal',
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