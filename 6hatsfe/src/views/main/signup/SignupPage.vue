<template>
  <div>
      <h2>회원가입</h2>
        Email
        <input placeholder="입력해주세요" v-model="user.email">
        <br>
        비밀번호
        <input placeholder="입력해주세요" v-model="user.password" type="password">
        <br>
        비밀번호 확인
        <input placeholder="입력해주세요" type="password">
        <br>
        이름
        <input placeholder="입력해주세요" v-model="user.name">
        <br>
        성별
        <input type="radio" v-model="user.gender" value="MAN">남
        <input type="radio" v-model="user.gender" value="WOMAN">여
        <br>
        생년월일
        <input type="date" v-model="user.birth"/>
        <br>
        직업
        <select v-model="user.job">
  <option value="OFFICE">직장인</option>
  <option value="STUDENT" selected>학생</option>
  <option value="JOBLESS">무직</option>
  <option value="OTHER">기타</option>
</select>
        <button type="submit" v-on:click="SignupUser">회원가입</button>
  </div>
</template>

<script>
import http from "@/api/http.js";
import { mapGetters } from "vuex";

export default {
    name: 'SignupPage',
  data() {
    return {
      user: {
        email: '',
        password: '',
        gender: '',
        birth: '',
        job: '',
        name:''

      },
    };
  },
  methods: {
    SignupUser() { // user 회원가입 함수
      console.log(this.user);
      alert("");
      http
        .post("/user", this.user)
        .then((data) => {
          console.log(data);
          alert();
        })
        .catch((err) => {
          alert(err);
        });
    }
  },
  computed:{
    ...mapGetters(['token'])
  }
}
</script>
<style>
 div{

}
</style>