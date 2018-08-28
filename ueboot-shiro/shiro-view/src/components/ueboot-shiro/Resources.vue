<template>
  <div>
    <Row>
      <i-col :span="6">
        <u-tree :tree="tree" @itemClick="itemClick"></u-tree>
        <Spin  v-if="tree.length === 0">
          <Icon type="ios-loading" size=18 class="demo-spin-icon-load"></Icon>
          <div>加载中</div>
        </Spin>
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
    data() {
      return {
        tree: [],
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
              {
                label: '资源类型',
                type: 'select',
                name: 'resourceType',
                required: true,
                data: [{'name': '菜单组', 'value': '菜单组'}, {'name': '菜单', 'value': '菜单'},
                  {'name': '功能', 'value': '功能'}
                ],
                onChange: (value) => {
                  this.changeResourceType(value)
                }
              },
              {label: '资源名称', type: 'text', name: 'name', required: true},
              {label: '父级资源', type: 'treeSelect', name: 'parentId', required: true, tree: this.tree},
              {
                label: '菜单icon名称', type: 'text', placeholder: '系统指定的icon名称', name: 'iconName',
                fieldFormat: (value, row) => {
                  let themeJson = row["themeJson"] || null
                  if (themeJson !== null) {
                    return JSON.parse(themeJson)["iconName"]
                  } else {
                    return ""
                  }
                }
              },
              {
                label: '菜单字体颜色', type: 'text', placeholder: '任意颜色值如:#FFFFFF', name: 'fontColor',
                fieldFormat: (value, row) => {
                  let themeJson = row["themeJson"] || null
                  if (themeJson !== null) {
                    return JSON.parse(themeJson)["fontColor"]
                  } else {
                    return ""
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
                data: [{'name': '启用', 'value': 'true'}, {'name': '不启用', 'value': 'false'}
                ],
                init: 'true'
              }
            ],
            //表单提交之前（校验之后），返回如果为false,则不继续执行后续操作
            //data为需要提交表单前的属性
            submitBefore: function (data) {
              //重新拼装一下请求对象格式
              data["theme"] = {"iconName": data["iconName"], "fontColor": data["fontColor"]}
              return true;
            },
          },
          table: {
            operation: {
              primaryKey: 'id'
            },
            columns: [
              {title: 'id', key: 'id'},
              {title: '资源名称', key: 'name',width:100},
              {title: '资源类型', key: 'resourceType',width:100},
              {title: '资源路径', key: 'url',width:150},
              {title: 'UI渲染', key: 'themeJson',width:260},
              {title: '权限标识符', key: 'permission',width:120},
              {title: '父级资源', key: 'parentName',width:120},
              {title: '排序', key: 'rank',width:80},
              {title: '是否启用', key: 'available',width:120,format:(value,row)=>{
                  return value?'是':'否'
                }}
            ]
          }
        }
      }
    },
    created(){
      this.fetchTreeData()
    },
    methods: {
      //获取树结构数据
      fetchTreeData(){
        this.$axios.post("/ueboot/resources/list",{}).then((response)=>{
          //默认给出一个根节点数据，后台插入的时候不校验是否存在
          let tree = [{id: 0, 'name': '根节点', parentId: null}]
          this.tree = response.body
          //合并数组
          this.formTree = [...tree,...response.body]
          //改变form表单中对应字段的tree值，这个需要代码主动修改一次
          this.$set(this.formGrid.form.columns[2],"tree",this.formTree)
        })
      },
      changeResourceType(value) {
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
        }else if(value === '功能'){
          // 隐藏部分字段
          this.formGrid.form.columns.forEach((c) => {
            if (['url','iconName','fontColor','rank'].includes(c.name)) {
              c.add = {show: false}
              c.edit = {show: false}
              c.view = {show: false}
              c.required = false
            }
          })
        } else {
          this.formGrid.form.columns.forEach((c) => {
            if (['url', 'permission','iconName','fontColor','rank'].includes(c.name)) {
              c.add = {show: true}
              c.edit = {show: true}
              c.view = {show: true}
              c.required = true
              if(c.n === 'fontColor'){
                c.required = false
              }
            }
          })
        }
      },
      itemClick() {

      }
    }
  }
</script>
