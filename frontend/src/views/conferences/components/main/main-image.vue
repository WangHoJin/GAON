<template lang="">
  <div class="main-image">
    <MainBanner />
    <!-- <MainCarousel /> -->
  </div>
</template>
<script>
import MainBanner from "./main-banner.vue";
import MainCarousel from "./main-carousel.vue";
export default {
  components: {
    MainBanner
    // MainCarousel
  }
};
</script>
<style scoped>
.main-font {
  /* margin-top: 50px; */
  font-size: 50px;
  color: white;
}
.main-image {
  text-align: left;
  height: 100%;
  /* background: linear-gradient(#e67010, #f5df4d); */
  /* background-image: url("school.png"); */
  background-image: url("main-background-img.jpg");
  background-size: 100% 100%;
  background-repeat: no-repeat;
  z-index: 1;
}
</style>
