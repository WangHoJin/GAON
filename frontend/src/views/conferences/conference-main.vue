<template>
  <el-container class="main-wrapper">
    <el-contianer class="main-container">
      <el-main style="padding: 0px 0px; text">
        <MainImage />
      </el-main>
    </el-contianer>
  </el-container>
</template>
<style>
@import "./conferences.css";
</style>
<script>
import { reactive, onMounted, onUnmounted } from "vue";
import { useStore } from "vuex";
import { useRoute } from "vue-router";
import MainImage from "./components/main/main-image.vue";

export default {
  name: "conference-main",
  components: {
    MainImage
  },
  mounted() {}
};
</script>
