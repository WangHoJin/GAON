<template>
  <el-form-item class="join-user align-middle">
    <el-row>
      <el-col :span="22">
        <span
          class="iconify"
          data-inline="false"
          data-icon="noto:cat-with-wry-smile"
          style="font-size: 30px; vertical-align:middle"
        ></span>
        | <span>{{ user.name }}</span> | <span>{{ user.email }}</span></el-col
      >
      <el-col :span="2">
        <el-button type="danger" size="mini" @click="deleteMember()"
          >X</el-button
        ></el-col
      >
    </el-row>
  </el-form-item>
</template>
<script>
export default {
  name: "members",
  props: {
    user: {
      type: Object
    }
  },
  methods: {
    //룸멤버 id로 룸멤버를 삭제
    deleteMember() {
      this.$store.dispatch("root/roomMemberDelete");
    }
  }
};
</script>
<style></style>
