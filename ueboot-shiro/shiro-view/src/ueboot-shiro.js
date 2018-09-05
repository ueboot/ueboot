/**
 * 对外提供配置入口，针对Main页面上的一些参数可以进行配置化
 */
import deepExtend from 'deep-extend'

let UebootShiroConfig = {
  logoImage: '/static/img/logo.png',
  //登录成功后的跳转路径
  loginSuccessPath:'/',
  //登录界面风格，可取值 ['default','ueboot1']
  loginTheme:'ueboot1',
  sysTitle: 'ueboot权限管理',
  layoutLogoStyle: {
    width: '250px'
  },
  layoutNavStyle: {
    width: '220px'
  }
}

export default {
  setConfig (config) {
    UebootShiroConfig = deepExtend({}, UebootShiroConfig, config)
  },
  getConfig () {
    return UebootShiroConfig
  }

}
