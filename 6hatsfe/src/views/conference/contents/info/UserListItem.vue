<template>
  <div class="name-box">
    <span class="user-name">이름</span>
    <div class="cnt-status">
      <span class="cnt-hat"></span>
      <ul class="select">
        <li class="status red-hat"></li>
        <li class="status yellow-hat"></li>
        <li class="status green-hat"></li>
        <li class="status blue-hat"></li>
        <li class="status white-hat"></li>
        <li class="status black-hat"></li>
        <li class="status random-hat"><i></i></li>
        <li class="status watch"><i></i></li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  name: 'UserListItme',
  components: {
	},
	data: () => {
		return {
		}
	},
	computed: {
	},
	methods: {
	},
}
</script>

<style scoped>
* {
  box-sizing: border-box;
}

.name-box {
  width: 184px;
  height: 24px;
  border-radius: 8px;
  background-color: #FFC0BA;
}

.select {
  position: absolute;
  display: none;
  margin: 0;
  padding: 0;
  background-color: #F6F6F6;
  padding: 2px 0;
  border-radius: 4px;
  height: 50px;
}

ul li {
  list-style: none;
  margin: 4px 0;
}

ul li:hover {
  cursor: pointer;
}

.cnt-hat {
  border-radius: 50%;
  width: 20px;
  height: 20px;
  background-color: #EA4335;
  display: block;
}

.cnt-hat:hover {
  cursor: pointer;
}

.cnt-status {
  position: relative;
  float: right;
  padding-left: 4px;
}

.cnt-status:hover > .select {
  display: block;
}

.status {
  width: 20px;
  height: 20px;
  border-radius: 50%;
}

.name-box {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 12px 0;
}

.user-name {
  margin: 0;
}

.red-hat {
  background-color: #EA4335;
}

.yellow-hat {
  background-color: #FBBC05;
}

.black-hat {
  background-color: black;
}

.white-hat {
  background-color: #C7C6C6;
}

.green-hat {
  background-color: #34A853;
}

.blue-hat {
  background-color: #4285F4;
}

.random-hat {
 background-color: #585858;
}

.watch {
  background-color: #585858;
}
</style>