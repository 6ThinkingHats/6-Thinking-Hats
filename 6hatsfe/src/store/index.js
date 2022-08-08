import { createStore } from 'vuex'
import users from '@/store/modules/users'

export default createStore({
  strict: true,
  modules: {
    users
  }
})
