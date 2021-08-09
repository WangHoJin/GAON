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
      const url = "/roommem";
      await $axios
        .post(url, roomMemberInfo)
        .then(res => {
          console.log("response from addRoomMember");
          console.log(res.data);
        })
        .catch(err => {
          console.log(err);
        });
    }
  }
};
