<template>
  <div>
    <u-form-grid :data="formGrid" :filterParams="gridQueryParams" ref="child"></u-form-grid>

    <Modal
      v-model="specOptionValueModal"
      :closable="true"
      :mask-closable="false">
      <template>
        <spec-option-value :filterParams="specQueryParams" v-if="specOptionValueModal"></spec-option-value>
        <div slot="footer">
          <Button type="primary" @click="hiddenSpecOptionValueModal">关闭</Button>
        </div>
      </template>
    </Modal>
  </div>
</template>

<script>
  import SpecOptionValue from "./SpecOptionValue.vue";

  export default {
    name: 'Specification',
    props: {
      filterParams: {
        type: Object,
        default: function () {
          return {}
        }
      },
    },
    components: {
      "SpecOptionValue": SpecOptionValue
    },
    watch: {
      //监听变量发生变化，将新的变量给查询参数。
      filterParams: {
        handler(curVal, oldVal) {
          this.gridQueryParams = curVal;
          console.log("spec gridQueryParams：%o",this.gridQueryParams);
        },
        deep: true
      }
    },
    data() {
      return {
        gridQueryParams: {},
        //显示可选值模态窗口
        specOptionValueModal: false,
        //查询规格可选值列表参数
        specQueryParams: {},
        formGrid: {
          options: {
            version: 'v2',
            url: {
              page: "/admin/specification/page",
              save: "/admin/specification/save",
              delete: "/admin/specification/delete",
            }
          },
          tips: {title: "类目规格管理", content: "定义每个类目下的规格项，添加商品时，不同的规格可以有不同的价格"},
          form: {
            modal: {
              title: "类目规格"
            },
            columns: [
              {label: "规格名称", type: "text", name: "name", required: true},
            ],
            submitBefore: (data) => {
              //临时从sessionStorage当中获取参数，避免从data当中无法获取值
              data["categoryId"] = window.sessionStorage.getItem("temp_categoryId") || null;
              console.log("data:%o", data);
              return true;
            },
          },
          table: {
            operation: {
              //调用查看、编辑、删除接口时，需要传递的参数主键名称，可以有多个。默认为id
              primaryKey: "id",
              buttons: [
                {
                  show: true, "label": "可选值列表", "theme": "primary", click: (row, index, _this) => {
                  this.showSpecOptionValueModal(row);
                }
                },
                {
                  key: "edit", show: true, "label": "编辑", "theme": "primary", click: (row, index, _this) => {
                  _this.optEditClick(row, index);
                }
                }, {
                  key: "delete", show: true, "label": "删除", "theme": "error", click: (row, index, _this) => {
                    _this.optDeleteClick(row, index);
                  }
                }],
              column: {
                title: '操作',
                key: 'action',
                width: 200,
              }
            },
            columns: [
              {title: '规格名称', key: 'name'},
            ]
          }
        }
      }
    },
    created(){
      this.gridQueryParams = this.filterParams;
      console.log("create gridQueryParams：%o",this.gridQueryParams);
    },
    methods: {
      showSpecOptionValueModal(row) {
        this.$set(this.specQueryParams, "specId", row.id);
        window.sessionStorage.setItem("temp_specId", row.id);
        this.specOptionValueModal = true;
      },
      hiddenSpecOptionValueModal() {
        this.specOptionValueModal = false;
      },
    },
    mounted() {
      window.sessionStorage.setItem("temp_categoryId", null);
      this.$nextTick(function () {
        //添加监听器
        //table重新加载
        this.$on('reloadData', () => {

          this.$refs.child.$emit('reloadData');
        });
      })
    }
  }
</script>

