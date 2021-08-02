<template lang="">
  <el-contianer class="main-container" align="center" id="gaon-start">
    <el-row>
      <el-col :span="11" @click="dialogFormVisible = true">
        <span
          id="make-room"
          class="iconify"
          data-inline="false"
          data-icon="bi:cloud-plus-fill"
          style="color: #ffd344; font-size: 200px;"
        ></span>
        <div>
          <el-button
            round
            type="text"
            @click="dialogFormVisible = true"
            @mousedown.right="mouseright()"
            @contextmenu.prevent
            >새로운 방 만들기</el-button
          >
        </div>
      </el-col>
      <el-col :span="2"> <el-divider direction="vertical"></el-divider></el-col>
      <el-col :span="11">
        <span
          class="iconify"
          data-inline="false"
          data-icon="bx:bxs-user-plus"
          style="color: #ffd344; font-size: 200px;"
        ></span>
        <div>
          <el-button round type="text" @click="dialogFormVisible_2 = true"
            >방에 참가하기</el-button
          >
        </div></el-col
      >
    </el-row>
  </el-contianer>
  <!-- dialog시작 -->
  <el-dialog
    title="새로운 방 만들기"
    v-model="dialogFormVisible"
    center
    top="10vh"
  >
    <el-divider></el-divider>
    <!-- 방 생성 dialog일 때 -->
    <el-form :model="form" v-if="makeRoomFlag">
      <el-form-item
        label="방 이름을 입력해주세요🏠"
        :label-width="formLabelWidth"
        id="room-make-form-label"
      >
        <el-input
          v-model="form.name"
          autocomplete="off"
          placeholder="방 이름은 중복될 수 없습니다."
        ></el-input>
      </el-form-item>
      <el-form-item
        label="비밀번호를 설정해주세요🔒"
        :label-width="formLabelWidth"
        id="room-make-form-label"
      >
        <el-input
          v-model="form.password"
          placeholder="비밀번호는 최소 4자리 이상이어야 합니다"
          show-password
        ></el-input>
      </el-form-item>
      <el-form-item
        label="방에 대해 설명해주세요!😀"
        :label-width="formLabelWidth"
        id="room-make-form-label"
        placeholder="자유롭게 적어주세요"
      >
        <el-input
          v-model="form.description"
          autocomplete="off"
          type="textarea"
          :rows="2"
          placeholder="자유롭게 적어주세요"
        ></el-input>
      </el-form-item>
    </el-form>
    <template #footer v-if="makeRoomFlag">
      <span class="dialog-footer">
        <el-button class="gaon-button" type="warning" @click="makeRoom()"
          >방 생성</el-button
        >
        <el-button
          @click="
            dialogFormVisible = false;
            form.name = '';
            form.password = '';
            form.description = '';
          "
          type="info"
          >취소</el-button
        >
      </span>
    </template>
    <!-- 방 생성 dialog 끝 -->
    <!-- 방 코드 확인 dialog시작 -->
    <el-form v-if="!makeRoomFlag">
      <el-form-item label="방 이름🏠"> {{ form.name }}</el-form-item>
      <el-form-item label="방 참여 코드🔑"> {{ code }}</el-form-item>
    </el-form>
    <template #footer v-if="!makeRoomFlag">
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible = false" type="info"
          >확인</el-button
        >
      </span>
    </template>
    <!-- 방 코드 확인 dialog끝 -->
  </el-dialog>
  <!-- 방 참가 dialog시작 -->
  <el-dialog
    title="방에 참여하기"
    v-model="dialogFormVisible_2"
    center
    top="10vh"
  >
    <el-divider></el-divider>
    <el-form :model="form2">
      <el-form-item
        label="방 코드를 입력해주세요🏠"
        :label-width="formLabelWidth"
        id="room-make-form-label"
      >
        <el-input
          v-model="form2.code"
          autocomplete="off"
          placeholder=""
        ></el-input>
      </el-form-item>
      <el-form-item
        label="비밀번호를 입력해주세요🔒"
        :label-width="formLabelWidth"
        id="room-make-form-label"
      >
        <el-input
          v-model="form2.password"
          placeholder=""
          show-password
        ></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button class="gaon-button" type="warning" @click="goConference()"
          >입장</el-button
        >
        <el-button
          @click="
            dialogFormVisible_2 = false;
            form2.code = '';
            form2.password = '';
          "
          type="info"
          >취소</el-button
        >
      </span>
    </template>
    <!-- 방 생성 dialog 끝 -->
  </el-dialog>
</template>
<script>
import $axios from "axios";
export default {
  data() {
    return {
      dialogFormVisible: false, // 방 생성 dialog
      dialogFormVisible_2: false, //방 참여 dialog
      makeRoomFlag: true, //방 생성 input dialog인지 확인용
      //방 생성 form
      form: {
        name: "", //방이름
        password: "", //방 비밀번호
        description: "" //방 설명
      },
      //방 참가 form
      form2: {
        code: "",
        password: ""
      },
      formLabelWidth: "120px",
      host_id: "", //방장아이디
      code: ""
    };
  },
  methods: {
    mouseright() {
      console.log("오른쪽 클릭");
    },
    // 회의실로 가기
    async goConference() {
      var self = this;
      console.log(this.form2);
      this.dialogFormVisible_2 = false;
      //code를 보내면 rid를 받아옴
      let roomInfo = await $axios.get("/room/code/" + this.form2.code);
      console.log(roomInfo);
      this.$router.push({
        name: "conference-detail",
        params: { conferenceId: roomInfo.data.id }
      });
    },
    makeRoom() {
      // this.dialogFormVisible = false;
      console.log(this.form);
      var roomInfo = {
        name: this.form.name,
        password: this.form.password,
        description: this.form.description,
        host_id: JSON.parse(sessionStorage.getItem("userInfo")).id, // 방 생성자 아이디
        code: "123" // 코드 임의생성
        // id: 1 // 룸 아이디 임의생성
      };
      console.log(roomInfo);
      $axios
        .post("/rooms", roomInfo)
        .then(res => {
          if (res.status === 200) {
            console.log("방 생성 성공");
            console.log(res);
            // dialog 데이터 초기화
            this.form.password = "";
            this.form.description = "";
            // 생성 후 새로운 dialog 띄우기 용
            this.makeRoomFlag = false;
            this.code = res.data.code;
          } else {
            alert("서버와 연결이 불안정합니다");
          }
        })
        .catch(err => console.log(err));
    }
  }
};
</script>
<style scoped>
.gaon-button {
  background-color: #ffd04b;
  border: none;
}
.el-row {
  margin-bottom: 20px;
}
.el-col {
  border-radius: 4px;
}
.bg-purple-dark {
  background: #99a9bf;
}
.bg-purple {
  background: #d3dce6;
}
.bg-purple-light {
  background: #e5e9f2;
}
.grid-content {
  border-radius: 4px;
  min-height: 720px;
}
.row-bg {
  padding: 10px 0;
  background-color: #f9fafc;
}
/* 시작페이지 글자 크기 조정 */
#gaon-start .el-button {
  font-size: 30px;
  color: black;
}
/* 수직 가운데 배치 */
.iconify {
  margin-top: 200px;
}
/* divider 설정 */
.el-divider--vertical {
  display: inline-block;
  width: 1px;
  height: 710px;
  margin: 0 8px;
}
/* 방만들기 아이콘 크기 조절 */
#make-room {
  width: 170px !important;
}
/* 모달창 글자 왼쪽정렬 */
.el-form-item #room-make-form-label {
  text-align: left !important;
}
/* 모달창 안 라벨 정렬 */
.el-form-item {
  display: -webkit-box;
  display: -ms-flexbox;
  display: block;
  margin-bottom: 22px;
  padding: 5px;
}
</style>
