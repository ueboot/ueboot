<template>
    <div class="layout">
        <Layout style="height: 100%">
            <Header :style="{color: '#fff'}">
                <Menu mode="horizontal" theme="dark" active-name="1">
                    <div class="layout-parent">
                        <div class="layout-logo" :style="config.page_main.logoStyle">
                           <Row>
                               <i-col :span="12">
                                   <img :src="config.logoImage" :alt="config.sysTitle" class="header-img">
                               </i-col>
                               <i-col :span="12">
                                   <div class="header-span">{{config.sysTitle}}</div>
                               </i-col>
                           </Row>
                        </div>
                        <div class="layout-nav" :style="config.page_main.rightStyle">
                            <Row justify="end" type="flex" v-if="config.page_main.rightTheme!=='dropdown'">
                                <i-col span="12">
                                    <MenuItem name="1">
                                        <a href="javascript:void(0)" @click="logout" class="header-menu ">
                                            <Icon type="md-exit" style="color:red;"></Icon>
                                            <span style="cursor:pointer;">退出系统</span>
                                        </a>
                                    </MenuItem>
                                </i-col>
                                <i-col span="12">
                                    <MenuItem name="2">
                                        <a href="javascript:void(0)" @click="resetPwd" class="header-menu">
                                            <Icon type="md-create" style="color:#657180;"></Icon>
                                            <span style="cursor:pointer;">修改密码</span>
                                        </a>
                                    </MenuItem>
                                </i-col>
                            </Row>
                            <Row justify="end" type="flex" v-else>
                                <i-col span="24">
                                    <span style="color: white; margin-right:6px">{{loginName}}</span>
                                    <Dropdown @on-click="dropdownClick">
                                        <Avatar :icon="config.page_main.dropdown.avatar.icon"
                                                :src="config.page_main.dropdown.avatar.src"
                                                :style="config.page_main.dropdown.avatar.style" size="large"/>
                                        <DropdownMenu slot="list">
                                            <DropdownItem v-for="(item,index) in config.page_main.dropdown.items"
                                                          :key="`di${index}`" :disabled="item.disabled"
                                                          :name="item.name" :divided="item.divided">
                                                <Icon :type="item.icon" style="color:#657180;" v-if="item.icon"></Icon>
                                                {{item.name}}
                                            </DropdownItem>
                                        </DropdownMenu>
                                    </Dropdown>
                                </i-col>
                            </Row>
                        </div>
                    </div>
                </Menu>

            </Header>
            <Layout>
                <Sider hide-trigger :style="{background: '#fff',height:clientHeight - 70 + 'px',position: 'fixed', left: 0, overflow: 'auto'}"
                       :width="config.page_main.menuWidth+'px'">
                    <Menu :theme="theme" :width="config.page_main.menuWidth+'px'"
                          :style="{ 'min-height' : clientHeight - 70 + 'px'}"
                          @on-select="menuClick" :active-name="activeMenuName"
                          :open-names="openMenuNames" accordion v-if="menus.length>0">
                        <template v-for="(menu, index) in menus">
                            <Submenu :name="'m'+menu.id" v-if="menu.parentId == null" :key="'sub'+index">
                                <template slot="title">
                                    <Icon :type="menu.themeJson.icon" size="15" :color="menu.themeJson.color"
                                          style="margin-right: 3px;"
                                          v-if="menu.themeJson"></Icon>
                                    <span class="layout-text">{{menu.name}}</span>
                                </template>
                                <template v-for="(child,index2) in menus">
                                    <Menu-item :name="'m'+child.id" :key="'child'+child.id+'m'+menu.id"
                                               v-if="child.parentId === menu.id&&child.resourceType === '菜单'">
                                        <Icon :type="child.themeJson.icon" :size="iconSize"
                                              v-if="child.themeJson"></Icon>
                                        <span class="layout-text">{{child.name}}</span>
                                    </Menu-item>
                                    <Submenu :name="'m'+child.id"
                                             v-if="child.parentId === menu.id&&child.resourceType === '菜单组'"
                                             :key="'subChild'+index2">
                                        <template slot="title">
                                            <Icon :type="child.themeJson.icon" :size="iconSize"
                                                  v-if="child.themeJson"></Icon>
                                            <span class="layout-text">{{child.name}}</span>
                                        </template>
                                        <template v-for="child2 in menus">
                                            <Menu-item :name="'m'+child2.id" :key="'child'+child2.id+'m'+menu.id"
                                                       v-if="child2.parentId === child.id&&child2.resourceType === '菜单'">
                                                <Icon :type="child2.themeJson.icon" :size="iconSize"
                                                      v-if="child2.themeJson"></Icon>
                                                <span class="layout-text">{{child2.name}}</span>
                                            </Menu-item>
                                        </template>
                                    </Submenu>
                                </template>
                            </Submenu>
                        </template>
                    </Menu>
                    <!--增加一个空白的div，防止菜单列表未加载完成时，页面没有占位，导致右侧内容左移-->
                    <div :style="{width: config.page_main.menuWidth+'px'}" v-else></div>
                </Sider>
                <Layout :style="{marginLeft:config.page_main.menuWidth+'px',padding: '0 24px 0 24px',width:'100%',maxWidth:rightWidth +'px'}">
                    <Breadcrumb :style="{margin: '12px 0'}">
                        <BreadcrumbItem v-for="(item, index) in breadItems" :key="'bread'+index">{{item.name}}
                        </BreadcrumbItem>
                    </Breadcrumb>
                    <Content
                        :style="{padding: '14px', minHeight: clientHeight - 175+ 'px', background: '#fff',width:'100%',maxWidth:'100% !important'}">
                        <transition name="fade"
                                    mode="out-in">
                            <router-view></router-view>
                        </transition>
                    </Content>
                </Layout>
            </Layout>

            <!--修改密码弹出层-->
            <div>
                <Modal v-model="passwordModel" title="修改密码" :mask-closable="false">
                    <Form ref="passwordForm" :model="pwdForm" :rules="pwdRule" :label-width="100">
                        <Form-item prop="originPwd" label="原密码">
                            <i-input type="password" v-model="pwdForm.originPwd" placeholder="请输入原密码">
                                <Icon type="md-lock" slot="prepend"></Icon>
                            </i-input>
                        </Form-item>
                        <Form-item label="新密码" prop="newPwd">
                            <i-input type="password" v-model="pwdForm.newPwd" placeholder="请输入新密码">
                                <Icon type="md-lock" slot="prepend"></Icon>
                            </i-input>
                        </Form-item>
                        <Form-item label="确认密码" prop="surePwd">
                            <i-input type="password" v-model="pwdForm.surePwd" placeholder="确认密码">
                                <Icon type="md-lock" slot="prepend"></Icon>
                            </i-input>
                        </Form-item>
                    </Form>
                    <div slot="footer">
                        <Button type="primary" @click="handlePasswordSubmit('passwordForm')">提交</Button>
                        <Button type="primary" @click="resetPasswordForm('passwordForm')" ghost>重置</Button>
                        <Button type="default" @click="passwordModel=false">取消</Button>
                    </div>
                </Modal>
            </div>
        </Layout>
    </div>
</template>

<script>
    import config from '../../config/Config';
    import util from 'core-util-is';
    import deepExtend from 'deep-extend'

    export default {
        data() {
            const validatePass = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入密码'));
                } else {
                    callback();
                }
            };
            const validatePassCheck = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请再次输入密码'));
                } else if (value !== this.pwdForm.newPwd) {
                    callback(new Error('两次输入密码不一致!'));
                } else {
                    callback();
                }
            };
            return {
                config: {},
                loginName: '', // 登录账号名称
                lastLoginTime: '', // 上次登录时间
                state: '',
                toggle: true,
                theme: 'light',
                activeMenuName: '',
                menus: [],
                breadItems: [], // 面包屑导航,
                openMenuNames: [],
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
            };
        },
        watch: {
            $route(to, from) {
                this.initBreadItems(to, this.menus);
            },
        },
        methods: {
            // 监听路由变化动态改变面包屑导航，暂时只支持两级菜单
            initBreadItems(to, array) {
                this.breadItems = [];
                array.forEach((n) => {
                    // 根据URL匹配，找到对应的菜单和父节点菜单ID
                    if (n.url === to.path) {
                        let parents = this.findParentMenu(n, array);
                        parents.push(n);
                        this.breadItems = parents;
                    }
                });
            },
            //查找父级菜单
            findParentMenu(child, array) {
                let parent = [];
                for (let m of array) {
                    if (child.parentId === m.id) {
                        if (m.parentId) {
                            let parent2 = this.findParentMenu(m, array);
                            if (parent2.length > 0) {
                                parent = parent2;
                            }
                        }
                        parent.push(m);
                    }
                }
                return parent;
            },
            init: function () {
                // 从后台读取当前用户的权限菜单
                this.$axios.get('/ueboot/shiro/private/menus').then((response) => {
                    let menus = [];
                    if (response && response.body) {
                        response.body.forEach((o) => {
                            //默认设置为0
                            if (!o.rank) {
                                o.rank = 0;
                            }
                        });
                        menus = response.body;
                        //倒序排
                        this.$utils.sort(menus, {field: 'rank', sort: 'desc'});
                    }
                    let matched = this.$route.matched;
                    matched.forEach((m) => {
                        if (m.parent) {
                            menus.forEach((n) => {
                                // 根据URL匹配，找到对应的菜单和父节点菜单ID
                                if (n.url === m.path) {
                                    this.initBreadItems(m, menus);
                                    this.activeMenuName = 'm' + n.id;
                                    if (n.parentId !== '') {
                                        //从面包屑导航数组当中获取需要打开的父级菜单
                                        this.breadItems.forEach((b) => {
                                            if (b.id !== n.id) {
                                                this.openMenuNames.push('m' + b.id);
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    });
                    this.menus = menus;

                });
            },
            menuClick(id) {
                this.menus.forEach((n) => {
                    // 根据URL匹配，找到对应的菜单和父节点菜单ID
                    if ('m' + n.id === id) {
                        this.$router.push(n.url);
                    }
                });
            },
            logout() {
                // 退出系统
                this.$Modal.confirm({
                    title: '系统提示',
                    content: '确定要退出当前系统吗',
                    onOk: () => {
                        this.$axios.post('/ueboot/shiro/private/logout', {}).then((data) => {
                            this.$Message.success('退出成功!');
                            this.$router.push(this.config.page_main.logoutSuccessRouter);
                        }, (response) => {
                            this.$log.d(response);
                            this.$Notice.error({
                                title: '系统异常',
                                desc: response.message ? response.message : '系统或网络异常'
                            });
                        });
                    }
                });
            },
            resetPwd(name) {
                // 修改密码
                this.resetPasswordForm('passwordForm');
                this.passwordModel = true;
            },

            resetPasswordForm(name) {
                this.$refs[name].resetFields();
            },
            handlePasswordSubmit(name) {
                this.$refs[name].validate((valid) => {
                    if (valid) {
                        let jsonData = {
                            oldPassword: this.pwdForm.originPwd,
                            newPassword: this.pwdForm.newPwd
                        };
                        this.$axios.post('/ueboot/shiro/private/updatePassword', jsonData).then((data) => {
                            // 关闭浮层
                            this.passwordModel = false;
                            this.$Modal.success({
                                title: '系统提示',
                                content: '修改成功,下次登录请用新密码登录, 谢谢!'
                            });
                        }, (response) => {
                            this.$log.d(response);
                            this.$Notice.error({
                                title: '系统异常',
                                desc: response.message ? response.message : '系统或网络异常'
                            });
                        });
                    }
                });
            },
            dropdownClick(name) {
                for (let item of this.config.page_main.dropdown.items) {
                    if (item.name === name && util.isFunction(item.onClick)) {
                        item.onClick(name);
                        break;
                    }
                }
            },
            //更新配置，供外部调用使用
            //this.$root.$children[0].$children[0].updateConfig({"sysTitle":"test"})
            updateConfig(config){
                this.config = deepExtend({},this.config,config)
            }
        },
        computed: {
            iconSize() {
                return this.toggle ? '14px' : '24px';
            },
            rightWidth() {
                return this.clientWidth - this.config.page_main.menuWidth;
            }
        },
        created() {
            this.config = config.getConfig();
            this.config.page_main.dropdown.items.forEach((t) => {
                if (t.name === '修改密码' && !util.isFunction(t.onClick)) {
                    t.onClick = this.resetPwd;
                } else if (t.name === '退出系统' && !util.isFunction(t.onClick)) {
                    t.onClick = this.logout;
                }
            });
        },
        mounted() {
            this.init();
            // 然后监听window的resize事件．在浏览器窗口变化时再设置下背景图高度．
            const that = this;
            window.onresize = function temp() {
                window.setTimeout(() => {
                    that.clientWidth = document.documentElement.clientWidth;
                }, 400);
            };
            let loginInfo = sessionStorage.getItem('ueboot_login_info') || '';
            if (loginInfo !== '') {
                let o = JSON.parse(loginInfo);
                this.loginName = o[this.config.page_main.userNameKey] || '';// 登录账号名称
            }

        }
    };
</script>

<style>
    *[v-cloak] {
        display: none;
    }

    /* 覆盖iview默认样式 */
    .ivu-layout-header {
        padding: 0;
        height: auto;
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
        height: 100%;
        background: #f5f7f9;
        position: relative;
        overflow: auto;
    }

    .layout-parent {
        display: flex;
        justify-content: space-between;
    }

    .layout-logo {
        display: flex;
        max-height: 60px;
        padding: 0 20px;
        border-radius: 3px;
        position: relative;
        vertical-align: middle;
    }

    .ivu-layout-header a {
        color: #fff !important;
    }

    .header-menu:hover {
        background-color: rgba(0, 0, 0, 0.05);
    }

    .header-img {
        border-style: none;
        display: inline-block !important;
        vertical-align: middle;
        visibility: visible;
        border: 0;
        width: 100%;
        height: 100%;
    }

    .header-span {
        color: rgb(184, 196, 201);
        font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", 微软雅黑, Arial, sans-serif;
        font-size: 20px;
        font-weight: bold;
        height: 100%;
        margin-left: 5px;
        text-align: left;
    }

    .layout-ceiling-main .header-menu {
        font-size: 14px;
        display: inline-block;
        text-align: center;
        width: 90px;
    }

</style>
