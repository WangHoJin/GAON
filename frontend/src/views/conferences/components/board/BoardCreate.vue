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
          :action="URL"
          :on-preview="handlePreview"
          :on-remove="handleRemove"
          :on-change="handleChange"
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

          <el-button type="info" @click="submitCancle();">취소</el-button>
          <el-button type="primary" class="gaon-button" @click="createPost('form', $route.params.bid);">글 작성</el-button>

        <el-dialog
          title="동일한 파일명이 존재합니다."
          v-model="duplicated"
          width="30%"
        >
          <template #footer>
            <span class="dialog-footer">
              <el-button @click="duplicated = false" type="info"
                >확인</el-button>
            </span>
          </template>
        </el-dialog>
      </el-main>
    </el-contianer>
  </el-container>
</template>

<script>
// API
import $axios from "axios";
export default {
  data() {
    return {
      duplicated: false,
      fileList:[],
      form: {
          title: '',
          uid: '',
          content: ''
        },
        URL: process.env.VUE_APP_API_URL,
    }
  },
  methods: {
    handleChange (file, fileList) {
      // console.log(file)
      // console.log(fileList)
      // fileList.pop()
      // this.fileList.pop()
      // console.log(fileList)
      // console.log(this.fileList)
      let step;
      const boundary = fileList.length - 1
      for (step = 0; step < boundary; step++) {
        if (fileList[step].name == file.name) {
          this.duplicated = true
          fileList.pop()
          break
        }
      }
    },

    submitCancle () {
        // console.log('cancle')
        this.form.title = ''
        this.form.content =''
        this.$router.push({name: 'board-post-list'})
      },
      submitUpload() {
        this.$refs.upload.submit()
        console.log("files are uploaded")
      },
      // 새 글 생성하기
      async createPost(formname, bid) {
        this.$refs[formname].validate((valid) => {
          if (valid) {
            console.log("새 글 생성");
            const url = `/boards/${bid}/posts`;
            const uid = JSON.parse(sessionStorage.getItem("userInfo")).id;
            this.form.uid = uid
            $axios
              .post(url, this.form)
              .then(res => {
                // console.log(res.data);
                this.form.title = ''
                this.form.content = ''
                this.form.uid = ''
                this.URL += `/api/v1/boards/posts/${res.data.post.id}/files/`
                // console.log(this.URL)
              })
              .then(res => {
                this.submitUpload()
                this.$router.push({name: 'board-post-list'})
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
