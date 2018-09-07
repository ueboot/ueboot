<template>
  <div id="particles-js">
    <div class="login">
      <Row class="vm-login vm-panel">
        <i-col span="14" class="login-ad">
          <span class="photo-author"></span>
        </i-col>
        <i-col span="10" class="login-form">
          <div class="login-header">
            <img src="../assets/logo.png" height="80" alt="">
            <p><span>ueboot权限管理系统</span></p>
          </div>
          <div class="login-form">
            <Form ref="formCustom" :model="formCustom" class="login-container">
              <i-input v-model="formCustom.username" placeholder="请输入登录账号"></i-input>
              <i-input v-model="formCustom.password" type="password" placeholder="请输入密码"></i-input>
              <Row type="flex" justify="space-between">
                <i-col span="16">
                  <i-input type="text" @on-enter="handleSubmit('formCustom')" v-model="formCustom.captcha" :maxlength="4"
                           placeholder="请输入验证码"></i-input>
                </i-col>
                <i-col span="8">
                  <img :src="captchaUrl" @click="changeCaptchaUrl" width="100%" height="45px" style="border-radius: 4px"/>
                </i-col>
              </Row>
              <Button type="primary" @click="handleSubmit('formCustom')" :loading="loading">登录</Button>
            </Form>
          </div>
          <div class="login-footer">
          </div>
        </i-col>
      </Row>
    </div>
  </div>
</template>
<script>
import '../styles/login.less'

export default {
  data () {
    return {
      formName: 'formName',
      // 提交按钮loading
      loading: false,
      // 登录form
      formCustom: {
        username: 'root',
        password: '111111',
        captcha: ''
      },
      now: new Date().getMilliseconds()
    }
  },
  methods: {
    // 刷新验证码
    changeCaptchaUrl: function () {
      this.now = new Date().getMilliseconds()
    },
    // 确认登录操作
    handleSubmit (name) {
      if (this.formCustom.username === '') {
        this.$Notice.error({
          title: '消息提示',
          desc: '账号不能为空'
        })
        return
      }
      if (this.formCustom.password === '') {
        this.$Notice.error({
          title: '消息提示',
          desc: '密码不能为空'
        })
        return
      }
      if (this.formCustom.captcha === '') {
        this.$Notice.error({
          title: '消息提示',
          desc: '验证码不能为空'
        })
        return
      }
      this.loading = true
      this.$axios.post('/ueboot/shiro/public/login', this.formCustom).then(response => {
        this.$Message.success('登录成功')
        this.$router.push({name: 'User'})
        this.loading = false
      }, (response) => {
        this.changeCaptchaUrl()
        this.formCustom.captcha = ''
        this.loading = false
      })
    }
  },
  computed: {
    captchaUrl: function () {
      return process.env.CONTEXT + '/ueboot/shiro/public/captcha?time=' + this.now
    }
  },
  mounted () {}
}
</script>
<style>
  .login .ivu-btn-primary {
    color: #fff;
    background-color: #41b883;
    border-color: #41b883;
  }

  .login .ivu-btn-primary:hover {
    color: #fff;
    background-color: #5ed19e;
    border-color: #5ed19e;
  }

  .login .ivu-btn {
    font-size: 13px;
  }

  body {
    margin: 0;
    font: normal 75% Arial, Helvetica, sans-serif;
  }

</style>
