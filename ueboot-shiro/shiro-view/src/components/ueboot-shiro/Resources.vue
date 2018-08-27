<template>
    <div>
      <Row>
        <i-col :span="6">
          <u-tree :tree="tree" @itemClick="itemClick"></u-tree>
        </i-col>
        <i-col :span="18">
          <u-form-grid :data="formGrid" ref="formGrid"></u-form-grid>
        </i-col>
      </Row>
    </div>
</template>

<script>
export default {
  name: 'Resources',
  data () {
    return {
      tree: [{id: 1, 'name': '根节点', parentId: null}],
      formGrid: {
        options: {
          url: {
            page: '/ueboot/resources/page',
            save: '/ueboot/resources/save',
            delete: '/ueboot/resources/delete'
          }
        },
        form: {
          labelWidth: 100,
          modal: {
            title: '资源管理'
          },
          columns: [
            {label: '资源类型',
              type: 'select',
              name: 'resourceType',
              required: true,
              data: [{'name': '菜单组', 'value': '菜单组'}, {'name': '菜单', 'value': '菜单'},
                {'name': '功能', 'value': '功能'}
              ],
              onChange: (value) => {
                this.changeResourceType(value)
              }},
            {label: '资源名称', type: 'text', name: 'name', required: true},
            {label: '父级资源', type: 'text', name: 'parentCode', required: true},
            {label: '菜单icon名称', type: 'text', placeholder: '系统指定的icon名称iebook', name: 'iconName'},
            {label: '菜单字体颜色', type: 'text', placeholder: '任意颜色值如:#FFFFFF', name: 'fontColor'},
            {label: '资源路径', type: 'text', name: 'url', required: true},
            {label: '权限标识符', type: 'text', name: 'permission', required: true},
            {label: '排序', type: 'number', placeholder: '数值越大，排序越靠前', name: 'rank'},
            {label: '是否启用',
              type: 'select',
              name: 'available',
              data: [{'name': '启用', 'value': 'true'}, {'name': '不启用', 'value': 'false'}
              ],
              init: 'true'}
          ]
        },
        table: {
          operation: {
            primaryKey: 'id'
          },
          columns: [
            {title: 'id', key: 'id'},
            {title: '资源名称', key: 'name'},
            {title: '资源类型', key: 'resourceType'},
            {title: '资源路径', key: 'url'},
            {title: 'UI渲染', key: 'themeJson'},
            {title: '权限标识符', key: 'permission'},
            {title: '父级资源', key: 'parentName'},
            {title: '排序', key: 'rank'},
            {title: '是否启用', key: 'available'}
          ]
        }
      }
    }
  },

  methods: {
    changeResourceType (value) {
      if (value === '菜单组') {
        // 隐藏部分字段
        this.formGrid.form.columns.forEach((c) => {
          if (['url', 'permission'].includes(c.name)) {
            c.add = {show: false}
            c.required = false
          }
        })
      } else {
        this.formGrid.form.columns.forEach((c) => {
          if (['url', 'permission'].includes(c.name)) {
            c.add = {show: true}
            c.required = true
          }
        })
      }
    },
    itemClick () {

    }
  }
}
</script>
