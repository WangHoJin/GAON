<template lang="">
  <div class="main-text">
    <h1>
      <span>W</span><span>E</span><span>L</span><span>C</span><span>O</span
      ><span></span><span>M</span><span>E</span> <span>T</span><span>O</span
      ><br />
      <span>G</span><span>A</span><span>O</span><span>N</span>
    </h1>
    <!-- <h1 class="text-focus-in">Welcome to GAON</h1>
    <h1 class="focus-in-contract-bck">Welcome to GAON</h1>
    <h1 class="scale-in-center">Welcome to GAON</h1> -->
  </div>
</template>
<script>
export default {};
</script>
<style scoped>
.main-text {
  width: 100%;
  text-align: center;
  /* position: absolute; */
  font-size: 50px;
  color: aliceblue;
  /* height: 100%; */
}

/* 귀염 */

.main-text h1 {
  padding-top: 2%;
  margin: 0%;
  font-size: 5em;
  font: bold 7.5vw/1.6 "Signika", sans-serif;
  user-select: none;
  line-height: 1.3;
}

.main-text h1 span {
  height: 100%;
  display: inline-block;
  animation: float 0.2s ease-in-out infinite;
}
@keyframes float {
  0%,
  100% {
    transform: none;
  }
  33% {
    transform: translateY(-1px) rotate(-2deg);
  }
  66% {
    transform: translateY(1px) rotate(2deg);
  }
}
h1:hover span {
  animation: bounce 0.6s;
}
@keyframes bounce {
  0%,
  100% {
    transform: translate(0);
  }
  25% {
    transform: rotateX(20deg) translateY(2px) rotate(-3deg);
  }
  50% {
    transform: translateY(-20px) rotate(3deg) scale(1.1);
  }
}

span:nth-child(4n) {
  color: hsl(50, 75%, 55%);
  text-shadow: 1px 1px hsl(50, 75%, 45%), 2px 2px hsl(50, 45%, 45%),
    3px 3px hsl(50, 45%, 45%), 4px 4px hsl(50, 75%, 45%);
}
span:nth-child(4n-1) {
  color: hsl(135, 35%, 55%);
  text-shadow: 1px 1px hsl(135, 35%, 45%), 2px 2px hsl(135, 35%, 45%),
    3px 3px hsl(135, 35%, 45%), 4px 4px hsl(135, 35%, 45%);
}
span:nth-child(4n-2) {
  color: hsl(155, 35%, 60%);
  text-shadow: 1px 1px hsl(155, 25%, 50%), 2px 2px hsl(155, 25%, 50%),
    3px 3px hsl(155, 25%, 50%), 4px 4px hsl(140, 25%, 50%);
}
span:nth-child(4n-3) {
  color: hsl(30, 65%, 60%);
  text-shadow: 1px 1px hsl(30, 45%, 50%), 2px 2px hsl(30, 45%, 50%),
    3px 3px hsl(30, 45%, 50%), 4px 4px hsl(30, 45%, 50%);
}

h1 span:nth-child(2) {
  animation-delay: 0.05s;
}
h1 span:nth-child(3) {
  animation-delay: 0.1s;
}
h1 span:nth-child(4) {
  animation-delay: 0.15s;
}
h1 span:nth-child(5) {
  animation-delay: 0.2s;
}
h1 span:nth-child(6) {
  animation-delay: 0.25s;
}
h1 span:nth-child(7) {
  animation-delay: 0.3s;
}
h1 span:nth-child(8) {
  animation-delay: 0.35s;
}
h1 span:nth-child(9) {
  animation-delay: 0.4s;
}
h1 span:nth-child(10) {
  animation-delay: 0.45s;
}
h1 span:nth-child(11) {
  animation-delay: 0.5s;
}
h1 span:nth-child(12) {
  animation-delay: 0.55s;
}
h1 span:nth-child(13) {
  animation-delay: 0.6s;
}
h1 span:nth-child(14) {
  animation-delay: 0.65s;
}
h1 span:nth-child(15) {
  animation-delay: 0.7s;
}
</style>
