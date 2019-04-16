<template>
  <div>
    <u-form-grid :data='formGrid' ref='child'>></u-form-grid>
    <select-goods-info v-show='showSelectGoodsInfo' @selectOk='selectOk'> </select-goods-info>
  </div>
</template>

<script>
import values from '../../utils/values'
import Moment from 'moment'
import SelectGoodsInfo from '../goods/SelectGoodsInfo.vue'

export default {
  //
  name: 'SecKillActivity',
  components: {
    'SelectGoodsInfo': SelectGoodsInfo
  },
  data () {
    let sn = new Moment().format('YYYYMMDDhhmm')
    return {
      // 显示选择商品界面
      showSelectGoodsInfo: false,
      //
      goodsInfoId: null,
      formGrid: {
        options: {
          version: 'v2',
          url: {
            page: '/admin/activities/seckillPage',
            save: '/admin/activities/save',
            delete: '/admin/activities/delete'
          }
        },
        tips: { title: '秒杀活动', content: '可以新建和编辑秒杀活动。活动开始时间后，就会自动上架，每次秒杀只能选择一个商品' },
        form: {
          modal: {
            title: '秒杀活动'
          },
          columns: [
            { label: '活动名称', type: 'text', name: 'name', required: true },
            { label: '活动编号',
              type: 'text',
              name: 'sn',
              required: true,
              init: sn,
              edit: {
                show: true, disabled: true
              } },
            { label: '开始时间',
              type: 'datetime',
              name: 'startTime',
              fieldFormat: (value) => {
              // 编辑时必须格式化成date对象，避免无法编辑,属于iview的一个bug
              // return new moment(value,'YYYY-MM-DD hh:mm:ss')
                return value
              } },
            { label: '结束时间',
              type: 'datetime',
              name: 'endTime',
              fieldFormat: (value) => {
              // 编辑时必须格式化成date对象，避免无法编辑,属于iview的一个bug
              // return new moment(value,'YYYY-MM-DD hh:mm:ss')
                return value
              } },
            {
              label: '秒杀商品',
              type: 'text',
              name: 'goodsInfo',
              placeholder: '点击选择要参加活动的商品',
              readonly: true,
              required: true,
              focus: () => {
                // 弹出商品选择界面
                this.showSelectGoodsInfo = true
              },
              view: { show: false },
              edit: { show: false }
            },
            {
              label: '秒杀商品',
              type: 'text',
              name: 'goodsInfoName',
              add: { show: false },
              view: { show: true },
              edit: {
                show: true, disabled: true
              }
            },
            { label: '商品总数', type: 'text', name: 'seckillProvideCopies' },
            { label: '秒杀价格', type: 'text', name: 'seckillPrice' },
            { label: '限购份数', type: 'text', name: 'seckillLimitCopies' },
            { label: '活动状态', type: 'select', name: 'status', required: true, data: values.activityStatus },
            { label: '规则说明', type: 'textarea', name: 'ruleDesc' }
          ],
          submitBefore: (data) => {
            data['goodsInfoId'] = this.goodsInfoId
            data['type'] = '秒杀'
            return true
          }
        },
        table: {
          operation: {
            primaryKey: 'id'
          },
          columns: [
            { title: '活动名称', key: 'name' },
            { title: '活动编号', key: 'sn' },
            { title: '活动开始时间', key: 'startTime' },
            { title: '活动结束时间', key: 'endTime' },
            { title: '商品图片', key: 'goodsInfoMainImgUrl' },
            { title: '商品名称', key: 'goodsInfoName' },
            { title: '商品总数', key: 'seckillProvideCopies' },
            { title: '秒杀价格', key: 'seckillPrice' },
            { title: '每人限购份数', key: 'seckillLimitCopies' },
            { title: '活动状态', key: 'status' }
          ]
        }
      }
    }
  },
  methods: {

    selectOk (goods) {
      if (goods && goods.length > 1) {
        this.$Message.info({ content: '秒杀活动只能选择一个商品' })
        return
      }
      // 调用方法时传递参数
      this.$refs.child.$emit('setFormFieldData', 'goodsInfo', goods[0].name)
      this.goodsInfoId = goods[0].id
      this.showSelectGoodsInfo = false
    }
  }
}
</script>
<style>
  .selectGoodsInfo{
    z-index: 2002
  }
</style>
