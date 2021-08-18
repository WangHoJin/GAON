<template>
  <div class="loginText">
    <h1 class="title">
      <span class="firstText">GAON</span>
      <!-- <span class="secondText">START</span>
      <span class="thirdText">NOW</span> -->
      <span class="second one">Gather</span>
      <span class="second two">As</span>
      <span class="second three">One</span>
      <span class="second four">Network</span>
    </h1>
  </div>
</template>
<script>
export default {};
</script>
<style>
.loginText {
  top: 0%;
  left: 10%;
  font-size: 45px;
  position: absolute;
  /* text-transform: uppercase; */
}
h1 span {
  width: 100%;
  float: left;
  color: #ffffff;
  -webkit-clip-path: polygon(100% 0, 100% 100%, 0 100%, 0 80%);
  clip-path: polygon(100% 0, 100% 100%, 0 100%, 0 80%);
  transform: translateY(-50px);
  opacity: 0;
  /* animation-name: titleAnimation;
  animation-timing-function: ease; */
  animation-duration: 2s;
}
.firstText {
  color: #f5df4d;
  opacity: 1;
  clip-path: none;
  transform: translateY(0);
  /* animation-name: titleAnimation2; */
  /* animation-fill-mode: forwards; */
}
.second {
  font-size: 60px;
  left: 0%;
  position: absolute;
  animation-name: titleAnimation2;
  animation-fill-mode: forwards;
  animation-timing-function: ease;
  animation-duration: 2s;
}
.second::first-letter {
  font-size: 70px;
  color: #f5df4d;
}
.one {
  top: 91%;
  left: 3%;
  animation-delay: 1.3s;
}
.two {
  top: 130%;
  left: 27.8%;
  animation-delay: 1.4s;
}
.three {
  top: 169%;
  left: 52.6%;
  animation-delay: 1.5s;
}
.four {
  width: 250px;
  top: 208%;
  left: 79.4%;
  animation-delay: 1.6s;
}

h1 span {
  animation-delay: 1.3s;
}
.secondText {
  animation-delay: 1.4s;
}
.thirdText {
  color: #ffffff;
  animation-delay: 1.5s;
}

@keyframes titleAnimation2 {
  0% {
    transform: translateY(-50px);
    opacity: 1;
    clip-path: polygon(100% 0, 100% 100%, 0 100%, 0 80%);
  }
  20% {
    transform: translateY(0);
    opacity: 1;
    -webkit-clip-path: polygon(100% 0, 100% 100%, 0 100%, 0 15%);
    clip-path: polygon(100% 0, 100% 100%, 0 100%, 0 10%);
  }
  80% {
    transform: translateY(0);
    opacity: 1;
    -webkit-clip-path: polygon(100% 0, 100% 100%, 0 100%, 0 15%);
    clip-path: polygon(100% 0, 100% 100%, 0 100%, 0 0%);
  }
  100% {
    transform: translateY(0);
    opacity: 1;
    clip-path: polygon(100% 0, 100% 100%, 0 100%, 0 0%);
    /* clip-path: polygon(100% 0, 100% 0%, 0 100%, 0 100%); */
  }
}
@keyframes titleAnimation {
  0% {
    transform: translateY(-50px);
    opacity: 0;
    show: false;
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
</style>
