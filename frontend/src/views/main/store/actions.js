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

export function verifyAuth({ commit }) {
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
    })
    .catch(function(err) {
      commit("setAuth", null);
    });
}
