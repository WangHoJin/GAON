<template>
  <el-container class="main-wrapper">
    <el-contianer class="main-container">
      <el-main>
        메인페이지입니다
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

export default {
  name: "conference-main",
  mounted() {}
};
</script>
