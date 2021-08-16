<template lang="">
  <el-container class="main-wrapper">
    <el-contianer class="main-container">
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-contianer>
  </el-container>
</template>
<script>
export default {};
</script>
<style>
.el-pagination.is-background .el-pager li:not(.disabled).active {
  background-color: #a2df14;
  color: var(--el-color-white);
}
</style>
