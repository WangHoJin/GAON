<template lang="">
  <div>
    <el-card class="box-card" style="  min-height: 200px; ">
      <template #header>
        <div class="card-header">
          <span>{{ title }}</span>
        </div>
      </template>
      <div>
        <img
          style="width:30%; float: left; vertical-align: middle"
          :src="src"
        />
        <p class="content">{{ content }}</p>
      </div>
    </el-card>
  </div>
</template>
<script>
export default {
  created() {
    console.log(this.title);
    console.log(this.content);
  },
  props: {
    title: {
      type: String
    },
    content: {
      type: String
    },
    src: {
      type: String
    }
  }
};
</script>
<style scoped>
.box-card {
  width: 100%;
  /* height: 80%; */
  margin-top: 80%;
  z-index: 2;
  text-align: left;
}
.body .el-card__body {
  padding: var(--el-card-padding);
  padding-top: 10%;
}
.card-header span {
  font-size: 25px;
}
.content {
  padding: 10% 0px 10% 0px;
}
</style>
