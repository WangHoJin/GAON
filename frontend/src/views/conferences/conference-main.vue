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
  // setup() {
  //   const route = useRoute();
  //   const store = useStore();
  //   const state = reactive({
  //     conferenceId: ""
  //   });

  //   // 페이지 진입시 불리는 훅
  //   onMounted(() => {
  //     // state.conferenceId = route.params.conferenceId;
  //     // console(state.conferenceId);
  //     console("console(state.conferenceId);");
  //     store.commit("root/setMenuActiveMenuName", "home");
  //   });

  //   // 페이지 이탈시 불리는 훅
  //   onUnmounted(() => {
  //     // state.conferenceId = "";
  //   });
  //   return { state };
  // },
  mounted() {
    console.log("??");
    console.log("mount됐을때 conferenceId");
    console.log(this.$route.params.conferenceId);
  }
};
</script>
