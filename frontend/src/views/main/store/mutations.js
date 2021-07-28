export function setPlatform(state, isDesktop) {
  state.isDesktopPlatform = isDesktop;
}

export function setMenuActive(state, index) {
  console.log("setMenuActive", state, index);
  const keys = Object.keys(state.menus);
  state.activeMenu = keys[index];
}

export function setConferenceActive(state, index) {
  console.log("setMenuActive", state, index);
  const keys = Object.keys(state.conferences);
  state.activeMenu = keys[index];
}

export function setMenuActiveMenuName(state, menuName) {
  state.activeMenu = menuName;
}

export function setAuth(state, auth) {
  state.auth = auth;
}
export function setLogin(state, isLogin) {
  state.isLogin = isLogin;
}
