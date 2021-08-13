<template>
  <el-row align="center">
    <el-col :span="3"> <conference-sidevar /></el-col>
    <el-col :span="21">
      <el-main>
        <!-- {{ $route.params.conferenceId + "번 방 상세 보기 페이지" }}
        <a
          href="https://localhost:8443/api/v1/boards/posts/1/file/1_test"
          class="modal_refer_font"
        >
          파일
        </a>
        <h5>테스트용 나중에 지우겠음</h5>
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
          <el-button size="small" type="primary">Click to upload</el-button>
          <div slot="tip" class="el-upload__tip">
            size less than 500kb
          </div>
        </el-upload> -->

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
import $axios from "axios";
import { Base64 } from "js-base64";

export default {
  data() {
    return {
      URL: process.env.VUE_APP_API_URL + "/api/v1/boards/posts/2/files/",
      fileList: [
        {
          name: "파일",
          // url: "https://localhost:8443/api/v1/boards/posts/1/file/30"
          url: "https://localhost:8443/api/v1/boards/posts/1/file/3"
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
      console.log("지우기버튼누름");
      console.log("file" + file);
      console.log("fileList: " + fileList);
    },
    handlePreview(file) {
      console.log("파일클릭");
      let fileName = "파일.png";
      console.log("fileName : " + fileName);
      fileName = decodeURI(fileName); // 파일명 디코딩 (프로젝트에 따라 사용여부 옵션)
      let link = document.createElement("a");
      link.href = file.url;
      link.target = "_self";
      if (fileName) link.download = fileName;
      link.click();
    },
    handleExceed(files, fileList) {
      console.log("최대 업로드 갯수 초과했을때");
      console.log("files" + files);
      console.log("fileList: " + fileList);
      this.$message.warning(
        `The limit is 3, you selected ${
          files.length
        } files this time, add up to ${files.length + fileList.length} totally`
      );
    },
    beforeRemove(file, fileList) {
      console.log(" beforeRemove ");
      console.log("file" + file);
      console.log("fileList: " + fileList);
      return this.$confirm(`Cancel the transfert of ${file.name} ?`);
    }
  },
  components: {
    ConferenceSidevar
  },
  name: "conference-detail",
  mounted() {
    console.log("mounted ");
    console.log("this.$route.params.conferenceId");
    console.log(this.$route.params.conferenceId);
    console.log("process env VUE_APP_API_URL");
    console.log(process.env.VUE_APP_API_URL);
  }
};
</script>
