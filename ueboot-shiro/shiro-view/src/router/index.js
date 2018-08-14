import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import Organization from '../views/shiro/Organization'
import Permission from '../views/shiro/Permission'
import Resources from '../views/shiro/Resources'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'HelloWorld',
      component: HelloWorld
    },
    {
      path: '/platform/Organization',
      name: 'Organization',
      component: Organization
    },
    {
      path: '/platform/Organization',
      name: 'Permission',
      component: Permission
    },
    {
      path: '/platform/Organization',
      name: 'Resources',
      component: Resources
    }
  ]
})
