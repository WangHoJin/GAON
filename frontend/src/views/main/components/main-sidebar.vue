<template>
  <div class="main-sidebar">
    <el-row class="sidebar-tool">
      <div class="sidebar-menu">
        <el-menu
          :default-active="String(state.activeIndex)"
          active-text-color="#ffd04b"
          align="center"
        >
          <el-menu-item @click="$router.push('/')">
            <span>홈</span>
          </el-menu-item>
          <el-menu-item
            v-for="i in 10"
            :key="i"
            :index="i"
            @click="conferenceSelect(i)"
          >
            <span>{{ i }}</span>
          </el-menu-item>
          <el-button type="warning"
            ><div
              class="iconify"
              id="main-sidebar-make-room"
              data-inline="false"
              data-icon="entypo:squared-plus"
              style="font-size: 20px;"
            ></div
          ></el-button>
          <el-button class="button" @click="signOut">로그아웃</el-button>
          <div>
            <el>{{ username }}</el>
          </div>
          <img :src="`${img}`" style="width : 30px; border-radius: 70%" />
        </el-menu>
      </div>
    </el-row>
  </div>
</template>
<style>
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
/* 방 생성 버튼에 대한 css */
#main-sidebar-make-room {
  padding: revert !important;
  position: fixed;
  bottom: 10px;
  left: 30px;
  /* margin-left: 10px; */
}
</style>
<script>
import { reactive, computed } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
export default {
  data() {
    if (sessionStorage.getItem("userInfo") != null) {
      return {
        username: JSON.parse(sessionStorage.getItem("userInfo")).nickname,
        img: JSON.parse(sessionStorage.getItem("userInfo")).imgUrl
      };
    }
  },
  name: "main-header",

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
    // 각 룸넘버마다 보여질 상세 페이지
    // const conferenceSelect = function(id) {
    //   router.push({
    //     name: "conference-detail",
    //     params: {
    //       conferenceId: id
    //     }
    //   });
    // };
    return { state, menuSelect };
  },
  methods: {
    conferenceSelect(conferenceId) {
      console.log("메인 네비에서 방 번호 고름");
      console.log(this.$router);
      console.log(this.$route.params.conferenceId);
      this.$router.push({
        name: "conference-detail",
        params: {
          conferenceId: conferenceId
        }
      });
    },
    async signOut() {
      console.log("로그아웃버튼누름");
      await window.gapi.auth2.getAuthInstance().disconnect();
      console.log("user Signed Out");
      sessionStorage.removeItem("userInfo");
      this.$store.commit("root/setLogin", false);
      this.$router.push("/");
    }
  }
};
</script>
<style scoped>
.main-sidebar {
  text-align: center;
}
</style>
