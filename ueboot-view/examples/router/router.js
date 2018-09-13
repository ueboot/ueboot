import Vue from "vue";
import Router from "vue-router";
import TreeDemo from "../views/demo/Tree.vue"
import FormDemo from "../views/demo/Form.vue"
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
import {Page_Login,Page_Main,Page_Shiro_User,Page_Shiro_Role,Page_Shiro_Resources} from 'ueboot'

import Login from '../../src/pages/shiro/Login'

Vue.use(Router)

export default new Router({
  scrollBehavior: () => ({
    y: 0
  }),
  routes: [
    {
      path: '/login',
      name: 'Login',
      component: Page_Login,
    },
    {
      path: '/',
      name: '框架示例',
      component: Page_Main,
      children: [
        {
          path: '/ueboot/shiro/User',
          name: '用户管理',
          component: Page_Shiro_User,
        },
        {
          path: '/ueboot/shiro/Resources',
          name: '资源管理',
          component: Page_Shiro_Resources,
        },
        {
          path: '/ueboot/shiro/Role',
          name: '角色管理',
          component: Page_Shiro_Role,
        },
        {
          path: 'treeSelect',
          name: '树结构下拉框',
          component: TreeSelect,
        },
        {
          path: 'tree',
          name: '树结构',
          component: TreeDemo,
        },
        {
          path: 'crud',
          name: 'CRUD视图',
          component: FormGrid,
        },
        {
          path: 'formGrid2',
          name: 'FormGrid视图2',
          component: FormGrid2,
        },
        {
          path: 'formGrid3',
          name: 'FormGrid视图3',
          component: FormGrid3,
        },
        {
          path: 'cascader',
          name: 'Cascader',
          component: Cascader,
        },
        {
          path: 'customer',
          name: 'customer',
          component: ListCustomer,
        },
        {
          path: 'Knowledge',
          name: 'Knowledge',
          component: ListKnowledge,
        },
        {
          path: 'FormDemo',
          name: 'Form表单',
          component: FormDemo,
        },
        {
          path: 'customers',
          name: 'Customers',
          component: Customers,
        },
        {
          path: 'category',
          name: 'category',
          component: Category,
        },
        {
          path: 'areas',
          name: 'Areas',
          component: Areas,
        },
        {
          path: 'activities',
          name: 'Activities',
          component: Activities,
        },
        {path: 'activities/secKillActivity', name: '秒杀活动', component: SecKillActivity},
      ]
    }
  ]
})
