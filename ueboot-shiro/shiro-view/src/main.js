// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'

import ueboot from 'ueboot'
import { Axios } from "ueboot";


/* 自定义样式(此处为iview的自定义样式，详情参考iview的文档) */
import 'ueboot/ue-theme/index.less'
/* ueboot 的组件样式 */
import 'ueboot/ueboot/styles/ueboot.css'
/* 字体图标样式 */
import 'font-awesome/scss/font-awesome.scss'
Vue.use(ueboot)
ueboot.Axios.init({ baseURL: process.env.CONTEXT, unauthorizedUrl: process.env.CONTEXT_HTML + '/#/login' })
Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
