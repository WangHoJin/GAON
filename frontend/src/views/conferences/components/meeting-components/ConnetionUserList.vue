<template>
  <el-col :span="14">
    <h1>총 인원</h1>
    <div v-for="mem in memberlist" :key="mem.id">
      {{ mem.nickname }}
    </div>
  </el-col>
  <el-col :span="10">
    <h1>접속 인원</h1>
    <div>{{ clientData }}(나)</div>
    <ConnetionUser v-for="sub in subscribers" :key="sub.id" :subscriber="sub" />
  </el-col>
</template>

<script>
import ConnetionUser from "./ConnetionUser";

export default {
  name: "ConnetionUserLsit",

  components: {
    ConnetionUser
  },
  data() {
    return {
      memberlist: []
    };
  },
  async created() {
    this.memberlist = await this.$store.dispatch(
      "getMembersByUsingRoomId",
      this.$route.params.conferenceId
    );
  },
  props: {
    publisher: undefined,
    subscribers: []
  },

  computed: {
    clientData() {
      const { clientData } = this.getConnectionData();
      console.log("이름");
      console.log(clientData);
      return clientData;
    },
    idData() {
      const { idData } = this.getConnectionData();
      console.log("아이디");
      console.log(idData);
      return idData;
    }
  },

  methods: {
    getConnectionData() {
      console.log("접속자");
      console.log(this.publisher);
      const { connection } = this.publisher.stream;
      return JSON.parse(connection.data);
    }
  }
};
</script>
