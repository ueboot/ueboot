<style>
  @import '../resources/css/base.css';
  @import '../resources/css/styles.css';
</style>
<template>
  <el-container>
    <el-aside width="auto">
      <el-row type="flex" align="middle" class="logo">
        <!--<img src="../asserts/images/logo.png"/>-->
        <span>光伏好销售</span>
      </el-row>
      <el-menu :router="true" class="el-menu-vertical" text-color="#cfcfcf" active-text-color="#FFFFFF" background-color="#222837" :collapse="collapsed">
        <el-menu-item-group>
          <span slot="title">设置</span>
            <el-menu-item index="0" :route="{name: 'Index'}">
              <i class="el-icon-view" style="color: #be6d0d"></i>
              <span slot="title" class="clear_margin">统计</span>
            </el-menu-item>
          <el-submenu index="1">
            <div slot="title">
              <i class="el-icon-picture" style="color: #19be6b"></i>
              <span >首页设置</span>
            </div>
            <el-menu-item index="1-1" :route="{name: 'IndexAd'}" >
              <span slot="title">开屏广告</span>
            </el-menu-item>
            <el-menu-item index="1-2" :route="{name: 'IndexSliderAd'}">
              <i  style="color: #bdbe0a"></i>
              <span slot="title">轮播广告</span>
            </el-menu-item>
          </el-submenu>
          <el-submenu index="2">
            <div slot="title">
              <i class="el-icon-mobile-phone" style="color: #19be6b"></i>
              <span slot="title">用户管理</span>
            </div>
            <el-menu-item index="2-1" :route="{name: 'XuserList'}">
              <i ></i>
              <span slot="title">用户列表</span>
            </el-menu-item>
            <el-menu-item index="2-2" :route="{name: 'Teamlist'}">
              <i ></i>
              <span slot="title">团队列表</span>
            </el-menu-item>
          </el-submenu>
          <el-submenu index="3">
            <div slot="title">
              <i class="el-icon-vueboot-friend" style="color: #19be6b"></i>
              <span slot="title">数据管理</span>
            </div>
            <el-menu-item index="3-1" :route="{name: 'customerData'}">
              <i ></i>
              <span slot="title">客户列表</span>
            </el-menu-item>
          </el-submenu>
          <el-submenu index="4">
            <div slot="title">
              <i class="el-icon-service" style="color: #19be6b"></i>
              <span slot="title">用户反馈</span>
            </div>
            <el-menu-item index="4-1" :route="{name: 'FeedbackList'}">
              <i ></i>
              <span slot="title">用户反馈</span>
            </el-menu-item>
            <el-menu-item index="4-2" :route="{name: 'FeedbackRoofList'}">
              <i ></i>
              <span slot="title">特殊屋型</span>
            </el-menu-item>
          </el-submenu>
          <el-submenu index="5">
            <div slot="title">
              <i class="el-icon-setting" style="color: #19be6b"></i>
              <span slot="title">安全设置</span>
            </div>
            <el-menu-item index="5-1" :route="{name: 'ModifyPassword'}">
              <i></i>
              <span slot="title">修改密码</span>
            </el-menu-item>
          </el-submenu>
          <!--<el-menu-item index="6" :route="{name: 'Home'}">-->
            <!--<i></i>-->
            <!--<span slot="title">首页</span>-->
          <!--</el-menu-item>-->
          <!--<el-menu-item index="7" :route="{name: 'SettingDetails'}">-->
            <!--<i></i>-->
            <!--<span slot="title">进度详情</span>-->
          <!--</el-menu-item>-->
          <!--<el-menu-item index="8" :route="{name: 'CustomerData'}">-->
            <!--<i></i>-->
            <!--<span slot="title">客户资料</span>-->
          <!--</el-menu-item>-->
          <!--<el-menu-item index="9" :route="{name: 'Signin'}">-->
            <!--<i></i>-->
            <!--<span slot="title">签到统计</span>-->
          <!--</el-menu-item>-->
          <!--<el-submenu index="2">
            <div slot="title">
              <i class="el-icon-vueboot-friendadd" style="color: #19be6b"></i>
              <span slot="title">权限管理</span>
            </div>
            <el-menu-item index="2-1" >
              <i class="el-icon-vueboot-dot-o"></i>
              <span slot="title">资源管理</span>
            </el-menu-item>
            <el-menu-item index="2-2" >
              <i class="el-icon-vueboot-dot-o"></i>
              <span slot="title">角色权限</span>
            </el-menu-item>
          </el-submenu>
          <el-submenu index="3">
            <div slot="title">
              <i class="el-icon-vueboot-friend" style="color: #2d8cf0"></i>
              <span slot="title">用户管理</span>
            </div>
            <el-menu-item index="3-1">
              <i class="el-icon-vueboot-dot-o"></i>
              <span slot="title">用户管理</span>
            </el-menu-item>
          </el-submenu>-->
        </el-menu-item-group>

        <!--<el-menu-item-group v-for="(menu, m) in data.menus" v-if="menu.resourceType === '菜单组' && !menu.parentCode" :key="m">
          <span slot="title">{{menu.name}}</span>
          <el-submenu :index="menu2.id+''" v-for="(menu2, n) in data.menus" v-if="menu2.parentCode === menu.code" :key="n">
            <div slot="title">
              <i :class="menu2.theme.icon" :style="{color: menu2.theme.color}"></i>
              <span slot="title">{{menu2.name}}</span>
            </div>
            <el-menu-item :index="menu2.id + '' + menu3.id" v-for="(menu3, i) in data.menus" v-if="menu3.parentCode === menu2.code" :key="i" :route="menu3.url">
              <i class="el-icon-vueboot-dot-o"></i>
              <span slot="title">{{menu3.name}}</span>
            </el-menu-item>
          </el-submenu>
        </el-menu-item-group>

      <el-submenu :index="menu.id+''" v-for="(menu, m) in data.menus" v-if="menu.resourceType === '菜单' && !menu.parentCode" :key="m">
        <div slot="title">
          <i :class="menu.theme.icon" :style="{color: menu.theme.color}"></i>
          <span slot="title">{{menu.name}}</span>
        </div>
        <el-menu-item :index="menu.id + '' + menu2.id" v-for="(menu2, n) in data.menus" v-if="menu2.parentCode === menu2.code" :key="n" :route="menu2.url">
          <i class="el-icon-vueboot-dot-o"></i>
          <span slot="title">{{menu2.name}}</span>
        </el-menu-item>
      </el-submenu>-->

      </el-menu>
    </el-aside>
    <el-container>
      <el-header height="54px">
        <el-row type="flex" class="warp" justify="right" align="middle" style="height: 54px;" :gutter="8">
         <!-- <el-col>
            <el-button class="circle none toggle" size="mini" round icon="el-icon-vueboot-menu"/>
          </el-col>-->
          <el-col>

          </el-col>
          <el-col :span="5" :xs="5" :sm="5" :lg="3" :xl="2">
            <el-row justify="right" align="middle" type="flex">
              <el-col :span="10">
                <img class="header" height="36" src="../asserts/images/header.png"/>
              </el-col>
              <el-col :span="14">
                <el-dropdown @command="handleCommand">
                    <span class="el-dropdown-link">
                      {{loginName}}<i class="el-icon-arrow-down el-icon--right"></i>
                    </span>
                  <el-dropdown-menu slot="dropdown">
                    <!--<el-dropdown-item  command="a" >修改密码</el-dropdown-item>-->
                    <el-dropdown-item  command="b" >退出系统</el-dropdown-item>
                  </el-dropdown-menu>
                </el-dropdown>
              </el-col>
            </el-row>
          </el-col>

        </el-row>
      </el-header>
      <el-main>
        <transition name="fade" mode="out-in">
          <div style="background:#eee;padding: 20px">
            <router-view/>
          </div>
        </transition>
      </el-main>
      <!--修改密码弹出层-->
      <div>
        <el-dialog :visible.sync="passwordModel" title="修改密码" :modal="true">
          <el-form  ref="passwordForm" :model="pwdForm" :rules="pwdRule" label-width="100">
            <el-form-item prop="originPwd" label="原密码">
              <el-input type="password" v-model="pwdForm.originPwd" placeholder="请输入原密码">
                <i class="ios-locked-outline" slot="prepend"></i>
              </el-input>
            </el-form-item>
            <el-form-item label="新密码" prop="newPwd">
              <el-input type="password" v-model="pwdForm.newPwd" placeholder="请输入新密码">
                <i class="ios-locked-outline" slot="prepend"></i>
              </el-input>
            </el-form-item>
            <el-form-item label="确认密码" prop="surePwd">
              <el-input type="password" v-model="pwdForm.surePwd" placeholder="确认密码">
                <i class="ios-locked-outline" slot="prepend"></i>
              </el-input>
            </el-form-item>
          </el-form>
          <div slot="footer">
            <el-button type="primary" @click="handlePasswordSubmit('passwordForm')">提交</el-button>
            <el-button type="ghost" @click="resetPasswordForm('passwordForm')">重置</el-button>
            <el-button type="ghost" @click="passwordModel=false">取消</el-button>
          </div>
        </el-dialog>
      </div>

    </el-container>
  </el-container>
</template>
<script>
import axios from 'axios'
import ElFooter from 'element-ui/packages/footer/src/main'
import md5 from 'md5'
import values from '../utils/values'

export default {

  components: {ElFooter},
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
      collapsed: false,
      loginName: '', // 登录账号名称
      menus: [],
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
  computed: {},
  methods: {
    handleCommand (cmd) {
      if (cmd === 'a') {
        this.passwordModel = true
      } else if (cmd === 'b') {
        this.logout()
      }
    },
    logout () {
      // 退出系统
      this.$confirm('确定要退出当前系统吗', {
        callback: (action) => {
          if (action === 'confirm') {
            axios.post('/backend/security/logout').then(() => {
              this.$message.success('退出成功!')
              this.$router.push({name: 'Login'})
            }, (response) => {
              this.$message.error(response.message ? response.message : '系统或网络异常')
            })
          }
        }
      })
    },
    resetPasswordForm (name) {
      console.log(this.$refs)
      this.$refs[name].resetFields()
    },
    handlePasswordSubmit (name) {
      this.$refs[name].validate((valid) => {
        if (valid) {
          let jsonData = {
            oldPassword: md5(this.pwdForm.originPwd + this.loginName),
            newPassword: md5(this.pwdForm.newPwd + this.loginName)
          }
          axios.post('/backend/security/updatePassword', jsonData).then((data) => {
            // 关闭浮层
            this.passwordModel = false
            this.$message.success('修改成功,下次登录请用新密码登录, 谢谢!')
          }, (response) => {
            this.$message.error(response.message ? response.message : '系统或网络异常')
          })
        }
      })
    }
  },
  mounted () {
    let sessionUser = JSON.parse(sessionStorage.getItem(values.storage.user)) || {}
    this.loginName = sessionUser.username// 登录账号名称
  }
}
</script>

<style scoped>
  .el-container {
    height: 100%;
    background: white;
  }

  .el-aside {
    background: #222837;
  }

  .el-header {
    background-color: #FFFFFF;
    color: #333;
    /*line-height: 56px;*/
    /*vertical-align: middle;*/
    box-shadow: 0 2px 2px rgba(0, 0, 0, 0.05), 0 1px 0 rgba(0, 0, 0, 0.05);
  }

  .header {
    border-radius: 30px;
    line-height: 36px;
  }

  .el-menu {
    border-right: solid 0px #e6e6e6;
  }

  .el-menu .el-submenu.is-active {
  }

  .el-menu-vertical:not(.el-menu--collapse) {
    width: 230px;
    min-height: 400px;
  }

  .el-submenu [class^="el-icon-vueboot-"] {
    padding-bottom: 2px;
  }

  .el-submenu__title {
    height: 45px;
    line-height: 45px;
    font-size: 13px;
    padding-left: 40px;
    transition: border-color 0.1s, background-color 0.1s, color 0.1s;
  }

  .el-menu .el-submenu.is-active .el-submenu__title {
    border-left: solid 4px #fc6c2e !important;
  }

  .el-menu-item {
    height: 45px;
    line-height: 45px;
    min-width: 220px;
    background-color: #1f212d !important;
    font-size: 13px;
    padding-left: 21px !important;
  }

  .el-menu-item.is-active {
    background-color: #1c1f27 !important;
  }

  .el-menu-item.is-active [class^="el-icon-vueboot-"] {
    font-weight: bold;
  }

  .toggle {
    margin-left: -6px;
  }

  .el-main {
    padding: 0px;
  }

  .logo {
    height: 120px;
    padding-top: 20px;
    padding-left: 24px;
  }
  .logo span {
    font-weight: bold;
    font-size: 24px;
    color: #bfbec0;
    margin-left:25px;
  }
  .el-menu-item span{
    margin-left: 32px;
    text-align: left;
  }
  .el-menu-item .clear_margin {
    margin: 0;
  }

  .el-footer {
    text-align: center;
    line-height: 60px;
  }

  .item {
    /*margin-top: 10px;*/
    /*margin-right: 50px;*/
  }
  .fl{
    float: left;
  }
  .fr{
    float: right;
  }
  .clearfix:after {
    content: "";
    display: block;
    visibility: hidden;
    height: 0;
    clear: both;
  }
  .clearfix {
    zoom: 1;
  }
</style>
