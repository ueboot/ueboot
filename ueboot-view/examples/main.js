import Vue from 'vue'
import App from './App.vue'
import router from './router/router'
import { Config } from '../src/index'
import iView from 'iview'
import 'babel-polyfill'
/* 自定义样式 */
import '../src/styles/index.less'

import 'font-awesome/less/font-awesome.less'

import userAvatarSrc from './assets/userImage.png'

Vue.use(iView)
Config.setConfig({
  log: { level: 4 },
  sysTitle: 'UEBOOT测试',
  axios: { baseURL: '', unauthorizedUrl: '/#/login' },
  page_login: { successRouter: { name: '用户管理' } },
  page_main: {
    menuWidth: 250,
    dropdown:
      {
        avatar: {
          src: userAvatarSrc
        }
      }
  }
})

Vue.config.productionTip = false
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  template: '<App/>',
  components: { App }
})

// 头像
function setUserImageSrc () {
  sessionStorage.setItem('login.userAvatar', 'http://0.0.0.0:8081/static/img/userImage.9228385.png')
}

setUserImageSrc()
