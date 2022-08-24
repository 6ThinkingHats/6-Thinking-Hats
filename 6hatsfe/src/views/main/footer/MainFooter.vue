<template>
  <div class="footer">
    <div class="dummy">
    </div>
    <div class="footer-text">
      <p class="team-contact">Team Contact : 010-8756-2387</p>
      <p class="team-github">Team Github : 
        <a id="gitlink" href="https://lab.ssafy.com/s07-webmobile1-sub2/S07P12A709">https://lab.ssafy.com/s07-webmobile1-sub2/S07P12A709</a>
      </p>
    </div>
    <a class="git-icon-link" href="https://lab.ssafy.com/s07-webmobile1-sub2/S07P12A709">
      <img class="git-icon" src="@/assets/giticon.png" alt="giticon">
    </a>
  </div>
</template>

<script>
export default {
	name: 'MainFooter',
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

<style>
  @import url("@/assets/css/views/main/landing/LandingPage.css");

  .footer {
    display: flex;
    position : relative;
    transform : translateY(-100%);
    width:100%;
    height: 60px;
    bottom: 0px;
    justify-content: space-between;
    align-items: center;
    border-top: 1px solid #939393;
    background-color: white;
    box-sizing: border-box;
    font-family: 'Noto Sans KR', sans-serif;
    z-index: 5;
  }

  .footer-text {
    display: flex;
    flex-direction: column;
  }

  .team-contact, .team-github {
    font-size: 16px;
    margin-bottom: 0;
  }

  .dummy, .git-icon {
    width: 50px;
    height: 50px;
    margin: 0 16px;
  }
  #gitlink{
    text-decoration: none;
    color: black;
  }
</style>