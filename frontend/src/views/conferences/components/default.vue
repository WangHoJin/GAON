<template lang="">
  <div class="container">
    <el-row align="middle" justify="center">
      <el-col :span="12">
        <el-card class="box-card" shadow="hover" style="margin-left:230px;">
          <template #header>
            <div class="card-header">
              <div class="icon-wrapper">
                <img
                  style="width: 50%; padding: 10px 10px;"
                  :src="require('@/assets/images/school-board.svg')"
                />
              </div>
            </div>
          </template>
          <h1>방 만들기</h1>
          <span style="color: gray">새로 방을 만들어 보세요!</span>
          <div>
            <el-button round @click="dialogFormVisible = true">Click</el-button>
          </div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card class="box-card" shadow="hover" style="margin-right:230px;">
          <template #header>
            <div class="card-header">
              <div class="icon-wrapper">
                <img
                  style="width: 50%; padding: 10px 10px;"
                  :src="require('@/assets/images/school-bus.svg')"
                />
              </div>
            </div>
          </template>
          <h1>참여하기</h1>
          <span style="color: gray">코드로 방에 입장하세요!</span>
          <div>
            <el-button round @click="dialogFormVisible_2 = true"
              >Click</el-button
            >
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
  <!-- 뒤로가기 버튼 -->
  <!-- <el-tooltip effect="dark" content="뒤로 가기" placement="bottom">
    <el-button
      id="back-btn"
      round
      v-if="$store.state.roomModule.isClickPlusBtn"
      @click="
        $store.state.roomModule.isClickPlusBtn = false;
        $router.go(-1);
      "
      ><img
        style="width: -webkit-fill-available;"
        :src="require('@/assets/images/back-icon.svg')"
    /></el-button>
  </el-tooltip> -->
  <!-- 로그아웃 버튼 -->
  <!-- <el-tooltip effect="dark" content="로그아웃" placement="bottom">
    <el-button
      id="logout-btn"
      round
      v-if="$store.state.roomModule.isClickPlusBtn"
      @click="signOut"
    >
      <img
        style="width: -webkit-fill-available;"
        :src="require('@/assets/images/exit.svg')"
    /></el-button>
  </el-tooltip> -->
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
          placeholder=""
        ></el-input>
      </el-form-item>
      <el-form-item
        style="font-family: none; "
        label="비밀번호를 설정해주세요🔒"
        :label-width="formLabelWidth"
        id="room-make-form-label"
      >
        <el-input id="pw" v-model="form.password" show-password></el-input>
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
        style="font-family: none; "
        label="비밀번호를 입력해주세요🔒"
        :label-width="formLabelWidth"
        id="room-make-form-label"
      >
        <el-input
          id="pw"
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
import store from "../../../common/lib/store";
import { mapGetters } from "vuex";
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
  components: {
    // Wave
  },
  mounted() {
    gapi.load("auth2", function() {
      gapi.auth2.init();
    });
  },
  beforeUnmount() {
    console.log("beforeUnmount");
    this.$store.state.roomModule.isClickPlusBtn = false;
  },
  methods: {
    async disconnectGoogle() {
      let authObject = await gapi.auth2.getAuthInstance();
      console.log("authObject: " + authObject);
      console.log(authObject);
      authObject.signOut().then(function() {
        console.log("user signed out");
      });
      await authObject.disconnect();
    },
    clearStorage() {
      sessionStorage.removeItem("userInfo");
      localStorage.clear();
      sessionStorage.clear();
    },
    async signOut() {
      try {
        await this.disconnectGoogle();
        this.clearStorage();
        this.$router.push("/googlelogin");
      } catch (error) {
        this.clearStorage();
        this.$router.push("/googlelogin");
      }
    },
    mouseright() {
      console.log("오른쪽 클릭");
    },
    // 방 참여하기
    async goConference() {
      //code를 보내면 rid를 받아옴
      let roomInfo = {
        code: this.form2.code,
        password: this.form2.password
      };
      // alert으로 코드가 틀렸을 때, 비밀번호가 틀렸을 때 알려줌
      const response = await this.$store.dispatch("verifyRoom", roomInfo);
      console.log("reponse from joinromm");
      console.log(response);
      if (response) {
        let codeResponse = await this.$store.dispatch(
          "getRoomByCode",
          this.form2.code
        );
        console.log("codeResponse from getRoombycode");
        console.log(codeResponse.id);
        // // 룸멤버추가
        let roomMemberInfo = {
          room_id: codeResponse.id,
          user_id: JSON.parse(sessionStorage.getItem("userInfo")).id
        };
        await this.$store.dispatch("addRoomMember", roomMemberInfo);
        // store에 있는 flag false
        // 방에 참가하기
        this.$router.push({
          name: "conference-main"
          // params: { conferenceId: codeResponse.id }
        });
      }
    },
    async makeRoom() {
      var roomInfo = {
        name: this.form.name,
        password: this.form.password,
        description: this.form.description,
        host_id: JSON.parse(sessionStorage.getItem("userInfo")).id // 방 생성자 아이디
      };
      // 방 생성
      const response = await this.$store.dispatch("createRoom", roomInfo);
      this.code = response.code;
      this.form.password = "";
      this.form.description = "";
      this.makeRoomFlag = false;
      // 방 생성 후 메인페이지로 보내기
      this.$router.push({ name: "conference-main" });
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
  height: 100%;
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
/* .iconify {
  margin-top: 200px;
} */
/* divider 설정 */
.el-divider--vertical {
  display: block;
  width: 1px;
  height: 100%;
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
.container {
  background-color: #f5df4d;
  background-size: 100% 100%;
  width: 100%;
  height: 100%;
}
.el-card {
  margin: auto auto;
  width: 55%;
  min-height: 500px;
  border-radius: 50px 50px;
  text-align: center;
}
.el-button {
  margin: 30px 0px;
  width: 100px;
  background-color: #f5df4d;
  border: none;
}
.el-dialog .el-button {
  margin: 4px 4px;
}
.el-button:focus,
.el-button:hover {
  color: #e67010;
}
#back-btn {
  position: fixed;
  top: 0px;
  /* height: 52px; */
  left: 29px;
  box-shadow: 2px 2px 3px #999;
  background-color: coral;
  /* background-color: #e67010; */
  width: 58px;
  padding: 10px 10px 6px 10px;
}
#pw {
  font-family: none;
}
.el-card:hover {
  /* box-shadow: 2px 2px 3px #999; */
  transform: scale(1.1, 1.1);
}
#back-btn:hover {
  /* box-shadow: 2px 2px 3px #999; */
  transform: scale(1.2, 1.2);
}
#logout-btn {
  position: fixed;
  top: 0px;
  /* height: 52px; */
  left: 120px;
  box-shadow: 2px 2px 3px #999;
  background-color: coral;
  /* background-color: #e67010; */
  width: 58px;
  padding: 10px 10px 6px 10px;
}
#logout-btn:hover {
  /* box-shadow: 2px 2px 3px #999; */
  transform: scale(1.2, 1.2);
}
</style>
