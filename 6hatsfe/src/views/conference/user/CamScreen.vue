<template>
  <div class="cam-box">
    <img src="" alt="" class="hat-img" v-if="isParticipate">
    <div class="cam-screen-box">
			<div v-if="streamManager">
      <ov-video class="cam-screen" :stream-manager="streamManager"/>
      <div><p>{{ clientData }}</p></div>
    </div>
    </div>
  </div>
</template>

<script>
import OvVideo from '@/views/conference/user/OvVideo.vue';

export default {
  name: 'CamScreen',

  components: {
    OvVideo,
  },
  data () {
    return {
      isParticipate: false
    }
  },
  props: {
		streamManager: Object,
	},
  computed: {
		clientData () {
			const { clientData } = this.getConnectionData();
			return clientData;
		},
	},
  methods: {
    getConnectionData () {
			const { connection } = this.streamManager.stream;
			return JSON.parse(connection.data);
		},
  },
}
</script>

<style scoped>
  .cam-screen {
    width: 200px;
    height: 120px;
    border-radius: 14px;
  }
</style>