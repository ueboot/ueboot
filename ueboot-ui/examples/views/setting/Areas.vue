<template>
  <u-form-grid :data="formGrid"></u-form-grid>
</template>

<script>
export default {
  name: 'Area',
  data () {
    return {
      formGrid: {
        options: {
          version: 'v2',
          url: {
            page: "/admin/areas/page",
            save: "/admin/areas/save",//记录添加和修改提交的地址，如果提交的对象有id则为修改
            delete: "/admin/areas/delete",//记录删除的提交地址，通过提交id的集合来实现一个或者多个记录
            get: "/admin/areas/find",//查看和编辑时用于查询对象的接口，参数为定义的keys
          }
        },
        tips: {title: "区域管理", content: "FormGrid是一个结合表单查询、修改、删除等全功能的组件。方便快速实现CRUD功能!"},

        form: {
          modal: {
            title: "区域信息"
          },
          columns: [
            {label: "地区名称", type: "text", name: "name", required: true},
            {label: "父级", type: "cascader", name: "parentIds", changeOnSelect:true, required: false, data: "url:/admin/areas/find", edit: { show: false },},
            {label: "全路径名称", type: "text", name: "fullPathName", required: false, add: {show: false}, edit: { disabled: true },},
            {label: "排序", type: "text", name: "rank", required: true},
          ]
        },
        table: {
          operation: {
            //调用查看、编辑、删除接口时，需要传递的参数主键名称，可以有多个。默认为id
            primaryKey: "id",
          },
          columns: [
            { title: '地区名称', key: 'name', width: 500},
            { title: '全路径名称', key: 'fullPathName', width: 500 },
            { title: '排序', key: 'rank', width: 500 },
          ]
        }
      }
    }
  },
  methods: {

  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
