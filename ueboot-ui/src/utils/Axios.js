/**
 * Created by Richard on 17/9/14.
 */

/**
 * 便于统一处理请求和返回
 */

import axios from 'axios'
import iView from 'iview'

export default class Axios {
  static init (conf) {
    axios.defaults = conf
    // 添加一个请求拦截器
    axios.interceptors.request.use(function (config) {
      // 显示加载框
      if (config.showLoading === undefined || config.showLoading === true) {
        iView.LoadingBar.start()
      }
      // 标识为ajax异步请求
      config.headers['X-Requested-With'] = 'XMLHttpRequest'
      if (conf && conf.baseURL) {
        config.baseURL = conf.baseURL
      }
      return config
    }, function (error) {
      iView.LoadingBar.error()
      return Promise.reject(error)
    })

    // 添加一个响应拦截器
    axios.interceptors.response.use(function (response) {
      iView.LoadingBar.finish()
      if (response.status === 200 && response.data.code === 'OK') {
        return response.data
      } else if (response.data.code === '401') {
        iView.Message.error('尚未登录，请先登录!')
        if (conf !== undefined && conf.unauthorizedUrl !== undefined) {
          window.location.href = conf.unauthorizedUrl
        }
      } else if (response.data.code === '500') {
        iView.Notice.error({desc: response.data.message})
      }
      iView.LoadingBar.error()
      return Promise.reject(response.data)
    }, function (error) {
      iView.LoadingBar.error()
      iView.Notice.error({desc: error.response.data.message})
      // 403 状态执行页面跳转，其余状态不跳转
      if (error.response.status === 403) {
        if (conf !== undefined && conf.unauthorizedUrl !== undefined) {
          window.location.href = conf.unauthorizedUrl
        }
      } else {
        return Promise.reject(error.response.data)
      }
    })
  }
};
