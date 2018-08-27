// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import iView from 'iView'

import ueboot from 'ueboot'

/* ueboot封装过的基于iview的自定义样式，也可以引入自己的自定义样式 */
import 'ueboot/ue-theme/dist/iview.css'

/* icon字体图标样式 */
import 'font-awesome/css/font-awesome.css'

Vue.use(iView)
Vue.use(ueboot)
ueboot.Axios.init({baseURL: process.env.CONTEXT, unauthorizedUrl: process.env.CONTEXT_HTML + '/#/login'})
Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: {App},
  template: '<App/>'
})
