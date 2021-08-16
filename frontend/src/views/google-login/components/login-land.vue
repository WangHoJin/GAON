<template lang="">
  <div class="land">
    <!-- wave -->
    <div class="wave-one"></div>
    <!-- <div class="wave-two"></div> -->
    <!-- <div class="wave-three"></div> -->
  </div>
</template>
<script>
export default {};
</script>
<style>
.land {
  top: 60%;
  left: 0px;
  width: 100%;
  height: 40%;
  overflow: hidden;
  position: absolute;
}

.wave-one {
  width: 111%;
  height: 180%;
  position: absolute;
  top: 0%;
  left: -5%;
  border-radius: 49%;
  background: radial-gradient(#647e66, #647e66, #719168, #98c416);

  /* animation: move 3s infinite linear; */
}

.wave-two {
  width: 5000px;
  height: 5000px;
  position: absolute;
  top: 55%;
  left: -78%;
  border-radius: 49%;
  background: #6a8518;
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
</style>
