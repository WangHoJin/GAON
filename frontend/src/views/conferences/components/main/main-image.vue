<template lang="">
  <div class="main-image">
    <MainText />
    <MainBanner />
  </div>
</template>
<script>
import MainBanner from "./main-banner.vue";
import MainText from "./main-text.vue";
import MainCarousel from "./main-carousel.vue";
export default {
  components: {
    MainBanner,
    MainText
  }
};
</script>
<style scoped>
.main-font {
  font-size: 50px;
  color: white;
}
.main-image {
  text-align: left;
  height: 100%;
  z-index: 1;

  background: linear-gradient(
      0deg,
      rgba(73, 73, 73, 0.637),
      rgba(77, 77, 77, 0.5)
    ),
    url("main-background-img.jpg");
  /* background-size: cover; */
  background-size: 100% 100%;
}
* {
  font-family: "jua";
}
</style>
