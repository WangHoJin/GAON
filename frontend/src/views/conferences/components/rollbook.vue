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
        <el-button @click="modifyRollbook">수정하기</el-button>
        <el-table
          :data="
            tableData.filter(
              data =>
                !search ||
                data.name.toLowerCase().includes(search.toLowerCase())
            )
          "
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
      </el-main>
    </el-contianer>
  </el-container>
</template>
<script>
import $axios from "axios";
export default {
  data() {
    return {
      tableData: [],
      search: ""
    };
  },

  methods: {
    selectday(day) {
      console.log(day);
      $axios
        .get("/rollbook/" + this.$route.params.conferenceId + "/" + day)
        .then(res => {
          console.log(res.data.rollbooks);
          this.tableData = res.data.rollbooks;
        });
    },
    // 출석부 수정
    modifyRollbook() {
      var rollbookList = this.tableData.filter(function(item, idx) {
        delete item.email;
        delete item.nickname;
        return item;
      });
      console.log(rollbookList);
      $axios.post("/rollbook/", rollbookList).then(res => {
        console.log("res.data.rollbooks");
        console.log(res.data.rollbooks);
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
