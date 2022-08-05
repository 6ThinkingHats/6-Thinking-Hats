<template>
  <ul>
    <li><router-link to="/"><img src = "@/assets/logoAlphabet.png" id="logo" href="/"></router-link></li>
    <li><router-link to="/noticepage" class = "left">공지사항</router-link></li>
    <li><router-link to="/explainpage" class = "left">설명</router-link></li>
    <li><router-link to="/teampage" class = "left">팀소개</router-link></li>
    <li><router-link to="/qnapage" class = "left">Q&A</router-link></li>
    <li><router-link to="/signuppage" class = 'right'>회원가입</router-link></li>
    <li><router-link to="/loginpage" class = "right">로그인</router-link></li>
    <li><router-link v-if="token" to="/loginpage" class = "right">방 생성</router-link></li>
    <li><router-link to="/conferencepage" class = "right">방 참가</router-link></li>
  </ul>
</template>

<script>
export default {
  name : 'NavBar',
  data(){
    return{
    token: ""
    }
  },
}
</script>

<style>
#logo:hover{
  background-color: white;
}
img{
  width : 60px;
  height: 60px;
  float : left;
}
.left {
  padding : 10px;
  float : left;
}
.right{
  float : right;
}
ul {
  border-bottom: 1px solid black;
  list-style-type: none;
  margin: 0;
  padding: 0;
  overflow: hidden;
  background-color: white;
  position: sticky;
  top: 0;
  width: 100%;

}

li a {
  display: block;
  color: black;
  text-align: center;
  padding: 14px 16px;
  text-decoration: none;
}
li {
  display: inline;
}
li a:hover {
  background-color: black;
  color: white;
}
</style>