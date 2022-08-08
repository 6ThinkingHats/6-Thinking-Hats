<template>
  <div>
      <h2>로그인</h2>
          <input placeholder="ID" v-model="user.email" >
          <br>
          <input placeholder="password" v-model="user.password" type = "password">
          <button type="submit" v-on:click="getUserToken">Login</button>
  </div>
  <div>{{id}}</div>
</template>
<script>
import http from "@/api/http";
import jwt_decode from "jwt-decode";
import { mapGetters } from "vuex";

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
      http
        .post("/user/login", this.user )
        .then((data) => {
          localStorage.setItem("access-token", data.data["access-token"]);//access-token 로컬 스토리지에 저장
          this.$store.commit('ChangeToken')
          var token=localStorage.getItem('access-token');
          var decoded = jwt_decode(token);//token 디코드
          //console.log(this.$store);
          this.$store.commit('ChangeId',decoded.userId);//id저장
          console.log(this.$store.state.users.id);

          http
        .get("/user/" + this.$store.state.users.id, null)
        .then((res) => {
          
          const info = res.data.user;
          this.$store.commit('ChangeName',info.name);
          this.$store.commit('ChangeJob',info.job);
          this.$store.commit('ChangeBirth',info.birth);
          this.$store.commit('ChangeEmail',info.email);
          this.$store.commit('ChangeGender',info.gender);
          location.replace('/')
          console.log(this.$store.state.users);
        }).catch((err) => {
          alert(err);
        })
        })
        .catch((err) => {
          alert(err);
        });

    }


},
  computed:{
    ...mapGetters(['id'])
  }}

</script>

<style>

</style>