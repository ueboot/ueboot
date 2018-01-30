/**
 * Created by Richard on 17/4/7.
 */
/**
 * 封装http请求，便于统一处理请求和返回
 */
import Vue from "vue";
import iView from 'iview'
import log from './Log'
import Resource from "vue-resource";

Vue.use(Resource);
Vue.use(iView);

let context = "/";
let unauthorizedUrl = null;

const method = {
  'POST': 'POST',
  'GET': 'GET',
  'DELETE': 'DELETE',
  'PUT': 'PUT'
};

function handleUrl(url) {
  if (url.indexOf(context)!=0) {
    url = context + url;
  }
  return url;
}

function handleIterceptors(emulateJSON) {
  Vue.http.options.emulateJSON = emulateJSON;
  Vue.http.interceptors.push((request, next) => {
    iView.LoadingBar.start();
    next((response) => {
      iView.LoadingBar.finish();
      return response;
    });
  });
}


function handleSuccessResponse(response, resolve, reject) {
  let status = response.status;
  if (status === 200) {
    if (response.body.code && response.body.code === 'OK' || response.body.status && response.body.status === 200) {
      if (typeof(resolve) === "function") {
        resolve(response.body);
      } else {
        log.e("没有回调函数接收处理:%o", response.body);
      }
      return;
    } else if (response.body.status && response.body.status === "401") {
      log.e(response.body.errorMsg);
      unauthorized();
    } else {
      log.e("返回的对象不存在code或status属性: %o", response.body)
    }
  }
  if (typeof(reject) === "function") {
    reject(response.body);
  }
}
function handleErrorResponse(response, reject) {
  iView.LoadingBar.error();
  let status = response.status;

  if (status === 401) {
    //未登录，重定向到初始化页面
    log.e("用户未登录 %o", response.body);
    iView.Message.info("尚未登录，请先登录!");
    unauthorized();
  }

  //reject方法永远不为空，所以如果自定义使用的话，需要return true
  let flag = reject(response.body);
/* 无法判断是用户自己执行的reject还是默认的reject方法被执行了。
 log.e("flag %o",flag);
 if(flag == undefined){
    iView.Notice.error({
      title: "系统异常",
      desc: response.body?response.body.message?response.body.message:"系统或网络异常":"系统或网络异常"
    });
  }*/
}

function unauthorized() {
  // 跳转到其他路径
  if (unauthorizedUrl) {
    window.location.href = unauthorizedUrl;
  }else{
    log.e("未配置登录页面，无法跳转！");
  }
}

export default {
  post(url, params) {
    return new Promise((resolve, reject) => {

      handleIterceptors(false);
      url = handleUrl(url);
      log.d("Ajax post url:%s params:%o", url, params);
      Vue.http.post(url, params).then(response => {
        log.d("post success response:%o", response);
        handleSuccessResponse(response, resolve, reject);
      }, response => {
        log.d("post error response:%o", response);
        handleErrorResponse(response, reject);
      });
    });
  },

  postForm(url, params) {
    return new Promise((resolve, reject) => {

      handleIterceptors(true);
      url = handleUrl(url);
      log.d("Ajax postForm url:%s params:%o", url, params);
      Vue.http.post(url, params).then(response => {
        log.d("postForm success response:%o", response);
        handleSuccessResponse(response, resolve, reject);
      }, response => {
        log.d("postForm error response:%o", response);
        handleErrorResponse(response, reject);
      });
    });
  },

  get(url, params) {
    return new Promise((resolve, reject) => {

      handleIterceptors(true);
      url = handleUrl(url);
      Vue.http.get(url, params).then(response => {
        handleSuccessResponse(response, resolve, reject);
      }, response => {
        handleErrorResponse(response, reject);
      });
    });
  },

  put(url, params) {
    return new Promise((resolve, reject) => {
      url = handleUrl(url);
      Vue.http.put(url, params).then(response => {
        handleSuccessResponse(response, resolve, reject);
      }, response => {
        handleErrorResponse(response, reject);
      });
    });
  },
  delete(url, params) {
    return new Promise((resolve, reject) => {
      url = handleUrl(url);
      Vue.http.delete(url, params).then(response => {
        handleSuccessResponse(response, resolve, reject);
      }, response => {
        handleErrorResponse(response, reject);
      });
    });
  },
  config (options) {
    if (options.context) {
      context = options.context;
    }
    if (options.unauthorizedUrl) {
      unauthorizedUrl = options.unauthorizedUrl;
    }
  }
};

