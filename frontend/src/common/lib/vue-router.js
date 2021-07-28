import { createRouter, createWebHistory } from "vue-router";
import Home from "@/views/home/home";
import ConferencesDetail from "@/views/conferences/conference-detail";
import History from "@/views/history/history";
import { nextTick } from "vue";
// import GoogleLogin from "@/views/google-login/google-login.vue";
const fullMenu = require("@/views/main/menu.json");
function makeRoutesFromMenu() {
  let routes = Object.keys(fullMenu).map(key => {
    if (key === "home") {
      return { path: fullMenu[key].path, name: key, component: Home };
    } else if (key === "history") {
      return { path: fullMenu[key].path, name: key, component: History };
    } else {
      // menu.json 에 들어있는 로그아웃 메뉴
      return null;
    }
  });
  // 로그아웃 파싱한 부분 제거
  routes = routes.filter(item => item);
  // menu 자체에는 나오지 않는 페이지 라우터에 추가(방 상세보기)
  routes.push({
    path: "/conferences/:conferenceId",
    name: "conference-detail",
    component: ConferencesDetail
  });
  return routes;
}

const routes = makeRoutesFromMenu();

const router = createRouter({
  history: createWebHistory(),
  routes
});

router.afterEach(to => {
  console.log(to);
});

//네비게이션 가드 사용
// router.beforeEach((to, from, next) => {
//   //로컬스토리지에 userInfo가 없다면 홈으로 리다이렉트
//   console.log("네비게이션 가드 탐");
//   console.log(to);
//   console.log(from);
//   if (sessionStorage.getItem("userInfo") === null) {
//     // alert("로그인해주세요");
//     console.log("로그인해주세요");
//     return next("/");
//   }
//   //로컬스토리지에 userInfo가 있다면 다음페이지로 이동
//   else if (sessionStorage.getItem("userInfo") != null) {
//     return next();
//   }
// });

export default router;
