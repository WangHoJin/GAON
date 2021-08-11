<template>
  <h1>참여 목록</h1>
  <el-row>
    <Members
      v-for="(member, index) in members"
      :key="index"
      :member="member"
      :rid="rid"
      @updateJoinedMember="updateJoinedMember"
    />
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
    rid: {
      type: String
    }
  },
  data() {
    return {
      members: [] // 해당 방에 참가한 유저들 목록
    };
  },
  async mounted() {
    // 마운트 될때 방의 멤버들을 다 받아온다.
    console.log("when mounted ");
    console.log(this.rid);
    this.members = await this.$store.dispatch(
      "getMembersByUsingRoomId",
      this.rid
    );
    console.log("response from actions getMembersByUsingRoomId");
    console.log(this.members);
  },
  methods: {
    async updateJoinedMember() {
      console.log("부모의 updateJoinedMember 실행됨.");
      this.members = await this.$store.dispatch(
        "getMembersByUsingRoomId",
        this.rid
      );
      console.log("response from actions getMembersByUsingRoomId");
      console.log(this.members);
    }
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
