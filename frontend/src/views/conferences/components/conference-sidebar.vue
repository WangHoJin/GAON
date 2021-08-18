<template>
  <div style="padding-right:1px">
    <el-menu active-text-color="#ffd04b" class="sidebar-tool sidebar-menu">
      <!-- 유저 정보 조회 -->
      <el-divider style="margin-top:0px;margin-bottom:10px"></el-divider>
      <UserInfoBox />
      <el-divider style="margin:0px">
        <i class="el-icon-star-on"></i>
      </el-divider>
      <!-- 화상미팅 -->
      <el-button @click="meetingSelect()" id="meeting-btn" style="width:100%">
        <span
          class="iconify"
          data-inline="false"
          data-icon="noto:school"
          style="font-size: 100px;"
        ></span>
      </el-button>
      <!-- 출석부 -->
      <el-menu-item @click="rollBookSelect()">
        <i class="el-icon-s-management" style="margin-bottom:2px"></i>
        <span>출석부</span>
      </el-menu-item>
      <!-- 게시판 목록 -->
      <el-menu-item
        v-for="i in $store.getters.boards"
        :key="i.id"
        :index="i.id"
        @click="mvList(i.id)"
        @mousedown.right="mouseRightClick($route.params.conferenceId, i)"
        @mousedown.stop
      >
        <i class="el-icon-edit" style="margin-bottom:2px"></i>
        <span>{{ i.name }}</span>
      </el-menu-item>
      <!-- 게시판 추가 버튼 -->
      <el-button
        v-if="host_id === uid"
        @click="dialogFormVisible_board = true"
        icon="el-icon-plus"
        circle
        style="width: 30px; height: 30px; min-height: 0px; min-width: 0px; padding: 0px; margin-top:20px"
      ></el-button>
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
      <el-form :model="roomInfo" ref="roomInfo">
        <el-form-item
          label="게시판 이름을 입력해주세요"
          :label-width="formLabelWidth"
          id="room-make-form-label"
          prop="name"
          :rules="{
            required: true,
            message: '게시판 이름을 입력해주세요.',
            trigger: 'blur'
          }"
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
            @click="makeBoard(roomInfo, 'roomInfo')"
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
    </el-dialog>
    <!-- 방 생성 dialog 끝 -->
    <!-- 방 코드 확인 dialog끝 -->

    <el-dialog
      title="방 정보를 수정하시겠습니까?"
      v-model="showModifyDialog"
      width="30%"
    >
      <template #footer>
        <span class="dialog-footer">
          <el-button type="primary" @click="openModifyDialog()">네</el-button>
          <el-button @click="showModifyDialog = false" type="info"
            >아니오</el-button
          >
        </span>
      </template>
    </el-dialog>
    <!-- 방 정보 수정 확인 dialog 끝-->
    <!-- 방 정보 수정 dialog 시작 -->
    <el-dialog
      title="게시판 수정"
      v-model="dialogFormVisible_modifyUser"
      center
      top="5vh"
    >
      <el-divider></el-divider>
      <!-- 방 생성 dialog일 때 -->
      <el-form :model="selectedBoardInfo" ref="selectedBoardInfo">
        <el-form-item
          label="게시판 이름을 입력해주세요"
          :label-width="formLabelWidth"
          id="room-make-form-label"
          prop="name"
          :rules="{
            required: true,
            message: '게시판 이름을 입력해주세요.',
            trigger: 'blur'
          }"
        >
          <el-input
            v-model="selectedBoardInfo.name"
            autocomplete="off"
            placeholder=""
          ></el-input>
        </el-form-item>
        <el-form-item
          label="게시판 설명을 입력해주세요"
          :label-width="formLabelWidth"
          id="room-make-form-label"
        >
          <el-input v-model="selectedBoardInfo.description"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button
            class="gaon-button"
            type="warning"
            @click="modifyBoardInfo('selectedBoardInfo')"
            >적용하기</el-button
          >
          <el-button @click="dialogFormVisible_modifyUser = false" type="info"
            >취소</el-button
          >
          <el-popconfirm
            confirmButtonText="OK"
            cancelButtonText="No, Thanks"
            icon="el-icon-info"
            iconColor="red"
            title="Are you sure to delete this?"
            @confirm="deleteBoard"
          >
            <template #reference>
              <el-button type="danger">게시판 삭제</el-button>
            </template>
          </el-popconfirm>
        </span>
      </template>
    </el-dialog>
    <!-- 방 정보 수정 dialog 끝 -->
  </div>
</template>

<script>
import { reactive, computed, onMounted } from "vue";
import { useStore } from "vuex";
import { useRouter, useRoute } from "vue-router";
import UserInfoBox from "./user/user-info-box.vue";
// API
import $axios from "axios";
import axios from "axios";

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
      showModifyDialog: false,
      dialogFormVisible_modifyUser: false,
      selectedBoardInfo: {},
      uid: 0,
      host_id: 0,
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
  created() {
    const route = useRoute();
    $axios.get(`/rooms/id/${route.params.conferenceId}`).then(res => {
      this.host_id = res.data.host_id;
      this.uid = JSON.parse(sessionStorage.getItem("userInfo")).id;
      console.log(this.host_id);
    });
  },
  methods: {
    //해당 게시판으로 이동하기
    mvList(bid) {
      this.$router.push({ name: "board", params: { bid: bid } });
    },
    // 게시판 생성하기
    async makeBoard(roomInfo, form) {
      this.$refs[form].validate(valid => {
        if (valid) {
          console.log("게시판 생성");
          const url = "/boards";
          $axios
            .post(url, roomInfo)
            .then(res => {
              // console.log(res.data);
              // response = res.data;
              // console.log("makeboard ");
              // console.log("res.data");
              // boards 배열 갱신
              this.$store.dispatch(
                "getBoardsByRoomId",
                this.$route.params.conferenceId
              );
              // 모달창 닫기
              this.dialogFormVisible_board = false;
              this.roomInfo.name = "";
              this.roomInfo.description = "";
            })
            .catch(err => {
              console.log(err);
            });
        } else {
          console.log("error submit!");
          return false;
        }
      });
    },
    //게시판 삭제
    async deleteBoard() {
      const url = `/boards/${this.selectedBoardInfo.id}`;
      await $axios
        .delete(url)
        .then(res => {
          console.log("board is deleted");
          // boards 배열 갱신
          this.$store.dispatch(
            "getBoardsByRoomId",
            this.$route.params.conferenceId
          );
          this.dialogFormVisible_modifyUser = false;
        })
        .catch(err => {
          console.log(err);
        });
    },
    mouseRightClick(rid, boardInfo) {
      const url = `/rooms/id/${rid}`;
      console.log(boardInfo);
      this.selectedBoardInfo = {
        id: boardInfo.id,
        name: boardInfo.name,
        description: boardInfo.description
      };
      $axios
        .get(url)
        .then(res => {
          // console.log(res.data)
          if (
            res.data.host_id ==
            JSON.parse(sessionStorage.getItem("userInfo")).id
          ) {
            // console.log("로그인한 사용자는 방장입니다.")
            this.showModifyDialog = true;
            // console.log(this.showModifyDialog)
          }
        })
        .catch(err => {
          console.log(err);
        });
    },

    openModifyDialog() {
      this.showModifyDialog = false;
      this.dialogFormVisible_modifyUser = true;
    },

    modifyBoardInfo(form) {
      this.$refs[form].validate(valid => {
        if (valid) {
          const url = `/boards/${this.selectedBoardInfo.id}`;
          const form = {
            name: this.selectedBoardInfo.name,
            description: this.selectedBoardInfo.description
          };
          $axios
            .put(url, form)
            .then(res => {
              console.log(res);
              // boards 배열 갱신
              this.$store.dispatch(
                "getBoardsByRoomId",
                this.$route.params.conferenceId
              );
              this.dialogFormVisible_modifyUser = false;
            })
            .catch(err => {
              console.log(err);
            });
        } else {
          console.log("error submit!");
          return false;
        }
      });
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
.el-menu-item span {
  font-family: "BMHANNAPro";
}
</style>
