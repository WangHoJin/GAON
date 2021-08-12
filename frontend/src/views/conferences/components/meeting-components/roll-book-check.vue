<template lang="">
  <div>
    <el-table
      :data="
        rollbookmemberList.filter(
          data =>
            !search ||
            data.nickname.toLowerCase().includes(search.toLowerCase())
        )
      "
      style="width: 100%"
    >
      <el-table-column label="이름" prop="nickname"> </el-table-column>
      <el-table-column align="right">
        <template #header>
          <el-input v-model="search" size="mini" placeholder="Type to search" />
        </template>
        <!-- 라디오 토글해주면 알아서 tableData의 state(출결상태)가 바뀜 -->
        <template #default="scope">
          <el-radio
            v-model="rollbookmemberList[scope.$index].state"
            label="출석"
            >출석</el-radio
          >
          <el-radio
            v-model="rollbookmemberList[scope.$index].state"
            label="결석"
            >결석</el-radio
          >
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>
<script>
export default {
  data() {
    return {
      rollbookmemberList: [],
      search: ""
    };
  },
  props: {
    publisher: undefined,
    subscribers: []
  },
  async created() {
    // 기본이 state가
    this.rollbookmemberList = await this.$store.dispatch(
      "getMembersByUsingRoomId",
      this.$route.params.conferenceId
    );
    // state 기본은 결석으로 설정해주기
    this.rollbookmemberList = this.rollbookmemberList.filter(function(item) {
      if (this.subscribers != undefined) {
        this.subscribers.forEach(sub => {
          const { idData } = JSON.parse(sub.stream.connection.data);
          console.log("찾는다ㅏㅏㅏ");
          console.log(idData);
        });
        console.log("찾아라ㅏㅏ");
        console.log(item);
      }
      item.state = "결석";
      return item;
    });
  }
};
</script>
<style lang=""></style>
