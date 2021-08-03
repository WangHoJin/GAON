<template>
  <el-row align="center">
    <el-col :span="2"> <conference-sidevar /></el-col>
    <el-col :span="22">
      <el-main>
        {{ $route.params.conferenceId + "번 방 상세 보기 페이지" }}
        <router-view></router-view> </el-main
    ></el-col>
  </el-row>
</template>
<style>
@import "./conferences.css";
</style>
<script>
import ConferenceSidevar from "../conferences/components/conference-sidebar.vue";
import { reactive, onMounted, onUnmounted } from "vue";
import { useStore } from "vuex";
import { useRoute } from "vue-router";

export default {
  components: {
    ConferenceSidevar
  },
  name: "conference-detail",
  mounted() {
    console.log(this.$route.params.conferenceId);
  }
};
</script>
