<template>
  <div class="explain-box">
    <div class="explain-line">
      <div class="explain-word explain-1">
        <span>제가 생각한 아이디어는 ~ 입니다</span>
      </div>
      <div class="explain-word explain-2">
        <span>이런 걸 해보면 어떨까요?</span>
      </div>
      <div class="explain-word explain-3">
        <span>이 아이디어를 개선할 방안은 ~ 입니다</span>
      </div>
    </div>
    <div class="explain-line">
      <div class="explain-word explain-5">
        <span>이 아이디어를 개선할 방안은 ~ 입니다</span>
      </div>
      <div class="explain-word explain-4">
        <span>기존의 사고에서 벗어나 새로운 시각과 창의적인 아이디어 제시</span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'RoleExplain',
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

.explain-box {
  display: flex;
  flex-direction: column;
  gap: 6px;
}

.explain-line {
  display: flex;
  gap: 4px;
}

.explain-line .explain-word {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 36px;
  padding: 2px 20px;
  margin: 0;
  background-color: #34A853;
  color: white;
  border-radius: 8px;
}

.explain-1 {
  width: 362px;
}

.explain-2 {
  width: 248px;
}

.explain-3 {
  width: 424px;
}

.explain-4 {
  width: 612px;
}

.explain-5 {
  width: 426px;
}
</style>