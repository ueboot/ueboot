<template>
  <u-form-grid :data='formGrid'></u-form-grid>
</template>

<script>
export default {
  name: 'Customers',
  data () {
    return {
      formGrid: {
        options: {
          version: 'v2',
          url: {
            page: '/admin/customers/page',
            save: '/admin/customers/save', // 记录添加和修改提交的地址，如果提交的对象有id则为修改
            delete: '/admin/customers/delete', // 记录删除的提交地址，通过提交id的集合来实现一个或者多个记录
            get: '/admin/customers/find'// 查看和编辑时用于查询对象的接口，参数为定义的keys
          }
        },
        tips: { 'title': '客户管理', 'content': 'FormGrid是一个结合表单查询、修改、删除等全功能的组件。方便快速实现CRUD功能!' },

        form: {
          modal: {
            title: '个人信息'
          },
          columns: [
            { label: '用户昵称', type: 'text', name: 'nickname', required: false },
            { label: '真实姓名', type: 'text', name: 'name', required: false },
            { label: '微信OPENID', type: 'text', name: 'openId', required: false },
            { label: '手机号', type: 'text', name: 'mobile', required: false },
            { label: '性别', type: 'select', name: 'gender', required: false },
            { label: '所属区域', type: 'text', name: 'area', required: false },
            { label: '头像', type: 'text', name: 'headerImgUrl', required: false }
            //              {label: '会员等级', type: 'text', name: 'memberRank', required: false},
          ]
        },
        toolbar: {
          delete: { show: false }
        },
        table: {
          operation: {
            // 调用查看、编辑、删除接口时，需要传递的参数主键名称，可以有多个。默认为id
            primaryKey: 'id',
            buttons: [
              { key: 'view',
                show: true,
                label: '查看',
                theme: 'text',
                click: (row, index, _this) => {
                  _this.optViewClick(row, index)
                } },
              { key: 'edit',
                show: true,
                label: '编辑',
                theme: 'primary',
                click: (row, index, _this) => {
                  _this.optEditClick(row, index)
                } }
            ]
          },
          columns: [
            { title: '用户昵称', key: 'nickname' },
            { title: '真实姓名', key: 'name' },
            { title: '微信OPENID', key: 'openId' },
            { title: '手机号', key: 'mobile' },
            { title: '性别', key: 'gender' },
            { title: '所属区域', key: 'area' },
            { title: '头像', key: 'headerImgUrl' }
          ]
        }
      }
    }
  },
  methods: {}
}
</script>

<!-- Add 'scoped' attribute to limit CSS to this component only -->
<style scoped>

</style>
