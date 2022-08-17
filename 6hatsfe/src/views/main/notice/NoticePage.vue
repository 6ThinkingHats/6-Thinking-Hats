<template>
  <div class="recgo">
    <header class="pagename">
    <h1>공지사항</h1>
    </header>
    <table class="table">
      <thead class="Rechead">
        <tr class="headrow">
          <th scope="col" style="width : 8%">글번호</th>
          <th scope ="col" style="width : 60%">제목</th>
          <th scope="col" >작성자</th>
          <th scope="col">작성일</th>
          <th scope="col">조회수</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(no,idx) in boards" :key ="idx" class="boardbody">
          <td scope="row">{{pageNum * pageSize + idx + 1}}</td>
          <td><router-link :to ="{
              path: '/noticecontentspage/' + no.boardId
              }" style="color: #000000">
            {{no.title}}</router-link></td>
          <td>{{no.name}}</td>
          <td>{{no.boardCreatedAt}}</td>
          <td>{{no.views}}</td>
        </tr>
      </tbody>
    </table>
    <nav aria-label="Page navigation example" style="display:flex; justify-content: center; padding:5px;">
      <ul class="pagination">
        <li class="page-item">
          <a v-on:click="substractPageNum" class="page-link" href="#" aria-label="Previous">
            <span aria-hidden="true">&laquo;</span>
            <span class="sr-only">Previous</span>
          </a>
        </li>
        <li class="page-item"><a class="page-link" href="#">{{ pageNum + 1}}</a></li>
        <li class="page-item">
          <a v-on:click="addPageNum" class="page-link" href="#" aria-label="Next">
            <span aria-hidden="true">&raquo;</span>
            <span class="sr-only">Next</span>
          </a>
        </li>
      </ul>
    </nav>
    <div class ="boardbtn">
      <button v-if ="userType==='ADMIN'" v-on:click="routeToWritePage" type="button" class="btn btn-primary">글쓰기</button>
    </div>
  </div>
</template>

<script>
import router from "@/router";
import interceptor from "@/api/interceptors";
import jwt_decode from "jwt-decode";

export default {
  name: 'NoticePage'
  ,
  data() {
    return {
      boards: { name: '', boardId: '', title: '', boardType: '', views: '',boardCreatedAt:'' },
      length: '', 
      userType:'',
      pageNum: 0,
      pageSize: 10,
    };
  }, 
  methods: {
    routeToWritePage(){
      router.push({ name: "NoticeWritePage" });
    },
    getBoardNotice(){
      interceptor({
      url: '/board/notice?page=' + this.pageNum + '&size=' + this.pageSize + '&sort=boardCreatedAt,boardId,desc',
      method: 'get'
    }).then((res) => {
      this.boards = res.data;
      this.length = res.data.length;
      console.log(res.data.length);
    }).catch((err) => {
      alert(err);
      router.push({ name: "LandingPage" });
    });
    },
    addPageNum(){
      if(this.length == this.pageSize) { // 꼼수 같은 코드
        this.pageNum++;
        this.getBoardNotice();
      }
    }, 
    substractPageNum(){
      if(this.pageNum - 1 >= 0 ) {
        this.pageNum--;
        this.getBoardNotice();
      }
    }
  },
  mounted() {
    // Intercepotor 시작
    this.getBoardNotice();

    if (localStorage.getItem('access-token')){

      var decoded = jwt_decode(localStorage.getItem('access-token'));//token 디코드
      interceptor({
        url: '/user/' + decoded.userId,
        method: 'get'
      }).then((res) => {
        this.userType = res.data.user.userType;
      }).catch((err) => {
        alert(err);
      });
    
    }
  }
}
</script>

<style scoped lang="css" src="@/assets/css/views/main/notice/NoticePage.css">
.pagination{

}
.page-item{

}
.page-link{

}
.sr-only{

}

</style>