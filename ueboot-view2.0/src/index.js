import Log from './utils/Log'
import UTree from './components/tree/UTree.vue'
import UTreeSelect from './components/tree-select/UTreeSelect.vue'
import UFormGrid from './components/form-grid/UFormGrid.vue'
import UForm from './components/form/UForm.vue'
import WebSocket from './utils/WebSocket'
import axios from 'axios'
import Utils from './utils/Utils'
import Config from './config/Config'
import PageMain from './pages/shiro/Main'
import PageLogin from './pages/shiro/Login'
import PageShiroUser from './pages/shiro/User'
import PageShiroResources from './pages/shiro/Resources'
import PageShiroRole from './pages/shiro/Role'

const ueboot = {
  UTree,
  UFormGrid,
  UTreeSelect,
  Log,
  WebSocket,
  UForm,
  Utils,
  Config,
  //兼容旧写法（带下划线的名字，无法通过eslint校验)
  Page_Login: PageLogin,
  PageLogin,
  //
  Page_Main: PageMain,
  PageMain,
  Page_Shiro_User: PageShiroUser,
  PageShiroUser,
  Page_Shiro_Resources: PageShiroResources,
  PageShiroResources,
  Page_Shiro_Role: PageShiroRole,
  PageShiroRole
};

const install = function (Vue) {
  if ( install.installed ) {
    return;
  }
  Object.keys(ueboot).forEach((key) => {
    Vue.component(key, ueboot[key])
  })
  Vue.prototype.$log = Log;
  Vue.prototype.$utils = Utils;
  Vue.prototype.$axios = axios;
};

// auto install
if ( typeof window !== 'undefined' && window.Vue ) {
  install(window.Vue)
}
export default {
  //一定需要这个install，否则无法使用Vue.use方法
  install,
  UTree,
  UFormGrid,
  UTreeSelect,
  Log,
  WebSocket,
  UForm,
  Utils,
  Config,
  //兼容旧写法（带下划线的名字，无法通过eslint校验)
  Page_Login: PageLogin,
  PageLogin,
  //
  Page_Main: PageMain,
  PageMain,
  Page_Shiro_User: PageShiroUser,
  PageShiroUser,
  Page_Shiro_Resources: PageShiroResources,
  PageShiroResources,
  Page_Shiro_Role: PageShiroRole,
  PageShiroRole
}
