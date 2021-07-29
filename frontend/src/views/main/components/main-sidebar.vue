<template>
  <el-row class="main-sidebar" :gutter="10" :style="{ width: width }">
    <div class="hide-on-small">
      <!-- <el-menu
        :default-active="String(state.activeIndex)"
        active-text-color="#ffd04b"
        class="el-menu-vertical-demo"
        @select="menuSelect"
      >
        <el-menu-item
          v-for="(item, index) in state.menuItems"
          :key="index"
          :index="index.toString()"
        >
          <i v-if="item.icon" :class="['ic', item.icon]" />
          <span>{{ item.title }}</span>
        </el-menu-item>
      </el-menu> -->

      <el-menu
        :default-active="String(state.activeIndex)"
        active-text-color="#ffd04b"
        class="el-menu-vertical-demo"
        @select="conferenceSelect"
      >
        <el-menu-item v-for="i in state.count" :key="i" :index="i">
          <span>{{ i }}</span>
        </el-menu-item>
        <el-button class="button" @click="signOut">로그아웃</el-button>
      </el-menu>
    </div>
  </el-row>
</template>
<style>
.main-sidebar .el-menu {
  margin-top: 0;
  padding-left: 0;
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
  margin-right: 5px;
}
</style>
<script>
import SidebarTool from "./sidebar-tool.vue";
import { reactive, computed } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";

export default {
  name: "main-header",
  methods: {
    signOut() {
      console.log("로그아웃버튼누름");
      window.gapi.auth2.getAuthInstance().disconnect();
      sessionStorage.removeItem("userInfo");
      this.$store.commit("root/setLogin", false);
    }
  },
  components: {
    SidebarTool
  },

  props: {
    width: {
      type: String,
      default: "240px"
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
      activeIndex: computed(() => store.getters["root/getActiveMenuIndex"])
    });

    if (state.activeIndex === -1) {
      state.activeIndex = 0;
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

    const conferenceSelect = function(param) {
      store.commit("root/setConferenceActive", param);
      const MenuItems = store.getters["root/getMenus"];
      let keys = Object.keys(MenuItems);
      router.push({
        name: keys[param]
      });
    };

    return { state, menuSelect, conferenceSelect };
  }
};
</script>
