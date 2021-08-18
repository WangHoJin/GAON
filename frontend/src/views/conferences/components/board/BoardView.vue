<template lang="">
  <el-container class="main-wrapper">
    <el-contianer class="main-container">
      <el-main style="text-align: initial;">
        <div class="horizontal-border">
          <p class="title">
            {{ form.title }}
          </p>
          <el-divider></el-divider>
          <div class="font">
            <span class="content">
              {{ form.nickname }}
              <div class="vertical-border"></div>
              {{ form.created_time.substring(0, 19) }}
            </span>
            <el-divider></el-divider>
          </div>
          <div
            class="pa-5"
            style="min-height: 300px; "
            v-html="enterToBr(form.content)"
          ></div>
        </div>
        <div style="padding: 10px 0px;">
          <el-button
            v-for="file in fileList"
            style="max-width: min-content;padding-right: 25px; color:black;"
            @click="download(file.pfid, file.filename)"
          >
            <span>{{ file.filename }}</span>
            <img
              style="width:25px;vertical-align:middle"
              src="data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZlcnNpb249IjEuMSIgeG1sbnM6eGxpbms9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkveGxpbmsiIHhtbG5zOnN2Z2pzPSJodHRwOi8vc3ZnanMuY29tL3N2Z2pzIiB3aWR0aD0iNTEyIiBoZWlnaHQ9IjUxMiIgeD0iMCIgeT0iMCIgdmlld0JveD0iMCAwIDUxMiA1MTIiIHN0eWxlPSJlbmFibGUtYmFja2dyb3VuZDpuZXcgMCAwIDUxMiA1MTIiIHhtbDpzcGFjZT0icHJlc2VydmUiPjxnPjxyZWN0IHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIgZmlsbD0iI2Y3Y2MzOCIgaGVpZ2h0PSIzMzYiIHJ4PSIzMiIgd2lkdGg9IjQxNiIgeD0iNzIiIHk9IjE1MiIgZGF0YS1vcmlnaW5hbD0iI2Y3Y2MzOCIgc3R5bGU9IiI+PC9yZWN0PjxwYXRoIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIgZD0ibTQ0MCAxMDR2MzA0YTMyIDMyIDAgMCAxIC0zMiAzMmgtMjU2YTMyIDMyIDAgMCAxIC0zMi0zMnYtMzUyYTMyIDMyIDAgMCAxIDMyLTMyaDIwOHoiIGZpbGw9IiNlOWVlZjIiIGRhdGEtb3JpZ2luYWw9IiNlOWVlZjIiIHN0eWxlPSIiPjwvcGF0aD48cGF0aCB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIGQ9Im00MjQgNDg4aC0zNjhhMzIgMzIgMCAwIDEgLTMyLTMydi0zMjBhMzIgMzIgMCAwIDEgMzItMzJoOTZhMzIgMzIgMCAwIDEgMzIgMzJ2NjRoMTc2YTMyIDMyIDAgMCAxIDMyIDMydjIyNGEzMiAzMiAwIDAgMCAzMiAzMiIgZmlsbD0iI2ZiZTM2YSIgZGF0YS1vcmlnaW5hbD0iI2ZiZTM2YSIgc3R5bGU9IiI+PC9wYXRoPjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIgZmlsbD0iI2MzYzZjNyI+PHBhdGggZD0ibTM5MiAxNjBoLTExMmE4IDggMCAwIDEgMC0xNmgxMTJhOCA4IDAgMCAxIDAgMTZ6IiBmaWxsPSIjYzNjNmM3IiBkYXRhLW9yaWdpbmFsPSIjYzNjNmM3IiBzdHlsZT0iIj48L3BhdGg+PHBhdGggZD0ibTI4MCAxMTJoLTU2YTggOCAwIDAgMSAwLTE2aDU2YTggOCAwIDAgMSAwIDE2eiIgZmlsbD0iI2MzYzZjNyIgZGF0YS1vcmlnaW5hbD0iI2MzYzZjNyIgc3R5bGU9IiI+PC9wYXRoPjxwYXRoIGQ9Im00NDAgMTA0aC00OGEzMiAzMiAwIDAgMSAtMzItMzJ2LTQ4eiIgZmlsbD0iI2MzYzZjNyIgZGF0YS1vcmlnaW5hbD0iI2MzYzZjNyIgc3R5bGU9IiI+PC9wYXRoPjxwYXRoIGQ9Im0yNDggMTYwaC0xNmE4IDggMCAwIDEgMC0xNmgxNmE4IDggMCAwIDEgMCAxNnoiIGZpbGw9IiNjM2M2YzciIGRhdGEtb3JpZ2luYWw9IiNjM2M2YzciIHN0eWxlPSIiPjwvcGF0aD48L2c+PHBhdGggeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiBkPSJtMTg0Ljg3OSA0MjMuOTk1IDU1LjI5My02NC42MTEtMzEuOTk4LjM1MS0xLjA1NS05NS45OTQtNDcuOTk3LjUyOCAxLjA1NSA5NS45OTQtMzEuOTk4LjM1MXoiIGZpbGw9IiNmNzk1MzkiIGRhdGEtb3JpZ2luYWw9IiNmNzk1MzkiIHN0eWxlPSIiPjwvcGF0aD48L2c+PC9zdmc+"
            />
          </el-button>
        </div>
        <!-- <el-footer v-if="form.uid === uid"> -->
        <el-row :gutter="20" v-if="form.uid === uid" style="height: 30px;">
          <el-col :span="18"
            ><el-button
              class="gaon-button"
              type="primary"
              @click="$router.push({ name: 'board-post-list' })"
              >목록</el-button
            ></el-col
          >
          <el-col :span="6" style="text-align: -webkit-right;">
            <el-button
              class="gaon-button"
              type="primary"
              @click="editPost($route.params.pid)"
              >수정</el-button
            >
            <el-popconfirm
              confirmButtonText="네"
              cancelButtonText="아니요"
              icon="el-icon-info"
              iconColor="red"
              title="정말 글을 삭제하시겠습니까?"
              @confirm="deletePost($route.params.pid)"
            >
              <template #reference>
                <el-button type="danger">삭제</el-button>
              </template>
            </el-popconfirm>
          </el-col>
        </el-row>
        <!-- </el-footer> -->
      </el-main>
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
    const route = useRoute();
    const url = `/boards/posts/${route.params.pid}`;
    // console.log("mounted")
    $axios
      .get(url)
      .then(res => {
        // console.log(res.data.post)
        this.form = res.data.post;
      })
      .catch(err => {
        console.log(err);
      });

    const fileURL = `/boards/posts/${route.params.pid}/files`;
    $axios
      .get(fileURL)
      .then(res => {
        console.log(res.data);
        res.data.forEach((element, idx, arr) =>
          this.fileList.push({
            pfid: arr[idx].pfid,
            filename: arr[idx].file_name
          })
        );
        // this.fileList
        // console.log(this.fileList)
        console.log("get uploaded files");
      })
      .catch(err => {
        console.log(err);
      });
  },
  data() {
    return {
      form: {
        id: 0,
        title: "",
        content: "",
        uid: 0,
        bid: 0,
        created_time: "",
        modified_time: ""
      },
      fileList: [],
      uid: 0
    };
  },
  methods: {
    async getDetail(pid) {
      console.log("get detail post");
      const url = `/boards/posts/${pid}`;
      await $axios
        .get(url)
        .then(res => {
          // console.log(res.data.posts);
          this.form = res.data.post;
          // console.log("makeboard ");
          // console.log("res.data");
        })
        .catch(err => {
          console.log(err);
        });
    },

    editPost(pid) {
      this.$router.push({ name: "board-post-modify" });
    },

    async deletePost(pid) {
      // console.log("delete post");
      const url = `/boards/posts/${pid}`;
      await $axios
        .delete(url)
        .then(res => {
          console.log("post is deleted");
          this.$router.push({ name: "board-post-list" });
        })
        .catch(err => {
          console.log(err);
        });
    },

    async download(pfid, filename) {
      console.log(pfid);
      const url = `/boards/posts/files/${pfid}`;
      await $axios
        .get(url, { responseType: "blob" })
        .then(res => {
          console.log(res);
          const fileurl = window.URL.createObjectURL(new Blob([res.data]));
          const link = document.createElement("a");
          link.href = fileurl;
          link.setAttribute("download", `${filename}`);
          document.body.appendChild(link);
          link.click();
        })
        .catch(err => {
          console.log(err);
        });
    },
    enterToBr(str) {
      if (str) return str.replace(/(?:\r\n|\r|\n)/g, "<br/>");
    }
  }
};
</script>
<style scoped>
.font {
  /* font-family:SpoqaHanSansNeo-Light; */
}
.pa-5 {
  /* font-family:SpoqaHanSansNeo-Medium; */
  font-size: initial;
}
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
  /* font-family: SpoqaHanSansNeo-Bold; */
}
.content {
  text-align: left;
  font-size: initial;
}
/* .horizontal-border {
    border-bottom: 1px solid #eee;
  } */
.vertical-border {
  content: "";
  display: inline-block;
  background: #ccc;
  width: 1px;
  height: 14px;
  margin: 0 10px 0 6px;
  vertical-align: -2px;
  /* font-size: ; */
}
.main-wrapper {
  background-color: white;
  /* padding: 20px 20px; */
}
.el-button {
  color: white;
}
</style>
