<template>
  <div>
    <el-row>
      <el-col :span="8" :offset="8">
        <el-card :body-style="{ padding: '0px' }">
          <img :src="pic" class="googlelogin" />
          <div style="padding: 40px;">
            <h1>Google로 시작하기</h1>
            <div id="google-signin-btn"></div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import pic from "@/assets/images/sample-img.png";
import $axios from "axios";

export default {
  data() {
    return {
      pic: pic
    };
  },
  mounted() {
    window.gapi.signin2.render("google-signin-btn", {
      onsuccess: this.onSignIn,
      width: 240,
      height: 50,
      longtitle: true,
      theme: "dark"
    });
  },
  methods: {
    onSignIn(googleUser) {
      const profile = googleUser.getBasicProfile();
      var self = this;
      const id_token = googleUser.getAuthResponse().id_token;
      const access_token = googleUser.getAuthResponse().access_token;
      let token = id_token;

      // http post 요청으로 서버에 id토큰 유효성 검사
      // const CLIENT_ID =
      //   "890408784203-ko60b9fublcta8prgu5lll4ccpilqsoo.apps.googleusercontent.com";
      // const { OAuth2Client } = require("google-auth-library");
      // const client = new OAuth2Client(CLIENT_ID);
      // async function verify() {
      //   const ticket = await client.verifyIdToken({
      //     idToken: token,
      //     audience: CLIENT_ID
      //   });
      //   const payload = ticket.getPayload();
      //   const userid = payload["sub"];

      // 회원가입 + 로그인
      const userInfo = {
        nickname: profile.getName(),
        email: profile.getEmail()
      };
      //로그인 기능

      $axios.post("/users/glogin", userInfo).then(res => {
        if (res.status === 200) {
          sessionStorage.setItem(
            "userInfo",
            JSON.stringify({
              id: res.data.id,
              nickname: res.data.nickname,
              email: res.data.email,
              imgUrl: profile.getImageUrl()
            })
          );
          // var uinfo = JSON.parse(sessionStorage.getItem("userInfo"));
          // console.log(uinfo);
          // console.log(
          //   "uid:" +
          //     uinfo.id +
          //     " " +
          //     uinfo.nickname +
          //     "님 환영합니다 \n 당신의 이메일주소는" +
          //     uinfo.email
          // );
          //self.$store.commit("root/setLogin", true);
          self.$router.push("/");
        } else {
          alert("서버와 연결이 불안정합니다");
        }
      });
    }
    // verify().catch(console.error);
  }
};
</script>
<style scoped>
.googlelogin {
  /* height: 300px; */
  width: 100%;
}
</style>
