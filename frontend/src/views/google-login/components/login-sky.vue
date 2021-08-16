<template lang="">
  <div class="earth">
    <!-- sky -->
    <div class="sky">
      <div class="sky-left"></div>
    </div>
    <!-- <div class="sun"></div>
    <div class="moon"></div> -->

    <!-- moon -->
    <!-- <el-button class="startBtn" @click="show = !show">눌러</el-button> -->
    <i class="fas fa-moon"></i>
    <i class="fas fa-moon blur"></i>

    <i class="fas fa-sun"></i>
    <i class="fas fa-sun blur"></i>

    <!-- star -->
    <div class="star">
      <i class="fas fa-asterisk star1"></i>
      <i class="fas fa-asterisk star2"></i>
      <i class="fas fa-asterisk star3"></i>
      <i class="fas fa-asterisk star4"></i>
      <i class="fas fa-asterisk star5"></i>
    </div>

    <LoginLand />
    <!-- <div class="earth"></div> -->
  </div>
</template>
<script>
import LoginLand from "./login-land.vue";
export default {
  data() {
    return {
      show: false
    };
  },
  components: {
    LoginLand
  },
  methods: {
    chageDays() {
      console.log("이벤트실행");
      this.show = !this.show;
    }
  }
};
</script>
<style scoped>
.startBtn {
  width: 100px;
  height: 100px;
  background: #ff0;
  position: absolute;
}
.earth {
  overflow: hidden;
  /* position: absolute; */
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
    to left,
    #72b6dd 0%,
    #4d9dcc 20%,
    #ffa55c 40%,
    #523963 60%,
    #463963 80%,
    #201d25 100%
  );
  background-position: 100% 0;
  background-size: 1200% 100%;
  background-repeat: no-repeat;
  animation: change-sky-color 2s linear alternate;
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
    background-position: 0% 0;
  }
  100% {
    background-position: 100% 0;
  }
}

/* moon css & animation */
.fa-sun {
  position: absolute;
  top: 70%;
  left: 73%;
  font-size: 130px;
  color: #f7de66;
  /* transform-origin: 320px 50%; */
  animation: sun-rotation2 5s linear;
  animation-fill-mode: forwards;
}

/* moon css & animation */
.fa-moon {
  position: absolute;
  top: 30%;
  left: 13%;
  font-size: 130px;
  color: rgba(255, 255, 255, 0.8);
  /* transform-origin: 320px 50%; */
  animation: moon-rotation2 5s linear;
  animation-fill-mode: forwards;
}

.blur {
  filter: blur(10px);
  -webkit-filter: blur(10px);
}
@keyframes moon-rotation2 {
  0% {
    transform: translateY(0);
  }
  30% {
    transform: translateY(500px);
    opacity: 0;
  }
  100% {
    transform: translateY(500px);
    opacity: 0;
  }
}
@keyframes sun-rotation2 {
  0% {
    transform: translateY(0);
    opacity: 1;
  }
  70% {
    transform: translateY(-600px);
    opacity: 1;
  }
  100% {
    transform: translateY(-600px);
    opacity: 1;
  }
}
@keyframes moon-rotation {
  0%,
  100% {
    transform: rotate(10deg);
  }
  50% {
    transform: rotate(-10deg);
  }
}

/* star css & animation */
.fa-asterisk {
  position: absolute;
  color: rgba(255, 255, 255, 0.8);
  font-size: 13px;
}

.star1 {
  top: 70px;
  left: 90px;
  animation: star-one 1.2s infinite;
}

.star2 {
  top: 50px;
  right: 110px;
  animation: star-two 1.7s infinite;
}

.star3 {
  top: 180px;
  left: 45px;
  animation: star-three 1.5s infinite;
}

.star4 {
  top: 120px;
  right: 40px;
  font-size: 5px;
  animation: star-five 1.9s infinite;
}

.star5 {
  top: 200px;
  right: 90px;
  font-size: 5px;
  animation: star-five 2s infinite;
}

@keyframes star-one {
  0%,
  100% {
    transform: scale(0);
    opacity: 0;
  }
  50% {
    transform: scale(1);
    opacity: 1;
  }
}

@keyframes star-two {
  0%,
  100% {
    transform: scale(0);
    opacity: 0;
  }
  50% {
    transform: scale(1);
    opacity: 1;
  }
}

@keyframes star-three {
  0%,
  100% {
    transform: scale(0);
    opacity: 0;
  }
  50% {
    transform: scale(1);
    opacity: 1;
  }
}

@keyframes star-four {
  0%,
  100% {
    transform: scale(0);
    opacity: 0;
  }
  50% {
    transform: scale(1);
    opacity: 1;
  }
}

@keyframes star-five {
  0%,
  100% {
    transform: scale(0);
    opacity: 0;
  }
  50% {
    transform: scale(1);
    opacity: 1;
  }
}
</style>
