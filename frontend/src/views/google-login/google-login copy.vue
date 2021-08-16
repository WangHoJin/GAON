<template>
  <div>
    <!-- sky -->
    <div class="sky">
      <div class="sky-left"></div>
      <!-- <div class="sky-right"></div> -->
    </div>
    <div class="sun"></div>
    <div class="moon"></div>
    <div class="earth"></div>

    <!-- wave -->
    <div class="wave-one"></div>
    <div class="wave-two"></div>
    <div class="wave-three"></div>

    <section class="container">
      <h1 class="title">
        <span>GAON</span>
        <span>Start</span>
        <span>Now</span>
        <div id="google-signin-btn"></div>
      </h1>
    </section>
    <!-- <button class="GAON"></button> -->
    <!-- <el-row class="loginrow">
      <el-col :span="8" :offset="8">
        <el-card :body-style="{ padding: '0px' }">
          <img :src="pic" class="googlelogin" />
          <div style="padding: 40px;">
            <h1>Google로 시작하기</h1>
            <div id="google-signin-btn"></div>
          </div>
        </el-card>
      </el-col>
    </el-row> -->
  </div>
</template>
<script>
import pic from "@/assets/images/sample-img.png";
import $axios from "axios";
import "./sky.css";

export default {
  data() {
    return {
      pic: pic
    };
  },
  async mounted() {
    await window.gapi.signin2.render("google-signin-btn", {
      onsuccess: this.onSignIn,
      width: 240,
      height: 50,
      longtitle: true,
      theme: "light"
    });

    // gapi.load("auth2", function() {
    //   console.log("init실행됐니?");
    //   gapi.auth2.init();
    //   var auth2 = gapi.auth2.getAuthInstance();
    //   console.log(auth2);
    //   var isGoogleLogin = auth2.isSignedIn.get();
    //   console.log("isGoogleLogin");
    //   console.log(isGoogleLogin);
    //   console.log("init실행됐니? 끝");
    // });

    // console.log("auth2: " + auth2);
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
@import "https://fonts.googleapis.com/css?family=Baloo+Paaji";
#google-signin-btn {
  height: 100px;
  width: 100%;
  /* background-image: url("GAON.png"); */
}
#main {
  height: 100%;
  width: 100%;
  font-family: "Baloo Paaji", cursive;
  /* background: #f5df4d; */
  background: #f5df4d;
  display: flex;
  justify-content: center;
  align-items: center;
  /* background-image: url("246.jpg"); */
  /* background-size: cover; */
  /* padding-top: 400px; */
}
.container {
  width: 400px;
  height: 220px;
  position: relative;
}
h1 {
  top: -55%;
  font-size: 75px;
  position: absolute;
  text-transform: uppercase;
}
h1 span {
  width: 100%;
  float: left;
  color: #ffffff;
  -webkit-clip-path: polygon(100% 0, 100% 100%, 0 100%, 0 80%);
  clip-path: polygon(100% 0, 100% 100%, 0 100%, 0 80%);
  transform: translateY(-50px);
  opacity: 0;
  animation-name: titleAnimation;
  animation-timing-function: ease;
  animation-duration: 2s;
}
h1 #google-signin-btn {
  opacity: 0;
  animation-name: buttonAnimation;
  animation-timing-function: ease;
  animation-duration: 3s;
}
h1 span {
  animation-delay: 0.6s;
  -webkit-animation-fill-mode: forwards;
}
h1 span:first-child {
  animation-delay: 0.7s;
}
h1 span:last-child {
  color: #ffffff;
  animation-delay: 0.5s;
}
h1 #google-signin-btn {
  animation-delay: 0.6s;
  -webkit-animation-fill-mode: forwards;
}

.usechrome {
  font-size: 10px;
  color: #ffff;
  font-family: helvetica, arial;
  position: absolute;
  bottom: 20px;
  width: 100%;
  text-align: center;
  left: 0;
}

@keyframes titleAnimation {
  0% {
    transform: translateY(-50px);
    opacity: 0;
    -webkit-clip-path: polygon(100% 0, 100% 100%, 0 100%, 0 80%);
    clip-path: polygon(100% 0, 100% 100%, 0 100%, 0 80%);
  }
  20% {
    transform: translateY(0);
    opacity: 1;
    -webkit-clip-path: polygon(100% 0, 100% 100%, 0 100%, 0 15%);
    clip-path: polygon(100% 0, 100% 100%, 0 100%, 0 15%);
  }
  80% {
    transform: translateY(0);
    opacity: 1;
    -webkit-clip-path: polygon(100% 0, 100% 100%, 0 100%, 0 15%);
    clip-path: polygon(100% 0, 100% 100%, 0 100%, 0 15%);
  }
  100% {
    transform: translateY(50px);
    opacity: 0;
    -webkit-clip-path: polygon(100% 0, 100% 0%, 0 100%, 0 100%);
    clip-path: polygon(100% 0, 100% 0%, 0 100%, 0 100%);
  }
}
@keyframes buttonAnimation {
  0% {
    transform: translateY(-50px);
    opacity: 0;
    clip-path: circle(40%);
  }
  20% {
    transform: translateY(0);
    opacity: 0;
    clip-path: circle(140%);
  }
  80% {
    transform: translateY(-50px);
    opacity: 0;
    clip-path: circle(140%);
  }
  100% {
    transform: translateY(0);
    opacity: 1;
    clip-path: circle(140%);
  }
}
.wave-one {
  width: 3100px;
  height: 3000px;
  position: absolute;
  top: 30%;
  left: -30%;
  border-radius: 50%;
  background: #f6e46c;
  animation: move 3s infinite linear;
}

.wave-two {
  width: 3100px;
  height: 3000px;
  position: absolute;
  top: 40%;
  left: -28%;
  border-radius: 49%;
  background: #f3d82b;
  animation: move 5s infinite linear;
}

.wave-three {
  width: 3100px;
  height: 3000px;
  position: absolute;
  top: 50%;
  left: -27%;
  border-radius: 49%;
  background: #f2c120;
  /* border: 6px solid rgba(131, 119, 152, 0.7); */
  animation: move 7s infinite linear;
}
@keyframes move {
  100% {
    transform: rotate(360deg);
  }
}
.earth {
  width: 100%;
  height: 520px;
  bottom: 0;
  left: calc(0%);
  background: #f5df4d;
  position: absolute;
}

.sun {
  width: 40px;
  height: 40px;
  background: #ff0;
  background: radial-gradient(
    ellipse at center,
    #eeee8c 0%,
    #fff491 60%,
    #ffb95e 100%
  );
  border-radius: 100%;
  top: calc(50% - 20px);
  left: calc(50% - 320px);
  position: absolute;
  transform-origin: 320px 50%;
  animation: sun-path 40s reverse infinite linear,
    sun-effect 2s alternate infinite;
}

.moon {
  background: radial-gradient(ellipse at center, #fff 0%, #eec 60%, #ffe 100%);
  border-radius: 100%;
  width: 40px;
  height: 40px;
  position: absolute;
  border-radius: 100%;
  top: calc(50% - 20px);
  left: calc(50% - 320px);
  transform-origin: 320px 50%;
  animation: moon-path 40s reverse infinite linear,
    moon-effect 2s alternate infinite;
  opacity: 0.9;
}

.sky {
  width: 100%;
  height: 100%;
}

.sky-left {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-image: linear-gradient(
    to right,
    #4d9dcc 0%,
    #72b6dd 17%,
    #ffa55c 33%,
    #ec2636 50%,
    #523963 67%,
    #463963 83%,
    #201d25 100%
  );
  background-position: 100% 0;
  background-size: 1200% 100%;
  background-repeat: no-repeat;
  animation: change-sky-color 20s infinite linear alternate;
}

.sky-right {
  position: absolute;
  top: 0;
  left: 50%;
  width: 50%;
  height: 100%;
  background-image: linear-gradient(
    to right,
    #4d9dcc 0%,
    #72b6dd 17%,
    #ffa55c 33%,
    rgba(236, 38, 54, 0.82) 50%,
    #523963 67%,
    #463963 83%,
    #201d25 100%
  );
  background-position: 100% 0;
  background-size: 1200% 100%;
  background-repeat: no-repeat;
  animation: change-sky-color 20s infinite linear alternate;
}

@keyframes sun-path {
  from {
    transform: rotate(0turn);
  }
  to {
    transform: rotate(1turn);
  }
}
@keyframes sun-effect {
  from {
    box-shadow: 0 0 10px #fff5c2, 0 0 50px #fff5c2, 0 0 75px #f7de66,
      0 0 0 20px rgba(255, 231, 164, 0.1), 0 0 0 40px rgba(255, 240, 163, 0.07),
      0 0 0 70px rgba(247, 223, 102, 0.03);
  }
  to {
    box-shadow: 0 0 10px #fff5c2, 0 0 50px #fff5c2, 0 0 75px #f7de66,
      0 0 8px 30px rgba(255, 249, 232, 0.08),
      0 0 10px 70px rgba(255, 247, 210, 0.05),
      0 0 20px 100px rgba(255, 248, 214, 0.03);
  }
}
@keyframes moon-path {
  from {
    transform: rotate(0.5turn);
  }
  to {
    transform: rotate(1.5turn);
  }
}
@keyframes moon-effect {
  from {
    box-shadow: 0 0 50px #eee, 0 0 100px #eee, 0 0 150px #eee;
  }
  to {
    box-shadow: 0 0 50px #eee, 0 0 75px #eee;
  }
}
@keyframes change-sky-color {
  0% {
    background-position: 100% 0;
  }
  100% {
    background-position: 0% 0;
  }
}
</style>
