<template>
  <google-login v-show="!isLogin"></google-login>
  <el-container v-if="isLogin" class="main-wrapper">
    <el-container class="main-container">
      <el-aside width="120px">
        <main-sidebar id="room-sidebar" :width="`120px`" /> </el-aside
      ><el-main>
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
      signUpDialogOpen: false
      // isLogin: false
    };
  },
  // mounted() {
  //   if (sessionStorage.getItem("userInfo") === null) {
  //     this.isLogin = false;
  //   } else {
  //     this.isLogin = true;
  //   }
  // },
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
    }
  }
};
</script>
<style>
#room-sidebar {
  height: 100%;
  /* border-right: solid 1px; */
  /* background-color: yellow; */
}
</style>
