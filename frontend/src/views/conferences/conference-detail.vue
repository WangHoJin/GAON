<template>
  <el-row align="center">
    <el-col :span="3"> <conference-sidevar /></el-col>
    <el-col :span="21">
      <el-main>
        {{ $route.params.conferenceId + "번 방 상세 보기 페이지" }
        <h3>테스트용으로 달아둠 나중에 지우겠음</h3>
        <el-upload
          class="upload-demo"
          :action="URL"
          :on-preview="handlePreview"
          :on-remove="handleRemove"
          :before-remove="beforeRemove"
          multiple
          :limit="3"
          :on-exceed="handleExceed"
          :file-list="fileList"
        >
          >
          <el-button size="small" type="primary">Click to upload</el-button>
          <div slot="tip" class="el-upload__tip">
            size less than 500kb
          </div>
        </el-upload>

        <router-view></router-view>
      </el-main>
    </el-col>
  </el-row>
</template>
<style>
@import "./conferences.css";
</style>
<script>
import ConferenceSidevar from "../conferences/components/conference-sidebar.vue";
import { reactive, onMounted, onUnmounted } from "vue";
import { useStore } from "vuex";
import { useRoute } from "vue-router";

export default {
  data() {
    return {
      URL: process.env.VUE_APP_API_URL + "/api/v1/boards/posts/2/files/",
      fileList: [
        {
          name: "food.jpeg",
          url:
            "https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100"
        },
        {
          name: "food2.jpeg",
          url:
            "https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100"
        }
      ]
    };
  },
  methods: {
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePreview(file) {
      console.log("파일클릭");
      console.log(file);
    },
    handleExceed(files, fileList) {
      this.$message.warning(
        `The limit is 3, you selected ${
          files.length
        } files this time, add up to ${files.length + fileList.length} totally`
      );
    },
    beforeRemove(file, fileList) {
      return this.$confirm(`Cancel the transfert of ${file.name} ?`);
    }
  },
  components: {
    ConferenceSidevar
  },
  name: "conference-detail",
  mounted() {
    console.log(this.$route.params.conferenceId);
    console.log(process.env.VUE_APP_API_URL);
  }
};
</script>
