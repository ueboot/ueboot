/**
 * Created by Richard on 17/9/14.
 */

/**
 * 便于统一处理请求和返回
 */

import axios from 'axios';
import iView from 'iview';
import config from './Config';
//标识一下是否显示了未登录提示，防止弹出多次
let showNotLogin = false

export default class AxiosConfig {
    static init(conf) {
        axios.defaults = conf;
        // 添加一个请求拦截器
        axios.interceptors.request.use(function (config) {
            // 显示加载框
            if (config.showLoading === undefined || config.showLoading === true) {
                iView.LoadingBar.start();
            }
            // 标识为ajax异步请求
            config.headers['X-Requested-With'] = 'XMLHttpRequest';
            if (conf && conf.baseURL) {
                config.baseURL = conf.baseURL;
            }
            return config;
        }, function (error) {
            iView.LoadingBar.error();
            return Promise.reject(error);
        });

        // 添加一个响应拦截器
        axios.interceptors.response.use(function (response) {
            iView.LoadingBar.finish();
            if (response.status === 200 && response.data.code === 'OK') {
                return response.data;
            } else if (response.data.code === '401') {
                toLogin(conf)
            } else if (response.data.code === '500' || response.data.code === '700') {
                iView.Message.error({
                    content: response.data.message,
                    duration: 10,
                    closable: true
                });
            }
            iView.LoadingBar.error();
            return Promise.reject(response.data);
        }, function (error) {
            iView.LoadingBar.error();

            // 403 状态执行页面跳转，其余状态不跳转
            if (error.response.status === 403 ||error.response.status === 401) {
                toLogin(conf)
                return Promise.reject(error.response.data);
            } else {
                iView.Message.error({
                    content: error.response.data.message?error.response.data.message:'系统异常！', duration: 10,
                    closable: true
                });
                return Promise.reject(error.response.data);
            }
        });

        function toLogin(conf) {
            if(showNotLogin){
                return
            }
            showNotLogin = true
            let cnf = config.getConfig()
            iView.Modal.info({
                closable: false,
                'mask-closable': false,
                title: '提示',
                content: cnf.axios.notLoginMsg,
                loading: true,
                onOk: () => {
                    setTimeout(() => {
                        showNotLogin = false
                        iView.Modal.remove();
                        if (conf !== undefined && conf.unauthorizedUrl !== undefined) {
                            window.location.href = conf.unauthorizedUrl;
                            //IE下有可能不刷新
                            if(isIE()){
                                window.location.reload();
                            }
                        }
                    }, 200);
                }
            });
        }

        function isIE() {
            let userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
            let isIE = userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1; //判断是否IE<11浏览器
            let isEdge = userAgent.indexOf("Edge") > -1 && !isIE; //判断是否IE的Edge浏览器
            let isIE11 = userAgent.indexOf('Trident') > -1 && userAgent.indexOf("rv:11.0") > -1;
            return (isIE || isEdge || isIE11);
        }
    }
}
