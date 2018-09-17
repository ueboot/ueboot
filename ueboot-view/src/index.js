import Log from './utils/Log';
import UTree from './components/tree/UTree.vue';
import UTreeSelect from './components/tree-select/UTreeSelect.vue';
import UFormGrid from './components/form-grid/UFormGrid.vue';
import UForm from './components/form/UForm.vue';
import WebSocket from './utils/WebSocket';
import axios from 'axios';
import Utils from './utils/Utils';
import Config from './config/Config';
import Page_Main from './pages/shiro/Main';
import Page_Login from './pages/shiro/Login';
import Page_Shiro_User from './pages/shiro/User';
import Page_Shiro_Resources from './pages/shiro/Resources';
import Page_Shiro_Role from './pages/shiro/Role';


const ueboot = {
    UTree,
    UFormGrid,
    UTreeSelect,
    Log,
    WebSocket,
    UForm,
    Utils,
    Config,
    Page_Login,
    Page_Main,
    Page_Shiro_User,
    Page_Shiro_Resources,
    Page_Shiro_Role
};

const install = function (Vue, opts = {}) {
    Object.keys(ueboot).forEach((key) => {
        Vue.component(key, ueboot[key]);
    });
    
    Vue.prototype.$log = Log;
    Vue.prototype.$utils = Utils;
    Vue.prototype.$axios = axios;
};

// auto install
if (typeof window !== 'undefined' && window.Vue) {
    install(window.Vue);
}

module.exports = Object.assign(ueboot, {install});
