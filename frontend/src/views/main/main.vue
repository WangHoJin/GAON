<template>
  <div>
    <google-login v-if="!$store.state.root.isLogin"></google-login>
    <el-row v-else>
      <el-col :span="2" @contextmenu.prevent> <main-sidebar /></el-col>
      <el-col :span="22" align="center"> <router-view></router-view> </el-col>
    </el-row>
  </div>
</template>

<style>
@import "https://unpkg.com/element-plus/lib/theme-chalk/index.css";
@import "./main.css";
@import "../../common/css/common.css";
@import "../../common/css/element-plus.css";
.el-main {
  height: 100%;
  width: 100% !important;
  display: inline-block;
  color: #333;
  text-align: center;
  overflow-x: hidden;
}
</style>
<script>
import MainSidebar from "./components/main-sidebar";
import GoogleLogin from "../google-login/google-login.vue";
import ConferenceSidevar from "../conferences/components/conference-sidebar.vue";
import { computed, reactive, toRefs, ref } from "vue";
import { useStore } from "vuex";
export default {
  name: "Main",
  components: {
    GoogleLogin,
    MainSidebar,
    ConferenceSidevar
  },
  mounted() {
    console.log("beforeMounted");
    console.log(this.$store.state.root.isLogin);
  },
  methods: {
    onOpenLoginDialog() {
      this.loginDialogOpen = true;
    },
    onCloseLoginDialog() {
      this.loginDialogOpen = false;
    },
    onOpenSignUpDialog() {
      this.signUpDialogOpen = true;
    },
    onCloseSignUpDialog() {
      this.signUpDialogOpen = false;
    }
  }
};
</script>
