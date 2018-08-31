<template>
  <div>
    <Row>
      <i-col :span="6">
        <Row>
          <Button icon="md-refresh" type="primary" @click="fetchTreeData">刷新</Button>
        </Row>
        <Row style="margin-top:20px">
          <u-tree :tree="tree" @item-click="itemClick"></u-tree>
          <Spin  v-if="loadingTree">
            <Icon type="ios-loading" size=18 class="demo-spin-icon-load"></Icon>
            <div>加载中</div>
          </Spin>
        </Row>
      </i-col>
      <i-col :span="18">
        <u-form-grid :data="formGrid" ref="formGrid"></u-form-grid>
      </i-col>
    </Row>
  </div>
</template>

<script>
import deepExtend from 'deep-extend'
export default {
  name: 'Resources',
  data () {
    return {
      loadingTree: false,
      tree: [],
      formGrid: {
        options: {
          url: {
            page: '/ueboot/resources/page',
            save: '/ueboot/resources/save',
            delete: '/ueboot/resources/delete'
          }
        },
        toolbar: {
          superFilter: {
            columns: [ {type: 'hidden', name: 'parentId', init: null} ]
          }
        },
        form: {
          labelWidth: 100,
          modal: {
            title: '资源管理'
          },
          columns: [
            {
              label: '资源类型',
              type: 'select',
              name: 'resourceType',
              required: true,
              data: [{'name': '菜单组', 'value': '菜单组'}, {'name': '菜单', 'value': '菜单'},
                {'name': '功能', 'value': '功能'}
              ],
              onChange: (value) => {
                console.log(value)
                this.changeResourceType(value)
              }
            },
            {label: '资源名称', type: 'text', name: 'name', required: true},
            {label: '父级资源', type: 'treeSelect', name: 'parentId', required: true, tree: []},
            {
              label: '菜单icon名称',
              type: 'text',
              placeholder: '系统指定的icon名称',
              name: 'iconName',
              fieldFormat: (value, row) => {
                let themeJson = row['themeJson'] || null
                if (themeJson !== null) {
                  return JSON.parse(themeJson)['iconName']
                } else {
                  return ''
                }
              }
            },
            {
              label: '菜单字体颜色',
              type: 'text',
              placeholder: '任意颜色值如:#FFFFFF',
              name: 'fontColor',
              fieldFormat: (value, row) => {
                let themeJson = row['themeJson'] || null
                if (themeJson !== null) {
                  return JSON.parse(themeJson)['fontColor']
                } else {
                  return ''
                }
              }
            },
            {label: '资源路径', type: 'text', name: 'url', required: true},
            {label: '权限标识符', type: 'text', name: 'permission', required: true},
            {label: '排序', type: 'number', placeholder: '数值越大，排序越靠前', name: 'rank'},
            {
              label: '是否启用',
              type: 'select',
              name: 'available',
              data: [{'name': '启用', 'value': 'true'}, {'name': '不启用', 'value': 'false'}],
              init: 'true'
            }
          ],
          // 表单提交之前（校验之后），返回如果为false,则不继续执行后续操作
          // data为需要提交表单前的属性
          submitBefore: function (data) {
            // 重新拼装一下请求对象格式
            data['theme'] = {'iconName': data['iconName'], 'fontColor': data['fontColor']}
            return true
          },
          submitAfter: () => {
            // 重新加载一次树结构
            this.fetchTreeData()
          }
        },
        table: {
          size: 'small',
          operation: {
            primaryKey: 'id'
          },
          columns: [
            {title: 'id', key: 'id', minWidth: 60},
            {title: '资源名称', key: 'name', minWidth: 100},
            {title: '资源类型', key: 'resourceType', minWidth: 100},
            {title: '资源路径', key: 'url', minWidth: 150},
            {title: '菜单icon名称',
              key: 'iconName',
              minWidth: 100,
              fieldFormat: (value, row) => {
                let themeJson = row['themeJson'] || null
                if (themeJson !== null) {
                  return JSON.parse(themeJson)['iconName']
                } else {
                  return ''
                }
              }},
            {title: '菜单字体颜色',
              key: 'fontColor',
              minWidth: 100,
              fieldFormat: (value, row) => {
                let themeJson = row['themeJson'] || null
                if (themeJson !== null) {
                  return JSON.parse(themeJson)['fontColor']
                } else {
                  return ''
                }
              }},
            {title: '权限标识符', key: 'permission', minWidth: 140},
            {title: '父级资源', key: 'parentName', minWidth: 100},
            {title: '排序', key: 'rank', minWidth: 80, init: 1},
            {title: '是否启用',
              key: 'available',
              align: 'center',
              minWidth: 120,
              fieldFormat: (value, row) => {
                if (value) {
                  return '是'
                } else {
                  return {value: '否', cellClassName: 'table-cell-red'}
                }
              }}
          ]
        }
      }
    }
  },
  created () {
    this.fetchTreeData()
  },
  methods: {
    // 获取树结构数据
    fetchTreeData () {
      this.loadingTree = true
      this.$axios.post('/ueboot/resources/list', {}).then((response) => {
        this.loadingTree = false
        // 默认给出一个根节点数据，后台插入的时候不校验是否存在
        let tree = [{id: 0, 'name': '根节点', parentId: null}]
        response.body.forEach((t) => {
          if (t.resourceType === '功能') {
            t.icon = 'fa fa-file'
          }
        })
        this.tree = [...response.body]
        // 合并数组
        let formTree = [...tree, ...response.body]
        // 为防止tree点击后影响整个tree数据，导致formGrid发生监听变化
        let newTree = []
        formTree.forEach((t) => {
          let n = deepExtend({}, t)
          newTree.push(n)
        })
        // 改变form表单中对应字段的tree值，这个需要代码主动修改一次
        this.$set(this.formGrid.form.columns[2], 'tree', newTree)
      })
    },
    changeResourceType (value) {
      if (value === '菜单组') {
        // 隐藏部分字段
        this.formGrid.form.columns.forEach((c) => {
          if (['url', 'permission'].includes(c.name)) {
            c.add = {show: false}
            c.edit = {show: false}
            c.view = {show: false}
            c.required = false
          }
        })
      } else if (value === '功能') {
        // 隐藏部分字段
        this.formGrid.form.columns.forEach((c) => {
          if (['url', 'iconName', 'fontColor', 'rank'].includes(c.name)) {
            c.add = {show: false}
            c.edit = {show: false}
            c.view = {show: false}
            c.required = false
          }
        })
      } else {
        this.formGrid.form.columns.forEach((c) => {
          if (['url', 'permission', 'iconName', 'fontColor', 'rank'].includes(c.name)) {
            c.add = {show: true}
            c.edit = {show: true}
            c.view = {show: true}
            c.required = true
          }
          if (['fontColor', 'iconName'].includes(c.name)) {
            c.required = false
          }
        })
      }
    },
    itemClick (oriNode, oriItem, e) {
      // 查询当前节点下的子节点，更改grid查询条件.和弹出表单当中的下拉框
      this.$set(this.formGrid.toolbar.superFilter.columns[0], 'init', oriItem.id)
      this.$set(this.formGrid.form.columns[2], 'init', oriItem.id)

      this.$refs.formGrid.$emit('reloadData')
    }
  }
}
</script>
