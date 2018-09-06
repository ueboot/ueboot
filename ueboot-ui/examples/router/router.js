import Vue from "vue";
import Router from "vue-router";
import Home from "../views/Home.vue";
import TreeDemo from "../views/demo/Tree.vue"
import FormGrid from "../views/demo/FormGrid.vue";
import FormGrid2 from "../views/demo/FormGrid2.vue";
import FormGrid3 from "../views/demo/FormGrid3.vue";
import Cascader from "../views/demo/Cascader.vue";
import ListCustomer from "../views/ListCustomer.vue";
import ListKnowledge from "../views/ListKnowledge.vue";
import Customers from "../views/demo/Customers.vue";
import Category from "../views/setting/Category.vue";
import Areas from "../views/setting/Areas.vue";
import Activities from "../views/activity/Activities.vue";
import SecKillActivity from "../views/activity/SecKillActivity.vue";
import TreeSelect from '../views/demo/TreeSelect'
import UebootResources from '../views/ueboot-shiro/Resources'
import UebootUser from '../views/ueboot-shiro/User'
import UebootRole from '../views/ueboot-shiro/Role'
import UebootMain from '../../src/views/shiro/Main'


import Form from "../views/demo/Form.vue";
import Login from '../../src/views/shiro/Login'

Vue.use(Router)

export default new Router({
  mode: 'history',
  scrollBehavior: () => ({
    y: 0
  }),
  routes: [
    {
      path: '/login',
      name: 'Login',
      component: Login,
    },
    {
      path: '/',
      name: '框架示例',
      isHide: false,
      component: UebootMain,
      children: [
        {
          path: 'UebootUser',
          name: '用户管理',
          component: UebootUser,
          isHide: false,
        },
        {
          path: 'UebootResources',
          name: '资源管理',
          component: UebootResources,
          isHide: false,
        },
        {
          path: 'UebootRole',
          name: '角色管理',
          component: UebootRole,
          isHide: false,
        },
        {
          path: 'treeSelect',
          name: '树结构下拉框',
          component: TreeSelect,
          isHide: false,
        },
        {
          path: 'tree',
          name: '树结构',
          component: TreeDemo,
          isHide: false,
        },
        {
          path: 'crud',
          name: 'CRUD视图',
          component: FormGrid,
          isHide: false,
        },
        {
          path: 'formGrid2',
          name: 'FormGrid视图2',
          component: FormGrid2,
          isHide: false,
        },
        {
          path: 'formGrid3',
          name: 'FormGrid视图3',
          component: FormGrid3,
          isHide: false,
        },
        {
          path: 'cascader',
          name: 'Cascader',
          component: Cascader,
          isHide: false,
        },
        {
          path: 'customer',
          name: 'customer',
          component: ListCustomer,
          isHide: false,
        },
        {
          path: 'Knowledge',
          name: 'Knowledge',
          component: ListKnowledge,
          isHide: false,
        },
        {
          path: 'form',
          name: 'Form表单',
          component: Form,
          isHide: false,
        },
        {
          path: 'customers',
          name: 'Customers',
          component: Customers,
          isHide: false,
        },
        {
          path: 'category',
          name: 'category',
          component: Category,
          isHide: false,
        },
        {
          path: 'areas',
          name: 'Areas',
          component: Areas,
          isHide: false,
        },
        {
          path: 'activities',
          name: 'Activities',
          component: Activities,
          isHide: false,
        },
        {path: 'activities/secKillActivity', name: '秒杀活动', component: SecKillActivity, isHide: false,},
      ]
    }
  ]
})
