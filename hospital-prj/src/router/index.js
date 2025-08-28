import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import NurseList from '../views/NurseList.vue'
import NurseAdd from '../views/NurseAdd.vue'
import StationList from '../views/StationList.vue'
import StationAdd from '../views/StationAdd.vue'
import StationEdit from '../views/StationEdit.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/nurse_list',
      name: 'NurseList',
      component: NurseList,
    },
    {
      path: '/nurse_add/:nurseId',
      name: 'NurseAdd',
      component: NurseAdd,
    },
    {
      path: '/station_list',
      name: 'StationList',
      component: StationList,
    },
    {
      path: '/station_add',
      name: 'StationAdd',
      component: StationAdd,
    },
    {
      path: '/station_edit/:stationId', // Dynamic route with a :stationId parameter
      name: 'StationEdit',
      component: StationEdit,
    },
  ],
})

export default router
