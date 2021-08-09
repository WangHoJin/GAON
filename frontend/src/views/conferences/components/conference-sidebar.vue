<template>
  <el-menu active-text-color="#ffd04b" class="sidebar-tool sidebar-menu">
    <el-menu-item
      v-for="i in $store.getters.boards"
      :key="i.id"
      :index="i.id"
      @click="mvList(i.id)"
    >
      <span>{{ i.name }}</span>
    </el-menu-item>
    <el-menu-item @click="rollBookSelect()">
      출석부
    </el-menu-item>
    <el-button @click="meetingSelect()" id="meeting-btn">
      <span
        class="iconify"
        data-inline="false"
        data-icon="noto:school"
        style="font-size: 100px;"
      ></span
    ></el-button>
    <el-button @click="dialogFormVisible_board = true">게시판 생성</el-button>
    <UserInfoBox />
  </el-menu>
  <!-- 게시판 생성 시작 -->
  <el-dialog
    title="새 게시판 만들기"
    v-model="dialogFormVisible_board"
    center
    top="10vh"
  >
    <el-divider></el-divider>
    <!-- 방 생성 dialog일 때 -->
    <el-form :model="roomInfo">
      <el-form-item
        label="게시판 이름을 입력해주세요"
        :label-width="formLabelWidth"
        id="room-make-form-label"
      >
        <el-input
          v-model="roomInfo.name"
          autocomplete="off"
          placeholder=""
        ></el-input>
      </el-form-item>
      <el-form-item
        label="게시판 설명을 입력해주세요"
        :label-width="formLabelWidth"
        id="room-make-form-label"
      >
        <el-input v-model="roomInfo.description"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button
          class="gaon-button"
          type="warning"
          @click="makeBoard(roomInfo)"
          >게시판 생성</el-button
        >
        <el-button
          @click="
            dialogFormVisible_board = false;
            roomInfo.name = '';
            roomInfo.description = '';
          "
          type="info"
          >취소</el-button
        >
      </span>
    </template>
    <!-- 방 생성 dialog 끝 -->
    <!-- 방 코드 확인 dialog끝 -->
  </el-dialog>
</template>

<script>
import { reactive, computed } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import UserInfoBox from "./user/user-info-box.vue";
// API
import $axios from "axios";

export default {
  name: "sidebar-tool",

  props: {
    width: {
      type: String,
      default: "240px"
    }
  },
  data() {
    return {
      boards: [], // 해당 방의 게시판들을 저장할 배열
      dialogFormVisible_board: false, // 게시판 생성 모달
      roomInfo: {
        rid: this.$route.params.conferenceId,
        name: "",
        description: ""
      }
    };
  },
  components: {
    UserInfoBox
  },
  methods: {
    //해당 게시판으로 이동하기
    mvList(bid) {
      this.$router.push({ name: "board", params: { bid: bid } });
    },
    // 게시판 생성하기
    async makeBoard(roomInfo) {
      console.log("게시판 생성");
      const url = "/boards";
      await $axios
        .post(url, roomInfo)
        .then(res => {
          console.log(res.data);
          response = res.data;
          console.log("makeboard ");
          console.log("res.data");
        })
        .catch(err => {
          console.log(err);
        });
      // boards 배열 갱신
      await this.$store.dispatch(
        "getBoardsByRoomId",
        this.$route.params.conferenceId
      );
      // 모달창 닫기
      this.dialogFormVisible_board = false;
      this.roomInfo.name = "";
      this.roomInfo.description = "";
    },
    rollBookSelect() {
      this.$router.push({
        name: "conference-rollbook"
      });
    },
    meetingSelect() {
      console.log("click startMeetingBtn");
      this.$router.push({
        name: "conference-meeting"
      });
    }
  }
};
</script>
<style>
#meeting-btn {
  bottom: 0;
  padding: 10px 10px;
  border: none;
  outline: none;
}
.el-menu {
  width: 100%;
  height: 100%;
  /* background-color: #ffe195b3; */
}
.sidebar-tool .el-menu {
  margin-top: 0;
  padding-left: 0;
}

.sidebar-tool .sidebar-menu {
  height: 100%;
  position: fixed;
}
.sidebar-tool .sidebar-menu .el-menu {
  height: 100%;
}
.sidebar-tool .el-menu .el-menu-item {
  cursor: pointer;
  border-right: none;
}
</style>
