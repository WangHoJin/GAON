<template lang="">
  <el-container class="main-wrapper">
    <el-contianer class="main-container">
      <el-main>
        <el-calendar>
          <template #dateCell="{data}">
            <div
              id="calendar-day"
              :class="data.isSelected ? 'is-selected' : ''"
              @click="selectday(data.day)"
            >
              {{
                data.day
                  .split("-")
                  .slice(1)
                  .join("-")
              }}
              {{ data.isSelected ? "✔️" : "" }}
            </div>
          </template>
        </el-calendar>
        <el-dialog title="출석부" v-model="dialogTableVisible" :before-close="beforeClose">
        <el-dialog v-model="modifyDialogVisible">
          </el-dialog>
          <el-table
            class="rollBook"
            :data="
              tableData.filter(
                data =>
                  !search ||
                  data.nickname.toLowerCase().includes(search.toLowerCase())
              )
            "
            height="300"
            style="width: 100%"
          >
            <el-table-column label="이름" prop="nickname"> </el-table-column>
            <el-table-column align="right">
              <template #header>
                <el-input
                  v-model="search"
                  size="mini"
                  placeholder="Type to search"
                />
              </template>
              <!-- 라디오 토글해주면 알아서 tableData의 state(출결상태)가 바뀜 -->
              <template #default="scope">
                <el-radio v-model="tableData[scope.$index].state" label="출석"
                  >출석</el-radio
                >
                <el-radio v-model="tableData[scope.$index].state" label="결석"
                  >결석</el-radio
                >
              </template>
            </el-table-column>
          </el-table>
          <el-row :gutter="20" style="margin-top:10px">
            <el-col :span="6" :offset="18">
              <el-button v-if="host_id == myid" @click="modifyRollbook">수정하기</el-button>
            </el-col>
          </el-row>

        </el-dialog>
      </el-main>
    </el-contianer>
  </el-container>
</template>
<script>
import $axios from "axios";
import { h } from 'vue';
import { createNamespacedHelpers } from 'vuex';
export default {
  data() {
    return {
      tableData: [],
      search: "",
      host_i: "",
      myid: "",
      dialogTableVisible: false,
      selectDay: String
    };
  },
  async mounted() {
    let roomInfo = await this.$store.dispatch(
      "getRoomById",
      this.$route.params.conferenceId
    );
    this.host_id = roomInfo.host_id;
    this.myid = JSON.parse(sessionStorage.getItem("userInfo")).id;
  },
  methods: {
    open1() {
      this.$notify({
        title: '알림',
        message: h('i', {style: 'color: teal'}, '변경사항이 저장되었습니다.'),
        type: 'success',
        duration: '1500'
      });
    },
    async beforeClose(done) {
      await $axios
        .get("/rollbook/" + this.$route.params.conferenceId + "/" + this.selectDay)
        .then(res => {
          let difference = false
          let step;
          for (step = 0; step < res.data.rollbooks.length; step++) {
            if (res.data.rollbooks[step].state != this.tableData[step].state) {
              difference = true
              break
            }
          }
          // console.log(difference)
          return difference
        })
        .then(difference => {
          if (difference) {
            this.$confirm('변경내용을 저장하시겠습니까?', {
              confirmButtonText: '저장',
              cancelButtonText: '저장 안함'
            })
                .then(_ => {
                  this.modifyRollbook()
                  done();
                  this.dialogVisible = false
                })
                .catch(_ => {
                  done();
                  this.dialogVisible = false
                });

          } else {
            this.$confirm('출석부를 종료하시겠습니까?', {
              confirmButtonText: '네',
              cancelButtonText: '아니요'
            })
                .then(_ => {
                  done();
                  this.dialogVisible = false
                })
                .catch(_ => {
                });
          }
        })
    },
    selectday(day) {
      console.log(day);
      this.selectDay = day
      $axios
        .get("/rollbook/" + this.$route.params.conferenceId + "/" + day)
        .then(res => {
          console.log(res.data.rollbooks);
          this.tableData = res.data.rollbooks;
          this.dialogTableVisible = true
        });
    },
    // 출석부 수정
    async modifyRollbook() {
      let rollbookList = []
      await this.tableData.forEach((item) => {
        rollbookList.push({
          date: item.date,
          rid: item.rid,
          state: item.state,
          uid: item.uid
        })
      })
      console.log(rollbookList);
      await $axios.post("/rollbook/", rollbookList).then(res => {
        console.log("res.data.rollbooks");
        console.log(res.data.rollbooks);
        this.open1()
      });
    }
  }
};
</script>
<style>
.is-selected {
  color: #1989fa;
}

#calendar-day {
  width: 100%;
  height: 100%;
  vertical-align: middle;
}
</style>
