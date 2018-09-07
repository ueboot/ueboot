import Log from './utils/Log'
import UTree from './components/tree/UTree.vue'
import UTreeSelect from './components/tree-select/UTreeSelect.vue'
import UFormGrid from './components/form-grid/UFormGrid.vue'
import UForm from './components/form/UForm.vue'
import WebSocket from './utils/WebSocket'
import Axios from './utils/Axios'
import axios from 'axios'
import Utils from './utils/Utils'
import Config from './config/Config'

const ueboot = {
  UTree,
  UFormGrid,
  UTreeSelect,
  Log,
  Axios,
  WebSocket,
  UForm,
  Utils,
  Config
}

const install = function (Vue, opts = {}) {
  Object.keys(ueboot).forEach((key) => {
    Vue.component(key, ueboot[key])
  })

  Vue.prototype.$log = Log
  Vue.prototype.$utils = Utils
  Vue.prototype.$axios = axios
}

// auto install
if (typeof window !== 'undefined' && window.Vue) {
  install(window.Vue)
}

module.exports = Object.assign(ueboot, {install})
