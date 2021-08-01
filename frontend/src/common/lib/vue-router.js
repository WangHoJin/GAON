import { createRouter, createWebHistory } from "vue-router";
// import Home from "@/views/home/home";
// import History from "@/views/history/history";
// import { nextTick } from "vue";
// import GoogleLogin from "@/views/google-login/google-login.vue";
// const fullMenu = require("@/views/main/menu.json");
// function makeRoutesFromMenu() {
//   let routes = Object.keys(fullMenu).map(key => {
//     if (key === "home") {
//       return { path: fullMenu[key].path, name: key, component: Home };
//     } else if (key === "history") {
//       return { path: fullMenu[key].path, name: key, component: History };
//     } else if (key === "room1") {
//       return { path: fullMenu[key].path, name: key, component: Home };
//     }
//     // 맨처음 시작화면 google로그인 창 띄우기
//     // else if (key === "google") {
//     //   return { path: fullMenu[key].path, name: key, component: GoogleLogin };
//     // }
//     else {
//       // menu.json 에 들어있는 로그아웃 메뉴
//       return null;
//     }
//   });
//   // 로그아웃 파싱한 부분 제거
//   routes = routes.filter(item => item);
//   // menu 자체에는 나오지 않는 페이지 라우터에 추가(방 상세보기)
//   routes.push({
//     path: "/conferences/:conferenceId",
//     name: "conference-detail",
//     component: ConferencesDetail
//   });
//   return routes;
// }

const routes = [
  {
    // 처음 들어왔을떄 들어간 방이 있는지 없는지 판단하는 페이지
    path: "/",
    component: () => import("../../views/conferences/components/default.vue")
  },
  {
    // 홈 페이지 ex 환영인사 등
    path: "/conference",
    name: "conference-main",
    component: () => import("@/views/conferences/conference-main.vue")
  },
  {
    // 각 회의실 상세보기 페이지
    path: "/conference/:conferenceId",
    name: "conference-detail",
    component: () => import("@/views/conferences/conference-detail.vue"),
    children: [
      {
        // 하위 공지사항 페이지
        path: "notice",
        name: "conference-notice",
        component: () => import("../../views/conferences/components/notice.vue")
      },
      {
        // 하위 과제제출 페이지
        path: "homework",
        name: "conference-homework",
        component: () =>
          import("../../views/conferences/components/homework.vue")
      },
      {
        // 하위 학습자료 페이지
        path: "studymaterial",
        name: "conference-studymaterial",
        component: () =>
          import("../../views/conferences/components/studymaterial.vue")
      },
      {
        // 하위 출석부 페이지
        path: "rollbook",
        name: "conference-rollbook",
        component: () =>
          import("../../views/conferences/components/rollbook.vue")
      }
    ]
  }
];

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
