// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App.vue'
import router from './router/router'
import ueboot from '../src/index'
import {Log, Axios} from '../src/index'
import iView from 'iview';
/*自定义样式*/
import '../ue-theme/index.less';

import "font-awesome/css/font-awesome.css"

Vue.use(iView);
Vue.use(ueboot);
Log.config({level:3})

ueboot.Axios.init({ baseURL: "/console", unauthorizedUrl: "/login"});


//Log.config({"level":0});
//设置加载提示
/*router.beforeEach((transition) => {
    iView.LoadingBar.start();
});

router.afterEach((transition) => {
    iView.LoadingBar.finish();
});*/

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
    el: '#app',
    router,
    template: '<div><App></App></div>',
    components: { App }
})
