<template>
  <el-container class="main-wrapper">
    <el-contianer class="main-container">
      <el-main>
        <el-form :model="form" ref="form">
          <el-form-item prop="title" label="제목" :label-width="formLabelWidth"
            :rules="{ required: true, message: '제목을 입력해주세요.', trigger: 'blur'}"
          >
            <el-input v-model="form.title" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item prop="content" label="내용" :label-width="formLabelWidth">
            <el-input v-model="form.content" autocomplete="off"></el-input>
          </el-form-item>
        </el-form>

        <el-upload
          class="upload-demo"
          drag
          ref="upload"
          action="https://jsonplaceholder.typicode.com/posts/"
          :on-preview="handlePreview"
          :on-remove="handleRemove"
          :file-list="fileList"
          :auto-upload="false"
          multiple
        >
          <i class="el-icon-upload"></i>
          <div class="el-upload__text">Drop file here or <em>click to upload</em></div>
          <el-button
            style="display:none"
            size="small"
            type="success"
            @click="submitUpload"
            >upload to server</el-button
          >
          <template #tip>
            <div class="el-upload__tip">
              jpg/png files with a size less than 500kb
            </div>
          </template>
        </el-upload>
          <el-button type="info" @click="submitCancle($route.params.pid);">취소</el-button>
          <el-button type="primary" class="gaon-button" @click="editPost('form', $route.params.pid);">글 수정</el-button>
      </el-main>
    </el-contianer>
  </el-container>
</template>

<script>
import { useRoute } from "vue-router";
// API
import $axios from "axios";
export default {
  mounted() {
    const route = useRoute()
    const url = `/boards/posts/${route.params.pid}`;
    // console.log("mounted")
      $axios
        .get(url)
        .then(res => {
          console.log(res.data.post)
          this.form.bid = res.data.post.bid,
          this.form.title = res.data.post.title,
          this.form.content = res.data.post.content
        })
        .catch(err => {
          console.log(err);
        });
  },
  data() {
    return {
      form: {
        bid:1,
        title:" ",
        content:""
        },
    }
  },
  methods: {
    submitCancle (pid) {
        // console.log('cancle')
        this.form.title = ''
        this.form.content =''
        this.$router.push({
          name: 'board-post-view',
          params: {
            pid: pid
          }
        })
      },
      submitUpload() {
        this.$refs.upload.submit()
        console.log("files are uploaded")
      },
      // 새 글 생성하기
      async editPost(formname, pid) {
        this.$refs[formname].validate((valid) => {
          if (valid) {
            console.log("글 수정");
            this.submitUpload()
            const url = `/boards/posts/${pid}`;
            const uid = JSON.parse(sessionStorage.getItem("userInfo")).id;
            this.form.uid = uid
            $axios
              .put(url, this.form)
              .then(res => {
                // console.log(res.data);
                // response = res.data;
                this.$router.push({
              name: 'board-post-view',
              params: {
                pid: pid
              }
            })
              })
              .catch(err => {
                console.log(err);
              });

          } else {
            console.log("error submit!")
            return false
          }
        })
      }
  }
}
</script>

<style>

</style>
