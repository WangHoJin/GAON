<template>
  <el-scrollbar class="container">
    <Message v-for="(msg, index) in msgs" :key="index" :msg="msg" />
  </el-scrollbar>
  <!-- <div class="container">
    <Message v-for="(msg, index) in msgs" :key="index" :msg="msg" />
  </div> -->
</template>

<script>
import Message from './message'
export default {
  name: 'MessageList',
  components: {
    Message,
  },
  props: {
    msgs: {
      type: Array
    }
  }
}
</script>

<style scoped>
.container {
  height: 500px;
  background: rgb(223, 208, 81);
}
</style>
