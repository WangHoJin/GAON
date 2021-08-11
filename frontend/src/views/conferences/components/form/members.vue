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
        | <span>{{ member.nickname }}</span> |
        <span>{{ member.email }}</span></el-col
      >
      <el-col :span="2">
        <el-button
          type="danger"
          size="mini"
          @click="deleteMember(member.user_id)"
          >X</el-button
        ></el-col
      >
    </el-row>
  </el-form-item>
</template>
<script>
// API
import $axios from "axios";
export default {
  name: "members",
  props: {
    member: {
      type: Object
    },
    rid: {
      type: String
    }
  },
  methods: {
    //룸멤버 id로 룸멤버를 삭제
    async deleteMember(user_id) {
      await $axios
        .delete("/room-member/" + this.rid + "/" + user_id)
        .then(res => {
          console.log("response from roomMemberDelete");
          console.log(res.data);
        })
        .catch(err => {
          console.log(err);
        });
      // 삭제 후 부모컴포넌트의 이벤트 실행해서 참여중인 멤버 갱신
      this.$emit("updateJoinedMember");
    }
  }
};
</script>
<style></style>
