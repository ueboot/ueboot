<template>
  <u-form-grid :data='formGrid' ref='child'></u-form-grid>
</template>
<script>
export default {
  data () {
    return {
      formGrid: {
        options: {
          url: {
            page: '/admin/api/knowledge/page', // 记录查询地址，必须使用分页查询并返回分页结果
            save: '/admin/api/knowledge/save', // 记录添加和修改提交的地址，如果提交的对象有id则为修改
            delete: '/admin/api/knowledge/delete', // 记录删除的提交地址，通过提交id的集合来实现一个或者多个记录
            get: '/admin/api/knowledge/find'// 查看和编辑时用于查询对象的接口，参数为定义的keys
          }
        },
        tips: { 'title': '功能提示', 'content': '展示当前系统知识库相关信息，可以对相数据进行相关操作' },
        // 工具栏，每个里面可以有一个button对象，属性为iView Button属性，外加click事件
        toolbar: {
          /* create: {
             show: false, label: '添加'
             }, */
          button: [
            {
              theme: 'success',
              label: '新增知识库',
              icon: 'plus',
              click: () => {
                // 显示新增model
                this.editModel = true
                this.formData.selectValue = []
                this.formData.content = ''
              }
            }
          ],
          /*
             this.$router.push({name: '提问列表',params:{id:"",type:""}})
             buttons: {
             theme: 'primary', label: '更多操作', icon: 'plus', items: [
             {
             label: '导出', icon: 'plus', click: (data) => {
             console.log('点击事件')
             }
             },
             {
             label: '定制化按钮', icon: 'plus', click: (data) => {
             console.log('点击事件')
             }
             },
             ],
             }, */
          // 关键字搜索框
          filter: {
            show: true,
            name: 'keyWord',
            placeholder: '根据关键字搜索'
          },
          superFilter: {
            label: '高级搜索',
            columns: [
              { type: 'text', label: '账号', name: 'userName', icon: 'plus', placeholder: '请填写账号' },
              { type: 'text', label: '姓名', name: 'realName', icon: 'plus', placeholder: '请填写姓名' },
              { type: 'date', label: '日期', name: 'date', placeholder: '选择日期', option: {}, format: '' },
              {
                type: 'datetime',
                label: '时间',
                name: 'datetime',
                placeholder: '选择时间',
                option: {},
                format: ''
              }
            ]
          }
        },
        form: {
          labelWidth: 100,
          colNumber: 1,
          modal: {
            title: '编辑知识库'
          },
          columns: [

            {
              'label': '类型名称',
              'type': 'cascader',
              'name': 'typeName',
              data: 'url:/admin/api/knowledgeType/find',
              required: true,
              onChange: (value, data) => {
                this.$refs.child.$emit('setFormFieldData', 'typeName', value.join('/'))
              }
            },
            { 'label': '内容', 'type': 'textarea', 'name': 'content', maxlength: 1800, required: true, rows: 8 }
          ]
        },

        table: {
          operation: {
            primaryKey: ['id'], // 调用查看、编辑、删除接口时，需要传递的参数主键名称，可以有多个。默认为id
            remote: false, // 查看、编辑功能是否通过远程查询方式获取。默认为false直接用当前行数据展示
            buttons: [{
              show: true,
              'label': '查看列表',
              'theme': 'info',
              click: (_this, row, index) => {
                // 跳转到列表页面
                _this.$router.push({ name: 'knowledge_question', params: { id: row.id } })
              }
            }, {
              key: 'edit',
              show: true,
              'label': '编辑',
              'theme': 'primary',
              click: (_this, row, index) => {
                _this.optEditClick(row, index)
              }
            }, {
              key: 'delete',
              show: true,
              'label': '删除',
              'theme': 'error',
              click: (_this, row, index) => {
                _this.optDeleteClick(row, index)
              }
            }],
            column: {
              title: '操作',
              key: 'action',
              width: 200

            }
          },
          columns: [
            {
              title: '类型',
              key: 'typeName'

            },
            {
              title: '内容',
              key: 'content',
              renderType: 'tooltip',
              format: (value, row) => {
                return 'a:' + value
              }
            },
            {
              title: '是否索引',
              key: 'esIndex'

            },

            {
              title: '创建时间',
              key: 'firstInsert'
            },
            {
              title: '修改时间',
              key: 'lastModified'
            }

          ]
        }
      }
    }
  }
}
</script>
