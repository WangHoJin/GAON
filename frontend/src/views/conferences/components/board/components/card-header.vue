<template lang="">
  <el-card
    class="box-card"
    style="font-family: BMDOHYEON_ttf ;padding-bottom: 0px;padding-top: 1px;text-align: left;    max-height: 60px;box-shadow: none;  padding-left: 40px; "
  >
    {{ boardName }} |
    <span style="font-size:12px; vertical-align:middle">
      <i class="header-icon el-icon-info"></i>{{ boardDescription }}
    </span>
  </el-card>
</template>
<script>
export default {
  props: {
    boardName: {
      type: String
    },
    boardDescription: {
      type: String
    }
  }
};
</script>
<style></style>
