<template>
  <el-container class="main-wrapper">
    <el-container class="main-container">
      <el-aside class="hide-on-small" width="120px">
        <main-sidebar :width="`120px`" />
      </el-aside>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
  <login-dialog
    :open="loginDialogOpen"
    @closeLoginDialog="onCloseLoginDialog"
  />
  <sign-up-dialog
    :open="signUpDialogOpen"
    @closeSignUpDialog="onCloseSignUpDialog"
  />
</template>
<style>
@import "https://unpkg.com/element-plus/lib/theme-chalk/index.css";
@import "./main.css";
@import "../../common/css/common.css";
@import "../../common/css/element-plus.css";
</style>
<script>
import LoginDialog from "./components/login-dialog";
import SignUpDialog from "./components/sign-up-dialog";
import MainSidebar from "./components/main-sidebar";

export default {
  name: "Main",
  components: {
    MainSidebar,
    LoginDialog,
    SignUpDialog
  },
  data() {
    return {
      loginDialogOpen: false,
      signUpDialogOpen: false
    };
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
