<template>
  <u-form-grid :data="formGrid" :filterParams="gridQueryParams"></u-form-grid>
</template>

<script>
  export default {
    name: 'SpecOptionValue',
    props: {
      filterParams: {
        type: Object,
        default: function () {
          return {}
        }
      },
    },
    watch: {
      //监听变量发生变化，将新的变量给查询参数。
      filterParams: {
        handler(curVal, oldVal) {
          this.gridQueryParams = curVal;
        },
        deep: true
      }
    },
    data() {
      return {
        gridQueryParams:{},
        formGrid: {
          options: {
            version: 'v2',
            url: {
              page: "/admin/specOptionValue/page",
              save: "/admin/specOptionValue/save",
              delete: "/admin/specOptionValue/delete",
            }
          },
          tips: {title: "规格可选值", content: "设置当前规格可选值的范围，发布商品是只能从当前列表当中选择。"},

          form: {
            modal: {
              title: "规格可选值"
            },
            columns: [
              {label: "值", type: "text", name: "value", required: true},

            ],
            submitBefore: (data) => {
              //临时从sessionStorage当中获取参数，避免从data当中无法获取值
              data["specId"] = window.sessionStorage.getItem("temp_specId") || null;
              return true;
            },
          },
          table: {
            operation: {
              primaryKey: "id",
            },
            columns: [
              {title: 'id', key: 'id'},
              {title: '值', key: 'value'},
            ]
          }
        }
      }
    },
    methods: {}
  }
</script>
