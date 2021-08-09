<template>
  <h1>참여 목록</h1>
  <el-row>
    <Members v-for="(member, index) in members" :key="index" :member="member" />
  </el-row>
</template>
<script>
import Members from "./members.vue";
export default {
  name: "join-member",
  components: {
    Members
  },
  props: {
    members: Array
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
