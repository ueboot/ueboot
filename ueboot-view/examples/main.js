import Vue from 'vue'
import App from './App.vue'
import router from './router/router'
import ueboot from '../src/index'
import iView from 'iview';
import 'babel-polyfill'
/*自定义样式*/
import '../src/styles/index.less';

import "font-awesome/less/font-awesome.less"

Vue.use(iView);
Vue.use(ueboot);
ueboot.Config.setConfig({
    sysTitle:'UEBOOT测试',
    axios:{baseURL: "", unauthorizedUrl: '/#/login'},
    page_main:{menuWidth:250}
})

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
    el: '#app',
    router,
    template: '<App/>',
    components: { App }
})
