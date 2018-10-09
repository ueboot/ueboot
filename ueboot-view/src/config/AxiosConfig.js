/**
 * Created by Richard on 17/9/14.
 */

/**
 * 便于统一处理请求和返回
 */

import axios from 'axios';
import iView from 'iview';

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
            if (error.response.status === 403) {
                toLogin(conf)
                return Promise.reject(error.response.data)
            } else {
                iView.Message.error({content: error.response.data.message,duration: 10,
                    closable: true});
                return Promise.reject(error.response.data);
            }
        });
        function toLogin(conf) {
            iView.Modal.info({
                closable:false,
                'mask-closable':false,
                title: '提示',
                content: '您尚未登录，或当前用户会话已过期，点击确定按钮返回到登录界面。',
                loading: true,
                onOk: () => {
                    setTimeout(() => {
                        iView.Modal.remove();
                        if (conf !== undefined && conf.unauthorizedUrl !== undefined) {
                            window.location.href = conf.unauthorizedUrl;
                        }
                    }, 500);
                }
            });
        }
    }
}
