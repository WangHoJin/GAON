import { createRouter, createWebHistory } from "vue-router";

const routes = [
  // 1번째 라우터
  {
    // 처음 들어왔을떄 들어간 방이 있는지 없는지 판단하는 페이지
    path: "/",
    component: () => import("../../views/conferences/components/default.vue")
  },
  {
    // 구글로그인 페이지
    path: "/googlelogin",
    component: () => import("../../views/google-login/google-login.vue")
  },
  {
    // 2번째 라우터
    // 메인페이지
    path: "/main",
    name: "main",
    component: () => import("../../views/main/main.vue"),
    children: [
      {
        // 3번째 라우터
        // 홈 페이지
        path: "",
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
            component: () =>
              import("../../views/conferences/components/notice.vue")
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
          },
          {
            // 화상회의 start
            path: "rollbook",
            name: "conference-meeting",
            component: () =>
              import("../../views/conferences/components/meeting.vue")
          }
        ]
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

router.beforeEach((to, from, next) => {
  if (sessionStorage.getItem("userInfo") === null) {
    if (to.fullPath != "/googlelogin") {
      console.log("로그인해주세요");
      return next("/googlelogin");
    } else {
      next();
    }
  } else if (sessionStorage.getItem("userInfo") != null) {
    return next();
  }
});

export default router;
