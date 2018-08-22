<template>
  <div class="layout">
    <Layout>
      <Header :style="{color: '#fff'}">
        <Menu mode="horizontal" theme="dark" active-name="1">
          <div class="layout-logo">
            <a class="header-logo">
              <img src="../assets/logo.png" alt="." class="header-img">
              <span class="header-span">虚拟架构查询</span>
            </a>
          </div>
          <div class="layout-nav">
            <MenuItem name="1">
              <a href="javascript:void(0)" @click="logout" class="header-menu ">
                <Icon type="android-exit" style="color:red;"></Icon>
                <span style="cursor:pointer;">退出系统</span>
              </a>
            </MenuItem>
            <!--<MenuItem name="2">-->
            <!--<a href="javascript:void(0)" @click="resetPwd" class="header-menu">-->
            <!--<Icon type="edit" style="color:#657180;"></Icon>-->
            <!--<span style="cursor:pointer;">修改密码</span>-->
            <!--</a>-->
            <!--</MenuItem>-->

          </div>
        </Menu>

      </Header>
      <Layout>
        <Sider hide-trigger :style="{background: '#fff',height: '100%'}">
          <Menu :theme="theme" :width="menuWidth" :style="{ 'min-height' : clientHeight - 112 + 'px'}" @on-select="menuClick" :active-name="activeMenuName"
                :open-names="openMenuNames">
            <template  v-for="(menu, index) in menus">
              <Submenu :name="'m'+menu.id" v-if="menu.parentId == null" :key="index">
                <template slot="title">
                  <Icon :type="menu.themeJson.icon" size="15" :color="menu.themeJson.color" style="margin-right: 3px;" v-if="menu.themeJson"></Icon>
                  <span class="layout-text">{{menu.name}}</span>
                </template>
                <template v-for="child in menus">
                  <Menu-item :name="'m'+child.id" :key="child.id" v-if="child.parentId === menu.id">

                    <Icon :type="child.themeJson.icon" :size="iconSize" v-if="child.themeJson"></Icon>
                    <span class="layout-text">{{child.name}}</span>
                  </Menu-item>
                </template>
              </Submenu>
            </template>
          </Menu>
        </Sider>
        <Layout :style="{padding: '0 24px 0 24px',width:'100%',maxWidth:clientWidth - 200 +'px'}">
          <Breadcrumb :style="{margin: '24px 0'}">
            <BreadcrumbItem v-for="(item, index) in breadItems" :key="'bread'+index">{{item.name}}</BreadcrumbItem>
          </Breadcrumb>
          <Content :style="{padding: '14px', minHeight: clientHeight - 175+ 'px', background: '#fff',width:'100%',maxWidth:'100% !important'}">
            <transition>
              <router-view></router-view>
            </transition>
          </Content>
          <div class="layout-copy">
            2018 &copy; ueboot权限管理系统
          </div>
        </Layout>
      </Layout>

      <!--修改密码弹出层-->
      <div>
        <Modal v-model="passwordModel" title="修改密码" :mask-closable="false">
          <Form ref="passwordForm" :model="pwdForm" :rules="pwdRule" :label-width="100">
            <Form-item prop="originPwd" label="原密码">
              <i-input type="password" v-model="pwdForm.originPwd" placeholder="请输入原密码">
                <Icon type="ios-locked-outline" slot="prepend"></Icon>
              </i-input>
            </Form-item>
            <Form-item label="新密码" prop="newPwd">
              <i-input type="password" v-model="pwdForm.newPwd" placeholder="请输入新密码">
                <Icon type="ios-locked-outline" slot="prepend"></Icon>
              </i-input>
            </Form-item>
            <Form-item label="确认密码" prop="surePwd">
              <i-input type="password" v-model="pwdForm.surePwd" placeholder="确认密码">
                <Icon type="ios-locked-outline" slot="prepend"></Icon>
              </i-input>
            </Form-item>
          </Form>
          <div slot="footer">
            <Button type="primary" @click="handlePasswordSubmit('passwordForm')">提交</Button>
            <Button type="ghost" @click="resetPasswordForm('passwordForm')">重置</Button>
            <Button type="ghost" @click="passwordModel=false">取消</Button>
          </div>
        </Modal>
      </div>
    </Layout>
  </div>
</template>

<script>
export default {
  data () {
    const validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'))
      } else {
        callback()
      }
    }
    const validatePassCheck = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else if (value !== this.pwdForm.newPwd) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
    return {
      loginName: '', // 登录账号名称
      lastLoginTime: '', // 上次登录时间
      state: '',
      toggle: true,
      theme: 'light',
      menuWidth: '200px',
      activeMenuName: '',
      menus: [],
      breadItems: [], // 面包屑导航,
      openMenuNames: [''],
      clientHeight: document.documentElement.clientHeight,
      clientWidth: document.documentElement.clientWidth,
      passwordModel: false, // 弹出修改密码弹出层
      pwdForm: {
        originPwd: '',
        newPwd: '',
        surePwd: ''
      },
      pwdRule: {
        originPwd: [
          {required: true, message: '请输入原密码', trigger: 'blur'}
        ],
        newPwd: [
          {required: true, validator: validatePass, trigger: 'blur'}
        ],
        surePwd: [
          {required: true, validator: validatePassCheck, trigger: 'change'}
        ]
      }
    }
  },
  watch: {
    $route (to, from) {
      this.initBreadItems(to)
    }
  },
  methods: {
    // 监听路由变化动态改变面包屑导航，暂时只支持两级菜单
    initBreadItems (to) {
      this.breadItems = []
      this.menus.forEach((n) => {
        // 根据URL匹配，找到对应的菜单和父节点菜单ID
        if (n.url === to.path) {
          if (n.parentId) {
            this.menus.forEach((m) => {
              if (n.parentId === m.id) {
                this.breadItems.push(m)
                this.breadItems.push(n)
              }
            })
          } else {
            this.breadItems.push(n)
          }
        }
      })
    },

    init: function () {
      // todo 先固定写死菜单，后续改为从后台读取

      this.menus = [
        // 关系数据
        {name: '权限管理', id: 10, parentId: null, url: ''},
        {name: '用户管理', id: 11, parentId: 10, url: '/ueboot/shiro/User'},
        {name: '角色管理', id: 12, parentId: 10, url: '/ueboot/shiro/Role'},
        {name: '菜单管理', id: 13, parentId: 10, url: '/ueboot/shiro/Resources'},
        {name: '权限设置', id: 14, parentId: 10, url: '/ueboot/shiro/Permission'},


      ]
      let matched = this.$route.matched
      matched.forEach((m) => {
        if (m.parent) {
          this.menus.forEach((n) => {
            // 根据URL匹配，找到对应的菜单和父节点菜单ID
            if (n.url === m.path) {
              this.initBreadItems(m)
              this.activeMenuName = 'm' + n.id
              this.openMenuNames.push('m' + n.parentId)
            }
          })
        }
      })
      /* axios.get('/menus').then(response => {
          this.menus = response.body
          this.activeMenuName = this.$route.name
          // 让菜单展开到路径匹配的位置
          let matched = this.$route.matched
          matched.forEach((m) => {
            if (!m.parent) {
              this.openMenuNames.push(m.name)
            }
          })
        }).catch(response => {
          console.log(response)
        }) */
    },
    menuClick (id) {
      this.menus.forEach((n) => {
        // 根据URL匹配，找到对应的菜单和父节点菜单ID
        if ('m' + n.id === id) {
          this.$router.push(n.url)
        }
      })
    },
    logout () {
      // 退出系统
      this.$Modal.confirm({
        title: '系统提示',
        content: '确定要退出当前系统吗',
        onOk: () => {
          this.$axios.post('/ueboot/shiro/private/logout', {}).then((data) => {
            this.$Message.success('退出成功!')
            this.$router.push({name: 'login'})
          }, (response) => {
            this.$log.d(response)
            this.$Notice.error({
              title: '系统异常',
              desc: response.message ? response.message : '系统或网络异常'
            })
          })
        }
      })
    },
    resetPwd (name) {
      // 修改密码
      this.resetPasswordForm('passwordForm')
      this.passwordModel = true
    },
    operationConfig () {
      let sessionUser = JSON.parse(sessionStorage.getItem('ueboot_user')) || {}
      let userType = sessionUser.userType
      this.routerConfig.routes.forEach(function (item) {
        if (item.role === userType) {
          item.isHide = false
        } else {
          item.isHide = true
        }
      })
    },
    resetPasswordForm (name) {
      this.$refs[name].resetFields()
    },
    handlePasswordSubmit (name) {
      this.$refs[name].validate((valid) => {
        if (valid) {
          let sessionUser = JSON.parse(sessionStorage.getItem('ueboot_user'))
          let jsonData = {
            oldPassword: this.pwdForm.originPwd,
            newPassword: this.pwdForm.newPwd,
            userType: sessionUser.userType,
            userId: sessionUser.userId
          }
          this.$axios.post('/ueboot/shiro/private/updatePassword', jsonData).then((data) => {
            // 关闭浮层
            this.passwordModel = false
            this.$Modal.success({
              title: '系统提示',
              content: '修改成功,下次登录请用新密码登录, 谢谢!'
            })
          }, (response) => {
            this.$log.d(response)
            this.$Notice.error({
              title: '系统异常',
              desc: response.message ? response.message : '系统或网络异常'
            })
          })
        }
      })
    }
  },
  computed: {
    iconSize () {
      return this.toggle ? '14px' : '24px'
    }
  },
  created () {
    this.init()
  },
  mounted () {
    let sessionUser = JSON.parse(sessionStorage.getItem('ueboot_user')) || {}
    this.loginName = sessionUser.userName// 登录账号名称
    this.lastLoginTime = sessionUser.lastLoginTime// 上次登录时间
  }
}
</script>

<style scoped>
  *[v-cloak] {
    display: none;
  }

  /* 解决内容与菜单两个区域会换行的问题 */
  .ivu-layout {
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;
    -webkit-box-orient: vertical;
    -webkit-box-direction: normal;
    -ms-flex-direction: column;
    flex-direction: column;
    -webkit-box-flex: 1;
    -ms-flex: auto;
    flex: auto;
    background: #f5f7f9;
  }

  .ivu-layout.ivu-layout-has-sider {
    -webkit-box-orient: horizontal;
    -webkit-box-direction: normal;
    -ms-flex-direction: row;
    flex-direction: row;
  }

  .ivu-layout-sider {
    transition: all 0.2s ease-in-out;
    position: relative;
    background: #515a6e;
    min-width: 0;
  }

  .ivu-layout-content {
    -webkit-box-flex: 1;
    -ms-flex: auto;
    flex: auto;
  }

  .layout {
    background: #f5f7f9;
    position: relative;
    overflow: auto;
  }

  .layout-logo {
    width: 200px;
    height: 30px;
    border-radius: 3px;
    float: left;
    position: relative;
  }

  .layout-nav {
    width: 130px;
    margin: 0 auto;
    margin-right: 0;
  }

  .layout-copy {
    text-align: center;
    padding: 5px 0 5px;
    color: #9ea7b4;
  }

  .ivu-layout-header a {
    color: #fff !important;
  }

  .header-menu:hover {
    background-color: rgba(0, 0, 0, 0.05);
  }

  .header-logo {
    background: 0 0;
    text-decoration: none;
    outline: 0;
    cursor: pointer;
    transition: color 0.2s ease;
    display: inline-block;
    float: none !important;
    height: auto;
    padding: 0 20px;
    font-size: 20px;
    font-weight: 700;
    line-height: 50px;
    text-align: center;
    color: #eaebed !important;
  }

  .header-img {
    border-style: none;
    display: inline-block !important;
    max-height: 20px;
    margin-top: -4px;
    vertical-align: middle;
    visibility: visible;
    border: 0;
    color: #eaebed !important;
  }

  .header-span {
    box-sizing: border-box;
    color: rgb(184, 196, 201);
    cursor: pointer;
    display: inline;
    font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", 微软雅黑, Arial, sans-serif;
    font-size: 20px;
    font-weight: bold;
    height: auto;
    line-height: 50px;
    margin-left: 5px;
    text-align: center;
  }

  .layout-ceiling-main .header-menu {
    font-size: 14px;
    display: inline-block;
    text-align: center;
    width: 90px;
  }

</style>
