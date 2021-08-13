<template>
  <video v-if="mainStream" class="main" autoplay />
  <video v-else autoplay />
</template>

<script>
export default {
  name: "OvVideo",

  props: {
    streamManager: Object,
    mainStream: Boolean
  },
  ////
  mounted() {
    this.streamManager.addVideoElement(this.$el);
  }
};
</script>
<style scoped>
.main {
  width: 960px;
  height: 600px;
}
</style>
