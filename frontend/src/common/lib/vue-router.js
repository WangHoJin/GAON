import { createRouter, createWebHistory } from "vue-router";

import store from "../lib/store";

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

const router = createRouter({
  history: createWebHistory(),
  routes
});

router.afterEach(to => {
  // console.log(to);
});
function second(to, from, next) {
  console.log("2");
  console.log("구글로그인으로 가는게 먼저 실행되었음");
  // goGoogleLogin(to, next);
  if (to.fullPath != "/googlelogin") {
    return next("/googlelogin");
  } else {
    next();
  }
}
async function first() {
  console.log("1");
  let authObject = undefined;
  await gapi.load("auth2", function() {
    console.log("init실행됐니? 1-1");
    gapi.auth2.init();
    authObject = gapi.auth2.getAuthInstance();
    console.log("authObject: " + authObject);
    console.log(authObject);

    let isGoogleLogin = authObject.isSignedIn.get();
    console.log("isGoogleLogin");
    console.log(isGoogleLogin);

    if (isGoogleLogin) {
      // 구글로그인이 되어있는 상태이면 disconnect
      console.log("만약 로그인이 되어있는 상태면 끊기");
      authObject.signOut().then(function() {
        console.log("user first signed out");
      });
      authObject.disconnect();
    } else {
      // 안되어있는 상태이면 아무것도하지않기.
      console.log("로그인이 안되어있으면 아무것도 하지않기");
    }
  });
}
router.beforeEach(async function(to, from, next) {
  console.log("구글라우터가드==============================================");
  // 회원탈퇴한 경우 탈퇴 페이지로 이동하기
  if (to.fullPath == "/quit") {
    next();
  }
  // 세션에 유저정보가 없는경우 = 세션 만료가 돼서 로그아웃이 됐다 or 최초접속자다
  if (sessionStorage.getItem("userInfo") === null) {
    // console.log("1");
    // let authObject = undefined;
    // await gapi.load("auth2", function() {
    //   console.log("init실행됐니? 1-1");
    //   gapi.auth2.init();
    //   authObject = gapi.auth2.getAuthInstance();
    //   console.log("authObject: " + authObject);
    //   console.log(authObject);

    //   let isGoogleLogin = authObject.isSignedIn.get();
    //   console.log("isGoogleLogin");
    //   console.log(isGoogleLogin);

    //   if (isGoogleLogin) {
    //     // 구글로그인이 되어있는 상태이면 disconnect
    //     console.log("만약 로그인이 되어있는 상태면 끊기");
    //     authObject.signOut().then(function() {
    //       console.log("user first signed out");
    //     });
    //     authObject.disconnect();
    //   } else {
    //     // 안되어있는 상태이면 아무것도하지않기.
    //     console.log("로그인이 안되어있으면 아무것도 하지않기");
    //   }
    // });
    // console.log("2");
    // console.log("구글로그인으로 가는게 먼저 실행되었음");
    // goGoogleLogin(to, next);
    if (to.fullPath != "/googlelogin") {
      return next("/googlelogin");
    } else {
      next();
    }
    // await first().then(() => {
    //   console.log("기다려기다려기다려");
    //   // second(to, from, next);
    // });
    // await first().then();
  }
  // 세션에 유저정보가 있는 경우
  else if (sessionStorage.getItem("userInfo") != null) {
    return next();
  }
});

function goGoogleLogin(to, next) {
  console.log("구글로그인 함수를 참조 가능했음.");
  if (to.fullPath != "/googlelogin") {
    next("/googlelogin");
  } else {
    next();
  }
}

export default router;
