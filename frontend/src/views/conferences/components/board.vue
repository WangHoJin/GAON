<template lang="">
  <el-container class="main-wrapper">
    <el-contianer class="main-container">
      <el-main>
        <h1 class="mt-3">게시판의 bid : {{ $route.params.bid }}</h1>
        <h1>여기에 게시판 글목록, 상세보기, 수정 등 구현</h1>
        <router-view></router-view>
      </el-main>
    </el-contianer>
  </el-container>
</template>
<script>
export default {
};
</script>
<style lang=""></style>
