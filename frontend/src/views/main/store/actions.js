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

// 방 생성 success
export async function createRoom({ state, commit }, payload) {
  console.log("방 생성 action 작동");
  const url = "/rooms";
  let response = "";
  await $axios
    .post(url, payload)
    .then(res => {
      console.log(res.data);
      // 사용자가 방을 추가했을때 사용자가 참여하고 있는 방들의 정보 업데이트 -> 추후
      // commit("CREATE_ROOM", res.data);
      // console.log("state의 rooms : 현재 사용자가 참여하고 있는 방들의 정보");
      // console.log(state.rooms);
      response = res.data.code;
      // console.log(this.response);
    })
    .catch(err => {
      console.log(err);
    });
  return response;
}
// 방 수정 success
export async function modifyRoom({ state, commit }, payload) {
  console.log("방 수정 action 작동");
  console.log(payload);
  const url = "/rooms/" + payload.id;
  let response = "";
  let roomInfo = {
    name: payload.name,
    description: payload.description
  };
  await $axios
    .put(url, roomInfo)
    .then(res => {
      // commit("MODIFY_ROOM", res.data);
      console.log(res.data);
      response = res.data;
    })
    .catch(err => {
      console.log(err);
    });
  return response;
}
// 방 삭제 success
export async function deleteRoom({ state, commit }, payload) {
  console.log("방 삭제 action 작동");
  const url = "/rooms/";
  let response = "";
  await $axios
    .delete(url + payload)
    .then(res => {
      response = res.data;
      // commit("DELETE_ROOM", res.data);
      // console.log(res.data);
      // console.log("state의 rooms : 현재 사용자가 참여하고 있는 방들의 정보");
      // console.log(state.rooms);
    })
    .catch(err => {
      console.log(err);
    });
  return response;
}

// 방 정보를 코드로 찾기 success
export async function getRoomByCode({ state, commit }, payload) {
  console.log("방 정보 code로 찾기 action 작동");
  const url = "/rooms/code/";
  let response = "";
  await $axios
    .get(url + payload)
    .then(res => {
      console.log("state의 rooms : code로 찾은 방의 정보");
      console.log(res.data);
      response = res.data;
    })
    .catch(err => {
      console.log(err);
    });
  return response;
}
//방 정보를 id로 찾기
export async function getRoomById({ state, commit }, payload) {
  console.log("방 정보 id로 찾기 action 작동");
  let response = "";
  const url = "/rooms/id/";
  await $axios
    .get(url + payload)
    .then(res => {
      console.log("state의 rooms : id로 찾은 방의 정보");
      response = res.data;
    })
    .catch(err => {
      console.log(err);
    });
  return response;
}
// 방에 참여하기 success
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

//==========================RoomMember======================
export async function roomMemberDelete({ state, commit }, payload) {
  console.log("action에 roomMemberDelete실행됨");
  const url = "roommem/"; //룸멤버id
}
