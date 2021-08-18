<template>
  <div class="loginForm">
    <el-row :gutter="12">
      <el-col :span="24">
        <el-card shadow="always" class="loginCard">
          <div class="loginBody">
            <div class="loginImg"></div>
            <h3 style="color:#606060">Google로 시작하세요!</h3>
            <div id="google-signin-btn" class="customBtn"></div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import $axios from "axios";

export default {
  data() {
    return {};
  },
  async mounted() {
    await window.gapi.signin2.render("google-signin-btn", {
      onsuccess: this.onSignIn,
      width: 240,
      height: 50,
      longtitle: true,
      theme: "light"
    });
  },
  methods: {
    onSignIn(googleUser) {
      const profile = googleUser.getBasicProfile();
      var self = this;
      const id_token = googleUser.getAuthResponse().id_token;
      const access_token = googleUser.getAuthResponse().access_token;
      let token = id_token;

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
          self.$router.push("/");
        } else {
          alert("서버와 연결이 불안정합니다");
        }
      });
    }
  }
};
</script>
<style>
.loginCard {
  width: 350px;
  height: 420px;
  border: none;
  background-color: #ffe873;
  /* box-shadow: 1px 2px 3px #999; */
  /* background-color: #f3d92c; */
}
.loginBody {
  width: auto;
  height: 100%;
  text-align: center;
  padding: 30px 30px;
  /* padding: 50px; */
  background-color: #ffe873;
}
.loginImg {
  width: 200px;
  height: 200px;
  margin: 0 auto;
  background-image: url("school.png");
  background-size: cover;
}
.customBtn {
  width: 240px;
  margin: 0 auto;
}
.customBtn > div {
  display: inline-block;
  background: #ffffff;
  color: #444;
  width: 190px;
  border-radius: 5px;
  /* border: thin solid #888; */
  /* box-shadow: 1px 1px 1px grey; */
  white-space: nowrap;
}
.customBtn span {
  font-family: Roboto, arial, sans-serif !important;
  font-weight: bold;
  color: #606060;
}
.loginForm {
  opacity: 0;
  position: absolute;
  top: 0%;
  left: 40%;
  animation: login-rotation 1s linear;
  animation-fill-mode: forwards;
  animation-delay: 2s;
}
@keyframes login-rotation {
  0% {
    transform: translateY(-300px);
    opacity: 1;
  }
  30% {
    transform: translateY(200px);
    opacity: 1;
  }
  100% {
    transform: translateY(200px);
    opacity: 1;
  }
}
</style>
