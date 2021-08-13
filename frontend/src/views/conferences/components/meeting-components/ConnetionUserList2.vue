<template>
  <el-col :span="14">
    <h1>총 인원</h1>
    <div v-for="mem in memberlist" :key="mem.id">
      {{ mem.nickname }}
    </div>
  </el-col>
  <el-col :span="10">
    <h1>접속 인원</h1>
    <div>{{ clientData }}(나),{{ checkUser }}</div>
    <ConnetionUser
      :memberlist="memberlist"
      v-for="sub in subscribers"
      :key="sub.id"
      :subscriber="sub"
    />
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
      memberlist: [],
      color: false
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
      //  중복사용자 체크
      console.log("중복 아디 체크");
      console.log("현재 나");
      console.log(this.publisher);
      console.log(this.publisher.stream.connection.data);
      const { clientData } = JSON.parse(this.publisher.stream.connection.data);
      const { idData } = JSON.parse(this.publisher.stream.connection.data);
      const nickname1 = clientData;
      const id1 = idData;
      console.log("접속자");
      this.subscribers.forEach(sub => {
        console.log(sub.stream.connection.data);
        // console.log(JSON.parse(sub.stream.connection.data));
        const { clientData } = JSON.parse(sub.stream.connection.data);
        const { idData } = JSON.parse(sub.stream.connection.data);
        console.log(nickname1);
        console.log(clientData);
        if (nickname1 == clientData && id1 == idData) {
          alert("같은 사용자가 존재합니다");
          this.$emit("leaveSession");
        }
      });
      // const { clientData } = this.getConnectionData();
      // console.log("이름");
      // console.log(clientData);
      return clientData;
    },
    idData() {
      const { idData } = this.getConnectionData();
      return idData;
    },
    checkUser() {
      const { idData } = this.getConnectionData();
      this.memberlist.forEach(mem => {
        console.log("id체크");
        console.log(mem.user_id);
        console.log(idData);
        if (mem.user_id == idData) {
          console.log("id가 똑같네");
          this.color = true;
          return this.color;
        }
      });
      console.log("왜나와..");
      return this.color;
    }
  },

  methods: {
    getConnectionData() {
      const { connection } = this.publisher.stream;
      return JSON.parse(connection.data);
    }
  }
};
</script>
