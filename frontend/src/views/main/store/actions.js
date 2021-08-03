// API
import $axios from "axios";

export function requestLogin({ state }, payload) {
  console.log("requestLogin", state, payload);
  const url = "/auth/login";
  let body = payload;
  return $axios.post(url, body);
}

export function requestSignUp({ state }, payload) {
  console.log("requestSignUp", state, payload);
  const url = "/users";
  let body = payload;
  return $axios.post(url, body);
}

export function verifyAuth({ state, commit }) {
  console.log("로그인 한 회원인지 검증");
  let accessToken = localStorage.getItem("token");
  const url = "/users/me";
  $axios
    .get(url, {
      headers: {
        Authorization: `Bearer ${accessToken}`
      }
    })
    .then(res => {
      commit("setAuth", res.data);
      console.log(state.auth);
    })
    .catch(err => {
      if (
        err.response.statusText === "SignatureVerificationException" ||
        err.response.statusText === "JWTDecodeException"
      ) {
        alert("세션이 유효하지 않습니다.");
      } else if (err.response.statusText === "TokenExpriedException") {
        alert("세션이 만료되었습니다.");
      }
      commit("setAuth", null);
    });
}

// 방 생성
export function createRoom({ state, commit }, payload) {
  console.log("방 생성 action 작동");
  const url = "/rooms";
  $axios
    .post(url, payload)
    .then(res => {
      commit("CREATE_ROOM", res.data);
      console.log(res.data);
      console.log("state의 rooms : 현재 사용자가 참여하고 있는 방들의 정보");
      console.log(state.rooms);
    })
    .catch(err => {
      console.log(err);
    });
}
// 방 수정
export function modifyRoom({ state, commit }, payload) {
  console.log("방 수정 action 작동");
  const url = "/rooms";
  $axios
    .put(url + payload)
    .then(res => {
      commit("MODIFY_ROOM", res.data);
      console.log(res.data);
      console.log("state의 rooms : 현재 사용자가 참여하고 있는 방들의 정보");
      console.log(state.rooms);
    })
    .catch(err => {
      console.log(err);
    });
}
// 방 삭제
// export function deleteRoom({ state, commit }, payload) {
//   console.log("방 삭제 action 작동");
//   const url = "/room";
//   $axios
//     .delete(url + payload)
//     .then(res => {
//       commit("DELETE_ROOM", res.data);
//       console.log(res.data);
//       console.log("state의 rooms : 현재 사용자가 참여하고 있는 방들의 정보");
//       console.log(state.rooms);
//     })
//     .catch(err => {
//       console.log(err);
//     });
// }

// 방 정보를 코드로 찾기
export function getRoomByCode({ state, commit }, payload) {
  console.log("방 정보 code로 찾기 action 작동");
  const url = "/rooms/code/";
  $axios
    .get(url + payload)
    .then(res => {
      console.log("state의 rooms : code로 찾은 방의 정보");
      console.log(res.data);
      return res.data;
    })
    .catch(err => {
      console.log(err);
    });
}
//방 정보를 id로 찾기
export function getRoomByCode({ state, commit }, payload) {
  console.log("방 정보 id로 찾기 action 작동");
  const url = "/rooms/id/";
  $axios
    .get(url + payload)
    .then(res => {
      console.log("state의 rooms : id로 찾은 방의 정보");
      console.log(res.data);
      return res.data;
    })
    .catch(err => {
      console.log(err);
    });
}
// 방에 참여하기
export function joinRoom({ state, commit }, payload) {
  console.log("code와 password로 방의 패스워드가 일치하는지 확인");
  const url = "/rooms/join";
  $axios
    .post(url, payload)
    .then(res => {
      console.log("code와 password로 일치하는지 여부");
      console.log(res.data.message);
      console.log(res.data.statusCode);
      return res.data;
    })
    .catch(err => {
      console.log(err);
    });
}
