
export default {
  state: {
    token: localStorage.getItem('access-token') ? localStorage.getItem('access-token') : ""
    ,  
    id:null,
    name:null,
    email:null,
    gender:null,
    job:null,
    birth:null
  },
  getters: {
    token(state) {
      return state.token
    },
    id(state){
      return state.id
    },
    gender(state){
      return state.gender
    },
    job(state){
      return state.job
    },
    email(state){
      return state.email
    },
    name(state){
      return state.name
    },
    birth(state){
      return state.birth
    }
  },
  mutations: {
    ChangeToken(state){
      state.token=localStorage.getItem('access-token')
    },
    ChangeId(state,value){
      state.id=value
    },
    ChangeEmail(state,value){
      state.email=value
    },
    ChangeName(state,value){
      state.name=value
    },
    ChangeJob(state,value){
      state.job=value
    },
    ChangeGender(state,value){
      state.gender=value
    },
    ChangeBirth(state,value){
      state.birth=value
    }
  },
  actions: {
  },
  modules: {
  }
}
