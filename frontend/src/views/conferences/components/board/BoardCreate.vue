<template>
  <el-container
    class="main-wrapper"
    style="background-color:white; text-align:left"
  >
    <el-contianer class="main-container">
      <!-- <el-main > -->
      <h1>새로운 글 등록</h1>
      <el-divider></el-divider>
      <el-form :model="form" ref="form">
        <el-form-item
          prop="title"
          label="제목"
          :label-width="formLabelWidth"
          :rules="{
            required: true,
            message: '제목을 입력해주세요.',
            trigger: 'blur'
          }"
        >
          <el-input
            v-model="form.title"
            autocomplete="off"
            placeholder="글 제목을 입력하세요!"
          ></el-input>
        </el-form-item>
        <el-form-item prop="content" label="내용" :label-width="formLabelWidth">
          <el-input
            type="textarea"
            :rows="5"
            placeholder="글 내용을 작성해주세요!"
            v-model="form.content"
          >
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-row>
            <el-col :span="8"
              ><el-upload
                class="upload-demo"
                drag
                ref="upload"
                :action="URL"
                :on-preview="handlePreview"
                :on-remove="handleRemove"
                :file-list="fileList"
                :auto-upload="false"
                multiple
              >
                <div>
                  <img
                    style="width:100px; margin-top:20px;"
                    src="data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZlcnNpb249IjEuMSIgeG1sbnM6eGxpbms9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkveGxpbmsiIHhtbG5zOnN2Z2pzPSJodHRwOi8vc3ZnanMuY29tL3N2Z2pzIiB3aWR0aD0iNTEyIiBoZWlnaHQ9IjUxMiIgeD0iMCIgeT0iMCIgdmlld0JveD0iMCAwIDQ5Ny4wMDUgNDk3LjAwNSIgc3R5bGU9ImVuYWJsZS1iYWNrZ3JvdW5kOm5ldyAwIDAgNTEyIDUxMiIgeG1sOnNwYWNlPSJwcmVzZXJ2ZSI+PGc+PGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj48cGF0aCBkPSJtODEuMTQyIDM0MC44NzRjLTE1LjI0My0uMDI4LTMwLjMxNC00LjA3Ny00My4xMjYtMTIuMzItMjIuODY4LTE0LjcxNC0zOC4wMTEtNDAuMzU4LTM4LjAxMS02OS41MzkgMC00MS42NjggMzAuODc1LTc2LjEyMiA3MS4wMjktODEuODQ0IDEuMjM0LTg4LjU0IDczLjUtMTU5LjkzOCAxNjIuNDgyLTE1OS45MzggNzAuOTcxIDAgMTMxLjMwNiA0NS40MjMgMTUzLjQ1NCAxMDguNzM0LjY1NC0uMDEyIDEuMzAxLS4wNDkgMS45NTctLjA0OSA1OS42OSAwIDEwOC4wNzggNDguMzA3IDEwOC4wNzggMTA3Ljg5NyAwIDU2LjA1NC00Mi44MjQgMTAyLjA5OS05Ny41ODkgMTA3LjM2Ny0yLjI1NS4yMTQtMzE4LjI3NC0uMzA4LTMxOC4yNzQtLjMwOHoiIGZpbGw9IiM3OWEyZjciIGRhdGEtb3JpZ2luYWw9IiM3OWEyZjciIHN0eWxlPSIiPjwvcGF0aD48cGF0aCBkPSJtODEuMTM3IDM0MC44NzZjLTE1LjI0My0uMDI4LTMwLjMxNC00LjA3Ny00My4xMjYtMTIuMzItMjIuODY4LTE0LjcxNC0zOC4wMTEtNDAuMzU4LTM4LjAxMS02OS41MzkgMC00MS42NjggMzAuODc1LTc2LjEyMiA3MS4wMjktODEuODQ0IDEuMjM0LTg4LjU0IDczLjUtMTU5LjkzOCAxNjIuNDgyLTE1OS45MzggNzAuOTcxIDAgMTMxLjMwNiA0NS40MjMgMTUzLjQ1NCAxMDguNzM0LjY1NC0uMDEyIDEuMzAxLS4wNDkgMS45NTctLjA0OSA1OS42OSAwIDEwOC4wNzggNDguMzA3IDEwOC4wNzggMTA3Ljg5NyAwIDU2LjA1NC00Mi44MjQgMTAyLjA5OS05Ny41ODkgMTA3LjM2Ny0yLjI1NS4yMTQtMzE4LjI3NC0uMzA4LTMxOC4yNzQtLjMwOHoiIGZpbGw9IiNhYWQ3ZTUiIGRhdGEtb3JpZ2luYWw9IiNhYWQ3ZTUiIHN0eWxlPSIiPjwvcGF0aD48cGF0aCBkPSJtMzI2LjgwNSA0Ni42MTNjLTE5LjQ1LTguNDQtNDAuODMtMTMuMTEtNjMuMjktMTMuMTEtODcuNjY2IDAtMTU5LjExMiA3MS4xNzEtMTYyLjM3OSAxNjAuMjM0LS4wODUgMi4zMjQtMS43ODkgNC4yNDctNC4wNyA0LjY5OC0zOC4xOTggNy41NDctNjcuMDYxIDQxLjk5OS02Ny4wNjEgODMuMzY4IDAgMTkuNDkgNi40MSAzNy40NSAxNy4xOSA1MS43OC0zLjE2LTEuNDYtNi4yMy0zLjEzLTkuMTgtNS4wMy0yMi44Ny0xNC43MS0zOC4wMS00MC4zNi0zOC4wMS02OS41NCAwLTQxLjY3IDMwLjg4LTc2LjEyIDcxLjAzLTgxLjg0IDEuMjMtODguNTQgNzMuNS0xNTkuOTQgMTYyLjQ4LTE1OS45NCAzNC43MiAwIDY2Ljg4IDEwLjg2IDkzLjI5IDI5LjM4eiIgZmlsbD0iIzk4Y2ZkZSIgZGF0YS1vcmlnaW5hbD0iIzk4Y2ZkZSIgc3R5bGU9IiI+PC9wYXRoPjxwYXRoIGQ9Im0zNDMuMDA1IDQ3OS43NzNoLTE4OWMtMTEuMDQ2IDAtMjAtOC45OTYtMjAtMjAuMDkzdi00MC44MTRjMC0xMS4wOTcgOC45NTQtMjAuMDkzIDIwLTIwLjA5M3MyMCA4Ljk5NiAyMCAyMC4wOTN2MTUuNzIxYzAgMi43NjEgMi4yMzkgNSA1IDVoMTM5YzIuNzYxIDAgNS0yLjIzOSA1LTV2LTE1LjcyMWMwLTExLjA5NyA4Ljk1NC0yMC4wOTMgMjAtMjAuMDkzczIwIDguOTk2IDIwIDIwLjA5M3Y0MC44MTRjMCAxMS4wOTgtOC45NTQgMjAuMDkzLTIwIDIwLjA5M3oiIGZpbGw9IiNhZWYyYWIiIGRhdGEtb3JpZ2luYWw9IiNhZWYyYWIiIHN0eWxlPSIiPjwvcGF0aD48cGF0aCBkPSJtMzYzLjAwNSA0NDQuNjgydjE1YzAgMTEuMS04Ljk1IDIwLjA5LTIwIDIwLjA5aC0xODljLTExLjA1IDAtMjAtOC45OS0yMC0yMC4wOXYtNDAuODJjMC0xMS4wOSA4Ljk1LTIwLjA5IDIwLTIwLjA5IDIuNjYgMCA1LjE5LjUyIDcuNTEgMS40Ny03LjM0IDIuOTctMTIuNTEgMTAuMTktMTIuNTEgMTguNjJ2MzUuOTFjMCA1LjUyIDQuNDggMTAgMTAgMTBoMTg0YzExLjA1IDAgMjAtOC45OSAyMC0yMC4wOXoiIGZpbGw9IiNhZGYxYWEiIGRhdGEtb3JpZ2luYWw9IiNhZGYxYWEiIHN0eWxlPSIiPjwvcGF0aD48cGF0aCBkPSJtMzYzLjAwNSA0NDQuNjgydjE1YzAgMTEuMS04Ljk1IDIwLjA5LTIwIDIwLjA5aC0xODljLTExLjA1IDAtMjAtOC45OS0yMC0yMC4wOXYtNDAuODJjMC0xMS4wOSA4Ljk1LTIwLjA5IDIwLTIwLjA5IDIuNjYgMCA1LjE5LjUyIDcuNTEgMS40Ny03LjM0IDIuOTctMTIuNTEgMTAuMTktMTIuNTEgMTguNjJ2MzUuOTFjMCA1LjUyIDQuNDggMTAgMTAgMTBoMTg0YzExLjA1IDAgMjAtOC45OSAyMC0yMC4wOXoiIGZpbGw9IiM5NmQwOTQiIGRhdGEtb3JpZ2luYWw9IiM5NmQwOTQiIHN0eWxlPSIiPjwvcGF0aD48cGF0aCBkPSJtMjM1LjU4NiAzNzMuOTUyIDUuNjM5IDI5LjZjMi4yODYuNzgxIDQuNzI4IDEuMjExIDcuMjc4IDEuMjExIDEyLjUwMyAwIDIyLjYyOS0xMC4xODYgMjIuNjI5LTIyLjc2MXYtOTYuNDI2bDIxLjMzOSAyMS40NjRjOC44MzcgOC44ODggMjMuMTYxIDguODg4IDMxLjk5OCAwIDguODQyLTguODc2IDguODQyLTIzLjMwOSAwLTMyLjE4NWwtNTkuOTY4LTYwLjMxOGMtMS43NDItMS43NzQtMy43ODQtMy4yMzItNi4wMDQtNC4zMjctMy40NDUtMS42OTktNS4xMzItLjMzOS04Ljk3NC0uMTIxLTUuNTQuMjY1LTEwLjkzNCAyLjY3My0xNC44MzMgNi42MzVsLTU5Ljk2NyA2MC4zMThjLTguODM3IDguODg4LTYuMzY4IDIwLjM4NiAyLjQ2OCAyOS4yNzQgNC40MTggNC40NDQgNS41NTQgNy4zOSAxMS4zNDQgNy4zOXMxMS41ODEtMi4yMjIgMTUuOTk5LTYuNjY2bDIxLjMzOS0yMS40NjR6IiBmaWxsPSIjZjdmMDZkIiBkYXRhLW9yaWdpbmFsPSIjZjdmMDZkIiBzdHlsZT0iIj48L3BhdGg+PGcgZmlsbD0iI2VkNSI+PHBhdGggZD0ibTI1Mi41MDUgMjE0LjU0M2MxLjc0LTEuNzcgMy43Ny0zLjIzIDUuOTktNC4zMi0zLjQ1LTEuNzEtNy4zMi0yLjU0LTExLjE2LTIuMzItNS41NC4yNy0xMC45MyAyLjY3LTE0LjgzIDYuNjRsLTU5Ljk3IDYwLjMxYy04LjgzIDguODktOC44MyAyMy4zIDAgMzIuMTkgNC41NiA0LjU5IDEwLjU4IDYuODEgMTYuNTYgNi42NS0uNjYtLjQ2LTEuMjgtLjk3LTEuODUtMS41NS04LjgzLTguODktOC44My0yMy4zIDAtMzIuMTl6IiBmaWxsPSIjZWVkZDU1IiBkYXRhLW9yaWdpbmFsPSIjZWVkZDU1IiBzdHlsZT0iIj48L3BhdGg+PHBhdGggZD0ibTIzNS43NjYgMjc1LjYxOS05Ljg5MyA5Ljk1OC4wMDEgOTYuNDI2YzAgMTAuMDIgNi40MiAxOC41MSAxNS4zNSAyMS41NWwtLjMzMS0xMjUuNzM0Yy0uMDA2LTIuNjczLTMuMjQyLTQuMDk2LTUuMTI3LTIuMnoiIGZpbGw9IiNlZWRkNTUiIGRhdGEtb3JpZ2luYWw9IiNlZWRkNTUiIHN0eWxlPSIiPjwvcGF0aD48L2c+PC9nPjwvZz48L3N2Zz4="
                  />
                </div>
                <!-- <i class="el-icon-upload"></i> -->
                <div class="el-upload__text">
                  파일 드래그 OR <em>클릭하세요!</em>
                </div>
                <el-button
                  style="display:none"
                  size="small"
                  type="success"
                  @click="submitUpload"
                  >upload to server</el-button
                >
                <!-- <template #tip>
                  <div class="el-upload__tip">
                    파일 용량 제한 : 2G
                  </div>
                </template> -->
              </el-upload></el-col
            >
            <el-col :span="16">
              <el-button
                type="info"
                @click="submitCancle()"
                class="create-cancle-btn"
                >취소</el-button
              >
              <el-button
                type="primary"
                class="gaon-button create-btn"
                @click="createPost('form', $route.params.bid)"
                >글 작성</el-button
              ></el-col
            >
          </el-row>
        </el-form-item>
      </el-form>

      <!-- </el-main> -->

      <!-- <template #tip>
            <div class="el-upload__tip">
              jpg/png files with a size less than 500kb
            </div>
          </template>
        </el-upload>

          <el-button type="info" @click="submitCancle();">취소</el-button>
          <el-button type="primary" class="gaon-button" @click="createPost('form', $route.params.bid);">글 작성</el-button>
      </el-main> -->
    </el-contianer>
  </el-container>
</template>

<script>
// API
import $axios from "axios";
export default {
  data() {
    return {
      fileList: [],
      form: {
        title: "",
        uid: "",
        content: ""
      },
      URL: process.env.VUE_APP_API_URL
    };
  },
  methods: {
    submitCancle() {
      // console.log('cancle')
      this.form.title = "";
      this.form.content = "";
      this.$router.push({ name: "board-post-list" });
    },
    submitUpload() {
      this.$refs.upload.submit();
      console.log("files are uploaded");
    },
    // 새 글 생성하기
    async createPost(formname, bid) {
      this.$refs[formname].validate(valid => {
        if (valid) {
          console.log("새 글 생성");
          const url = `/boards/${bid}/posts`;
          const uid = JSON.parse(sessionStorage.getItem("userInfo")).id;
          this.form.uid = uid;
          $axios
            .post(url, this.form)
            .then(res => {
              // console.log(res.data);
              this.form.title = "";
              this.form.content = "";
              this.form.uid = "";
              this.URL += `/api/v1/boards/posts/${res.data.post.id}/files/`;
              // console.log(this.URL)
            })
            .then(res => {
              this.submitUpload();
              this.$router.push({ name: "board-post-list" });
            })
            .catch(err => {
              console.log(err);
            });
        } else {
          console.log("error submit!");
          return false;
        }
      });
    }
  }
};
</script>

<style scoped>
.main-wrapper {
  background-color: white;
  /* padding: 20px 20px; */
}
.create-btn {
  position: absolute;
  right: 80px;
  margin-top: 134px;
}
.create-cancle-btn {
  position: absolute;
  right: 0px;
  margin-top: 134px;
}
</style>
