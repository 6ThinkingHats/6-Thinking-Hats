<template>
  <nav-bar></nav-bar>
  <router-view/>
  <background-wave></background-wave>
</template>

<script>
import NavBar from "@/views/main/header/NavBar.vue"
import BackgroundWave from "@/views/main/background/BackgroundWave.vue"

export default{
  components : {
    NavBar,
    BackgroundWave
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: #42b983;
}
</style>
