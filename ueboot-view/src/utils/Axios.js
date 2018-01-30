/**
 * Created by Richard on 17/9/14.
 */
/**
 * 便于统一处理请求和返回
 */

import Vue from "vue";
import axios from 'axios'
import iView from 'iview'


export default class Axios {

  constructor() {

  };

  static init(conf) {
    axios.defaults = conf;
    // 添加一个请求拦截器
    axios.interceptors.request.use(function (config) {
      // Do something before request is sent
      if (config.showLoading == undefined || config.showLoading === true) {
        iView.LoadingBar.start();
      }
      //标识为ajax异步请求
      config.headers["X-Requested-With"]="XMLHttpRequest";
      if (conf && conf.baseURL) {
        config.baseURL = conf.baseURL;
      }
      console.log("ueboot config is: %o", config);
      return config;
    }, function (error) {
      iView.LoadingBar.error();
      return Promise.reject(error);
    });

    // 添加一个响应拦截器
    axios.interceptors.response.use(function (response) {
      if (response.status == 200 && response.data.code == "OK") {
        iView.LoadingBar.finish();
        return response.data;
      } else {
        iView.LoadingBar.error();
        return Promise.reject(response.data);
      }
    }, function (error) {
      iView.LoadingBar.error();
      if (error.response.status == 401 || error.response.status == 403) {
        iView.Message.info("尚未登录，请先登录!");
        if (conf != undefined && conf.unauthorizedUrl != undefined) {
          window.location.href = conf.unauthorizedUrl;
        }
      }
      return Promise.reject(error.response.data);
    });
  }
};
