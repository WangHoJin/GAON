<template>
  <div>
    <el-button class="button" @click="signOut">로그아웃</el-button>
    <div>
      <el>{{ username }}</el>
    </div>
    <img :src="`${img}`" style="width : 30px; border-radius: 70%" />
  </div>
</template>
<script>
export default {
  name: "user-info-box",
  data() {
    if (sessionStorage.getItem("userInfo") != null) {
      return {
        username: JSON.parse(sessionStorage.getItem("userInfo")).nickname,
        img: JSON.parse(sessionStorage.getItem("userInfo")).imgUrl,
        uid: JSON.parse(sessionStorage.getItem("userInfo")).id,
        email: JSON.parse(sessionStorage.getItem("userInfo")).email
      };
    }
  }
};
</script>
<style></style>
