// API
import $axios from "axios";

export default {
  namespace: true,
  state: {
    rooms: [], //사용자가 참여하고있는 방들의 정보
    isClickPlusBtn: false, //플러스 버튼을 눌렀는지 안눌렀는지 체크
    boards: [] // 해당 방의 게시판
  },
  getters: {
    rooms(state) {
      return state.rooms;
    },
    room(state) {
      return state.room;
    },
    boards(state) {
      return state.boards;
    }
  },
  mutations: {
    CREATE_ROOM(state, payload) {
      let roomInfo = {
        code: payload.code,
        description: payload.description,
        host_id: payload.host_id,
        id: payload.id,
        name: payload.name
      };
      state.rooms.push(roomInfo);
    },
    SET_ROOM_BY_USERID(state, payload) {
      console.log("set room by userID at mutation");
      state.rooms = payload;
      /// 배열 상태 변화 감지위해
      state.rooms.push({});
      state.rooms.pop();
    },
    SET_BOARDS(state, payload) {
      console.log("set boards ");
      state.boards = payload;
      state.boards.push({});
      state.boards.pop();
    }
  },
  actions: {
    async createRoom({ state, commit }, payload) {
      console.log("방 생성 action 작동");
      const url = "/rooms";
      let response = "";
      await $axios
        .post(url, payload)
        .then(res => {
          commit("CREATE_ROOM", res.data);
          return (response = res.data);
        })
        .catch(err => {
          console.log(err);
        });
      return response;
    },
    async modifyRoom({ state, commit }, payload) {
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
          response = res.data;
        })
        .catch(err => {
          console.log(err);
        });
      return response;
    },
    async deleteRoom({ state, commit }, payload) {
      console.log("방 삭제 action 작동");
      const url = "/rooms/";
      let response = "";
      await $axios
        .delete(url + payload)
        .then(res => {
          response = res.data;
        })
        .catch(err => {
          console.log(err);
        });
      return response;
    },
    // 방 정보를 코드로 찾기
    async getRoomByCode({ state, commit }, payload) {
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
    },
    // 방에 참여하기 success
    async joinRoom({ state, commit }, payload) {
      console.log("code와 password로 방의 패스워드가 일치하는지 확인");
      const url = "/rooms/verity";
      let response = "";
      await $axios
        .post(url, payload)
        .then(res => {
          if (res.data.statusCode == 200) {
            console.log(res.data);
            response = res.data;
          }
        })
        .catch(err => {
          console.log(err.response);
          if (err.response.data.statusCode == 401) {
            alert(err.response.data.message);
            response = null;
          } else if (err.response.data.statusCode == 404) {
            alert(err.response.data.message);
            response = null;
          }
        });
      return response;
    },

    //방 정보를 id로 찾기
    async getRoomById({ state, commit }, payload) {
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
    },
    // 해당 방의 게시판들 찾기
    async getBoardsByRoomId({ state, commit }, id) {
      console.log("get board in actions");
      let response = "";
      await $axios
        .get("/rooms/id/" + id + "/boards")
        .then(res => {
          console.log(res.data.boards);
          commit("SET_BOARDS", res.data.boards);
          response = res.data.boards;
        })
        .catch(err => {
          console.log(err);
        });
      return response;
    },
    // 해당 방의 멤버들 찾기
    async getMembersByUsingRoomId({ state, commit }, id) {
      console.log("members from getmembers by using");
      let response = "";
      await $axios
        .get("/rooms/id/" + id + "/members")
        .then(res => {
          console.log(res.data.members);
          //commit("SET_ROOM_MEMBER", res.data.members);
          response = res.data.members;
        })
        .catch(err => {
          console.log(err);
        });
      return response;
    },
    // 해당 유저가 참가한 모든 방들 찾기 test안해봄
    async getRoomByUserId({ state, commit }, payload) {
      console.log("get Rooms by userId in actions");
      const url = "/rooms/uid/";
      await $axios
        .get(url + payload)
        .then(res => {
          commit("SET_ROOM_BY_USERID", res.data.rooms);
        })
        .catch(err => {
          console.log(err);
        });
    }
  }
};
