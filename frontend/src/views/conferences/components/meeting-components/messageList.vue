<template>
  <div id="app_chat_list" class="container">
    <Message v-for="(msg, index) in msgs" :key="index" :msg="msg" />
  </div>
  <!-- <div class="container">
    <Message v-for="(msg, index) in msgs" :key="index" :msg="msg" />
  </div> -->
</template>

<script>
import Message from "./message";
export default {
  name: "MessageList",
  components: {
    Message
  },
  props: {
    msgs: {
      type: Array
    }
  },
  watch: {
    msgs: function() {
      console.log("출력");
      var objDiv = document.getElementById("app_chat_list");
      objDiv.scrollTop = objDiv.scrollHeight + 10;
    }
  }
};
</script>

<style scoped>
.container {
  height: 500px;
  background: #b4b4b4;
  overflow: scroll;
  border-radius: 10px;
}
</style>
