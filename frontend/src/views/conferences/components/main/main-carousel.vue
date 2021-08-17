<template lang="">
  <div class="c">
    <el-carousel :interval="10000" type="card">
      <Carousel v-for="(item, index) in 3" :key="index" />
    </el-carousel>
  </div>
</template>
<script>
import Carousel from "./components/carousel.vue";
export default {
  components: {
    Carousel
  }
};
</script>
<style scoped>
.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
}

.el-carousel__item {
  background-color: white;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: white;
}
.el-carousel {
  padding-top: 28%;
}
</style>
