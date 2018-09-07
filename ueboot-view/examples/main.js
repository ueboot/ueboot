import Vue from 'vue'
import App from './App.vue'
import router from './router/router'
import ueboot from '../src/index'
import iView from 'iview';
/*自定义样式*/
import '../src/styles/index.less';

// import "font-awesome/css/font-awesome.min.css"

Vue.use(iView);
Vue.use(ueboot);
ueboot.Config.setConfig({
    axios:{baseURL: "", unauthorizedUrl: '/#/login'}
})

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
