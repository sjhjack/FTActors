import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import ProfileView from '../views/ProfileView.vue'
import BoardView from '../views/BoardView.vue'
import MontageView from '../views/MontageView.vue'
import AboutView from '../views/AboutView.vue'
import LoginView from '../views/LoginView.vue'
import LogoutView from '../views/LogoutView.vue'
import JoinView from '../views/JoinView.vue'
import MypageView from '@/views/MypageView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/profile',
      name: 'profile',
      component: ProfileView
    },
    {
      path: '/board',
      name: 'board',
      component: BoardView
    },
    {
      path: '/montage',
      name: 'montage',
      component: MontageView
    },
    {
      path: '/about',
      name: 'about',
      component: AboutView
    },
    {
      path: '/login',
      name: 'login',
      component: LoginView
    },
    {
      path: '/logout',
      name: 'logout',
      component: LogoutView
    },
    {
      path: '/join',
      name: 'join',
      component: JoinView
    },
    {
      path: '/mypage',
      name: 'mypage',
      component: MypageView
    }

  ]
})

export default router
