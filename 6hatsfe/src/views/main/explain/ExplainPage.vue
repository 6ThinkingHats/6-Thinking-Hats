<template>
  <div class="explain-body">
    <img class="logo-explain" src="@/assets/logoExplain.png" alt="logoExplain">
    <div>
      <img src="@/assets/hatconference.png" alt="hatconference">
      <p>가 뭔가요?</p>
      <p>'모자라지 않은 회의'는 에드워드 드 보노(Edward de Bono)가 고안한 여섯 색깔 사고모자(Six thinking hats) 기법을 기반으로 진행하는 회의입니다.</p>
      <p>여섯 색깔의 모자들에게 서로 다른 성격과 역할이 주어지며, 각자 맡은 색깔에 맞게 역할을 수행하며 회의를 진행합니다.</p>
      <p>이 기법으로 회의 분위기가 감정적으로 변하는 것을 막고, 다양한 측면에서 폭넓은 사고를 할 수 있습니다.</p>
    </div>
    <div>
      <img src="@/assets/hatconference.png" alt="hatconference">
      <p>는 어떻게 하나요?</p>
      <p>먼저 방 생성 혹은 방 참가 버튼으로 회의방에 입장합니다.</p>
      <p>방장은 회의방에서 회의 모드를 설정하고, 참가자들은 각자 맡을 역할을 정합니다.</p>
      <p>회의를 시작하면 회의 모드에 맞게 발언 순서가 결정되며, 파란 모자는 회의 주제를 설정합니다.</p>
      <p>다른 모자들은 자기 차례가 되면 모자 색깔에 맞게 발언하며, 회의록에 자신의 의견을 정리해 작성합니다.</p>
    </div>
    <div>
      <p>어떤 역할이 있나요?</p>
    </div>
    <div>
      <img src="@/assets/randomhat.png" alt="randomhat">
      <p>랜덤 모자입니다.</p>
      <p>여섯 색깔의 모자들 중 임의의 모자 하나를 자동으로 선택해주는 모자입니다.</p>
      <p>쓴 사람이 없는 모자 색깔부터 먼저 선택됩니다.</p>
    </div>
    <div>
      <img src="@/assets/bluehat.png" alt="bluehat">
      <p>통제, 냉정함, 정리, 결론 도출</p>
      <p>파란 모자입니다.</p>
      <p>객관적, 이성적 판단을 기반으로 다른 모자들의 의견을 정리, 요약하여 결론을 제시하는 모자입니다.</p>
      <p>회의를 이끄는 리더로서 회의 주제를 정하고 회의를 진행합니다.</p>
      <p>정해진 발언 순서와 별개로 언제든지 발언할 수 있으며, 다른 모자의 발언 순서를 넘기거나 되돌릴 수도 있습니다.</p>
      <p>회의가 마무리됐다면 회의를 종료할 수 있습니다.</p>
    </div>
    <div>
      <img src="@/assets/greenhat.png" alt="greenhat">
      <p>창의적, 확산적, 혁신적</p>
      <p>기존의 사고에서 벗어나 새로운 아이디어를 제시하고, 약점을 보완할 대안을 제시하는 모자입니다.</p>
      <p>'아이디어 제안' 모드에서는 회의 초반에 주어진 정보들을 바탕으로 새로운 아이디어를 제시합니다.</p>
      <p>'아이디어 검증' 모드에서는 회의 후반에 다른 모자들의 의견을 듣고 이에 대한 대안을 제시합니다.</p>
    </div>
    <div>
      <img src="@/assets/redhat.png" alt="redhat">
      <p>직감, 감정, 정서</p>
      <p>두려움, 걱정, 의심, 예감 등의 감정을 기반으로 의견을 제시하는 모자입니다.</p>
      <p>제시하는 의견에 대한 근거나 정당성이 전혀 필요없습니다.</p>
      <p>주어진 아이디어 혹은 대안에 대해 어떤 느낌이 드는지 표현하면 됩니다.</p>
    </div>
    <div>
      <img src="@/assets/yellowhat.png" alt="yellowhat">
      <p>긍정적, 강점, 실현 가능성, 이점</p>
      <p>논리적인 근거를 기반으로 장점을 찾고 명확한 실행 방안을 제시하는 모자입니다.</p>
      <p>주어진 아이디어 혹은 대안에 어떤 이점이 있고 어떻게 구체화 될 수 있는지 제시합니다.</p>
      <p>부정적인 사고는 배제하고 긍정적인 면만 바라봐야 합니다.</p>
    </div>
    <div>
      <img src="@/assets/blackhat.png" alt="blackhat">
      <p>논리적, 부정적, 잠재 위험, 오류</p>
      <p>기존의 정보와 맞지 않는 것, 부정확한 것, 위험한 것 등을 논리적으로 지적하는 모자입니다.</p>
      <p>주어진 아이디어 혹은 대안에 어떤 위험 요소와 결점이 있는지 찾아냅니다.</p>
      <p>오직 부정적인 요소들만 생각해야 합니다.</p>
    </div>
    <div>
      <img src="@/assets/whitehat.png" alt="whitehat">
      <p>객관적, 중립적인 사실과 정보</p>
      <p>검증된 정확한 정보를 제시하고 중립성, 객관성을 유지한 의견을 제시하는 모자입니다.</p>
      <p>회의 첫 순서에 우리가 가진 확실한 정보는 무엇이고 어떤 정보가 더 필요한지 정리합니다.</p>
      <p>자신의 발언에 주관적인 생각을 담아선 안되며, 객관적인 정보만을 제시해야 합니다.</p>
    </div>
    <div>
      <img src="@/assets/spectator.png" alt="spectator">
      <p>관전자입니다.</p>
      <p>회의방에는 참가하지만 모자 회의에는 참가하지 않는 역할입니다.</p>
      <p>모자 회의 중에 발언할 수 없으며, 화면 공유, 회의록 작성도 불가능합니다.</p>
    </div>
  </div>
</template>

<script>
export default {
	name: 'ExplainPage',
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

</style>