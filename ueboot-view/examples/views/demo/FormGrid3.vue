<template>
  <u-form-grid :data="formGrid" tableRef="exportTable"></u-form-grid>
</template>
<script>
  export default {
    data () {
      return {
        formGrid: {
          exportParams: ["startDate", "endDate"],
          options: {
            autoLoad: false,
            url: {
              page: "/admin/api/question/unMatch/page",//记录查询地址，必须使用分页查询并返回分页结果
            }
          },
          tips: {"title": "功能提示", "content": "展示当前系统客服分组列表，可以对相数据进行相关操作"},
          //工具栏，每个里面可以有一个button对象，属性为iView Button属性，外加click事件
          toolbar: {
            groups: {
              show: true,
              export: {
                currentShow: true,
                currentLabel: "当前页数据导出",
                show: true,
                label: "所有数据导出",
                fileName: "exportTable"
              },
              import: {
                show: true,
                label: '客户数据导入',
                modelTitle: '客户数据批量导入',
                template: true,
                size: 1024*50,//单位kb
                fileType: ['xls', 'xlsx'],
                action: "/console/admin/api/knowledge/import",
                params: {param1: "param1"}
              }
            },
            //关键字搜索框
            filter: {
              show: false,
              name: "keyWord",
              placeholder: "根据关键字搜索"
            },
            superFilter: {
              show: true,
              label: "高级搜索",
              columns: [
                {type: "date", label: "开始时间", name: "startDate", icon: "calendar", placeholder: "请选择开始时间"},
                {type: "date", label: "结束时间", name: "endDate", icon: "calendar", placeholder: "请选择结束时间"}
              ]
            }
          },
          form: {
            labelWidth: 100,
            colNumber: 1,
            modal: {
              title: "编辑知识库"
            },
            columns: [
              {"label": "分组名称", "type": "text", "name": "groupName", maxlength: 20, required: true},
            ]
          },
          table: {
            showCheckbox: false,
            operation: {
              show: false,
              remote: false,//查看、编辑功能是否通过远程查询方式获取。默认为false直接用当前行数据展示
              buttons: [],
              column: {}
            },
            columns: [
              {
                title: '询问问题内容',
                key: 'content',
              },

              {
                title: '询问次数得分',
                key: 'score',
              },
              {
                title: '询问问题日期',
                key: 'consultDate',
              },

            ]
          }
        }
      }
    },
    methods: {},
    mounted: function () {

    }
  }
</script>
