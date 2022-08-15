import { createRouter, createWebHistory } from 'vue-router'


const routes = [
  {
    path: '/',
    name: 'LandingPage',
    //landingpage

    component: () => import('@/views/main/landing/LandingPage.vue')
  },
  {
    path: '/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '@/views/AboutView.vue')
  },
  {
    path: '/explainpage',
    name: 'ExplainPage',
    //explainpage

    component: () => import('@/views/main/explain/ExplainPage.vue')
  },
  {
    path: '/historypage',
    name: 'HistoryPage',
    //history router

    component: () => import('@/views/main/history/HistoryPage.vue')
  },
  {
    path: '/loginpage',
    name: 'LoginPage',
    //login page router

    component: () => import('@/views/main/login/LoginPage.vue')
  },
  {
    path: '/kakaologinpage',
    name: 'KakaoLoginPage',
    //login page router

    component: () => import('@/views/main/login/KakaoLoginPage.vue')
  },
  {
    path: '/qnapage',
    name: 'QnaPage',
    //qna router

    component: () => import('@/views/main/qna/QnaPage.vue')
  },
  {
    path: '/qnawritepage',
    name: 'QnaWritePage',
    //notice router

    component: () => import('@/views/main/qna/QnaWritePage.vue'),
  },
  {
    path: '/noticepage',
    name: 'NoticePage',
    //notice router

    component: () => import('@/views/main/notice/NoticePage.vue'),
  },
  {
    path: '/noticewritepage',
    name: 'NoticeWritePage',
    //notice router

    component: () => import('@/views/main/notice/NoticeWritePage.vue'),
  },
  {
    path: '/teampage',
    name: 'TeamPage',
    //팀소개 router

    component: () => import('@/views/main/team/TeamPage.vue')
  },
  {
    path: '/signuppage',
    name: 'SignupPage',
    //signup router

    component: () => import('@/views/main/signup/SignupPage.vue')
  },
  {
    path: '/conferencepage',
    name: 'ConferencePage',
    //conference router

    component: () => import('@/views/conference/ConferencePage.vue')
  },
  {
    path: '/roompage/:sessionCode',
    name: 'RoomPage',
    //conference router

    component: () => import('@/views/conference/RoomPage.vue')
  },
  {
    path: '/profilepage',
    name: 'ProfilePage',
    //profile router

    component: () => import('@/views/main/profile/ProfilePage')
  },
  {
    path: '/recpage/:roomId',
    name: 'RecPage',
    //record router

    component: () => import('@/views/main/history/rec/RecPage')
  },
  {
    path: '/noticecontentspage/:boardId',
    name: 'NoticecontentsPage',
    //notice contests router

    component: () => import('@/views/main/notice/NoticecontentsPage')
  },
  {
    path: '/noticecontentspage/:boardId',
    name: 'NoticecontentsPage',
    //notice contests router

    component: () => import('@/views/main/notice/NoticecontentsPage')
  },
  {
    path: '/noticemodifypage/:boardId',
    name: 'NoticemodifyPage',
    //notice contests router

    component: () => import('@/views/main/notice/NoticemodifyPage')
  }

]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
