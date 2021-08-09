import { createStore } from "vuex";
import root from "@/views/main/store";
import createPersistedState from "vuex-persistedstate";
import roomModule from "../../views/main/store/room.module";
import roomMemberModule from "../../views/main/store/roomMember.module";

export default createStore({
  modules: {
    root,
    roomModule,
    roomMemberModule
  },
  plugins: [createPersistedState()]
});
