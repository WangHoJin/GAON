// Vuex 조립
import state from "./state";
import * as getters from "./getters";
import * as mutations from "./mutations";
import * as actions from "./actions";

// moudule명
const root = {
  namespaced: true,
  state,
  getters,
  mutations,
  actions
};
// store.js에서 조립
export default root;
