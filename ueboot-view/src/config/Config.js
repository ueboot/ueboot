import deepExtend from 'deep-extend'
import Log from '../utils/Log'
import AxiosConfig from './AxiosConfig'
let uebootConfig = {
  sysTitle: 'ueboot权限管理',
  logoImage: '/static/img/ueboot.png',
  page_login: {
    // 登录界面风格，可取值 ['theme1','theme2']
    theme: 'theme2',
    /**
         * 登录成功后的回调方法，有这个方法后会替换默认的路由跳转事件
         * @param responseBody 登录成功后返回的结果
         * @param _this 当前Login.vue页面的this对象，如果需要跳转路由等，直接用
         * _this.$router.push({path:'/'});
         */
    // successCallBack:(responseBody,_this)=>{},
    // 登录成功后的跳转路径。与successCallBack互斥，存在callback方法，则当前配置无效
    successRouter: { path: '/' },
    // logo图片行内样式
    logoStyle: 'width:100px;height:100px;',
    bgImage: '@/asserts/graphic3.svg',
    formTitle: '系统登录'
  },
  page_main: {
    // 右上角显示的用户名取值的key,从登录成功后的ueboot_login_info当中获取
    userNameKey: 'username',
    menuWidth: 250,
    logoStyle: {
      minWidth: '250px'
    },
    logoImageStyle: {
      width: '50%',
      height: '50%'
    },
    // 右侧风格，下拉模式或者横向模式
    rightTheme: 'dropdown',
    rightStyle: {
      minWidth: '100px'
    },
    dropdown: {
      // item内容，默认两个item，并且会添加对应的事件，如果需要自定义item或者替换默认的item
      // 可以重载当前对象即可，要求name唯一且系统默认的两个菜单如果需要使用，请保持名字不变
      items: [
        { name: '修改密码', disabled: false, divided: false, icon: 'md-create' },
        { name: '退出系统', disabled: false, divided: false, icon: 'md-exit' }
      ],
      // 头像图标，icon和src二选一
      avatar: {
        icon: 'ios-person',
        src: '',
        style: null
      }

    },
    // 退出登录成功后跳转的路由配置
    logoutSuccessRouter: { name: 'Login' }
  },
  axios: {
    baseURL: '',
    unauthorizedUrl: '/#/login',
    // 未登录文字提示
    notLoginMsg: '您尚未登录，或当前用户会话已过期，点击确定按钮返回到登录界面。'
  },
  // 日志级别，使用this.$log.d()这种方式记录日志时的级别
  log: {
    level: 2
  }
}

/**
 * 对外提供配置入口，针对Main页面上的一些参数可以进行配置化
 */
export default {
  setConfig (conf) {
    uebootConfig = deepExtend({}, uebootConfig, conf)
    // 设置log级别
    Log.config(uebootConfig.log)
    // 设置axios配置
    AxiosConfig.init(uebootConfig.axios)
  },
  getConfig () {
    return uebootConfig
  }
}
