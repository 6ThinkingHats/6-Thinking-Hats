<template>
  <img src="">
  <h2>{{state.user.name}}</h2>
      <li><router-link to="/profilepage" >내 정보</router-link></li>
      <li><router-link to="/historypage" >회의 이력</router-link></li>
      <li><button>로그아웃</button></li>
</template>

<script>
export default {
    name:'MyprofileModal',

}
</script>

<style>

</style>