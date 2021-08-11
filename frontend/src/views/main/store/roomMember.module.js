// API
import $axios from "axios";

export default {
  namespace: true,
  state: {},
  getters: {},
  mutations: {},
  actions: {
    async addRoomMember({ state, commit }, roomMemberInfo) {
      console.log("actions addRoomMember roomMemberModule");
      const url = "/room-member";
      await $axios
        .post(url, roomMemberInfo)
        .then(res => {
          console.log("response from addRoomMember");
          console.log(res.data);
        })
        .catch(err => {
          console.log(err.response);
          console.log("이미 참가한 방입니다");
          if (err.response.data.statusCode == 409) {
            alert("이미 방에 참가중입니다!");
            response = null;
          }
        });
    },
    async deleteRoomMember({ state, commit }, roomMemberInfo) {
      console.log("actions deleteRoomMember roomMemberModule");
      const url = "/room-member";
      await $axios
        .delete(url, roomMemberInfo)
        .then(res => {
          console.log("response from deleteRoomMember");
          console.log(res.data);
        })
        .catch(err => {
          console.log(err);
        });
    }
  }
};
