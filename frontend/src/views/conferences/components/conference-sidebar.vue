<template>
  <div>
    <el-row class="sidebar-tool">
      <div class="sidebar-menu">
        <el-menu active-text-color="#ffd04b">
          <el-menu-item @click="noticeSelect()">
            공지사항
          </el-menu-item>
          <el-menu-item @click="hwSelect()">
            과제 제출
          </el-menu-item>
          <el-menu-item @click="studyMaterialSelect()">
            학습 자료
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
          <UserInfoBox />
        </el-menu>
      </div>
    </el-row>
  </div>
</template>
<style>
#meeting-btn {
  bottom: 0;
  padding: 10px 10px;
  border: none;
  outline: none;
}
.sidebar-menu .el-menu-item {
  width: 100%;
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
/* .sidebar-tool .el-menu .el-menu-item .ic {
  margin-right: 5px;
} */
</style>
<script>
import { reactive, computed } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import UserInfoBox from "./user/user-info-box.vue";
export default {
  name: "sidebar-tool",

  props: {
    width: {
      type: String,
      default: "240px"
    }
  },
  components: {
    UserInfoBox
  },
  methods: {
    noticeSelect() {
      this.$router.push({
        name: "conference-notice"
      });
    },
    hwSelect() {
      this.$router.push({
        name: "conference-homework"
      });
    },
    studyMaterialSelect() {
      this.$router.push({
        name: "conference-studymaterial"
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
