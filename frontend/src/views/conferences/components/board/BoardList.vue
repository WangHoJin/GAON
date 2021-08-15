<template lang="">
  <el-container class="main-wrapper">
    <el-contianer class="main-container">
      <!-- <div class="background"> -->
      <el-main>
        <el-row>
          <el-col :span="20">
            <el-card class="box-card">
              <template #header>
                <div class="card-header">
                  {{ boardName }}
                </div>
              </template>
              <div class="card-body">
                <i class="header-icon el-icon-info"></i>
                {{ boardDescription }}
              </div>
            </el-card>
            <div style="text-align:left; ">
              <!-- <h1 style="margin-bottom: 0px; margin-left:5px; ">
                게시글목록
              </h1> -->
            </div>
          </el-col>
          <el-col :span="4"
            ><el-button
              icon="el-icon-edit"
              circle
              class="write-btn"
              @click="boardCreate()"
            ></el-button
          ></el-col>

          <!-- <el-card
            id="title"
            style=" width: 100%;  background: linear-gradient(90deg, #f56c6c 1%, #fff 1%);     align-self: start;"
          >
            <h1>게시글 목록</h1>
          </el-card> -->
          <el-table
            empty-text="글이 없어요. 새 글을 작성해 주세요😥!"
            :data="pagedTableData"
            style="width: 100%"
            @current-change="handleCurrentChange"
          >
            <el-table-column prop="id" label="No" width="180">
            </el-table-column>
            <el-table-column prop="title" label="제목" :formatter="formatter">
            </el-table-column>
            <el-table-column prop="nickname" label="작성자" width="300">
            </el-table-column>
            <el-table-column prop="created_time" label="작성시간" width="300">
              <template #default="scope">
                <i class="el-icon-time"></i>
                <span style="margin-left: 10px">{{
                  scope.row.created_time
                }}</span>
              </template>
            </el-table-column>
          </el-table>
          <div
            align="center"
            style="
    width: 100%;
"
          >
            <el-pagination
              id="pg"
              background
              layout="prev, pager, next"
              :page-size="pageSize"
              :total="tableData.length"
              @current-change="setPage"
            >
            </el-pagination>

            <!-- <div class="right">
            <el-button placement="right-end">새 글 작성</el-button>
          </div> -->
          </div>
        </el-row>
      </el-main>
      <!-- </div> -->
    </el-contianer>
  </el-container>
</template>
<script>
import { useRouter, useRoute } from "vue-router";
import { computed, onMounted } from "vue";
import { useStore } from "vuex";
// API
import $axios from "axios";
export default {
  data() {
    return {
      tableData: [],
      page: 1,
      pageSize: 5,
      formLabelWidth: "120px",
      currentRow: null,
      boardName: null,
      boardDescription: null
    };
  },
  async mounted() {
    const route = useRoute();
    const url = `/boards/${route.params.bid}/posts`;
    console.log("mounted");
    await $axios
      .get(url)
      .then(res => {
        // console.log(res.data.posts);
        console.log(res.data.posts);
        this.tableData = res.data.posts;
        this.tableData.reverse();
        // crated time 뒤에 소수점 자르기
        this.tableData.filter(function(item, idx) {
          item.created_time = item.created_time.substring(0, 19);
          return item;
        });
      })
      .catch(err => {
        console.log(err);
      });
    // 게시판의 정보를 가져오기
    await $axios
      .get(`/boards/id/${this.$route.params.bid}`)
      .then(res => {
        console.log("게시판의 정보를 가져오기");
        console.log(res.data);
        console.log("게시판이름가져오기");
        console.log(res.data.name);
        this.boardName = res.data.name;
        console.log(this.boardName);
        this.boardDescription = res.data.description;
      })
      .catch(err => {
        console.log(err);
      });
  },
  async updated() {
    const route = useRoute();
    const url = `/boards/${route.params.bid}/posts`;
    console.log("updated");
    await $axios
      .get(url)
      .then(res => {
        console.log(res.data.posts);
        this.tableData = res.data.posts;
        this.tableData.reverse();
        // crated time 뒤에 소수점 자르기
        this.tableData.filter(function(item, idx) {
          item.created_time = item.created_time.substring(0, 19);
          return item;
        });
      })
      .catch(err => {
        console.log(err);
      });

    // 게시판의 정보를 가져오기
    await $axios
      .get(`/boards/id/${this.$route.params.bid}`)
      .then(res => {
        console.log("게시판의 정보를 가져오기");
        console.log(res.data);
        this.boardName = res.data.name;
        this.boardDescription = res.data.description;
      })
      .catch(err => {
        console.log(err);
      });
  },

  computed: {
    pagedTableData() {
      return this.tableData.slice(
        this.pageSize * this.page - this.pageSize,
        this.pageSize * this.page
      );
    }
  },

  methods: {
    formatter(row, column) {
      return row.title;
    },

    setPage(val) {
      this.page = val;
    },

    boardCreate() {
      this.$router.push({ name: "board-post-create" });
    },

    handleCurrentChange(val) {
      this.currentRow = val;
      // console.log(val.id)
      this.$router.push({
        name: "board-post-view",
        params: {
          pid: val.id
        }
      });
    },

    async getPosts(bid) {
      console.log("get posts");
      const url = `/boards/${bid}/posts`;
      await $axios
        .get(url)
        .then(res => {
          // console.log(res.data.posts);
          this.tableData = res.data.posts;
          this.tableData.reverse();
          // console.log("makeboard ");
          // console.log("res.data");
        })
        .catch(err => {
          console.log(err);
        });
    }
  }
};
</script>
<style scoped>
.right {
  float: right;
  width: 110px;
}
.gaon-button {
  background-color: #ffd04b;
  border: none;
}
.el-card {
  padding: 10px;
  border-radius: 35px;
}
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.text {
  font-size: 20px;
}

.item {
  margin-bottom: 18px;
  display: flex;
}
.card-body {
  display: flex;
}
.box-card {
  width: 200px;
  background: linear-gradient(90deg, #a2df14 7%, #fff 7%);
}
.el-table--fit {
  width: 100%;
  padding: 25px;
  border-radius: 35px;
}

.title .el-card__body {
  padding: 0px;
}
.background {
  width: 80%;
  background-color: #fcf3bf;
  z-index: -1;
}

.write-btn {
  position: absolute;
  right: 0px;
  margin-top: 90px;
  background-color: #a2df14;
  color: white;
  width: 50px;
  height: 50px;
  font-size: larger;
}
.write-btn:focus,
.write-btn:hover {
  background: #a2df14;
  border-color: #a2df14;
  color: #fff;
  transform: scale(1.2, 1.2);
}
#title {
  padding: 0px;
}
</style>
