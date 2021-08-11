<template lang="">
  <el-container class="main-wrapper">
    <el-contianer class="main-container">
      <el-main>
        <!-- <el-button @click="getPosts($route.params.bid)"></el-button> -->
        <el-table
          :data="pagedTableData"
          style="width: 100%"
          @current-change="handleCurrentChange">
          <el-table-column
            prop="id"
            label="No"
            width="180">
          </el-table-column>
          <el-table-column
            prop="title"
            label="제목"
            :formatter="formatter">
          </el-table-column>
          <el-table-column
            prop="nickname"
            label="작성자"
            width="180">
          </el-table-column>
          <el-table-column
            prop="created_time"
            label="작성시간"
            width="180">
          </el-table-column>
        </el-table>
        <div>
          <el-pagination
            background
            layout="prev, pager, next"
            :page-size="pageSize"
            :total="tableData.length"
            @current-change="setPage">
          </el-pagination>

          <div class="right">
            <el-button placement="right-end" @click="boardCreate()">새 글 작성</el-button>
          </div>
        </div>
      </el-main>
    </el-contianer>
  </el-container>
</template>
<script>
import { useRouter, useRoute } from "vue-router";
import { computed, onMounted } from 'vue';
import { useStore } from "vuex";
// API
import $axios from "axios";
  export default {
    mounted() {
      // this.$nextTick(function() {
      //       // 모든 화면이 렌더링된 후 실행합니다.
      //   })
      const route = useRoute()
      const url = `/boards/${route.params.bid}/posts`;
      console.log("mounted")
        $axios
          .get(url)
          .then(res => {
            // console.log(res.data.posts);
            console.log(res.data.posts)
            this.tableData = res.data.posts
            this.tableData.reverse()
            // console.log("makeboard ");
            // console.log("res.data");
          })
          .catch(err => {
            console.log(err);
          });
    },
    updated() {
      const route = useRoute()
      const url = `/boards/${route.params.bid}/posts`;
      console.log("updated")
        $axios
          .get(url)
          .then(res => {
            // console.log(res.data.posts);
            console.log(res.data.posts)
            this.tableData = res.data.posts
            this.tableData.reverse()
            // console.log("makeboard ");
            // console.log("res.data");
          })
          .catch(err => {
            console.log(err);
          });
    },
    data() {
      return {
        tableData: [

        ],
      page: 1,
      pageSize: 10,
        formLabelWidth: '120px',
        currentRow: null
      }
    },

    computed: {
      pagedTableData() {
        return this.tableData.slice(this.pageSize * this.page - this.pageSize, this.pageSize * this.page)
      }
    },

    methods: {
      formatter(row, column) {
        return row.title;
      },

      setPage (val) {
        this.page = val
      },

      boardCreate () {
        this.$router.push({name: 'board-post-create'})
      },

      handleCurrentChange(val) {
        this.currentRow = val;
        // console.log(val.id)
        this.$router.push({
          name: 'board-post-view',
          params: {
            pid: val.id
          }
        })
      },

      async getPosts(bid) {
      console.log("get posts");
      const url = `/boards/${bid}/posts`;
      await $axios
        .get(url)
        .then(res => {
          // console.log(res.data.posts);
          this.tableData = res.data.posts
          this.tableData.reverse()
          // console.log("makeboard ");
          // console.log("res.data");
        })
        .catch(err => {
          console.log(err);
        });
    }

    }
  }
</script>
<style>
  .right {
        float: right;
        width: 110px;
      }
  .gaon-button {
  background-color: #ffd04b;
  border: none;
}
</style>
