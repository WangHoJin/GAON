<template lang="">
  <el-container class="main-wrapper" style="background-color:green">
    <el-contianer class="main-container">
      <el-main>
        {{ boardName }}
        {{ boardDescription }}
        <router-view></router-view>
      </el-main>
    </el-contianer>
  </el-container>
</template>
<script>
import $axios from "axios";
import { useRoute } from "vue-router";
export default {
  data() {
    return {
      boardName: "",
      boardDescription: ""
    };
  },
  async mounted() {
    console.log("mounted");
    const route = useRoute();
    const url = `/boards/id/${route.params.bid}`;
    console.log("라우터에서 bid가져오기");
    console.log(route.params.bid);
    await $axios
      .get(url)
      .then(res => {
        console.log("get " + res);
        console.log(res.data);
        this.boardName = res.data.name;
        this.boardDescription = res.data.description;
        // this.form = res.data.post
        // console.log("makeboard ");
        // console.log("res.data");
      })
      .catch(err => {
        console.log(err);
      });
  }
};
</script>
<style lang=""></style>
