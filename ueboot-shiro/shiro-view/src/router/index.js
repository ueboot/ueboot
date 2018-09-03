import Vue from 'vue'
import Router from 'vue-router'
import Resources from '../views/Resources'
import User from '../views/User'
import Role from '../views/Role'
import Main from '../views/Main.vue'
import Login from '../views/Login.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {path: '/login', name: 'login', component: Login},
    // 登录之后的页面都放在这个路径下
    {
      path: '/',
      component: Main,
      children: [
        {
          path: '/ueboot/shiro/User',
          name: 'User',
          component: User
        },
        {
          path: '/ueboot/shiro/Role',
          name: 'Role',
          component: Role
        },
        {
          path: '/ueboot/shiro/Resources',
          name: 'Resources',
          component: Resources
        }
      ]
    }
  ]
})
