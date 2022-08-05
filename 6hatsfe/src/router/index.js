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
    path: '/qnapage',
    name: 'QnaPage',
    //qna router

    component: () => import('@/views/main/qna/QnaPage.vue')
  },
  {
    path: '/noticepage',
    name: 'NoticePage',
    //notice router

    component: () => import('@/views/main/notice/NoticePage.vue')
  },
  {
    path: '/teampage',
    name: 'TeamPage',
    //team router

    component: () => import('@/views/main/team/TeamPage.vue')
  },
  {
    path: '/signuppage',
    name: 'SigunupPage',
    //signup router

    component: () => import('@/views/main/signup/SignupPage.vue')
  },
  {
    path: '/conferencepage',
    name: 'ConferencePage',
    //conference router

    component: () => import('@/views/conference/ConferencePage.vue')
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
