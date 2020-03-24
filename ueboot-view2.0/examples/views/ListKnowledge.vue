<template>
  <div>
    <u-form-grid :data='formGrid' ref='child'></u-form-grid>
  </div>
</template>
<script>
export default {

  data () {
    return {
      operation: 'add',
      formData: {
        content: '',
        selectValue: [],
        id: ''
      },
      ruleValidate: {
        content: [
          { required: true, message: '请输入个人介绍', trigger: 'blur' }
        ]
      },
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
              { type: 'text', label: '账号', name: 'userName', icon: 'plus', placeholder: '请填写账号', init: '你好' },
              { type: 'text', label: '姓名', name: 'realName', icon: 'plus', placeholder: '请填写姓名' },
              { type: 'hidden', label: '姓名', name: 'realName2', icon: 'plus', placeholder: '请填写姓名', init: '你好22' }

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

            { 'label': '类型名称',
              'type': 'cascader',
              'name': 'typeName',
              data: 'url:/admin/api/knowledgeType/find',
              required: true,
              onChange: () => {
              // this.$refs.child.$emit('setFormFieldData','typeName',value.join('/'))
              },
              fieldFormat: function (value) {
              // row['typeName_']=[value]
                return value.split('/')
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
              click: (_this, row) => {
                // 跳转到列表页面
                if (row.typeId === null || row.typeId === undefined) {
                  this.$Message.error({
                    content: '请先为当前知识库添加类型',
                    duration: 2,
                    closable: true
                  })
                  return
                }
                this.$router.push({ name: 'knowledge_questionList', query: { id: row.id, typeId: row.typeId } })
              }
            }, {
              key: 'edit',
              show: true,
              'label': '编辑',
              'theme': 'primary',
              click: (row, index, _this) => {
                _this.optEditClick(row, index)
              }
            }, {
              key: 'delete',
              show: true,
              'label': '删除',
              'theme': 'error',
              click: (row, index, _this) => {
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
              key: 'typeName',
              renderType: 'a',
              click: function (row) {
                alert('haha')
              }
            },
            {
              title: '内容',
              key: 'content',
              width: '25%',
              renderType: 'tooltip'
              /* render:(h,params)=>{
                  let value =  params['row']['content']
                  let tmp = value.split("")
                  let i= 0
                  let str = []
                  let tmpStr = []
                  for(let t of tmp){
                    tmpStr.push(t)
                    if(i>15){
                      i = 0
                      console.log(tmpStr.join(""))
                      str.push(h('p',tmpStr.join("")))
                      tmpStr = []
                    }
                    i++
                  }

                  str.push(h('p',tmpStr.join("")))
                  console.log(str)
                  return h('Tooltip',[h('div',{
                    style:{
                      overflow:'hidden',
                      textOverflow:'ellipsis',
                      whiteSpace:'nowrap'
                    }
                  },value),h('p',{slot: 'content',},str)])
                } */
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
      },
      sexSelect: []// 性别下拉
    }
  },
  methods: {
  },
  mounted: function () {

  }
}
</script>
