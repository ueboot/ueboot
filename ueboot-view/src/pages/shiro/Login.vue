<template>
    <div id="particles-js" style="height: 100%">
        <div class="login" v-if="config.page_login.theme === 'theme1'">
            <Row class="vm-login vm-panel">
                <i-col span="14" class="login-ad">
                    <span class="photo-author"></span>
                </i-col>
                <i-col span="10" class="login-form">
                    <div class="login-header">
                        <img :src="config.logoImage" height="80" alt="">
                        <p><span>{{config.sysTitle}}</span></p>
                    </div>
                    <div class="login-form">
                        <Form ref="formCustom" :model="formCustom" class="login-container">
                            <i-input v-model="formCustom.username" placeholder="请输入登录账号"></i-input>
                            <i-input v-model="formCustom.password" type="password" placeholder="请输入密码"></i-input>
                            <Row type="flex" justify="space-between">
                                <i-col span="16">
                                    <i-input type="text" @on-enter="handleSubmit('formCustom')"
                                             v-model="formCustom.captcha" :maxlength="4"
                                             placeholder="请输入验证码"></i-input>
                                </i-col>
                                <i-col span="8">
                                    <img :src="captchaUrl" @click="changeCaptchaUrl" width="100%" height="45px"
                                         style="border-radius: 4px"/>
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
        <div v-if="config.page_login.theme === 'theme2'" style="height: 100%;">
            <div class="form-body without-side">
                <div class="website-logo">
                    <div class="logo">
                        <img class="logo-size" :src="config.logoImage" alt="">
                    </div>
                </div>
                <div class="row">
                    <div class="img-holder">
                        <div class="bg"></div>
                        <div class="info-holder">
                            <img src="../../asserts/graphic3.svg" alt="">
                        </div>
                    </div>
                    <div class="form-holder">
                        <div class="form-content">
                            <div class="form-items">
                                <h3>系统登录</h3>
                                <p>{{config.sysTitle}}</p>
                                <Form ref="formCustom" :model="formCustom" class="login-container">
                                    <i-input v-model="formCustom.username" placeholder="请输入登录账号"></i-input>
                                    <i-input v-model="formCustom.password" type="password"
                                             placeholder="请输入密码"></i-input>
                                    <Row type="flex" justify="space-between">
                                        <i-col span="16">
                                            <i-input type="text" @on-enter="handleSubmit('formCustom')"
                                                     v-model="formCustom.captcha" :maxlength="4"
                                                     placeholder="请输入验证码"></i-input>
                                        </i-col>
                                        <i-col span="8">
                                            <img :src="captchaUrl" @click="changeCaptchaUrl" width="100%" height="45px"
                                                 style="border-radius: 4px"/>
                                        </i-col>
                                    </Row>
                                    <Button type="primary" @click="handleSubmit('formCustom')" :loading="loading"
                                            size="large">登录
                                    </Button>
                                </Form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
    import config from '../../config/Config'

    export default {
        data() {
            return {
                config: {},
                formName: 'formName',
                // 提交按钮loading
                loading: false,
                // 登录form
                formCustom: {
                    username: '',
                    password: '',
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
            handleSubmit(name) {
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
                    this.$router.push(this.config.page_login.successRouter)
                    this.loading = false
                }, (response) => {
                    if (response.code === '400') {
                        this.$Notice.error({desc: response.message})
                    }
                    this.changeCaptchaUrl()
                    this.formCustom.captcha = ''
                    this.loading = false
                })
            }
        },
        computed: {
            captchaUrl: function () {
                return this.config.axios.baseURL + '/ueboot/shiro/public/captcha?time=' + this.now
            }
        },
        created() {
            this.config = config.getConfig()
        },
        mounted() {
        }
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
