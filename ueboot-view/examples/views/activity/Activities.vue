<template>
  <u-form-grid :data="formGrid"></u-form-grid>
</template>

<script>
  import values from '../../utils/values'

  export default {
    name: 'Activities',
    data() {
      return {
        formGrid: {
          options: {
            version: 'v2',
            url: {
              page: "/admin/activities/page",
              save: "/admin/activities/save",
              delete: "/admin/activities/delete",
            }
          },
          tips: {title: "活动管理", content: "Activity"},

          form: {
            modal: {
              title: "活动管理"
            },
            columns: [
              {label: "活动名称", type: "text", name: "name", required: true,click:function (){
                console.log("click");
               // alert("hello1")
                //弹出商品选择界面
                // this.$Message.info({content:"hello"});
              }},
              {label: "活动编号", type: "text", name: "sn", required: true},
              {label: "活动类型", type: "select", name: "type", required: true, data: values.activityType},
              {label: "活动状态", type: "select", name: "status", required: true, data: values.activityStatus},
              {label: "规则说明", type: "text", name: "ruleDesc", required: true},
              {label: "活动开始时间", type: "datetime", name: "startTime", required: true},
              {label: "活动结束时间", type: "datetime", name: "endTime", required: true},
              {label: "描述说明", type: "textarea", name: "remark", required: true},

            ]
          },
          table: {
            operation: {
              primaryKey: "id",
            },
            columns: [
              {title: '活动名称', key: 'name'},
              {title: '活动类型', key: 'type'},
              {title: '活动编号', key: 'sn'},
              {title: '活动状态', key: 'status'},
              {title: '规则说明', key: 'ruleDesc'},
              {title: '活动开始时间', key: 'startTime'},
              {title: '活动结束时间', key: 'endTime'},
            ]
          }
        }
      }
    },
    methods: {}
  }
</script>
