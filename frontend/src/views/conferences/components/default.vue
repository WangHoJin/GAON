<template lang="">
  <div class="container">
    <el-row align="middle" justify="center">
      <el-col :span="12">
        <el-card class="box-card">
          <template #header>
            <div class="card-header">
              <div class="icon-wrapper">
                <img style="width: 50%; padding: 10px 10px;" :src="src1" />
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
        <el-card class="box-card">
          <template #header>
            <div class="card-header">
              <div class="icon-wrapper">
                <!-- 초록 -->
                <img style="width: 50%; padding: 10px 10px;" :src="src2" />
              </div>
            </div>
          </template>
          <h1>참여하기</h1>
          <span style="color: gray">코드로 방에 입장하세요!</span>
          <div>
            <el-button round @click="dialogFormVisible = true">Click</el-button>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
  <el-button
    v-if="$store.state.roomModule.isClickPlusBtn"
    @click="
      $store.state.roomModule.isClickPlusBtn = false;
      $router.go(-1);
    "
    >뒤로가기</el-button
  >
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

  beforeUnmount() {
    console.log("beforeUnmount");
    this.$store.state.roomModule.isClickPlusBtn = false;
  },
  methods: {
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
  },
  data() {
    return {
      src1:
        "data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZlcnNpb249IjEuMSIgeG1sbnM6eGxpbms9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkveGxpbmsiIHhtbG5zOnN2Z2pzPSJodHRwOi8vc3ZnanMuY29tL3N2Z2pzIiB3aWR0aD0iNTEyIiBoZWlnaHQ9IjUxMiIgeD0iMCIgeT0iMCIgdmlld0JveD0iMCAwIDUxMiA1MTIiIHN0eWxlPSJlbmFibGUtYmFja2dyb3VuZDpuZXcgMCAwIDUxMiA1MTIiIHhtbDpzcGFjZT0icHJlc2VydmUiIGNsYXNzPSIiPjxnPjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+PGNpcmNsZSBjeD0iMjU2IiBjeT0iMjU2IiBmaWxsPSIjZjVkZjRkIiByPSIyNTYiIGRhdGEtb3JpZ2luYWw9IiNmZmQzMDAiIHN0eWxlPSIiIGNsYXNzPSIiPjwvY2lyY2xlPjxnPjxwYXRoIGQ9Im01MTIgMjU2YzAtMTkuODcyLTIuMjY1LTM5LjIxNS02LjU0OS01Ny43ODVsLTk5LjU1My05OS41NTMtNjguMTI2IDI5LjAwMS00OC4yNDgtNDguMjQ4LTIwNC4zMzMgMjQzLjc0OCA3Mi45MjYgNzIuOTI2LTE4LjA4NyAzNi40OTcgNzYuMzYyIDc2LjM2MWMxMi45MDkgMi4wMDUgMjYuMTM2IDMuMDUzIDM5LjYwOCAzLjA1MyAxNDEuMzg1IDAgMjU2LTExNC42MTUgMjU2LTI1NnoiIGZpbGw9IiNmZjllMDAiIGRhdGEtb3JpZ2luYWw9IiNmZjllMDAiIHN0eWxlPSIiIGNsYXNzPSIiPjwvcGF0aD48L2c+PGc+PHBhdGggZD0ibTE3MC4wMyA0MzIuNTg2aC0zMGw0MC0xMzcuMjhoMzB6IiBmaWxsPSIjNTQ0ZjU2IiBkYXRhLW9yaWdpbmFsPSIjNTQ0ZjU2IiBzdHlsZT0iIiBjbGFzcz0iIj48L3BhdGg+PHBhdGggZD0ibTMzNS45NDkgNDMyLjU4NmgzMGwtNDAtMTM3LjI4aC0zMHoiIGZpbGw9IiM0MDNiNDEiIGRhdGEtb3JpZ2luYWw9IiM0MDNiNDEiIHN0eWxlPSIiIGNsYXNzPSIiPjwvcGF0aD48cGF0aCBkPSJtMTA2LjEwMiA5OC42NjJ2MTg1Ljc2Mmw3NC4wMzYgMTcuNzM3IDc1Ljg2Mi0xNy43MzcgOC44NjktODAuODItOC44NjktMTA0Ljk0MnoiIGZpbGw9IiNhZGUyMzIiIGRhdGEtb3JpZ2luYWw9IiNhZGUyMzIiIHN0eWxlPSIiIGNsYXNzPSIiPjwvcGF0aD48cGF0aCBkPSJtNDA1Ljg5OCA5OC42NjJoLTE0OS44OTh2MTg1Ljc2Mmw3NC45NDkgMTcuNzM3IDc0Ljk0OS0xNy43Mzd6IiBmaWxsPSIjM2RiMjMyIiBkYXRhLW9yaWdpbmFsPSIjOGRjODA1IiBzdHlsZT0iIiBjbGFzcz0iIj48L3BhdGg+PHBhdGggZD0ibTg1LjE5MSAyODQuNjY5djM4LjQ5NGgxNzAuODA5bDguODY5LTIxLjY0NC04Ljg2OS0xNi44NXoiIGZpbGw9IiNiMTcwNWIiIGRhdGEtb3JpZ2luYWw9IiNiMTcwNWIiIHN0eWxlPSIiIGNsYXNzPSIiPjwvcGF0aD48cGF0aCBkPSJtMjU2IDI4NC42NjloMTcwLjgwOXYzOC40OTVoLTE3MC44MDl6IiBmaWxsPSIjODg1MTQwIiBkYXRhLW9yaWdpbmFsPSIjODg1MTQwIiBzdHlsZT0iIiBjbGFzcz0iIj48L3BhdGg+PHBhdGggZD0ibTIyMi40NzcgNzkuNDE0djM4LjQ5NWgzMy41MjNsOC44NjktMjEuNzU0LTguODY5LTE2Ljc0MXoiIGZpbGw9IiNiMTcwNWIiIGRhdGEtb3JpZ2luYWw9IiNiMTcwNWIiIHN0eWxlPSIiIGNsYXNzPSIiPjwvcGF0aD48cGF0aCBkPSJtMjU2IDc5LjQxNGgzMy41MjN2MzguNDk1aC0zMy41MjN6IiBmaWxsPSIjODg1MTQwIiBkYXRhLW9yaWdpbmFsPSIjODg1MTQwIiBzdHlsZT0iIiBjbGFzcz0iIj48L3BhdGg+PC9nPjwvZz48L2c+PC9zdmc+",
      src2:
        "data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZlcnNpb249IjEuMSIgeG1sbnM6eGxpbms9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkveGxpbmsiIHhtbG5zOnN2Z2pzPSJodHRwOi8vc3ZnanMuY29tL3N2Z2pzIiB3aWR0aD0iNTEyIiBoZWlnaHQ9IjUxMiIgeD0iMCIgeT0iMCIgdmlld0JveD0iMCAwIDUxMiA1MTIiIHN0eWxlPSJlbmFibGUtYmFja2dyb3VuZDpuZXcgMCAwIDUxMiA1MTIiIHhtbDpzcGFjZT0icHJlc2VydmUiIGNsYXNzPSIiPjxnPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIGlkPSJzdXJmYWNlMSI+CjxwYXRoIGQ9Ik0gNTEyIDI1NiBDIDUxMiAzOTcuMzg2NzE5IDM5Ny4zODY3MTkgNTEyIDI1NiA1MTIgQyAxMTQuNjEzMjgxIDUxMiAwIDM5Ny4zODY3MTkgMCAyNTYgQyAwIDExNC42MTMyODEgMTE0LjYxMzI4MSAwIDI1NiAwIEMgMzk3LjM4NjcxOSAwIDUxMiAxMTQuNjEzMjgxIDUxMiAyNTYgWiBNIDUxMiAyNTYgIiBzdHlsZT0iIiBmaWxsPSIjM2RiMjMyIiBkYXRhLW9yaWdpbmFsPSIjNjViMmZmIiBjbGFzcz0iIj48L3BhdGg+CjxwYXRoIGQ9Ik0gNTEyIDI1NiBDIDUxMiAyODIuMDUwNzgxIDUwOC4xMDkzNzUgMzA3LjE5MTQwNiA1MDAuODcxMDk0IDMzMC44NzEwOTQgQyA0NjguODU5Mzc1IDQzNS43MTg3NSAzNzEuMzI4MTI1IDUxMiAyNTYgNTEyIEMgMTUxLjQ0MTQwNiA1MTIgNjEuNTExNzE5IDQ0OS4zMDA3ODEgMjEuNzY5NTMxIDM1OS40NDkyMTkgQyA3Ljc2OTUzMSAzMjcuODIwMzEyIDAgMjkyLjgyMDMxMiAwIDI1NiBDIDAgMjA5Ljc4MTI1IDEyLjI1IDE2Ni40Mjk2ODggMzMuNjc5Njg4IDEyOSBDIDc3LjgwMDc4MSAyMDYuMDcwMzEyIDE2MC44Mzk4NDQgMjU4IDI1NiAyNTggQyAzNTEuMTYwMTU2IDI1OCA0MzQuMjEwOTM4IDIwNi4wNzAzMTIgNDc4LjMyMDMxMiAxMjkgQyA0OTkuNzUgMTY2LjQyOTY4OCA1MTIgMjA5Ljc4MTI1IDUxMiAyNTYgWiBNIDUxMiAyNTYgIiBzdHlsZT0iIiBmaWxsPSIjM2RiMjMyIiBkYXRhLW9yaWdpbmFsPSIjMGY5YmU4IiBjbGFzcz0iIj48L3BhdGg+CjxwYXRoIGQ9Ik0gNDgxLjI2MTcxOSAzNzcuNjk5MjE5IEMgNDc0LjEwMTU2MiAzOTAuOTIxODc1IDQ2NS44MjgxMjUgNDAzLjQ0MTQwNiA0NTYuNTM5MDYyIDQxNS4xMjg5MDYgQyA0NDguMTAxNTYyIDQyNS43NjE3MTkgNDM4LjgyMDMxMiA0MzUuNjk5MjE5IDQyOC44MjAzMTIgNDQ0Ljg1MTU2MiBDIDM4My4yODEyNSA0ODYuNTUwNzgxIDMyMi42MDkzNzUgNTEyIDI1NiA1MTIgQyAxODkuMzkwNjI1IDUxMiAxMjguNzE4NzUgNDg2LjU1MDc4MSA4My4xNzk2ODggNDQ0Ljg1MTU2MiBDIDczLjE3OTY4OCA0MzUuNjk5MjE5IDYzLjg5ODQzOCA0MjUuNzYxNzE5IDU1LjQ2MDkzOCA0MTUuMTI4OTA2IEMgNDYuMTcxODc1IDQwMy40NDE0MDYgMzcuODk4NDM4IDM5MC45MjE4NzUgMzAuNzM4MjgxIDM3Ny42OTkyMTkgWiBNIDQ4MS4yNjE3MTkgMzc3LjY5OTIxOSAiIHN0eWxlPSIiIGZpbGw9IiM3NDdlOWUiIGRhdGEtb3JpZ2luYWw9IiM3NDdlOWUiIGNsYXNzPSIiPjwvcGF0aD4KPHBhdGggZD0iTSA0MjguODIwMzEyIDQ0NC44NTE1NjIgQyAzODMuMjgxMjUgNDg2LjU1MDc4MSAzMjIuNjA5Mzc1IDUxMiAyNTYgNTEyIEMgMTg5LjM5MDYyNSA1MTIgMTI4LjcxODc1IDQ4Ni41NTA3ODEgODMuMTc5Njg4IDQ0NC44NTE1NjIgWiBNIDQyOC44MjAzMTIgNDQ0Ljg1MTU2MiAiIHN0eWxlPSIiIGZpbGw9IiM0NzRmNmYiIGRhdGEtb3JpZ2luYWw9IiM0NzRmNmYiPjwvcGF0aD4KPHBhdGggZD0iTSAzOC4zMzIwMzEgMzQwLjAxNTYyNSBMIDQ3My42NjQwNjIgMzQwLjAxNTYyNSBMIDQ3My42NjQwNjIgMzc4LjUzNTE1NiBMIDM4LjMzMjAzMSAzNzguNTM1MTU2IFogTSAzOC4zMzIwMzEgMzQwLjAxNTYyNSAiIHN0eWxlPSIiIGZpbGw9IiMzMjNiNWEiIGRhdGEtb3JpZ2luYWw9IiMzMjNiNWEiPjwvcGF0aD4KPHBhdGggZD0iTSA0NzMuNjY3OTY5IDIxMS4yNDYwOTQgTCA0NzMuNjY3OTY5IDM4NS45MTAxNTYgTCA0NTIuNTkzNzUgMzg1LjkxMDE1NiBDIDQ0OC44NTE1NjIgMzg1LjkxMDE1NiA0NDUuNjk5MjE5IDM4My4xNDA2MjUgNDQ1LjE3MTg3NSAzNzkuNDMzNTk0IEMgNDQzLjY2NDA2MiAzNjguNzI2NTYyIDQzOC40NTMxMjUgMzU5LjE5OTIxOSA0MzAuODc4OTA2IDM1Mi4yMDMxMjUgQyA0MjIuNjk1MzEyIDM0NC42MzY3MTkgNDExLjc1MzkwNiAzNDAuMDE1NjI1IDM5OS43MjY1NjIgMzQwLjAxNTYyNSBDIDM4Ny43MTQ4NDQgMzQwLjAxNTYyNSAzNzYuNzY5NTMxIDM0NC42NDA2MjUgMzY4LjU4NTkzOCAzNTIuMjAzMTI1IEMgMzYxLjAxNTYyNSAzNTkuMTk5MjE5IDM1NS44MDQ2ODggMzY4LjcyNjU2MiAzNTQuMjkyOTY5IDM3OS40MzM1OTQgQyAzNTMuNzY5NTMxIDM4My4xNDA2MjUgMzUwLjYxMzI4MSAzODUuOTEwMTU2IDM0Ni44NzEwOTQgMzg1LjkxMDE1NiBMIDE1Ni41MzkwNjIgMzg1LjkxMDE1NiBDIDE1NC4xOTUzMTIgMzg1LjkxMDE1NiAxNTIuMjc3MzQ0IDM4NC4xMDE1NjIgMTUyLjA2NjQwNiAzODEuNzY5NTMxIEMgMTUxLjAzMTI1IDM3MC4xMDU0NjkgMTQ1LjYyODkwNiAzNTkuNjk5MjE5IDEzNy41MDc4MTIgMzUyLjIwMzEyNSBDIDEyOS4zMjAzMTIgMzQ0LjYzNjcxOSAxMTguMzc1IDM0MC4wMTU2MjUgMTA2LjM1NTQ2OSAzNDAuMDE1NjI1IEMgOTQuMzM1OTM4IDM0MC4wMTU2MjUgODMuMzkwNjI1IDM0NC42NDA2MjUgNzUuMjA3MDMxIDM1Mi4yMDMxMjUgQyA2Ny4wODk4NDQgMzU5LjY5OTIxOSA2MS42ODM1OTQgMzcwLjEwNTQ2OSA2MC42NDQ1MzEgMzgxLjc2OTUzMSBDIDYwLjQzNzUgMzg0LjEwMTU2MiA1OC41MTk1MzEgMzg1LjkxMDE1NiA1Ni4xNzU3ODEgMzg1LjkxMDE1NiBMIDM4LjUyMzQzOCAzODUuOTEwMTU2IEwgMzguNTIzNDM4IDI5Ni41OTc2NTYgQyAzOC41MjM0MzggMjkxLjkzNzUgNDEuNTQyOTY5IDI4Ny44MDg1OTQgNDUuOTkyMTg4IDI4Ni4zOTg0MzggTCA3Mi44Mzk4NDQgMjc3Ljg3NSBMIDg3LjM5ODQzOCAyMTUuMjI2NTYyIEwgODkuMTA1NDY5IDIwNy45MDIzNDQgQyA5MC42NjAxNTYgMjAxLjIwMzEyNSA5Ni42MjUgMTk2LjQ2NDg0NCAxMDMuNTAzOTA2IDE5Ni40NjQ4NDQgTCA0NTguODgyODEyIDE5Ni40NjQ4NDQgQyA0NjcuMDUwNzgxIDE5Ni40NjA5MzggNDczLjY2Nzk2OSAyMDMuMDc4MTI1IDQ3My42Njc5NjkgMjExLjI0NjA5NCBaIE0gNDczLjY2Nzk2OSAyMTEuMjQ2MDk0ICIgc3R5bGU9IiIgZmlsbD0iI2ZmZDExMiIgZGF0YS1vcmlnaW5hbD0iI2ZmZDExMiI+PC9wYXRoPgo8cGF0aCBkPSJNIDQ3My42Njc5NjkgMjExLjI0NjA5NCBMIDQ3My42Njc5NjkgMzg1LjkxMDE1NiBMIDQ1Mi41OTM3NSAzODUuOTEwMTU2IEMgNDQ4Ljg1MTU2MiAzODUuOTEwMTU2IDQ0NS42OTkyMTkgMzgzLjE0NDUzMSA0NDUuMTc1NzgxIDM3OS40Mzc1IEMgNDQzLjY2NDA2MiAzNjguNzIyNjU2IDQzOC40NTMxMjUgMzU5LjE5OTIxOSA0MzAuODc4OTA2IDM1Mi4yMDMxMjUgQyA0MjIuNjk1MzEyIDM0NC42MzY3MTkgNDExLjc1MzkwNiAzNDAuMDE1NjI1IDM5OS43MjY1NjIgMzQwLjAxNTYyNSBDIDM4Ny43MTQ4NDQgMzQwLjAxNTYyNSAzNzYuNzczNDM4IDM0NC42NDA2MjUgMzY4LjU4OTg0NCAzNTIuMjAzMTI1IEMgMzYxLjAxMTcxOSAzNTkuMTk5MjE5IDM1NS44MDQ2ODggMzY4LjcyMjY1NiAzNTQuMjkyOTY5IDM3OS40Mzc1IEMgMzUzLjc2OTUzMSAzODMuMTQ0NTMxIDM1MC42MTcxODggMzg1LjkxMDE1NiAzNDYuODc1IDM4NS45MTAxNTYgTCAyNjkuODk4NDM4IDM4NS45MTAxNTYgTCAyNjkuODk4NDM4IDE5Ni40NjA5MzggTCA0NTguODgyODEyIDE5Ni40NjA5MzggQyA0NjcuMDQ2ODc1IDE5Ni40NjA5MzggNDczLjY2Nzk2OSAyMDMuMDgyMDMxIDQ3My42Njc5NjkgMjExLjI0NjA5NCBaIE0gNDczLjY2Nzk2OSAyMTEuMjQ2MDk0ICIgc3R5bGU9IiIgZmlsbD0iI2ZmOWYwMCIgZGF0YS1vcmlnaW5hbD0iI2ZmOWYwMCIgY2xhc3M9IiI+PC9wYXRoPgo8cGF0aCBkPSJNIDIyMS43NjE3MTkgMjkxLjM1OTM3NSBMIDQ3My42NjQwNjIgMjkxLjM1OTM3NSBMIDQ3My42NjQwNjIgMzAxLjEyMTA5NCBMIDIyMS43NjE3MTkgMzAxLjEyMTA5NCBaIE0gMjIxLjc2MTcxOSAyOTEuMzU5Mzc1ICIgc3R5bGU9IiIgZmlsbD0iI2ZmZmZmZiIgZGF0YS1vcmlnaW5hbD0iI2ZmZmZmZiIgY2xhc3M9IiI+PC9wYXRoPgo8cGF0aCBkPSJNIDIyMS43NjE3MTkgMzE3LjE2MDE1NiBMIDQ3My42NjQwNjIgMzE3LjE2MDE1NiBMIDQ3My42NjQwNjIgMzI2LjkyMTg3NSBMIDIyMS43NjE3MTkgMzI2LjkyMTg3NSBaIE0gMjIxLjc2MTcxOSAzMTcuMTYwMTU2ICIgc3R5bGU9IiIgZmlsbD0iI2ZmZmZmZiIgZGF0YS1vcmlnaW5hbD0iI2ZmZmZmZiIgY2xhc3M9IiI+PC9wYXRoPgo8cGF0aCBkPSJNIDc1LjIwNzAzMSAzNTIuMjAzMTI1IEMgNjcuMDg5ODQ0IDM1OS42OTkyMTkgNjEuNjgzNTk0IDM3MC4xMDU0NjkgNjAuNjQ0NTMxIDM4MS43Njk1MzEgQyA2MC40Mzc1IDM4NC4xMDE1NjIgNTguNTE5NTMxIDM4NS45MTAxNTYgNTYuMTc1NzgxIDM4NS45MTAxNTYgTCAzOC41MjM0MzggMzg1LjkxMDE1NiBMIDM4LjUyMzQzOCAzNTIuMjAzMTI1IFogTSA3NS4yMDcwMzEgMzUyLjIwMzEyNSAiIHN0eWxlPSIiIGZpbGw9IiM0NzRmNmYiIGRhdGEtb3JpZ2luYWw9IiM0NzRmNmYiPjwvcGF0aD4KPHBhdGggZD0iTSAzNjguNTg1OTM4IDM1Mi4yMDMxMjUgQyAzNjEuMDExNzE5IDM1OS4xOTkyMTkgMzU1LjgwNDY4OCAzNjguNzIyNjU2IDM1NC4yOTI5NjkgMzc5LjQzNzUgQyAzNTMuNzY5NTMxIDM4My4xNDQ1MzEgMzUwLjYxNzE4OCAzODUuOTEwMTU2IDM0Ni44NzEwOTQgMzg1LjkxMDE1NiBMIDE1Ni41MzUxNTYgMzg1LjkxMDE1NiBDIDE1NC4xOTUzMTIgMzg1LjkxMDE1NiAxNTIuMjc3MzQ0IDM4NC4xMDE1NjIgMTUyLjA3MDMxMiAzODEuNzY5NTMxIEMgMTUxLjAyNzM0NCAzNzAuMTA5Mzc1IDE0NS42MjUgMzU5LjcwMzEyNSAxMzcuNTA3ODEyIDM1Mi4yMDMxMjUgWiBNIDM2OC41ODU5MzggMzUyLjIwMzEyNSAiIHN0eWxlPSIiIGZpbGw9IiM0NzRmNmYiIGRhdGEtb3JpZ2luYWw9IiM0NzRmNmYiPjwvcGF0aD4KPHBhdGggZD0iTSA0NzMuNjY3OTY5IDM1Mi4yMDMxMjUgTCA0NzMuNjY3OTY5IDM4NS45MTAxNTYgTCA0NTIuNTkzNzUgMzg1LjkxMDE1NiBDIDQ0OC44NTE1NjIgMzg1LjkxMDE1NiA0NDUuNjk5MjE5IDM4My4xNDQ1MzEgNDQ1LjE3NTc4MSAzNzkuNDM3NSBDIDQ0My42NjQwNjIgMzY4LjcyMjY1NiA0MzguNDUzMTI1IDM1OS4xOTkyMTkgNDMwLjg3ODkwNiAzNTIuMjAzMTI1IFogTSA0NzMuNjY3OTY5IDM1Mi4yMDMxMjUgIiBzdHlsZT0iIiBmaWxsPSIjNDc0ZjZmIiBkYXRhLW9yaWdpbmFsPSIjNDc0ZjZmIj48L3BhdGg+CjxwYXRoIGQ9Ik0gMTU4LjIyMjY1NiAyMTUuMjI2NTYyIEwgMjI0LjY3MTg3NSAyMTUuMjI2NTYyIEwgMjI0LjY3MTg3NSAzODUuOTEwMTU2IEwgMTU4LjIyMjY1NiAzODUuOTEwMTU2IFogTSAxNTguMjIyNjU2IDIxNS4yMjY1NjIgIiBzdHlsZT0iIiBmaWxsPSIjMzIzYjVhIiBkYXRhLW9yaWdpbmFsPSIjMzIzYjVhIj48L3BhdGg+CjxwYXRoIGQ9Ik0gMTQ0LjIzNDM3NSAyMTUuMjI2NTYyIEwgMTQ0LjIzNDM3NSAyNzcuODc1IEwgNzIuODQzNzUgMjc3Ljg3NSBMIDg3LjM5ODQzOCAyMTUuMjI2NTYyIFogTSAxNDQuMjM0Mzc1IDIxNS4yMjY1NjIgIiBzdHlsZT0iIiBmaWxsPSIjNDc0ZjZmIiBkYXRhLW9yaWdpbmFsPSIjNDc0ZjZmIj48L3BhdGg+CjxwYXRoIGQ9Ik0gMTQ0LjIzNDM3NSAyMTUuMjI2NTYyIEwgMTQ0LjIzNDM3NSAyNzcuODc1IEwgOTEuMzA0Njg4IDI3Ny44NzUgTCAxMDUuODU5Mzc1IDIxNS4yMjY1NjIgWiBNIDE0NC4yMzQzNzUgMjE1LjIyNjU2MiAiIHN0eWxlPSIiIGZpbGw9IiMzMjNiNWEiIGRhdGEtb3JpZ2luYWw9IiMzMjNiNWEiPjwvcGF0aD4KPHBhdGggZD0iTSAyMzkuMzI4MTI1IDIxNS4yMjY1NjIgTCAzMDEuOTY4NzUgMjE1LjIyNjU2MiBMIDMwMS45Njg3NSAyNzcuODc1IEwgMjM5LjMyODEyNSAyNzcuODc1IFogTSAyMzkuMzI4MTI1IDIxNS4yMjY1NjIgIiBzdHlsZT0iIiBmaWxsPSIjNDc0ZjZmIiBkYXRhLW9yaWdpbmFsPSIjNDc0ZjZmIj48L3BhdGg+CjxwYXRoIGQ9Ik0gMzE3LjQ0MTQwNiAyMTUuMjMwNDY5IEwgMzgwLjA4NTkzOCAyMTUuMjMwNDY5IEwgMzgwLjA4NTkzOCAyNzcuODcxMDk0IEwgMzE3LjQ0MTQwNiAyNzcuODcxMDk0IFogTSAzMTcuNDQxNDA2IDIxNS4yMzA0NjkgIiBzdHlsZT0iIiBmaWxsPSIjNDc0ZjZmIiBkYXRhLW9yaWdpbmFsPSIjNDc0ZjZmIj48L3BhdGg+CjxwYXRoIGQ9Ik0gMzk1LjU1NDY4OCAyMTUuMjMwNDY5IEwgNDU4LjE5OTIxOSAyMTUuMjMwNDY5IEwgNDU4LjE5OTIxOSAyNzcuODcxMDk0IEwgMzk1LjU1NDY4OCAyNzcuODcxMDk0IFogTSAzOTUuNTU0Njg4IDIxNS4yMzA0NjkgIiBzdHlsZT0iIiBmaWxsPSIjNDc0ZjZmIiBkYXRhLW9yaWdpbmFsPSIjNDc0ZjZmIj48L3BhdGg+CjxwYXRoIGQ9Ik0gMTU4LjIyMjY1NiAzMTguMDE1NjI1IEwgMjI0LjY3MTg3NSAzMTguMDE1NjI1IEwgMjI0LjY3MTg3NSAzODUuOTEwMTU2IEwgMTU4LjIyMjY1NiAzODUuOTEwMTU2IFogTSAxNTguMjIyNjU2IDMxOC4wMTU2MjUgIiBzdHlsZT0iIiBmaWxsPSIjZDBmNmZmIiBkYXRhLW9yaWdpbmFsPSIjZDBmNmZmIiBjbGFzcz0iIj48L3BhdGg+CjxwYXRoIGQ9Ik0gMTU4LjIyMjY1NiAzNTIuMjAzMTI1IEwgMjI0LjY3MTg3NSAzNTIuMjAzMTI1IEwgMjI0LjY3MTg3NSAzODUuOTEwMTU2IEwgMTU4LjIyMjY1NiAzODUuOTEwMTU2IFogTSAxNTguMjIyNjU2IDM1Mi4yMDMxMjUgIiBzdHlsZT0iIiBmaWxsPSIjMzIzYjVhIiBkYXRhLW9yaWdpbmFsPSIjMzIzYjVhIj48L3BhdGg+CjxwYXRoIGQ9Ik0gMjY5Ljg5NDUzMSAyMTUuMjI2NTYyIEwgMzAxLjk2ODc1IDIxNS4yMjY1NjIgTCAzMDEuOTY4NzUgMjc3Ljg3NSBMIDI2OS44OTQ1MzEgMjc3Ljg3NSBaIE0gMjY5Ljg5NDUzMSAyMTUuMjI2NTYyICIgc3R5bGU9IiIgZmlsbD0iIzMyM2I1YSIgZGF0YS1vcmlnaW5hbD0iIzMyM2I1YSI+PC9wYXRoPgo8cGF0aCBkPSJNIDM0OC4wMTE3MTkgMjE1LjIyNjU2MiBMIDM4MC4wODU5MzggMjE1LjIyNjU2MiBMIDM4MC4wODU5MzggMjc3Ljg3NSBMIDM0OC4wMTE3MTkgMjc3Ljg3NSBaIE0gMzQ4LjAxMTcxOSAyMTUuMjI2NTYyICIgc3R5bGU9IiIgZmlsbD0iIzMyM2I1YSIgZGF0YS1vcmlnaW5hbD0iIzMyM2I1YSI+PC9wYXRoPgo8cGF0aCBkPSJNIDQyNi4xMjUgMjE1LjIyNjU2MiBMIDQ1OC4xOTUzMTIgMjE1LjIyNjU2MiBMIDQ1OC4xOTUzMTIgMjc3Ljg3NSBMIDQyNi4xMjUgMjc3Ljg3NSBaIE0gNDI2LjEyNSAyMTUuMjI2NTYyICIgc3R5bGU9IiIgZmlsbD0iIzMyM2I1YSIgZGF0YS1vcmlnaW5hbD0iIzMyM2I1YSI+PC9wYXRoPgo8cGF0aCBkPSJNIDE0MC4wNjI1IDM4NS45MTAxNTYgQyAxNDAuMDYyNSA0MDQuNTI3MzQ0IDEyNC45NzI2NTYgNDE5LjYxNzE4OCAxMDYuMzU1NDY5IDQxOS42MTcxODggQyA4Ny43MzgyODEgNDE5LjYxNzE4OCA3Mi42NDg0MzggNDA0LjUyNzM0NCA3Mi42NDg0MzggMzg1LjkxMDE1NiBDIDcyLjY0ODQzOCAzNjcuMjkyOTY5IDg3LjczODI4MSAzNTIuMjAzMTI1IDEwNi4zNTU0NjkgMzUyLjIwMzEyNSBDIDEyNC45NzI2NTYgMzUyLjIwMzEyNSAxNDAuMDYyNSAzNjcuMjkyOTY5IDE0MC4wNjI1IDM4NS45MTAxNTYgWiBNIDE0MC4wNjI1IDM4NS45MTAxNTYgIiBzdHlsZT0iIiBmaWxsPSIjNDc0ZjZmIiBkYXRhLW9yaWdpbmFsPSIjNDc0ZjZmIj48L3BhdGg+CjxwYXRoIGQ9Ik0gMTI5LjA4NTkzOCAzODUuOTEwMTU2IEMgMTI5LjA4NTkzOCAzOTguNDY0ODQ0IDExOC45MTAxNTYgNDA4LjY0MDYyNSAxMDYuMzU1NDY5IDQwOC42NDA2MjUgQyA5My44MDQ2ODggNDA4LjY0MDYyNSA4My42Mjg5MDYgMzk4LjQ2NDg0NCA4My42Mjg5MDYgMzg1LjkxMDE1NiBDIDgzLjYyODkwNiAzNzMuMzU5Mzc1IDkzLjgwNDY4OCAzNjMuMTgzNTk0IDEwNi4zNTU0NjkgMzYzLjE4MzU5NCBDIDExOC45MTAxNTYgMzYzLjE4MzU5NCAxMjkuMDg1OTM4IDM3My4zNTkzNzUgMTI5LjA4NTkzOCAzODUuOTEwMTU2IFogTSAxMjkuMDg1OTM4IDM4NS45MTAxNTYgIiBzdHlsZT0iIiBmaWxsPSIjMzIzYjVhIiBkYXRhLW9yaWdpbmFsPSIjMzIzYjVhIj48L3BhdGg+CjxwYXRoIGQ9Ik0gMTQwLjA2NjQwNiAzODUuOTEwMTU2IEMgMTQwLjA2NjQwNiA0MDQuNTI3MzQ0IDEyNC45NzI2NTYgNDE5LjYyMTA5NCAxMDYuMzU1NDY5IDQxOS42MjEwOTQgTCAxMDYuMzU1NDY5IDM1Mi4yMDMxMjUgQyAxMjQuOTcyNjU2IDM1Mi4yMDMxMjUgMTQwLjA2NjQwNiAzNjcuMjg5MDYyIDE0MC4wNjY0MDYgMzg1LjkxMDE1NiBaIE0gMTQwLjA2NjQwNiAzODUuOTEwMTU2ICIgc3R5bGU9IiIgZmlsbD0iIzMyM2I1YSIgZGF0YS1vcmlnaW5hbD0iIzMyM2I1YSI+PC9wYXRoPgo8cGF0aCBkPSJNIDEyMS42MDE1NjIgMzg1LjkxMDE1NiBDIDEyMS42MDE1NjIgMzc3LjQ5MjE4OCAxMTQuNzc3MzQ0IDM3MC42NjQwNjIgMTA2LjM1NTQ2OSAzNzAuNjY0MDYyIEMgOTcuOTMzNTk0IDM3MC42NjQwNjIgOTEuMTA5Mzc1IDM3Ny40OTIxODggOTEuMTA5Mzc1IDM4NS45MTAxNTYgQyA5MS4xMDkzNzUgMzk0LjMyODEyNSA5Ny45MzM1OTQgNDAxLjE1MjM0NCAxMDYuMzU1NDY5IDQwMS4xNTIzNDQgQyAxMTQuNzc3MzQ0IDQwMS4xNTIzNDQgMTIxLjYwMTU2MiAzOTQuMzI4MTI1IDEyMS42MDE1NjIgMzg1LjkxMDE1NiBaIE0gMTIxLjYwMTU2MiAzODUuOTEwMTU2ICIgc3R5bGU9IiIgZmlsbD0iI2ZmZmZmZiIgZGF0YS1vcmlnaW5hbD0iI2ZmZmZmZiIgY2xhc3M9IiI+PC9wYXRoPgo8cGF0aCBkPSJNIDEyMS42MDU0NjkgMzg1LjkxMDE1NiBDIDEyMS42MDU0NjkgMzk0LjMzMjAzMSAxMTQuNzc3MzQ0IDQwMS4xNTIzNDQgMTA2LjM1NTQ2OSA0MDEuMTUyMzQ0IEwgMTA2LjM1NTQ2OSAzNzAuNjY3OTY5IEMgMTE0Ljc3NzM0NCAzNzAuNjY0MDYyIDEyMS42MDU0NjkgMzc3LjQ5MjE4OCAxMjEuNjA1NDY5IDM4NS45MTAxNTYgWiBNIDEyMS42MDU0NjkgMzg1LjkxMDE1NiAiIHN0eWxlPSIiIGZpbGw9IiNkMGY2ZmYiIGRhdGEtb3JpZ2luYWw9IiNkMGY2ZmYiIGNsYXNzPSIiPjwvcGF0aD4KPHBhdGggZD0iTSAxMTMuNzMwNDY5IDM4NS45MTAxNTYgQyAxMTMuNzMwNDY5IDM4OS45ODQzNzUgMTEwLjQyOTY4OCAzOTMuMjg1MTU2IDEwNi4zNTU0NjkgMzkzLjI4NTE1NiBDIDEwMi4yODEyNSAzOTMuMjg1MTU2IDk4Ljk4MDQ2OSAzODkuOTg0Mzc1IDk4Ljk4MDQ2OSAzODUuOTEwMTU2IEMgOTguOTgwNDY5IDM4MS44MzU5MzggMTAyLjI4MTI1IDM3OC41MzUxNTYgMTA2LjM1NTQ2OSAzNzguNTM1MTU2IEMgMTEwLjQyOTY4OCAzNzguNTM1MTU2IDExMy43MzA0NjkgMzgxLjgzNTkzOCAxMTMuNzMwNDY5IDM4NS45MTAxNTYgWiBNIDExMy43MzA0NjkgMzg1LjkxMDE1NiAiIHN0eWxlPSIiIGZpbGw9IiMzZGIyMzIiIGRhdGEtb3JpZ2luYWw9IiM2NWIyZmYiIGNsYXNzPSIiPjwvcGF0aD4KPHBhdGggZD0iTSA0MzMuNDQxNDA2IDM4NS45MTAxNTYgQyA0MzMuNDQxNDA2IDQwNC41MjczNDQgNDE4LjM1MTU2MiA0MTkuNjE3MTg4IDM5OS43MzQzNzUgNDE5LjYxNzE4OCBDIDM4MS4xMTcxODggNDE5LjYxNzE4OCAzNjYuMDI3MzQ0IDQwNC41MjczNDQgMzY2LjAyNzM0NCAzODUuOTEwMTU2IEMgMzY2LjAyNzM0NCAzNjcuMjkyOTY5IDM4MS4xMTcxODggMzUyLjIwMzEyNSAzOTkuNzM0Mzc1IDM1Mi4yMDMxMjUgQyA0MTguMzUxNTYyIDM1Mi4yMDMxMjUgNDMzLjQ0MTQwNiAzNjcuMjkyOTY5IDQzMy40NDE0MDYgMzg1LjkxMDE1NiBaIE0gNDMzLjQ0MTQwNiAzODUuOTEwMTU2ICIgc3R5bGU9IiIgZmlsbD0iIzQ3NGY2ZiIgZGF0YS1vcmlnaW5hbD0iIzQ3NGY2ZiI+PC9wYXRoPgo8cGF0aCBkPSJNIDQyMi40NjQ4NDQgMzg1LjkxMDE1NiBDIDQyMi40NjQ4NDQgMzk4LjQ2NDg0NCA0MTIuMjg5MDYyIDQwOC42NDA2MjUgMzk5LjczNDM3NSA0MDguNjQwNjI1IEMgMzg3LjE4MzU5NCA0MDguNjQwNjI1IDM3Ny4wMDc4MTIgMzk4LjQ2NDg0NCAzNzcuMDA3ODEyIDM4NS45MTAxNTYgQyAzNzcuMDA3ODEyIDM3My4zNTkzNzUgMzg3LjE4MzU5NCAzNjMuMTgzNTk0IDM5OS43MzQzNzUgMzYzLjE4MzU5NCBDIDQxMi4yODkwNjIgMzYzLjE4MzU5NCA0MjIuNDY0ODQ0IDM3My4zNTkzNzUgNDIyLjQ2NDg0NCAzODUuOTEwMTU2IFogTSA0MjIuNDY0ODQ0IDM4NS45MTAxNTYgIiBzdHlsZT0iIiBmaWxsPSIjMzIzYjVhIiBkYXRhLW9yaWdpbmFsPSIjMzIzYjVhIj48L3BhdGg+CjxwYXRoIGQ9Ik0gNDMzLjQ0NTMxMiAzODUuOTEwMTU2IEMgNDMzLjQ0NTMxMiA0MDQuNTI3MzQ0IDQxOC4zNTE1NjIgNDE5LjYyMTA5NCAzOTkuNzM0Mzc1IDQxOS42MjEwOTQgTCAzOTkuNzM0Mzc1IDM1Mi4yMDMxMjUgQyA0MTguMzUxNTYyIDM1Mi4yMDMxMjUgNDMzLjQ0NTMxMiAzNjcuMjg5MDYyIDQzMy40NDUzMTIgMzg1LjkxMDE1NiBaIE0gNDMzLjQ0NTMxMiAzODUuOTEwMTU2ICIgc3R5bGU9IiIgZmlsbD0iIzMyM2I1YSIgZGF0YS1vcmlnaW5hbD0iIzMyM2I1YSI+PC9wYXRoPgo8cGF0aCBkPSJNIDQxNC45ODA0NjkgMzg1LjkxMDE1NiBDIDQxNC45ODA0NjkgMzc3LjQ5MjE4OCA0MDguMTU2MjUgMzcwLjY2NDA2MiAzOTkuNzM0Mzc1IDM3MC42NjQwNjIgQyAzOTEuMzEyNSAzNzAuNjY0MDYyIDM4NC40ODgyODEgMzc3LjQ5MjE4OCAzODQuNDg4MjgxIDM4NS45MTAxNTYgQyAzODQuNDg4MjgxIDM5NC4zMjgxMjUgMzkxLjMxMjUgNDAxLjE1MjM0NCAzOTkuNzM0Mzc1IDQwMS4xNTIzNDQgQyA0MDguMTU2MjUgNDAxLjE1MjM0NCA0MTQuOTgwNDY5IDM5NC4zMjgxMjUgNDE0Ljk4MDQ2OSAzODUuOTEwMTU2IFogTSA0MTQuOTgwNDY5IDM4NS45MTAxNTYgIiBzdHlsZT0iIiBmaWxsPSIjZmZmZmZmIiBkYXRhLW9yaWdpbmFsPSIjZmZmZmZmIiBjbGFzcz0iIj48L3BhdGg+CjxwYXRoIGQ9Ik0gNDE0Ljk4NDM3NSAzODUuOTEwMTU2IEMgNDE0Ljk4NDM3NSAzOTQuMzMyMDMxIDQwOC4xNTYyNSA0MDEuMTUyMzQ0IDM5OS43MzQzNzUgNDAxLjE1MjM0NCBMIDM5OS43MzQzNzUgMzcwLjY2Nzk2OSBDIDQwOC4xNTYyNSAzNzAuNjY0MDYyIDQxNC45ODQzNzUgMzc3LjQ5MjE4OCA0MTQuOTg0Mzc1IDM4NS45MTAxNTYgWiBNIDQxNC45ODQzNzUgMzg1LjkxMDE1NiAiIHN0eWxlPSIiIGZpbGw9IiNkMGY2ZmYiIGRhdGEtb3JpZ2luYWw9IiNkMGY2ZmYiIGNsYXNzPSIiPjwvcGF0aD4KPHBhdGggZD0iTSA0MDcuMTA5Mzc1IDM4NS45MTAxNTYgQyA0MDcuMTA5Mzc1IDM4OS45ODQzNzUgNDAzLjgwODU5NCAzOTMuMjg1MTU2IDM5OS43MzQzNzUgMzkzLjI4NTE1NiBDIDM5NS42NjAxNTYgMzkzLjI4NTE1NiAzOTIuMzU5Mzc1IDM4OS45ODQzNzUgMzkyLjM1OTM3NSAzODUuOTEwMTU2IEMgMzkyLjM1OTM3NSAzODEuODM1OTM4IDM5NS42NjAxNTYgMzc4LjUzNTE1NiAzOTkuNzM0Mzc1IDM3OC41MzUxNTYgQyA0MDMuODA4NTk0IDM3OC41MzUxNTYgNDA3LjEwOTM3NSAzODEuODM1OTM4IDQwNy4xMDkzNzUgMzg1LjkxMDE1NiBaIE0gNDA3LjEwOTM3NSAzODUuOTEwMTU2ICIgc3R5bGU9IiIgZmlsbD0iIzNkYjIzMiIgZGF0YS1vcmlnaW5hbD0iIzY1YjJmZiIgY2xhc3M9IiI+PC9wYXRoPgo8cGF0aCBkPSJNIDU2LjUgMzIyLjA0Mjk2OSBDIDU2LjUgMzMxLjk2ODc1IDQ4LjQ1MzEyNSAzNDAuMDE1NjI1IDM4LjUyMzQzOCAzNDAuMDE1NjI1IEwgMzguNTIzNDM4IDMwNC4wNjY0MDYgQyA0OC40NTMxMjUgMzA0LjA2NjQwNiA1Ni41IDMxMi4xMTMyODEgNTYuNSAzMjIuMDQyOTY5IFogTSA1Ni41IDMyMi4wNDI5NjkgIiBzdHlsZT0iIiBmaWxsPSIjZmZmZmZmIiBkYXRhLW9yaWdpbmFsPSIjZmZmZmZmIiBjbGFzcz0iIj48L3BhdGg+CjxwYXRoIGQ9Ik0gMTIzLjgyODEyNSAyOTEuMTg3NSBMIDE0NC4yMzQzNzUgMjkxLjE4NzUgTCAxNDQuMjM0Mzc1IDMwMC41NjY0MDYgTCAxMjMuODI4MTI1IDMwMC41NjY0MDYgWiBNIDEyMy44MjgxMjUgMjkxLjE4NzUgIiBzdHlsZT0iIiBmaWxsPSIjMzIzYjVhIiBkYXRhLW9yaWdpbmFsPSIjMzIzYjVhIj48L3BhdGg+CjxwYXRoIGQ9Ik0gMTg2LjU3ODEyNSAxNDYuNjY3OTY5IEMgMTg2LjU3ODEyNSAxNDYuNjAxNTYyIDE4Ni41ODIwMzEgMTQ2LjUzNTE1NiAxODYuNTgyMDMxIDE0Ni40Njg3NSBDIDE4Ni41ODIwMzEgMTM0LjMwMDc4MSAxNzYuNzE0ODQ0IDEyNC40MzM1OTQgMTY0LjU0Njg3NSAxMjQuNDMzNTk0IEMgMTYxLjY1MjM0NCAxMjQuNDMzNTk0IDE1OC44OTQ1MzEgMTI0Ljk5NjA5NCAxNTYuMzYzMjgxIDEyNi4wMDc4MTIgQyAxNTEuNDc2NTYyIDExNy4yNTM5MDYgMTQyLjEyODkwNiAxMTEuMzMyMDMxIDEzMS4zOTA2MjUgMTExLjMzMjAzMSBDIDExNy40NDUzMTIgMTExLjMzMjAzMSAxMDUuODM5ODQ0IDEyMS4zMTY0MDYgMTAzLjMxNjQwNiAxMzQuNTI3MzQ0IEMgMTAyLjU2NjQwNiAxMzQuNDE3OTY5IDEwMS43OTY4NzUgMTM0LjM1OTM3NSAxMDEuMDE1NjI1IDEzNC4zNTkzNzUgQyA5My40MzM1OTQgMTM0LjM1OTM3NSA4Ny4wODIwMzEgMTM5LjYwOTM3NSA4NS4zODI4MTIgMTQ2LjY2Nzk2OSBaIE0gMTg2LjU3ODEyNSAxNDYuNjY3OTY5ICIgc3R5bGU9IiIgZmlsbD0iI2ZmZmZmZiIgZGF0YS1vcmlnaW5hbD0iI2ZmZmZmZiIgY2xhc3M9IiI+PC9wYXRoPgo8cGF0aCBkPSJNIDE2NS4wNzQyMTkgMTQ2LjY2Nzk2OSBDIDE2NS4wNzQyMTkgMTQ2LjYzMjgxMiAxNjUuMDc4MTI1IDE0Ni41OTM3NSAxNjUuMDc4MTI1IDE0Ni41NTQ2ODggQyAxNjUuMDc4MTI1IDEzOS41NTg1OTQgMTU5LjQwNjI1IDEzMy44ODI4MTIgMTUyLjQwNjI1IDEzMy44ODI4MTIgQyAxNTAuNzQ2MDk0IDEzMy44ODI4MTIgMTQ5LjE2MDE1NiAxMzQuMjA3MDMxIDE0Ny43MDMxMjUgMTM0Ljc4OTA2MiBDIDE0NC44OTQ1MzEgMTI5Ljc1NzgxMiAxMzkuNTE5NTMxIDEyNi4zNTE1NjIgMTMzLjM0Mzc1IDEyNi4zNTE1NjIgQyAxMjUuMzI0MjE5IDEyNi4zNTE1NjIgMTE4LjY1MjM0NCAxMzIuMDkzNzUgMTE3LjE5OTIxOSAxMzkuNjg3NSBDIDExNi43Njk1MzEgMTM5LjYyNSAxMTYuMzI4MTI1IDEzOS41OTM3NSAxMTUuODc4OTA2IDEzOS41OTM3NSBDIDExMS41MTk1MzEgMTM5LjU5Mzc1IDEwNy44NjcxODggMTQyLjYwOTM3NSAxMDYuODkwNjI1IDE0Ni42NzE4NzUgTCAxNjUuMDc0MjE5IDE0Ni42NzE4NzUgWiBNIDE2NS4wNzQyMTkgMTQ2LjY2Nzk2OSAiIHN0eWxlPSIiIGZpbGw9IiNkMGY2ZmYiIGRhdGEtb3JpZ2luYWw9IiNkMGY2ZmYiIGNsYXNzPSIiPjwvcGF0aD4KPHBhdGggZD0iTSA0MzEuODQ3NjU2IDE0Ni42Njc5NjkgQyA0MzEuODQ3NjU2IDE0Ni42MDE1NjIgNDMxLjg1MTU2MiAxNDYuNTM1MTU2IDQzMS44NTE1NjIgMTQ2LjQ2ODc1IEMgNDMxLjg1MTU2MiAxMzQuMzAwNzgxIDQyMS45ODQzNzUgMTI0LjQzMzU5NCA0MDkuODEyNSAxMjQuNDMzNTk0IEMgNDA2LjkyMTg3NSAxMjQuNDMzNTk0IDQwNC4xNjQwNjIgMTI0Ljk5NjA5NCA0MDEuNjMyODEyIDEyNi4wMDc4MTIgQyAzOTYuNzQ2MDk0IDExNy4yNTM5MDYgMzg3LjM5ODQzOCAxMTEuMzMyMDMxIDM3Ni42NjAxNTYgMTExLjMzMjAzMSBDIDM2Mi43MTQ4NDQgMTExLjMzMjAzMSAzNTEuMTA5Mzc1IDEyMS4zMTY0MDYgMzQ4LjU4NTkzOCAxMzQuNTI3MzQ0IEMgMzQ3LjgzNTkzOCAxMzQuNDE3OTY5IDM0Ny4wNjY0MDYgMTM0LjM1OTM3NSAzNDYuMjg1MTU2IDEzNC4zNTkzNzUgQyAzMzguNzAzMTI1IDEzNC4zNTkzNzUgMzMyLjM1MTU2MiAxMzkuNjA5Mzc1IDMzMC42NTIzNDQgMTQ2LjY2Nzk2OSBaIE0gNDMxLjg0NzY1NiAxNDYuNjY3OTY5ICIgc3R5bGU9IiIgZmlsbD0iI2ZmZmZmZiIgZGF0YS1vcmlnaW5hbD0iI2ZmZmZmZiIgY2xhc3M9IiI+PC9wYXRoPgo8cGF0aCBkPSJNIDQxMC4zNDM3NSAxNDYuNjY3OTY5IEMgNDEwLjM0Mzc1IDE0Ni42MzI4MTIgNDEwLjM0NzY1NiAxNDYuNTkzNzUgNDEwLjM0NzY1NiAxNDYuNTU0Njg4IEMgNDEwLjM0NzY1NiAxMzkuNTU4NTk0IDQwNC42NzU3ODEgMTMzLjg4MjgxMiAzOTcuNjc1NzgxIDEzMy44ODI4MTIgQyAzOTYuMDE1NjI1IDEzMy44ODI4MTIgMzk0LjQyOTY4OCAxMzQuMjA3MDMxIDM5Mi45NzI2NTYgMTM0Ljc4OTA2MiBDIDM5MC4xNjQwNjIgMTI5Ljc1NzgxMiAzODQuNzg5MDYyIDEyNi4zNTE1NjIgMzc4LjYxMzI4MSAxMjYuMzUxNTYyIEMgMzcwLjU5Mzc1IDEyNi4zNTE1NjIgMzYzLjkyMTg3NSAxMzIuMDkzNzUgMzYyLjQ2ODc1IDEzOS42ODc1IEMgMzYyLjAzOTA2MiAxMzkuNjI1IDM2MS41OTc2NTYgMTM5LjU5Mzc1IDM2MS4xNDg0MzggMTM5LjU5Mzc1IEMgMzU2Ljc4NTE1NiAxMzkuNTkzNzUgMzUzLjEzNjcxOSAxNDIuNjA5Mzc1IDM1Mi4xNjAxNTYgMTQ2LjY3MTg3NSBMIDQxMC4zNDM3NSAxNDYuNjcxODc1IFogTSA0MTAuMzQzNzUgMTQ2LjY2Nzk2OSAiIHN0eWxlPSIiIGZpbGw9IiNkMGY2ZmYiIGRhdGEtb3JpZ2luYWw9IiNkMGY2ZmYiIGNsYXNzPSIiPjwvcGF0aD4KPHBhdGggZD0iTSAzMDYuNTkzNzUgNzUuMzgyODEyIEMgMzA2LjU5Mzc1IDc1LjMxMjUgMzA2LjU5NzY1NiA3NS4yNSAzMDYuNTk3NjU2IDc1LjE4MzU5NCBDIDMwNi41OTc2NTYgNjMuMDExNzE5IDI5Ni43MzA0NjkgNTMuMTQ0NTMxIDI4NC41NjI1IDUzLjE0NDUzMSBDIDI4MS42NzE4NzUgNTMuMTQ0NTMxIDI3OC45MTAxNTYgNTMuNzEwOTM4IDI3Ni4zODI4MTIgNTQuNzIyNjU2IEMgMjcxLjQ5MjE4OCA0NS45Njg3NSAyNjIuMTQ0NTMxIDQwLjA0Mjk2OSAyNTEuNDA2MjUgNDAuMDQyOTY5IEMgMjM3LjQ2NDg0NCA0MC4wNDI5NjkgMjI1Ljg1NTQ2OSA1MC4wMjczNDQgMjIzLjMzNTkzOCA2My4yMzgyODEgQyAyMjIuNTgyMDMxIDYzLjEzMjgxMiAyMjEuODEyNSA2My4wNzAzMTIgMjIxLjAzMTI1IDYzLjA3MDMxMiBDIDIxMy40NDkyMTkgNjMuMDcwMzEyIDIwNy4wOTc2NTYgNjguMzIwMzEyIDIwNS40MDIzNDQgNzUuMzgyODEyIFogTSAzMDYuNTkzNzUgNzUuMzgyODEyICIgc3R5bGU9IiIgZmlsbD0iI2ZmZmZmZiIgZGF0YS1vcmlnaW5hbD0iI2ZmZmZmZiIgY2xhc3M9IiI+PC9wYXRoPgo8cGF0aCBkPSJNIDI4NS4wOTM3NSA3NS4zODI4MTIgQyAyODUuMDkzNzUgNzUuMzQzNzUgMjg1LjA5Mzc1IDc1LjMwNDY4OCAyODUuMDkzNzUgNzUuMjY1NjI1IEMgMjg1LjA5Mzc1IDY4LjI2OTUzMSAyNzkuNDIxODc1IDYyLjU5NzY1NiAyNzIuNDI1NzgxIDYyLjU5NzY1NiBDIDI3MC43NjE3MTkgNjIuNTk3NjU2IDI2OS4xNzU3ODEgNjIuOTE3OTY5IDI2Ny43MjI2NTYgNjMuNTAzOTA2IEMgMjY0LjkxMDE1NiA1OC40Njg3NSAyNTkuNTM1MTU2IDU1LjA2MjUgMjUzLjM1OTM3NSA1NS4wNjI1IEMgMjQ1LjM0Mzc1IDU1LjA2MjUgMjM4LjY2Nzk2OSA2MC44MDQ2ODggMjM3LjIxODc1IDY4LjM5ODQzOCBDIDIzNi43ODUxNTYgNjguMzM5ODQ0IDIzNi4zNDM3NSA2OC4zMDQ2ODggMjM1Ljg5NDUzMSA2OC4zMDQ2ODggQyAyMzEuNTM1MTU2IDY4LjMwNDY4OCAyMjcuODgyODEyIDcxLjMyMDMxMiAyMjYuOTA2MjUgNzUuMzgyODEyIFogTSAyODUuMDkzNzUgNzUuMzgyODEyICIgc3R5bGU9IiIgZmlsbD0iI2QwZjZmZiIgZGF0YS1vcmlnaW5hbD0iI2QwZjZmZiIgY2xhc3M9IiI+PC9wYXRoPgo8L2c+CjwvZz48L3N2Zz4="
    };
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
.icon-wrapper {
  /* vertical-align: middle; */
  /* display: block; */
}
.container {
  background-color: #f5df4d;
  /* background-image: url("../../../assets/images/5221332.jpg"); */
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
.el-button:focus,
.el-button:hover {
  color: #e67010;
}
</style>
