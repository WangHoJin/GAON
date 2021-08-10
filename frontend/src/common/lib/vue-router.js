import { createRouter, createWebHistory } from "vue-router";
import store from "../lib/store";

const routes = [
  // 1번째 라우터
  {
    // 처음 들어왔을떄 들어간 방이 있는지 없는지 판단하는 페이지
    path: "/",
    component: () => import("../../views/conferences/components/default.vue"),
    beforeEnter: async function(to, from, next) {
      console.log("라우터가드");
      console.log(store.state.roomModule.isClickPlusBtn);
      await store.dispatch(
        "getRoomByUserId",
        JSON.parse(sessionStorage.getItem("userInfo")).id
      );
      if (!store.state.roomModule.isClickPlusBtn) {
        console.log("최초 로그인 상태일때");
        console.log(store.getters.rooms.length);
        if (store.getters.rooms.length > 0) {
          console.log("참여하고있는 방들이 있다면 ");
          return next("/main");
        } else {
          console.log("참여하고 있는 방들이 없다면");
          return next();
        }
      } else {
        console.log("plus버튼을 눌렀습니다");
        next();
      }
    }
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
            // 하위 게시판 페이지
            path: "board/:bid",
            name: "board",
            component: () =>
              import("../../views/conferences/components/board.vue"),
              redirect: {
                name: 'board-post-list'
              },
            children: [
              {
                path: "",
                name: "board-post-list",
                component: () => import("@/views/conferences/components/board/BoardList.vue")
              },
              {
                path: "create",
                name: "board-post-create",
                component: () => import("@/views/conferences/components/board/BoardCreate.vue")
              },
              {
                path: "view/:pid",
                name: "board-post-view",
                component: () => import("@/views/conferences/components/board/BoardView.vue")
              },
              {
                path: "modify/:pid",
                name: "board-post-modify",
                component: () => import("@/views/conferences/components/board/BoardModify.vue")
              }
            ]
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
            path: "meeting",
            name: "conference-meeting",
            component: () =>
              import("../../views/conferences/components/meeting.vue")
          },
          {
            // 하위 테이블 페이지
            path: ":subject/table/:tableId",
            name: "table-detail",
            component: () =>
              import("@/views/conferences/components/table-detail.vue")
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
  console.log("구글라우터가드==============================================");
  console.log(sessionStorage.getItem("userInfo"));
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
