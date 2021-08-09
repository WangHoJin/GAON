<template lang="">
  <div></div>
</template>
<script>
export default {
  data() {
    return {
      posts: [] // 게시판의 게시글들 저장할 배열
    };
  },
  created() {
    console.log("게시판번호임");
    console.log(this.$route.params.bid);
    // 게시판의 모든 게시글 가져오기
  }
};
</script>
<style lang=""></style>
