<template>
  <div class="screen-share-modal-box">
    <div class="screen-share-window">
      <p 
      class="no-screen-share-word">
        화면 공유 안 하고 있음
      </p>
      <ov-video 
      class="cam-screen" :stream-manager="screenShare"
      v-if="screenShare"/>
      <ov-video 
      class="cam-screen" :stream-manager="screenSub"
      v-if="screenSub"/>
    </div>
    <button class="screen-share-modal-close-btn"
    @click="closeScreenShareModal">
      <i class='bx bxs-caret-up-circle'></i>
    </button>
  </div>
</template>

<script>
import OvVideo from '@/views/conference/user/OvVideo.vue';

export default {
  name: 'ScreenShareModal',
  components: {
    OvVideo,
  },
  data: () => {
    return {
      
      }
  },
  props: {
    screenShare: Object,
    screenSub: Object
  },
  computed: {},
  methods: {
    closeScreenShareModal() {
      this.$emit('closeScreenShareModal')
    }
  }
}
</script>

<style scoped>
  .screen-share-modal-close-btn {
    border: none;
  }

  .screen-share-modal-close-btn:hover {
    cursor: pointer;
  }

</style>