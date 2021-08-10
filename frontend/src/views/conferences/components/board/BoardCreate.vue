<template>
  <el-container class="main-wrapper">
    <el-contianer class="main-container">
      <el-main>
        <el-form :model="form">
          <el-form-item label="제목" :label-width="formLabelWidth">
            <el-input v-model="form.title" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="내용" :label-width="formLabelWidth">
            <el-input v-model="form.content" autocomplete="off"></el-input>
          </el-form-item>
        </el-form>

        <el-upload
          class="upload-demo"
          drag
          action="https://jsonplaceholder.typicode.com/posts/"
          :on-preview="handlePreview"
          :on-remove="handleRemove"
          :file-list="fileList"
          :auto-upload="false"
          multiple
        >
          <i class="el-icon-upload"></i>
          <div class="el-upload__text">Drop file here or <em>click to upload</em></div>
          <template #tip>
            <div class="el-upload__tip">
              jpg/png files with a size less than 500kb
            </div>
          </template>
        </el-upload>
          <el-button type="info" @click="dialogFormVisible = false;submitCancle();">취소</el-button>
          <el-button type="primary" class="gaon-button" @click="dialogFormVisible = false;createPost($route.params.bid);">글 작성</el-button>
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
      form: {
          title: '',
          uid: '',
          content: ''
        },
    }
  },
  methods: {
    submitCancle () {
        // console.log('cancle')
        this.form.title = ''
        this.form.content =''
        this.$router.push({name: 'board-post-list'})
      },
      // 새 글 생성하기
      async createPost(bid) {
        console.log("새 글 생성");
        const url = `/boards/${bid}/posts`;
        const uid = JSON.parse(sessionStorage.getItem("userInfo")).id;
        this.form.uid = uid
        await $axios
          .post(url, this.form)
          .then(res => {
            // console.log(res.data);
            // response = res.data;
            this.form.title = ''
            this.form.content = ''
            this.form.uid = ''
            this.$router.push({name: 'board-post-list'})
          })
          .catch(err => {
            console.log(err);
          });
      }
  }
}
</script>

<style>

</style>
