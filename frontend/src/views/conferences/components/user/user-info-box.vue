<template>
  <el-menu
    default-active="2"
    class="el-menu-vertical-demo"
    @open="handleOpen"
    @close="handleClose"
  >
    <el-submenu index="1">
      <template #title>
        <el-row>
          <el-col :span="8">
            <img
              :src="`${img}`"
              style="width : 30px; border-radius: 70%;  vertical-align: middle "
            />
          </el-col>
          <el-col :span="13" style="overflow: hidden;">
            <span style="font-size:middle;">
              {{ username }}
            </span>
          </el-col>
          <el-col :span="3"></el-col>
        </el-row>
      </template>
      <el-menu-item
        index="1-1"
        @click="signOut"
        style="min-width: 0px; padding:0px"
      >
        <i class="el-icon-remove-outline"></i>
        로그아웃</el-menu-item
      >
      <el-menu-item
        index="1-2"
        @click="showUserInfo"
        style="min-width: 0px; padding:0px"
      >
        <i class="el-icon-setting"></i>
        유저정보</el-menu-item
      >
    </el-submenu>
  </el-menu>
  <el-card class="user-info-box" body-style="padding:0px"> </el-card>
  <!-- 회원정보조회 dialog 시작 -->
  <el-dialog
    title="회원 정보 조회"
    v-model="dialogFormVisible_showUserInfo"
    center
    top="5vh"
  >
    <el-divider></el-divider>
    <el-form>
      <el-form-item
        v-if="!isModify"
        label="이름"
        :label-width="formLabelWidth"
        id="room-make-form-label"
      >
        {{ username }}
      </el-form-item>
      <el-form-item
        v-else
        label="이름"
        :label-width="formLabelWidth"
        id="room-make-form-label"
      >
        <el-input v-model="mofidyUsername" placeholder=""></el-input>
      </el-form-item>

      <el-form-item
        label="e-mail"
        :label-width="formLabelWidth"
        id="room-make-form-label"
      >
        {{ email }}
      </el-form-item>
      <el-form-item
        label="프로필 사진"
        :label-width="formLabelWidth"
        id="room-make-form-label"
      >
        <el-image
          style="width: 100px; height: 100px"
          :src="`${img}`"
          :fit="fit"
        ></el-image>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer" v-if="!isModify">
        <el-button class="gaon-button" type="warning" @click="isModify = true"
          >수정하기</el-button
        >

        <el-popover placement="top" :width="160" v-model:visible="visible">
          <p>정말탈퇴하시겠습니까?</p>
          <div style="text-align: right; margin: 0">
            <el-button type="primary" size="mini" @click="quit">네</el-button>
            <el-button type="info" size="mini" @click="visible = false"
              >아니오</el-button
            >
          </div>
          <template #reference>
            <el-button type="danger" @click="visible = true"
              >회원 탈퇴</el-button
            >
          </template>
        </el-popover>

        <el-button
          @click="
            dialogFormVisible_showUserInfo = false;
            mofidyUsername = username;
          "
          type="info"
          >취소</el-button
        >
      </span>
      <span class="dialog-footer" v-else>
        <el-button class="gaon-button" type="warning" @click="modifyUserInfo"
          >수정하기</el-button
        >
        <el-button
          @click="
            dialogFormVisible_showUserInfo = false;
            isModify = false;
            mofidyUsername = username;
          "
          type="info"
          >취소</el-button
        >
      </span>
    </template>
  </el-dialog>
  <!-- 회원정보 조회 dialog 끝 -->
</template>
<script>
import $axios from "axios";
export default {
  name: "user-info-box",
  data() {
    return {
      username: JSON.parse(sessionStorage.getItem("userInfo")).nickname,
      img: JSON.parse(sessionStorage.getItem("userInfo")).imgUrl,
      uid: JSON.parse(sessionStorage.getItem("userInfo")).id,
      email: JSON.parse(sessionStorage.getItem("userInfo")).email,
      dialogFormVisible_showUserInfo: false, // 회원정보 조회 dialog 표시
      isModify: false, //수정폼인지 그냥인지 확인
      visible: false, // 탈퇴 innerdialog 띄우기용
      mofidyUsername: JSON.parse(sessionStorage.getItem("userInfo")).nickname // binding 방지용
    };
  },
  mounted() {
    gapi.load("auth2", function() {
      gapi.auth2.init();
    });
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
    // 회원정보 조회
    showUserInfo() {
      this.dialogFormVisible_showUserInfo = true;
    },
    // 회원정보 조회 dialog -> 수정 dialog
    switchIsModify() {
      this.isModify = true;
    },
    // 회원 수정
    async modifyUserInfo() {
      this.dialogFormVisible_showUserInfo = false;
      this.isModify = false;
      var self = this;
      await $axios
        .put("/users", {
          email: this.email,
          nickname: this.mofidyUsername
        })
        .then(res => {
          console.log(res.data);
          self.username = res.data.nickname;
          sessionStorage.setItem(
            "userInfo",
            JSON.stringify({
              id: self.uid,
              nickname: res.data.nickname,
              email: res.data.email,
              imgUrl: self.img
            })
          );
        })
        .catch(err => {
          console.log(err);
        });
    },
    // 회원 탈퇴
    async quit() {
      var self = this;
      await $axios
        .delete("/users/" + JSON.parse(sessionStorage.getItem("userInfo")).id)
        .then(async function(res) {
          console.log("삭제완료");
          self.disconnectGoogle();
          self.clearStorage();
          self.visible = false;
          self.$router.push("/googlelogin");
        })
        .catch(err => {
          console.log(err);
        });
    }
  }
};
</script>
<style scoped>
.image {
  width: 100%;
  display: block;
}
.user-info-box {
  display: inline-flex;
  line-height: 50px;
}
.user-info-box * {
  margin: 4px 4px;
}

/* element.style {
  padding-left: 0px !important;
}
.el-submenu__title {
  padding: 0px !important;
} */
</style>
