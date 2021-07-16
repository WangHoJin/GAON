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
