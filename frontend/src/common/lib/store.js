import { createStore } from "vuex";
import root from "@/views/main/store";
import createPersistedState from "vuex-persistedstate";
import roomModule from "../../views/main/store/room.module";

export default createStore({
  modules: {
    root,
    roomModule
  },
  plugins: [createPersistedState()]
});
