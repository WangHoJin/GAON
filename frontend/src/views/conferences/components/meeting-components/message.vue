<template>
  <div style="text-align: left;" id="chat">
    <span id="msgs">{{ msg }}</span>
  </div>
</template>

<script>
export default {
  name: "Message",
  props: {
    msg: {
      type: String
    }
  }
};
</script>

<style scoped>
#msgs {
  font-size: 15px;
  margin: 2px;
  padding-left: 5px;
  padding-right: 5px;
}
#chat {
  background-color: rgb(243, 227, 3);
  margin: 10px;
  padding: 5px;
  border-radius: 15px;
  width: fit-content;
}
</style>
