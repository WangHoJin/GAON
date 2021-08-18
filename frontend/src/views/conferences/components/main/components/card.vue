<template lang="">
  <div>
    <el-card class="box-card" style="  min-height: 150px; ">
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
  /* margin-top: 80%; */
  z-index: 2;
  text-align: left;

  /* transform-origin: 50% 0%;
  animation-name: shake;
  animation-duration: 2s;
  animation-iteration-count: 1;
  animation-delay: 0.5s; */
}

.box-card:hover {
  transform: scale(1.1, 1.1);
}
/* @keyframes shake {
  0% {
    transform: scale(1.1, 1.1);
  }
  10% {
    transform: scale(1.15, 1.15);
  }
   20% {
    transform: rotate(-30deg);
  }
  30% {
    transform: rotate(20deg);
  }
  40% {
    transform: rotate(-20deg);
  }
  50% {
    transform: rotate(5deg);
  }
  60% {
    transform: rotate(-5deg);
  }
  70% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(0deg);
  }
} */
/* @keyframes shake {
  0% {
    transform: rotate(0deg);
  }
  10% {
    transform: rotate(30deg);
  }
  20% {
    transform: rotate(-30deg);
  }
  30% {
    transform: rotate(20deg);
  }
  40% {
    transform: rotate(-20deg);
  }
  50% {
    transform: rotate(5deg);
  }
  60% {
    transform: rotate(-5deg);
  }
  70% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(0deg);
  }
} */
/* .body .el-card__body {
  padding: var(--el-card-padding);
  padding-top: 10%;
} */
.card-header span {
  font-size: 25px;
}
.content {
  padding: 5% 0px 5% 0px;
}
</style>
