
export default {
  state: {
    token: localStorage.getItem('access-token') ? localStorage.getItem('access-token') : ""
  },
  getters: {
    token(state) {
      return state.token
    }
  },
  mutations: {
  },
  actions: {
  },
  modules: {
  }
}
