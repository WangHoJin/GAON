<template>
  <el-menu
    :default-active="String(state.activeIndex)"
    active-text-color="#d17d00"
    align="center"
    class="main-sidebar hide-on-small"
  >
    <el-menu-item @click="$router.push('/')">
      <span>홈</span>
    </el-menu-item>
    <div>
      <el-menu-item
        v-for="i in $store.getters.rooms"
        :key="i.id"
        :index="i.id"
        @click="conferenceSelect(i.id)"
        @mousedown.right="mouseRightClick(i.id)"
        @mousedown.stop
      >
        <span>{{ i.name }}</span>
      </el-menu-item>
    </div>
    <!-- 방 생성 버튼 -->
    <el-button type="warning" @click="plusBtn()"
      ><div
        class="iconify"
        id="main-sidebar-make-room"
        data-inline="false"
        data-icon="entypo:squared-plus"
        style="font-size: 20px;"
      ></div
    ></el-button>
  </el-menu>

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
    title="방 정보 수정"
    v-model="dialogFormVisible_modifyUser"
    center
    top="5vh"
  >
    <el-divider></el-divider>
    <el-form :model="modifyform">
      <el-form-item
        label="방 제목🏠"
        :label-width="formLabelWidth"
        id="room-make-form-label"
      >
        <el-input
          v-model="modifyform.name"
          autocomplete="off"
          placeholder=""
        ></el-input>
      </el-form-item>
      <el-form-item
        label="방 설명🏠"
        :label-width="formLabelWidth"
        id="room-make-form-label"
      >
        <el-input
          v-model="modifyform.description"
          autocomplete="off"
          placeholder=""
        ></el-input>
      </el-form-item>
      <el-form-item
        label="방 코드🔑"
        :label-width="formLabelWidth"
        id="room-make-form-label"
      >
        {{ modifyform.code }}
      </el-form-item>
      <JoinMember :members="members" />
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button class="gaon-button" type="warning" @click="modifyRoomInfo()"
          >적용하기</el-button
        >
        <el-button @click="dialogFormVisible_modifyUser = false" type="info"
          >취소</el-button
        >
      </span>
    </template>
  </el-dialog>
  <!-- 방 정보 수정 dialog 끝 -->
</template>

<script>
import { reactive, computed } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import $axios from "axios";
import JoinMember from "../../conferences/components/form/join-member.vue";
export default {
  data() {
    return {
      showModifyDialog: false,
      dialogFormVisible_modifyUser: false,
      roomInfo: {}, //여기저기서 활용될 현재 Room의 정보

      modifyform: {
        //수정폼에사용될 Room의 정보
        id: "",
        name: "",
        code: "",
        host_id: "",
        description: ""
      },
      members: [] // 해당 방에 참가한 유저들 목록
    };
  },
  components: {
    JoinMember
  },
  name: "main-header",
  methods: {
    plusBtn() {
      console.log("clicked plus btn");
      this.$store.state.roomModule.isClickPlusBtn = true;
      this.$router.push("/");
    },
    async getRoomInfo(conferenceId) {
      this.roomInfo = await this.$store.dispatch(
        "root/getRoomById",
        conferenceId
      );
    },
    conferenceSelect(conferenceId) {
      this.$router.push({
        name: "conference-detail",
        params: {
          conferenceId: conferenceId
        }
      });
    },
    // uid와 host_id를 비교해 같다면 방 정보 수정 dialog를 띄워준다.
    async mouseRightClick(conferenceId) {
      let response = await this.$store.dispatch("getRoomById", conferenceId);
      if (
        JSON.parse(sessionStorage.getItem("userInfo")).id == response.host_id
      ) {
        this.modifyform = response;
        console.log("modifyform info");
        console.log(this.modifyform);
        this.members = await this.$store.dispatch(
          "getMembersByUsingRoomId",
          this.modifyform.id
        );
        console.log("response from actions getMembersByUsingRoomId");
        console.log(this.members);
        this.showModifyDialog = true;
      }
    },
    // 방 정보 수정 창 띄우기
    async openModifyDialog() {
      this.showModifyDialog = false;
      this.dialogFormVisible_modifyUser = true;
    },
    // 방 정보 수정하기
    async modifyRoomInfo() {
      this.dialogFormVisible_modifyUser = false;
      let payload = {
        id: this.modifyform.id,
        name: this.modifyform.name,
        description: this.modifyform.description
      };
      await this.$store.dispatch("modifyRoom", payload);
      await this.$store.dispatch(
        "getRoomByUserId",
        JSON.parse(sessionStorage.getItem("userInfo")).id
      );
    }
  },
  setup() {
    const store = useStore();
    const router = useRouter();

    const state = reactive({
      count: computed(() => store.getters["root/getNumberOfConferneces"]),
      isLogin: computed(() => store.getters["root/getAuth"]),
      searchValue: null,
      menuItems: computed(() => {
        const MenuItems = store.getters["root/getMenus"];
        let keys = Object.keys(MenuItems);
        let menuArray = [];
        for (let i = 0; i < keys.length; ++i) {
          let menuObject = {};
          menuObject.icon = MenuItems[keys[i]].icon;
          menuObject.title = MenuItems[keys[i]].name;
          menuArray.push(menuObject);
        }
        return menuArray;
      }),
      activeConferenceIndex: computed(
        () => store.getters["root/getActiveConferenceIndex"]
      ),
      activeIndex: computed(() => store.getters["root/getActiveMenuIndex"])
    });

    if (state.activeIndex === -1) {
      state.activeIndex = 0;
      store.commit("root/setMenuActive", 0);
    }

    if (state.activeConferenceIndex === -1) {
      state.activeConferenceIndex = 0;
      store.commit("root/setMenuActive", 0);
    }

    const menuSelect = function(param) {
      store.commit("root/setMenuActive", param);
      const MenuItems = store.getters["root/getMenus"];
      let keys = Object.keys(MenuItems);
      router.push({
        name: keys[param]
      });
    };
    return { state, menuSelect };
  }
};
</script>
<style>
.main-sidebar {
  height: 100%;
  background-color: #ffd344 !important;
}
.main-sidebar .el-menu .el-menu-item {
  cursor: pointer;
  border-right: none;
}
/* 방 생성 버튼에 대한 css */
#main-sidebar-make-room {
}
.main-sidebar .el-menu {
  margin-top: 0;
  padding-left: 0;
  background-color: #ffd344 !important;
}
.main-sidebar .hide-on-small {
  height: 100%;
  position: fixed;
}
.main-sidebar .hide-on-small .el-menu {
  height: 100%;
}
.main-sidebar .el-menu .el-menu-item {
  cursor: pointer;
  border-right: none;
}
.main-sidebar .el-menu .el-menu-item .ic {
  /* margin-right: 5px; */
}
.gaon-button {
  background-color: #ffd04b;
  border: none;
}
/* 모달창 글자 왼쪽정렬 */
.el-form-item #room-make-form-label {
  text-align: left !important;
}
/* 모달창 안 라벨 정렬 */
.el-form-item {
  display: -webkit-box;
  display: -ms-flexbox;
  display: block !important;
  margin-bottom: 22px;
  padding: 5px;
}
.el-menu {
  width: 100%;
}
</style>
