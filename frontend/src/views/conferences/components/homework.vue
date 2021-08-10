<template lang="">
  <el-container class="main-wrapper">
    <el-contianer class="main-container">
      <el-main>
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
            prop="name"
            label="작성자"
            width="180">
          </el-table-column>
          <el-table-column
            prop="date"
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
            <el-button placement="right-end" @click="dialogFormVisible = true">새 글 작성</el-button>
          </div>
        </div>

        <el-dialog title="과제 제출" v-model="dialogFormVisible">
          <el-form :model="form">
            <el-form-item label="제목" :label-width="formLabelWidth">
              <el-input v-model="form.title" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="내용" :label-width="formLabelWidth">
              <el-input v-model="form.content" autocomplete="off"></el-input>
            </el-form-item>
          </el-form>
          <template #footer>
            <span class="dialog-footer">
              <el-button type="info" @click="dialogFormVisible = false;submitCancle();">취소</el-button>
              <el-button type="primary" class="gaon-button" @click="dialogFormVisible = false;submitUpload();">과제 제출</el-button>
            </span>
          </template>
        </el-dialog>

      </el-main>
    </el-contianer>
  </el-container>
</template>
<script>
import { useRouter } from "vue-router";
  export default {
    data() {
      return {
        tableData: [{
          id: 1,
          date: '2016-05-03',
          name: 'Tom',
          title: '1번글'
        }, {
          id: 2,
          date: '2016-05-02',
          name: 'Tom',
          title: '2번글'
        }, {
          id: 3,
          date: '2016-05-04',
          name: 'Tom',
          title: '3번글'
        }, {
          id: 4,
          date: '2016-05-01',
          name: 'Tom',
          title: '4번글'
        }],
      page: 1,
      pageSize: 10,
      dialogFormVisible: false,
      form: {
          title: '',
          name: '',
          content: '',
          date1: '',
          date2: '',
          delivery: false,
          type: [],
          resource: '',
          desc: ''
        },
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

      submitCancle () {
        // console.log('cancle')
        this.form.title = ''
        this.form.content =''
      },

      submitUpload () {
        // console.log('submit')
        const pushId = this.tableData[this.tableData.length - 1].id + 1

        const date = new Date()
        let year = date.getFullYear();
        let month = (1 + date.getMonth());
        month = month >= 10 ? month : '0' + month;
        let day = date.getDate();
        day = day >= 10 ? day : '0' + day;
        const today = year + '-' + month + '-' + day;

        const username = JSON.parse(sessionStorage.getItem("userInfo")).nickname;

        this.tableData.push({
          id: pushId,
          date: today,
          name: username,
          title: this.form.title,
          content: this.form.content
        })
        // this.$refs.upload.submit()
        this.form.title = ''
        this.form.content =''
      },
      handleCurrentChange(val) {
        this.currentRow = val;
        // console.log(val.id)
        this.$router.push({
          name: 'table-detail',
          params: {
            tableId: val.id
          }
        })
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
