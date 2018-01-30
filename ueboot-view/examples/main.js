// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App.vue'
import router from './router/router'
import ueboot from '../src/index'
import {Ajax, Log, Axios} from '../src/index'

/*自定义样式*/
import '../ue-theme/index.less'

/*导入tree的样式*/
import '../src/components/tree/css/style.css'
import "font-awesome/scss/font-awesome.scss"


Vue.use(ueboot);
Axios.init({ baseURL: "/console", unauthorizedUrl: "/"});
// Ajax.config({context: "/console", unauthorizedUrl: "/"});

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
