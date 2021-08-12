<template lang="">
  <el-container class="main-wrapper">
    <el-contianer class="main-container">
    <el-scrollbar class="show-scroll" height="400px">
      <el-main>
        <div class="horizontal-border">
        <p class="title">
        {{ form.title }}
        </p>
        <p class="content">
        {{ form.nickname }}
        <div class="vertical-border"></div>
        {{ form.created_time }}
        </p>
        </div>
        <p>
        {{ form.content }}
        </p>
      </el-main>

      <!-- <el-footer v-if="form.uid === uid"> -->
        <el-row :gutter="20" v-if="form.uid === uid">
          <el-col :span="6" :offset="18">
            <el-button @click="editPost($route.params.pid)">Edit</el-button>
            <el-popconfirm
              confirmButtonText='OK'
              cancelButtonText='No, Thanks'
              icon="el-icon-info"
              iconColor="red"
              title="Are you sure to delete this?"
              @confirm="deletePost($route.params.pid)"
            >
            <template #reference>
              <el-button type="danger">Delete</el-button>
              </template>
            </el-popconfirm>

          </el-col>
        </el-row>
      <!-- </el-footer> -->
      </el-scrollbar>
    </el-contianer>
  </el-container>
</template>
<script>
import { useRoute } from "vue-router";
import $axios from "axios";
export default {
  mounted() {
    // this.$nextTick(function() {
    //       // 모든 화면이 렌더링된 후 실행합니다.
    //   })
    this.uid = JSON.parse(sessionStorage.getItem("userInfo")).id;
    const route = useRoute()
    const url = `/boards/posts/${route.params.pid}`;
    // console.log("mounted")
    $axios
      .get(url)
      .then(res => {
        // console.log(res.data.post)
        this.form = res.data.post
      })
      .catch(err => {
        console.log(err);
      });

    const fileURL = `/boards/posts/${route.params.pid}/files`
    $axios
      .get(fileURL)
      .then(res => {
        console.log("get uploaded files")
        console.log(res)
      })
      .catch(err => {
        console.log(err)
      })


  },
  data() {
    return {
      form: {
          id: 0,
          title: "post_title",
          content: "post_content",
          uid: 0,
          bid: 0,
          created_time: "2021-08-10 00:14:01.130000",
          modified_time: "2021-08-10 00:14:01.130000"
        },
        uid: 0
    }
  },
  methods: {
      async getDetail(pid) {
      console.log("get detail post");
      const url = `/boards/posts/${pid}`;
      await $axios
        .get(url)
        .then(res => {
          // console.log(res.data.posts);
          this.form = res.data.post
          // console.log("makeboard ");
          // console.log("res.data");
        })
        .catch(err => {
          console.log(err);
        });
      },

      editPost(pid) {
      this.$router.push({name: 'board-post-modify'})
      },

      async deletePost(pid) {
      // console.log("delete post");
      const url = `/boards/posts/${pid}`;
      await $axios
        .delete(url)
        .then(res => {
          console.log("post is deleted");
          this.$router.push({name: 'board-post-list'})
        })
        .catch(err => {
          console.log(err);
        });
      }
    }

};
</script>
<style scoped>
  .el-row {
    margin-bottom: 20px;
  }
  .el-col {
    border-radius: 4px;
  }
  .row-bg {
    padding: 10px 0;
    background-color: #f9fafc;
  }
  .title {
    text-align: left;
  }
  .content {
    text-align: left;
  }
  .horizontal-border {
    border-bottom: 1px solid #eee;
  }
  .vertical-border {
    content: "";
    display: inline-block;
    background: #ccc;
    width: 1px;
    height: 14px;
    margin: 0 10px 0 6px;
    vertical-align: -2px;
  }
</style>
