<template lang="">
  <el-container class="main-wrapper">
    <el-contianer class="main-container">
      <el-main>
        <el-table
          :data="pagedTableData"
          style="width: 100%">
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

        <el-pagination
          background
          layout="prev, pager, next"
          :page-size="pageSize"
          :total="tableData.length"
          @current-change="setPage">
        </el-pagination>

        <div class="right">
          <el-button placement="right-end" @click="dialogFormVisible = true">과제 제출</el-button>
        </div>

        <el-dialog title="과제 제출" v-model="dialogFormVisible">
          <el-form :model="form">
            <el-form-item label="제목" :label-width="formLabelWidth">
              <el-input v-model="form.name" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="내용" :label-width="formLabelWidth">
              <el-input v-model="form.content" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="파일첨부" :label-width="formLabelWidth">
              <el-upload
              class="upload-demo"
              drag
              action="https://jsonplaceholder.typicode.com/posts/"
              :on-preview="handlePreview"
              :on-remove="handleRemove"
              :file-list="fileList"
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
            </el-form-item>
          </el-form>
          <template #footer>
            <span class="dialog-footer">
              <el-button @click="dialogFormVisible = false">Cancel</el-button>
              <el-button type="primary" @click="dialogFormVisible = false">Confirm</el-button>
            </span>
          </template>
        </el-dialog>
      </el-main>
    </el-contianer>
  </el-container>
</template>
<script>
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
          name: '',
          content: '',
          date1: '',
          date2: '',
          delivery: false,
          type: [],
          resource: '',
          desc: ''
        },
        formLabelWidth: '120px'
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


    }
  }
</script>
<style>
  .right {
        float: right;
        width: 110px;
      }
</style>
ㄴ
