<template>
  <div>
    <u-form-grid :data="formGrid" ref="child"></u-form-grid>
  </div>
</template>

<script type="application/javascript">
  import Vue from "vue";
  export default {
    data () {
      return {
        formGrid: {
          options: {
            url: {
              /*query: "http://wodewj.oss-cn-shanghai.aliyuncs.com/dev/data.json"*/
              page: "/api/cors/chat/session/findMessage"
            }
          },
          tips: {"title": "功能提示", "content": "FormGrid是一个结合表单查询、修改、删除等全功能的组件。方便快速实现CRUD功能!"},
          toolbar: {
            superFilter: {
              label: "高级搜索",
              columns: [
                {type: "text", label: "姓名", name: "name", icon: "plus", placeholder: "请填写姓名"},
                {
                  "label": "消息",
                  "type": "select",
                  "name": "message",
                  data: "im:性别",
                  required: true,
                  init: {"value": "男"}
                },
                {type: "text", label: "电话", name: "mobile", icon: "plus", placeholder: "请填写电话"},
                {type: "date", label: "日期", name: "date", placeholder: "选择日期", option: {}, format: ""},
                {
                  type: "daterange",
                  label: "日期范围",
                  name: "daterange",
                  placeholder: "选择日期范围",
                  option: {},
                  format: ""
                },
                {
                  type: "datetime",
                  label: "时间",
                  name: "datetime",
                  placeholder: "选择时间",
                  option: {},
                  format: ""
                },
                {
                  type: "datetimerange",
                  label: "时间范围",
                  name: "datetimerange",
                  placeholder: "选择时间范围",
                  option: {},
                  format: ""
                },

                {
                  type: "cascader",
                  label: "景点",
                  name: "location",
                  data: [{
                    value: 'beijing',
                    label: '北京',
                    children: [{value: 'gugong', label: '故宫'}, {value: 'tiantan', label: '天坛'}, {
                      value: 'wangfujing',
                      label: '王府井'
                    }]
                  }]
                },
                {
                  type: "select",
                  name: "province",
                  label: "创建",
                  clearable: true,
                  filterable: false,
                  multiple: false,
                  data: [{name: "哈哈", value: "haha"}, {name: "呵呵", value: "hehe"}]
                },
              ],
            },
            button: [
              {
                theme: "success", label: "自定义按钮1", icon: "plus", click:  (selections) =>{
                console.log("自定义按钮1,%o", selections)
                this.reload();

              }
              },
              {
                theme: "success", label: "自定义按钮2", icon: "plus", click: function (selections) {
                console.log("自定义按钮2,%o", selections)
              }
              }
            ],
            buttons: {
              theme: "primary", label: "更多操作", icon: "plus", items: [
                {
                  label: "导出d", icon: "plus", click: function (selections) {
                  this.defaultExport("exportTable","导出数据");
                }
                },
                {
                  label: "定制化按钮", icon: "plus", click: function (selections) {
                  console.log("定制化按钮,%o", selections)
                }
                },
              ]
            }
          },
          form: {
            modal: {
              title: "个人信息"
            },
            columns: [
              {
                "label": "渠道", "type": "text", "name": "channel", rule: {}, prepend: "http://", append: ".com",
                add: {
                  show: false
                },
                edit: {
                  show: true, disabled: true
                },
                view: {
                  show: false
                }
              },
              {"label": "用户名", "type": "text", "name": "createTime", required: true},
              /* {"label": "消息", "type": "select", "name": "message",data:"im:性别", required: true,init:"男"},*/
              {"label": "消息", "type": "text", "name": "message"},
              {"label": "消息", "type": "textarea", "name": "message1","rows":3},
              {"label": "发送方", "type": "text", "name": "sender", required: true},
              {"label": "toUser", "type": "text", "name": "toUser", disabled: true},
              {"label": "最大接待量", "type": "number", "min": 1, "max": 10, "name": "sessionId", init: 3,editFormat:(value)=>{

              }},
              {"label": "类型名称", "type": "cascader", "name": "typeName", data:'url:/admin/api/knowledgeType/find',onChange:(value,data)=>{
                  //console.log("####onchange,%o",value);
                this.$refs.child.$emit('setFormFieldData',"typeName",value.join("/"));
              },required:true},


            ]
          },

          table: {
            operation: {
              primaryKey: "createTime",
              buttons: [{
                show: true, "label": "查看", "theme": "text", click: (_this, row, index) => {
                  _this.optViewClick(row, index);
                }
              }, {
                show: true, "label": "编辑", "theme": "primary", click: (_this, row, index) => {
                  _this.optEditClick(row, index);
                }
              }, {
                show: true, "label": "删除", "theme": "error", click: (_this, row, index) => {
                  _this.optDeleteClick(row, index);
                }
              }],
            },
            columns: [
              {
                title: '渠道',
                key: 'channel',
                fixed: 'left',
              },
              {
                title: '会话时间',
                key: 'createTime',

              },
              {
                title: '用户名',
                key: 'fromUserName',

              },
              {
                title: '消息',
                key: 'message',

              },
              {
                title: '发送方',
                key: 'sender',
              },
              {
                title: 'toUser',
                key: 'toUser',
              },
            ]
          }
        }
      }
    },
    methods:{
        reload(){
            this.$refs.child.$emit('reloadData');
            console.log("reload");
        }
    }

  }
</script>
