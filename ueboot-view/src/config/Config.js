import deepExtend from 'deep-extend'

let config = {
    sysTitle: 'ueboot权限管理',
    logoImage: '/static/img/logo.png',
    page_login: {
        // 登录界面风格，可取值 ['theme1','theme2']
        theme: 'theme2',
        //登录成功后的跳转路径
        successRouter:{path:'/'}
    },
    page_main: {
        logoStyle: {
            width: '250px'
        },
        navStyle: {
            width: '220px'
        },
        //退出登录成功后跳转的路由配置
        logoutSuccessRouter:{name:'Login'}
    },
    axios: {
        baseURL:'',
        unauthorizedUrl:'/#/login'
    },
}

/**
 * 对外提供配置入口，针对Main页面上的一些参数可以进行配置化
 */
export default {
    setConfig(conf) {
        config = deepExtend({}, conf, config)
    },
    getConfig() {
        return config
    }

}
