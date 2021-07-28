<template>
  <el-button class="button" @click="signOut">로그아웃</el-button>
  <google-login v-if="!isLogin"></google-login>
  <el-container v-else class="main-wrapper">
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
import GoogleLogin from "../google-login/google-login.vue";

import { computed, reactive, toRefs, ref } from "vue";
import { useStore } from "vuex";
export default {
  name: "Main",
  components: {
    MainSidebar,
    LoginDialog,
    SignUpDialog,
    GoogleLogin
  },
  data() {
    return {
      loginDialogOpen: false,
      signUpDialogOpen: false,
      isLogin: false
    };
  },
  computed: {
    isLogin() {
      return this.$store.state.root.isLogin;
    }
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
    },
    signOut() {
      console.log("로그아웃버튼누름");
      window.gapi.auth2.getAuthInstance().disconnect();
      sessionStorage.removeItem("userInfo");
      this.$store.commit("root/setLogin", false);

      // this.isLogin = false;
      // this.$router.push("/");
    }
  }
};
</script>
