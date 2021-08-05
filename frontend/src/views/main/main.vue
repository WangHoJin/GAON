<template>
  <!-- <google-login v-if="!$store.state.root.isLogin"></google-login> -->
  <el-row>
    <el-col :span="2" @contextmenu.prevent> <main-sidebar /></el-col>
    <el-col :span="22" align="center"> <router-view></router-view> </el-col>
  </el-row>
</template>

<script>
import MainSidebar from "./components/main-sidebar";
export default {
  name: "Main",
  components: {
    MainSidebar
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
.el-row {
  height: 100%;
}
</style>
