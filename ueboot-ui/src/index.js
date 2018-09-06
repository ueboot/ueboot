import Log from './utils/Log'
import UTree from './components/tree/UTree.vue';
import UFormGrid from './components/form-grid/UFormGrid.vue';
import UForm from "./components/form/UForm.vue";
import WebSocket from "./utils/WebSocket";
import Axios from "./utils/Axios";
import axios from "axios";
import Utils from "./utils/Utils";

const ueboot = {
  UTree,
  UFormGrid,
  Log,
  Axios,
  WebSocket,
  UForm,
  Utils
};

const install = function (Vue, opts = {}) {

  Object.keys(ueboot).forEach((key) => {
    Vue.component(key, ueboot[key]);
  });

  // Vue.prototype.$http = Axios;
  Vue.prototype.$log = Log;
  Vue.prototype.$utils=Utils;
  Vue.prototype.$axios=axios;
};

// auto install
if (typeof window !== 'undefined' && window.Vue) {
  install(window.Vue);
}

module.exports = Object.assign(ueboot, {install});   // eslint-disable-line no-undef

