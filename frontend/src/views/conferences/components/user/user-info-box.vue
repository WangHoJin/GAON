<template>
  <!-- <el-card :body-style="{ padding: '0px' }">
    <img :src="`${img}`" class="image" />
    <div style="padding: 14px;">
      <el>{{ username }}</el>
      <div class="bottom">
        <el-button class="button" @click="signOut">로그아웃</el-button>
      </div>
    </div>
  </el-card> -->

  <el-card>
    <el-button class="button" @click="signOut">로그아웃</el-button>
    <div>
      {{ username }}
    </div>
    <img :src="`${img}`" style="width : 30px; border-radius: 70%" />
  </el-card>
</template>
<script>
export default {
  name: "user-info-box",
  data() {
    if (sessionStorage.getItem("userInfo") != null) {
      return {
        username: JSON.parse(sessionStorage.getItem("userInfo")).nickname,
        img: JSON.parse(sessionStorage.getItem("userInfo")).imgUrl,
        uid: JSON.parse(sessionStorage.getItem("userInfo")).id,
        email: JSON.parse(sessionStorage.getItem("userInfo")).email
      };
    }
  },
  methods: {
    async signOut() {
      // console.log("로그아웃버튼누름");
      // await window.gapi.auth2.getAuthInstance().disconnect();
      // console.log("user Signed Out");
      // sessionStorage.removeItem("userInfo");
      // this.$store.commit("root/setLogin", false);
      // this.$router.push("/");
      try {
        console.log("try disconnect");
        await window.gapi.auth2.getAuthInstance().signOut();
        sessionStorage.removeItem("userInfo");
        this.$store.commit("root/setLogin", false);
        this.$router.push("/");
      } catch (error) {
        console.log("****disconnect 실패 : " + error);
        console.log("fn --- user Signed Out");
        sessionStorage.removeItem("userInfo");
        this.$store.commit("root/setLogin", false);
        this.$router.push("/");
      }
    }
  }
};
</script>
<style scoped>
.image {
  width: 100%;
  display: block;
}
</style>
