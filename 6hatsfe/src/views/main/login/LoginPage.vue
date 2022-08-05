<template>
  <div>
      <h2>Log In</h2>
          <input placeholder="ID" v-model="user.email" >
          <br>
          <input placeholder="password" v-model="user.password" type = "password">
          <button type="submit" v-on:click="getUserToken">Login</button>
  </div>
  <div>{{token}}</div>
</template>
<script>
import http from "@/api/http";

export default {
  data() {
    return {
      user: {
        email: '',
        password: '',
      }
    };
  },
  methods: {
    getUserToken() {
      console.log(this.user);
      alert("");
      http
        .post("/user/login", null, { params: this.user })
        .then((data) => {
          alert();
          localStorage.setItem("access-token", data.data["access-token"]);//access-token 로컬 스토리지에 저장
        })
        .catch((err) => {
          alert(err);
        });
    }
  }
}
</script>

<style>

</style>