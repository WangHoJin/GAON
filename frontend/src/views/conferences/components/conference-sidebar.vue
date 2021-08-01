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
        </el-menu>
      </div>
    </el-row>
  </div>
</template>
<style>
.sidebar-menu .el-menu-item {
  width: 100px;
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
.sidebar-tool .el-menu .el-menu-item .ic {
  margin-right: 5px;
}
</style>
<script>
import { reactive, computed } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";

export default {
  name: "sidebar-tool",

  props: {
    width: {
      type: String,
      default: "240px"
    }
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
    }
  }
  // setup() {
  //   const store = useStore();
  //   const router = useRouter();

  //   const state = reactive({
  //     isLogin: computed(() => store.getters["root/getAuth"]),
  //     searchValue: null,
  //     sidebarTools: computed(() => {
  //       const SidebarTools = store.getters["root/getSidebarTools"];
  //       let keys = Object.keys(SidebarTools);
  //       let sidebarToolArray = [];
  //       for (let i = 0; i < keys.length; ++i) {
  //         let sidebarToolObject = {};
  //         sidebarToolObject.icon = SidebarTools[keys[i]].icon;
  //         sidebarToolObject.title = SidebarTools[keys[i]].name;
  //         sidebarToolArray.push(sidebarToolObject);
  //       }
  //       return sidebarToolArray;
  //     })
  //   });

  //   const sidebarToolSelect = function(param) {
  //     // store.commit("root/setSidebarToolActive", param);
  //     const MenuItems = store.getters["root/getMenus"];
  //     let keys = Object.keys(MenuItems);
  //     router.push({
  //       name: keys[param]
  //     });
  //   };

  //   return { state, sidebarToolSelect };
  // }
};
</script>
