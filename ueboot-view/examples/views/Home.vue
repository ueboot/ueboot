<template>
  <div class='layout'>
    <nav :class="{'layout-hide-text': !menuToggle}">
      <div style='background-color: #3a3f51'>
        <a class='header-logo'>
          <img src='../assets/logo.png' alt='.' class='header-img'>
          <span class='header-span'>Vue</span>
        </a>
      </div>
      <Menu :theme='theme' :width='menuWidth' :style="{ 'min-height' : clientHeight - 52 + 'px'}" @on-select='menuClick' :active-name='activeMenuName'
            :open-names='openMenuNames'>
        <Submenu :name='route.name' v-for='(route,index) in routerConfig.routes'
                 :key='index'>
          <template slot='title'>
            <Icon :type='route.icon'></Icon>
            <span class='layout-text'>{{route.name}}</span>
          </template>
          <Menu-item :name='routeChild.name'
                     v-for='(routeChild, indexChild) in route.children'
                     :key='indexChild'>
            <Icon :type='routeChild.icon' :size='iconSize'></Icon>
            <span class='layout-text'>{{routeChild.name}}</span>
          </Menu-item>
        </Submenu>
      </Menu>
    </nav>
    <div style='width: 100%'>
      <Affix>
        <div class='layout-header'>
          <a href='javascript:' class='header-user'>
            <Icon type='ios-person-outline' class='header-userIcon'></Icon>
          </a>
          <div class='layout-ceiling' style='line-height: 50pxfont-size: 18px'>
            <div class='layout-ceiling-main'>
              <a href='#' class='header-menu'>
                <Dropdown>
                  <a href='javascript:void(0)'>
                    菜单
                    <span class='caret'></span>
                  </a>
                  <Dropdown-menu slot='list'>
                    <Dropdown-item>驴打滚</Dropdown-item>
                    <Dropdown-item>炸酱面</Dropdown-item>
                    <Dropdown-item>豆汁儿</Dropdown-item>
                    <Dropdown-item>冰糖葫芦</Dropdown-item>
                    <Dropdown-item>北京烤鸭</Dropdown-item>
                  </Dropdown-menu>
                </Dropdown>
              </a>
              <a href='#' class='header-menu'>
                <Dropdown>
                  <a href='javascript:void(0)'>
                    菜单
                    <span class='caret'></span>
                  </a>
                  <Dropdown-menu slot='list'>
                    <Dropdown-item>驴打滚</Dropdown-item>
                    <Dropdown-item>炸酱面</Dropdown-item>
                    <Dropdown-item>豆汁儿</Dropdown-item>
                    <Dropdown-item>冰糖葫芦</Dropdown-item>
                    <Dropdown-item>北京烤鸭</Dropdown-item>
                  </Dropdown-menu>
                </Dropdown>
              </a>
              <a href='#' class='header-menu'>
                <Dropdown>
                  <a href='javascript:void(0)'>
                    菜单
                    <span class='caret'></span>
                  </a>
                  <Dropdown-menu slot='list'>
                    <Dropdown-item>驴打滚</Dropdown-item>
                    <Dropdown-item>炸酱面</Dropdown-item>
                    <Dropdown-item>豆汁儿</Dropdown-item>
                    <Dropdown-item>冰糖葫芦</Dropdown-item>
                    <Dropdown-item>北京烤鸭</Dropdown-item>
                  </Dropdown-menu>
                </Dropdown>
              </a>
            </div>
          </div>
        </div>
      </Affix>
      <div class='layout-breadcrumb'>
        <Breadcrumb>
          <Breadcrumb-item href='#'>首页</Breadcrumb-item>
          <Breadcrumb-item href='#'>应用中心</Breadcrumb-item>
          <Breadcrumb-item>某应用</Breadcrumb-item>
        </Breadcrumb>
      </div>

      <div class='layout-content' :style="{ 'min-height' : clientHeight - 124+ 'px'}">
        <div class='layout-content-main'>

          <transition>
            <router-view></router-view>
          </transition>
        </div>
      </div>
      <div class='layout-copy'>
      </div>
    </div>
  </div>
</template>

<script>
import router from '../router/router'

export default {
  data () {
    return {
      state: '',
      menuToggle: true,
      theme: 'dark',
      routerConfig: router.options,
      menuWidth: '200px',
      activeMenuName: '',
      openMenuNames: [],
      clientHeight: document.documentElement.clientHeight
    }
  },
  methods: {
    menuClick (name) {
      this.$router.push({ name: name })
    },
    init () {
      let name = this.$route.name
      this.activeMenuName = name
      let matched = this.$route.matched
      matched.forEach((m) => {
        if (!m.parent) {
          this.openMenuNames.push(m.name)
        }
      })
    }
  },
  computed: {
    iconSize () {
      return this.menuToggle ? '14px' : '24px'
    }
  },
  created () {
    this.init()
  },
  mounted () {
  }
}
</script>

<style scoped>
  *[v-cloak]{
    display: none
  }
  .layout {
    background: #f5f7f9
    height: 100%
    display: flex
    flex-wrap: nowrap
  }

  .layout-breadcrumb {
    padding: 10px 15px 0
  }

  .layout-content {
    min-height: 200px
    margin: 15px 15px 0 15px
    overflow: hidden
    background: #fff
    border-radius: 4px
  }

  .layout-content-main {
    padding: 10px
  }

  .layout-copy {
    text-align: center
    padding: 5px 0 5px
    color: #9ea7b4
  }

  .layout-header {
    height: 50px
    background: #fff
    padding-right: 15px
    padding-left: 15px
    box-shadow: 0 2px 2px rgba(0, 0, 0, 0.05), 0 1px 0 rgba(0, 0, 0, 0.05)
  }

  .layout-logo-left {
    width: 90%
    height: 30px
    background: #5b6270
    border-radius: 3px
    margin: 15px auto
  }

  .layout-ceiling-main a {
    color: #9ba7b5
  }

  /*隐藏文字*/
  .layout-hide-text .layout-text {
    display: none
  }

  /*隐藏向下箭头*/
  .layout-hide-text .ivu-menu-vertical .ivu-menu-submenu-title-icon {
    display: none
  }

  .ivu-col {
    transition: width .2s ease-in-out
  }

  .header-logo {
    background: 0 0
    text-decoration: none
    outline: 0
    cursor: pointer
    transition: color .2s ease
    display: inline-block
    float: none !important
    height: auto
    padding: 0 20px
    font-size: 20px
    font-weight: 700
    line-height: 50px
    text-align: center
    color: #eaebed !important
  }

  .header-img {
    border-style: none
    display: inline-block !important
    max-height: 20px
    margin-top: -4px
    vertical-align: middle
    visibility: visible
    border-style: none
    border: 0
    color: #eaebed !important
  }

  .header-span {
    box-sizing: border-box
    color: rgb(88, 102, 110)
    cursor: pointer
    display: inline
    font-family: 'Helvetica Neue', Helvetica, 'PingFang SC', 'Hiragino Sans GB', 'Microsoft YaHei', 微软雅黑, Arial, sans-serif
    font-size: 20px
    font-weight: bold
    height: auto
    line-height: 50px
    margin-left: 5px
    text-align: center
  }

  .layout-ceiling-main .header-menu {
    font-size: 14px
    display: inline-block
    text-align: center
    width: 67px
  }

  .caret {
    display: inline-block
    width: 0
    height: 0
    margin-left: 2px
    vertical-align: middle
    border-top: 4px dashed
    border-right: 4px solid transparent
    border-left: 4px solid transparent
  }

  .layout-ceiling-main a {
    color: rgb(88, 102, 110) !important
  }

  .header-menu:hover {
    background-color: rgba(0, 0, 0, 0.05)
  }

  .header-user {
    display: inline-block
    font-size: 14px
    text-align: center
    white-space: nowrap
    vertical-align: middle
    touch-action: manipulation
    cursor: pointer
    border: 1px solid transparent
    font-weight: 500
    border-radius: 2px
    outline: 0 !important
    margin-top: 8px
    margin-bottom: 8px
    float: right
    margin-right: 10px
  }

  .header-userIcon {
    color: #333
    font-size: 30px
  }

  .ivu-dropdown-item {
    line-height: 20px
  }
</style>
