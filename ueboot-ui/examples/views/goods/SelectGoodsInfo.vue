<template>
  <Modal
    :value="show"
    title="选择商品"
    width="700"
    class-name="selectGoodsInfo">
    <u-form-grid :data="formGrid" ref="child"></u-form-grid>
    <div slot="footer">
    </div>
  </Modal>
</template>

<script>
  export default {
      /*
       //商品选择功能组件，用于其他页面嵌入使用.
       //示例      <select-goods-info v-if="showSelectGoodsInfo" @selectOk="selectOk"></select-goods-info>
       其中selectOk事件用于接收最后用户选择的商品列表，参数为商品列表对象  类型为array
       */
    name: 'SelectGoodsInfo',
    data () {
      return {
        show: true,
        formGrid: {
          options: {
            version: 'v2',
            url: {
              page: "/admin/goodsInfo/page",
            }
          },
          form:{},
          toolbar: {
            create: {
              show: false
            },
            delete: {
              show: false
            },
            button: [
              {
                theme: "success", label: "选择当前商品", icon: "plus", click: (selections) => {
                if (!selections || selections.length == 0) {
                  this.$Message.error({content: "必须选择一个商品"});
                  return;
                }
                this.$emit('selectOk', selections);
              }
              }],
          },
          table: {
            operation: {
              primaryKey: "id",
              show:false
            },
            columns: [
              {title: 'id', key: 'id'},
              {title: '商品名称', key: 'name'},
              {title: '品牌名称', key: 'brandName'},
              {title: '商品属性', key: 'goodsType'},
            ]
          }
        }
      }
    },
    methods: {
    }
  }
</script>
