<style scoped>
  @import '../resources/css/base.css';
  @import '../resources/css/flex.css';
  @import '../resources/css/styles.css';
</style>
<template>
  <div style="background: #eceff4; height: 100%">
    <el-row type="flex" style="height: 100%" justify="center" align="middle">
      <el-col :xs="9" :md=7 :sm="8" :lg="7" :xl="4">
        <el-card>
          <el-tabs v-model="activeName" @tab-click="handleClick">
            <el-tab-pane label="用户名登录" name="second">
              <br>
              <el-row type="flex" justify="center" align="middle">
                <el-col style="font-size: 30px;text-align: center">Sign In</el-col>
              </el-row>
              <el-form :model="loginForm" status-icon size="small" :rules="rules" ref="loginForm" label-width="0px" class="form">
                <el-form-item prop="username">
                  <el-input type="text" v-model="loginForm.username" auto-complete="off" placeholder="账号"/>
                </el-form-item>
                <el-form-item prop="password">
                  <el-input type="password" v-model="loginForm.password" auto-complete="off" placeholder="密码"/>
                </el-form-item>
                <el-form-item prop="captcha">
                  <el-row>
                    <!--<el-col :span=16>-->
                    <!--<el-input type="text" :maxlength="6" v-model="loginForm.captcha" auto-complete="off" placeholder="验证码"/>-->
                    <!--</el-col>-->
                    <!--<el-col :span=8>-->
                    <!--<img :src="captchaUrl" width="100%" style="height: 35px;" @keyup.enter="handleSubmit"/>-->
                    <!--</el-col>-->
                  </el-row>
                </el-form-item>
                <el-form-item>
                  <el-button type="danger" style="width: 80px;" round @click="handleSubmit('loginForm')">Login</el-button>
                  <el-button type="text" size="mini" disabled>Forgot password?</el-button>
                </el-form-item>
              </el-form>
            </el-tab-pane>
          </el-tabs>

          <el-row type="flex" justify="center" align="middle">
            <el-col/>
          </el-row>
        </el-card>
      </el-col>
      <el-col :xs="9" :md=7 :sm="8" :lg="7" :xl="4">
        <el-card class="right-card">
          <br/><br/>
          <el-row type="flex" align="middle">
            <el-col style="font-size: 26px; color: white;">光伏好销售后台管理系统</el-col>
          </el-row>
          <br/>
          <el-row type="flex" align="middle">
            <el-col style="font-size: 12px; color: #484f66;">
              后台管理功能
            </el-col>
          </el-row>
          <br/>
          <el-row type="flex" align="middle">
            <el-col style="font-size: 12px; color: #484f66;"/>
          </el-row>
          <br/>
          <!--<el-row type="flex" align="middle">
            <el-col>
              <el-button type="danger" size="small" style="width: 100px;" round>Learn more</el-button>
            </el-col>
          </el-row>-->
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import axios from 'axios'
import md5 from 'md5'
import values from '../utils/values'

export default {
  data () {
    return {
      formName: 'loginForm',
      activeName: 'second',
      // 提交按钮loading
      loading: false,
      // 登录form admin 123456
      loginForm: {
        username: '',
        password: '',
        captcha: ''
      },
      rules: {},
      now: new Date()
    }
  },
  methods: {
    initCsrf: function () {
      axios.get('/backend/csrf').then(response => {
        console.log(response.data)
      })
    },
    handleClick (tab, event) {
      console.log(tab, event)
    },
    // 刷新验证码
    changeCaptchaUrl: function () {
      this.now = new Date()
    },
    // 确认登录操作
    handleSubmit (name) {
      if (this.loginForm.username === '') {
        this.$message.error('账号不能为空')
        return
      }
      if (this.loginForm.password === '') {
        this.$message.error('密码不能为空')
        return
      }
      // if (this.loginForm.captcha === '') {
      //   this.$message.error('验证码不能为空')
      //   return
      // }
      this.loading = true
      let form = {
        username: this.loginForm.username,
        password: md5(this.loginForm.password + this.loginForm.username),
        captcha: this.loginForm.captcha
      }
      axios.post('/backend/login', form).then(response => {
        this.$message.success('登录成功')
        this.$router.replace('/Index')
        window.sessionStorage.setItem(values.storage.user, JSON.stringify({username: this.loginForm.username}))
        this.loading = false
      }, (response) => {
        this.$message.error(response.message)
        this.changeCaptchaUrl()
        this.loading = false
      })
    }
  },
  computed: {
    // captchaUrl: function () {
    //   return '/backend/captcha?time=' + this.now
    // }
  },
  mounted () {
    this.initCsrf()
  }
}
</script>

<style scoped>
  .el-button + .el-button {
    margin-left: 3px;
  }
  .el-card {
    box-shadow: 0 0px 50px 0 rgba(0, 0, 0, 0.05);
    border-radius: 0px;
    border: 0px;
    height: 380px;
  }
  .right-card {
    background: #23293b;
    height: 300px;
    box-shadow: 0 0px 0px 0 rgba(0, 0, 0, 0.05);
  }

   .form .el-input .el-input__inner {
    border-radius: 0px;
    border-width: 0px;
    border-bottom: 1px solid #dcdfe6;
    padding: 0 8px;
  }

  .form {
    margin: 15px 40px 25px 40px;
  }

</style>
