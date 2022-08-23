<template>
  <div id="recgo">
    <header class="pagename">
    <h1>회의이력</h1>
    </header>
  <table class="table">
  <thead class="Rechead">
    <tr class="headrow">
      <th scope="col">#</th>
      <th colspan="2">회의생성일자</th>
      <th scope="col">진행시간</th>
      <th scope="col">회의록</th>
      <th scope="col">녹화파일</th>
    </tr>
  </thead>
  <tbody>
    <tr v-for ="(no,idx) in rooms" :key="idx" class="boardbody">
      <td scope="row">{{pageNum * pageSize + idx + 1}}</td>
      <td colspan="2">{{conversionTime(no.roomStartTime)}}</td>
      <td>{{timeGapcal(no.roomStartTime,no.roomEndTime)}}</td>
      <td v-if="no.opinionFileUrl && no.opinionFileValid">
        <a class="file-download-btn" @click="getTxt(no.opinionFileUrl, no.roomStartTime)">다운로드</a>
      </td>
      <td v-else>불가</td>
      <td><router-link :to="{
              path: '/recpage/' + no.opinionFileUrl,
            }"
            >녹화파일</router-link></td>
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
</div>
</template>

<script>
import interceptor from "@/api/interceptors";
import jwt_decode from "jwt-decode";
import axios from 'axios';
export default {
  name: 'HistoryPage'
  ,
  data() {
    return {
      rooms: {
        creator: true,
        opinionFileUrl: "",
        opinionFileValid: '',
        roomEndTime: '',
        roomId: '',
        roomStartTime: '',
        userId: ''
      },
      pageNum: 0,
      pageSize: 10,
    }
  }
  , 
    methods: {
      timeRange() {
        this.rooms.roomEndTime
      },
      conversionTime(value) {
        if (typeof value === "string") { 
        let constartTime = value.slice(0, 16);
        constartTime = constartTime.replace(/\D/g, ':');
        return constartTime;
        }
      },
      timeGapcal(start,end) {
        var fromTime = new Date(start);
        var toTime = new Date(end);

        var differenceTravel = toTime.getTime() - fromTime.getTime();
        var hours = Math.floor((differenceTravel) / (1000 * 60 * 60));
        differenceTravel -= hours * 1000 * 60 * 60;
        var minute = Math.floor((differenceTravel) / (1000 * 60));

        let value= hours%60+" 시간 "+minute+" 분"

        if(hours < 0){
          return "진행중";
        }
        return value;
      },
      clickRecdownload(value) {
        this.$router.push({
          path: "RecPage",
          query:{roomId:value},
        });
      },
      getHistories(){
          var token=localStorage.getItem('access-token');
          var decoded = jwt_decode(token);//token 디코드
          this.$store.commit('ChangeId',decoded.userId);
        // Intercepotor 시작
          interceptor({
            url: '/user/' + decoded.userId +'/rooms?page='+ this.pageNum + '&size=' + this.pageSize + '&sort=userRoomId,desc',
            method: 'get'
          }).then((res) => {
            console.log(res.data.rooms);
            this.rooms = res.data.rooms.reverse();
          }).catch((err) => {
            alert(err);
          });
      },
       addPageNum(){
      if(this.rooms.length == this.pageSize) { // 꼼수 같은 코드
        this.pageNum++;
        this.getHistories();
      }
      }, 
      substractPageNum(){
      if(this.pageNum - 1 >= 0 ) {
        this.pageNum--;
        this.getHistories();
      }
      },
      getTxt(file, roomStartTime){
        axios.get('https://i7a709.p.ssafy.io:8081/file/txt?opinionFileUrl=' + file, {
                responseType: "blob"
            }).then(response => {
                const url = window.URL.createObjectURL(new Blob([response.data]));
                const link = document.createElement('a');
                link.href = url;
                link.setAttribute('download', `${roomStartTime.slice(0,10)}.txt`); //or any other extension
                document.body.appendChild(link);
                link.click();
            }).catch(exception => {
                alert(exception);
            })
      },
      
  },
  created() {
    this.getHistories();
  }

}
</script>

<style>
#recgo{
  width:60%;
  margin: auto;
  background: rgba(255, 255, 255, 0.7);
  height: calc(100% - 72px);
}
.pagename {
  position: relative;
  top: 0;
  left: 0;
  right: 0;
  width: 100%;
  height: 75px;
  padding: 1rem;
  color:black;
  /* background: #C1EFFF; */
  font-weight: bold;
  display: flex;
  justify-content: space-between;
  align-items: center;
}
h1, p {
  margin: 0;
}
.Rechead{
  background: #4285F4;
  color:#FFFFFF;
}
.table{
  font-size: 14px;
}
.boardbody:hover{
  background-color: #C7C6C6;
}

.file-download-btn:hover {
  cursor: pointer;
}
</style>