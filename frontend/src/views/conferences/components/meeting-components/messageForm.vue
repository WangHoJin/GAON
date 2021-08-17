<template>
  <el-form @keyup.enter="submitForm">
    <el-row>
      <el-col :span="18">
        <el-input
          id="input_textarea"
          type="textarea"
          :rows="2"
          placeholder="채팅을 입력하세요."
          v-model="messageForm.message"
          style="width:100%"
        >
        </el-input>
      </el-col>
      <el-col :span="6">
        <el-button
          class="my-btn"
          type="primary"
          @click="submitForm"
          style="border-radius:0px 0px 10px 0px; width:100%"
          >입력
        </el-button>
      </el-col>
    </el-row>
  </el-form>
</template>

<script>
export default {
  data() {
    return {
      messageForm: {
        message: ""
      }
    };
  },

  props: {
    userName: String
  },

  methods: {
    submitForm(event) {
      const msg = this.messageForm.message.trim();
      if (msg != "") {
        event.preventDefault();
        // this.$emit("sendMsg", "[" + this.userName + "] : " + msg);
        var string = JSON.stringify({
          userName: this.userName,
          msg: msg
        });
        this.$emit("sendMsg", string);
      }
      this.messageForm.message = "";
    }
  }
};
</script>

<style scoped>
.my-btn {
  height: 100%;
}
</style>
