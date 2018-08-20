import Vue from 'vue'
import Router from 'vue-router'
import Organization from '../components/ueboot-shiro/Organization'
import Permission from '../components/ueboot-shiro/Permission'
import Resources from '../components/ueboot-shiro/Resources'
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
          path: '/ueboot/shiro/Organization',
          name: 'Organization',
          component: Organization
        },
        {
          path: '/ueboot/shiro/Organization',
          name: 'Permission',
          component: Permission
        },
        {
          path: '/ueboot/shiro/Organization',
          name: 'Resources',
          component: Resources
        }
      ]
    }
  ]
})
