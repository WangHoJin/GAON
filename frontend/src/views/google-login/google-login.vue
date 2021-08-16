<template>
  <div class="main">
    <!-- sky -->
    <LoginSky />
    <!-- text -->
    <LoginText />

    <LoginForm />
  </div>
</template>
<script>
import LoginForm from "./components/login-form.vue";
import LoginSky from "./components/login-sky.vue";
import LoginText from "./components/login-text.vue";
export default {
  components: { LoginForm, LoginSky, LoginText }
};
</script>
<style scoped>
@import "https://fonts.googleapis.com/css?family=Baloo+Paaji";

.main {
  top: 0px;
  left: 0px;
  height: 100%;
  width: 100%;
  font-family: "Baloo Paaji", cursive;
  /* background: #f5df4d; */
  display: flex;
  justify-content: center;
  align-items: center;
  /* background-image: url("246.jpg"); */
  /* background-size: cover; */
  /* padding-top: 400px; */
}
.container {
  width: 400px;
  height: 220px;
  position: relative;
}
</style>
