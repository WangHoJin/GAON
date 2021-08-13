<template>
  <div v-if="streamManager">
    <ov-video :stream-manager="streamManager" />
    <div>
      <p>{{ clientData }}</p>
    </div>
  </div>
</template>

<script>
import OvVideo from "./OvVideo";

export default {
  name: "UserVideo",

  components: {
    OvVideo
  },

  props: {
    streamManager: Object
  },

  computed: {
    clientData() {
      const { clientData } = this.getConnectionData();
      return clientData;
    }
  },

  methods: {
    getConnectionData() {
      const { connection } = this.streamManager.stream;
      console.log("메인 비디오로 변경!!");
      console.log(connection);
      return JSON.parse(connection.data);
    }
  }
};
</script>
