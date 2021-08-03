export function setPlatform(state, isDesktop) {
  state.isDesktopPlatform = isDesktop;
}

export function setMenuActive(state, index) {
  console.log("setMenuActive", state, index);
  const keys = Object.keys(state.menus);
  state.activeMenu = keys[index];
}

export function setConferenceActive(state, index) {
  console.log("setConferenceActive", state, index);
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
// 방 생성
export function CREATE_ROOM(state, roomInfo) {
  state.rooms.push(roomInfo);
}
// 방 수정
export function MODIFY_ROOM(state, modifyRoomInfo) {
  state.rooms.forEach(element => {
    if (element.id === modifyRoomInfo.id) {
      element = modifyRoomInfo;
    }
  });
}
// 방 삭제
// export function DELETE_ROOM(state, modifyRoomInfo) {
//   state.rooms = state.rooms.filter(element => )
// }
// 방 정보를 코드로 찾기
//방 정보를 id로 찾기
