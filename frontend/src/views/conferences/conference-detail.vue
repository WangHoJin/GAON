<template>
  <el-container class="main-wrapper">
    <el-contianer class="main-container">
      <!-- 얘떄문에 conference-sidber가 밀려보였던거 -->
      <el-aside class="hide-on-small" width="120px">
        <conference-sidebar :width="`120px`" />
      </el-aside>
      <el-main>
        {{ $route.params.conferenceId + "번 방 상세 보기 페이지" }}
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
import ConferenceSidebar from "./components/conference-sidebar";

export default {
  name: "conference-detail",
  components: {
    ConferenceSidebar
  },

  setup() {
    const route = useRoute();
    const store = useStore();
    const state = reactive({
      conferenceId: ""
    });

    // 페이지 진입시 불리는 훅
    onMounted(() => {
      state.conferenceId = route.params.conferenceId;
      store.commit("root/setMenuActiveMenuName", "home");
    });

    // 페이지 이탈시 불리는 훅
    onUnmounted(() => {
      state.conferenceId = "";
    });

    return { state };
  }
};
</script>
