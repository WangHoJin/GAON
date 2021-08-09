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
          console.log(err);
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
