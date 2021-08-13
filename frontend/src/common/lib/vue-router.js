import { createRouter, createWebHistory } from "vue-router";
import store from "../lib/store";

const routes = [
  // 1번째 라우터
  {
    // 처음 들어왔을떄 들어간 방이 있는지 없는지 판단하는 페이지
    path: "/",
    component: () => import("../../views/conferences/components/default.vue"),
    beforeEnter: async function(to, from, next) {
      await store.dispatch(
        "getRoomByUserId",
        JSON.parse(sessionStorage.getItem("userInfo")).id
      );
      if (!store.state.roomModule.isClickPlusBtn) {
        if (store.getters.rooms.length > 0) {
          return next("/main");
        } else {
          return next();
        }
      } else {
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
    // 회원탈퇴 페이지
    path: "/quit",
    component: () => import("../../views/main/Quit.vue")
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
              name: "board-post-list"
            },
            children: [
              {
                path: "",
                name: "board-post-list",
                component: () =>
                  import("@/views/conferences/components/board/BoardList.vue")
              },
              {
                path: "create",
                name: "board-post-create",
                component: () =>
                  import("@/views/conferences/components/board/BoardCreate.vue")
              },
              {
                path: "view/:pid",
                name: "board-post-view",
                component: () =>
                  import("@/views/conferences/components/board/BoardView.vue")
              },
              {
                path: "modify/:pid",
                name: "board-post-modify",
                component: () =>
                  import("@/views/conferences/components/board/BoardModify.vue")
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
//
const router = createRouter({
  history: createWebHistory(),
  routes
});

router.afterEach(to => {
  // console.log(to);
});

router.beforeEach(async (to, from, next) => {
  console.log("구글라우터가드==============================================");
  // 회원탈퇴한 경우 탈퇴 페이지로 이동하기
  if (to.fullPath == "/quit") {
    return next();
  }
  var auth2 = undefined;
  if (sessionStorage.getItem("userInfo") === null) {
    await new Promise((resolve, reject) => {
      gapi.load("auth2", async function() {
        await gapi.auth2.init();
        auth2 = await gapi.auth2.getAuthInstance();

        let isGoogleLogin = await auth2.isSignedIn.get();
        if (isGoogleLogin) {
          // 구글로그인이 되어있는 상태이면 로그아웃 isGoogleLogin -> false가 된다.
          await auth2.signOut().then(function() {
            console.log("user first signed out");
          });
          auth2.disconnect(); // 사용자가 부여한 모든 범위 취소
          isGoogleLogin = await auth2.isSignedIn.get();
        } else {
        }
        resolve();
      });
    });
    if (to.fullPath != "/googlelogin") {
      return next("/googlelogin");
    } else {
      return next();
    }
  }
  // 세션에 유저정보가 있는 경우
  else if (sessionStorage.getItem("userInfo") != null) {
    return next();
  }
});

export default router;
