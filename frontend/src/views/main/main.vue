<template>
  <el-row>
    <el-col :span="1" @contextmenu.prevent> <main-sidebar /></el-col>
    <el-col :span="23" align="center"> <router-view></router-view> </el-col>
  </el-row>
</template>

<script>
import MainSidebar from "./components/main-sidebar";
export default {
  name: "Main",
  components: {
    MainSidebar
  }
};
</script>
<style>
.el-row {
  height: 100%;
}
.el-row > .el-col {
  width: 100%;
}
</style>
