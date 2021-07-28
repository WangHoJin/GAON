// 플랫폼 관련 정보 가져오기
export function getIsDesktopPlatform(state) {
  return state.isDesktopPlatform;
}
// 메뉴 객체 가져오기
export function getMenus(state) {
  return state.menus;
}
// Active된 메뉴 인덱스 가져오기
export function getActiveMenuIndex(state) {
  const keys = Object.keys(state.menus);
  return keys.findIndex(item => item === state.activeMenu);
}
// 사이드바 툴 가져오기
export function getSidebarTools(state) {
  return state.sidbarTools;
}
// conference 갯수 가져오기
export function getNumberOfConferneces(state) {
  return Object.keys(state.conferences).length
}

export function getAuth(state) {
  console.log("state.auth", state.auth);
  return state.auth;
}
export function getLogin(state) {
  return state.isLogin;
}
