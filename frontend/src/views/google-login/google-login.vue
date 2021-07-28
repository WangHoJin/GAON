<template>
  <div>
    <el-row>
      <el-col :span="8" :offset="8">
        <el-card :body-style="{ padding: '0px' }">
          <img :src="pic" class="googlelogin" />
          <div style="padding: 40px;">
            <h1>Google로 시작하기</h1>
            <div id="google-signin-btn"></div>
            <el-button class="button" @click="signOut">로그아웃</el-button>
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
    console.log("구글로그인들어왔름");
  },
  methods: {
    async onSignIn(googleUser) {
      const profile = googleUser.getBasicProfile();
      console.log("ID: " + profile.getId());
      console.log("Full Name: " + profile.getName());
      console.log("Given Name: " + profile.getGivenName());
      console.log("Family Name: " + profile.getFamilyName());
      console.log("Image URL: " + profile.getImageUrl());
      console.log("Email: " + profile.getEmail());

      const id_token = googleUser.getAuthResponse().id_token;
      const access_token = googleUser.getAuthResponse().access_token;
      console.log("access_token");
      console.log(access_token);
      // axios.post(" ", access_token).then(res => {
      //   console.log("access_token 전달 성공 ");
      // });

      console.log("ID Token: " + id_token);
    },
    signOut() {
      window.gapi.auth2.getAuthInstance().disconnect();
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
