<template>
  <h1>참여 목록</h1>
  <el-row>
    <Members v-for="(user, index) in users" :key="index" :user="user" />
  </el-row>
</template>
<script>
import Members from "./members.vue";
export default {
  name: "join-member",
  components: {
    Members
  },
  data() {
    return {
      //나중에 Gatters로 가져와야할듯
      users: [
        {
          name: "김채연",
          email: "tomybaru@maver.com"
        },
        {
          name: "김민정",
          email: "co323co@gmail.com"
        },
        {
          name: "오수형",
          email: "tngud128@gmail.com"
        },
        {
          name: "왕호진",
          email: "kkgmco0825@gmail.com"
        },
        {
          name: "정대진",
          email: "12jund@gmail.com"
        }
      ]
    };
  }
};
</script>
<style scoped>
.join-user {
  border-radius: 4px;
  background: #e5e9f2;
  margin: 5px 5px;
  min-width: -webkit-fill-available;
}
</style>
