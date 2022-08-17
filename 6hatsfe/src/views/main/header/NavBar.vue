<template>
  <ul v-if="!token" class="navbarul">
    <li><router-link to="/"><img src = "@/assets/logoAlphabet.png" id="logo" href="/" class="navbarleft"></router-link></li>
    <li><router-link to="/noticepage" class="navbarleft">공지사항</router-link></li>
    <li><router-link to="/explainpage" class="navbarleft">설명</router-link></li>
    <li><router-link to="/teampage" class="navbarleft">팀소개</router-link></li>
    <li><router-link to="/qnapage" class="navbarleft">Q&A</router-link></li>
    <li><router-link to="/signuppage" class="navbarright">회원가입</router-link></li>
    <li><router-link to="/loginpage" class="navbarright">로그인</router-link></li>
  </ul>
  <ul v-else class="navbarul">
    <li><router-link to="/"><img src = "@/assets/logoAlphabet.png" id="logo" href="/"></router-link></li>
    <li><router-link to="/noticepage" class="navbarleft">공지사항</router-link></li>
    <li><router-link to="/explainpage" class="navbarleft">설명</router-link></li>
    <li><router-link to="/teampage" class="navbarleft">팀소개</router-link></li>
    <li><router-link to="/qnapage" class="navbarleft">Q&A</router-link></li>
    <li>
      <div class="dropdown">
        <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
        {{name}}님
        </button>
        <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
          <li><div id="navbarprofilebox"><img src="@/assets/melong.jpg" id="navbarprofileimg"></div></li>
          <li><router-link to="/profilepage" class = "dropdown-item">내정보</router-link></li>
          <li><router-link to="/historypage" class = "dropdown-item">회의이력</router-link></li>
          <li><button class="dropdown-item" id= "logoutbtn" @click = "logout">로그아웃</button></li>
        </ul>
      </div>
    </li>
    <li id = "createroom" @click="createRoom" class = "navbarright">방 생성</li>
  </ul>
</template>

<script>
import { mapGetters } from "vuex"

export default {
  name : 'NavBar',
  methods: {
    logout() {
      localStorage.clear('access-token');
      localStorage.clear('username');
      this.$store.commit('ChangeToken', '');
      this.$router.push('/')
    },
    createRoom() {
      const sessionCode = Math.random().toString(22).substring(2,22);
      alert(sessionCode);
      this.$router.push({
        name: "ConferencePage",
        params: {
          sessionCode: sessionCode
        }
      })
    }
  },
  computed: {
    ...mapGetters(['loginstatus','name','token'])
  },
  created() {
    this.$store.commit("ChangeName", localStorage.getItem('username'));
  }


}
</script>

<style lang="css" scoped>
/* #logo:hover{
  background-color: white;
} */
#logo{
  width : 60px;
  height: 60px;
  float : left;
  margin-top : 5px;
  margin-left: 10px;
  margin-right: 10px;
  padding:0px;
}
#dropdown-menu{
  float: right;
}
.navbarul {
  border-bottom: 1px solid black;
  list-style-type: none;
  margin: 0;
  padding: 0;
  overflow: visible;
  background-color: white;
  position: sticky;
  top: 0;
  height: 72px
}
.navbarleft {
    color: black;
    text-decoration: none;
  padding: 14px 16px;
  margin : 10px;
  float : left;
}
.navbarright{
    color: black;
    text-decoration: none;
  padding: 14px 16px;
    margin : 10px;
  float : right;
}
li a:hover {
  background-color: black;
  color: white;
}
#dropdownMenuButton1{
  float: right;
  margin: 16px;
  color:black;
  background-color: white;
  border: 0px;
}
#navbarprofilebox{
    margin:auto;
    width: 60px;
    height: 60px; 
    border-radius: 70%;
    overflow: hidden;
    margin-bottom: 5px;
}
#navbarprofileimg{
    width: 100%;
    height: 100%;
    object-fit: cover;
}
#logoutbtn:hover{
    background-color: black;
    color: white;
}
#createroom:hover{
  background-color: black;
  color: white;
}

</style>