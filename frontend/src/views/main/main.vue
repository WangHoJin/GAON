<template>
  <el-row>
    <el-col :span="1" @contextmenu.prevent> <main-sidebar /></el-col>
    <el-col :span="23" align="center"> <router-view></router-view> </el-col>
  </el-row>
</template>

<script>
import MainSidebar from "./components/main-sidebar";
// import { mapGetters, mapActions, mapMutations, mapState } from "vuex";
export default {
  name: "Main",
  components: {
    MainSidebar
  }
  // computed: {
  //   ...mapGetters(["rrrs"]),
  //   ...mapState(["roomModule/rrrr"])
  // },
  // methods: {
  //   ...mapMutations(["setrrrs"]),
  //   ...mapActions(["raction"])
  // }
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
