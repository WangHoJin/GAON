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
// import { ref } from "vue";
import pic from "@/assets/images/sample-img.png";
import axios from "../../common/lib/axios";
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

      console.log("ID: " + profile.getId());
      console.log("Full Name: " + profile.getName());
      console.log("Given Name: " + profile.getGivenName());
      console.log("Family Name: " + profile.getFamilyName());
      console.log("Image URL: " + profile.getImageUrl());
      console.log("Email: " + profile.getEmail());

      const id_token = googleUser.getAuthResponse().id_token;
      const access_token = googleUser.getAuthResponse().access_token;
      let token = id_token;
      // console.log("access_token");
      // console.log(access_token);

      // http post 요청으로 서버에 id토큰 유효성 검사
      const CLIENT_ID =
        "890408784203-ko60b9fublcta8prgu5lll4ccpilqsoo.apps.googleusercontent.com";
      const { OAuth2Client } = require("google-auth-library");
      const client = new OAuth2Client(CLIENT_ID);
      async function verify() {
        const ticket = await client.verifyIdToken({
          idToken: token,
          audience: CLIENT_ID
        });
        const payload = ticket.getPayload();
        const userid = payload["sub"];

        // console.log("Google API 클라이언트 라이브러리 사용으로 받아온 값");
        // console.log(payload);
        // console.log(userid);
        //내정보가있는지

        // 회원가입 + 로그인
        const userInfo = {
          nickname: profile.getName(),
          email: profile.getEmail()
        };
        //로그인 기능
        var email = "";
        var nickname = "";

        $axios.post("/auth/glogin", userInfo).then(res => {
          // const store = use Store();
          // store.commit("root/setUserInfo", res);
          console.log("구글 계정 정보, 요청 전달 성공 ");
          console.log("백엔드로부터 전달 받은 response :" + res);
          console.log("ResponseEntity/data/guserinfo : " + res.data.guserinfo);
          email = res.data.guserinfo.email;
          nickname = res.data.guserinfo.nickname;
          console.log(
            nickname + "님 환영합니다 \n 당신의 이메일주소는" + email
          );
        });

        sessionStorage.setItem(
          "userInfo",
          JSON.stringify({
            nickname: profile.getName(),
            email: profile.getEmail(),
            imgUrl: profile.getImageUrl()
          })
        );
      }
      verify().catch(console.error);
      this.$store.commit("root/setLogin", true);
    }
  }
};
</script>
<style scoped>
.googlelogin {
  /* height: 300px; */
  width: 100%;
}
</style>
