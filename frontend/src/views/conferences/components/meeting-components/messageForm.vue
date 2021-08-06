<template>
  <el-form @keyup.enter="submitForm" label-width="100px">
    <el-row>
      <el-col :span="20">
        <el-input
        type="textarea"
        :rows="2"
        placeholder="채팅을 입력하세요."
        v-model="messageForm.message">
        </el-input>
      </el-col>
      <el-col :span="3">
        <el-button class="my-btn" type="primary" @click="submitForm">입력</el-button>
      </el-col>
    </el-row>
</el-form>
</template>

<script>
export default {
  data() {
    return {
      messageForm: {
        message: ''
      }
    }
  },
  methods: {
    submitForm(event) {
      event.preventDefault()
      this.$emit('sendMsg', this.messageForm.message)
      this.messageForm.message = ''
    }
  },
}
</script>

<style scoped>
.my-btn {
  height: 100%;
}
</style>
