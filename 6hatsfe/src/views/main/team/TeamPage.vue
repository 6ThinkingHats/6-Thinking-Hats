<template>
	<div class="team-body">
		<!--맨위 팀 소개 -->
		<!--fe be 나누는 회색 바 -->
		<div class="team-row" style="align-items: center;padding:10px;">
			<button class="button button4">Front-End</button>
			<div class="team-row team-row-top">
				<img class="logo-team" src="@/assets/logoAlphabet.png" alt="logo-team">
			</div>
			<button class="button button5">Back-End</button>
		</div>
		<!--팀원 각자 소개 -->
		<div class="flip-card">
			<div class="flip-card-inner" style="border-color: #FFFFFF;">
				<div class="flip-card-front">
					<div>
						<img class="profile" src="@/assets/cbs.png" alt="최병성">
					</div>
					<div>
						<p style="font-weight: bold;">최병성</p>
						<p>Front-End 팀장</p>
						<p>애완거북이 '귀요미' 아빠</p>
					</div>
				</div>
				<div class="flip-card-back">
					<div>
						<img class="profile" src="@/assets/fsj.png" alt="오성준">
					</div>
					<div>
						<p style="font-weight: bold;">오성준</p>
						<p>프로젝트 팀장</p>
						<p>Back-End 팀장</p>
					</div>
				</div>
			</div>
			<div class="flip-card-inner" style="border-color: #FFFFFF;">
				<div class="flip-card-front">
					<div>
						<img class="profile" src="@/assets/ksr.png" alt="권수린">
					</div>
					<div>
						<p style="font-weight: bold;">권수린</p>
						<p>Front-End 팀원</p>
					</div>
				</div>
				<div class="flip-card-back">
					<div>
						<img class="profile" src="@/assets/ksj.png" alt="김성진">
					</div>
					<div>
						<p style="font-weight: bold;">김성진</p>
						<p>Back-End 팀원</p>
					</div>
				</div>
			</div>
			<div class="flip-card-inner" style="border-color: #FFFFFF;">
				<div class="flip-card-front">
					<div>
						<img class="profile" src="@/assets/hsy.png" alt="황석영">
					</div>
					<div>
						<p style="font-weight: bold;">황석영</p>
						<p>Front-End 팀원</p>
					</div>
				</div>
				<div class="flip-card-back">
					<div>
						<img class="profile" src="@/assets/cjw.png" alt="최지원">
					</div>
					<div>
						<p style="font-weight: bold;">최지원</p>
						<p>Back-End 팀원</p>
					</div>
				</div>
			</div>
		</div>
		<!--팀문서 링크 걸어놓음 -->
		<div class="team-row">
			<div class="polaroid" style="justify-content: center;border-color: #FFFFFF;">
				<div>
					<img src="@/assets/notion.png" alt="노션" style="width:100px;height100px;justify-content: center;"
						onclick="window.open('https://www.notion.so/9fab16fcf2344d6bbc8076c9e4f85383')">
				</div>
			</div>

			<div class="polaroid" style="justify-content: center;border-color: #FFFFFF;">
				<div>
					<img src="@/assets/gitlab.png" alt="깃랩" style="width:100px;height100px;"
						onclick="window.open('https://lab.ssafy.com/s07-webmobile1-sub2/S07P12A709')">
				</div>
			</div>

			<div class="polaroid" style="justify-content: center;border-color: #FFFFFF;">
				<div>
					<img src="@/assets/jira.png" alt="지라" style="width:100px;height100px;"
						onclick="window.open('https://jira.ssafy.com/secure/RapidBoard.jspa?rapidView=12687&projectKey=S07P12A709&view=planning.nodetail&issueLimit=100')">
				</div>
			</div>

			<div class="polaroid" style="justify-content: center;border-color: #FFFFFF;">
				<div>
					<img src="@/assets/youtube.png" alt="유투브" style="width:100px;height100px;"
						onclick="window.open('')">
				</div>
			</div>

		</div>
	</div>
</template>

<script>
export default {
	name: 'TeamPage',
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

<style lang="css" scoped>
@import url("@/assets/css/views/main/team/TeamPage.css");
</style>