<template>
  <div>
    <Button @click='clicker'>点击</Button>
    <Cascader :data='data' v-model='parentIds' change-on-select></Cascader>

    <br/>
    {{typeof (datetime)}}
    <DatePicker type='datetime' v-model='datetime' placeholder='选择日期和时间' style='width: 200px'></DatePicker>
  </div>
</template>
<script>
export default {
  data () {
    return {
      parentIds: ['beijing', 'gugong'],
      data: [],
      datetime: null
    }
  },
  methods: {
    clicker: function () {
      this.data = [{
        value: 'beijing',
        label: '北京',
        children: [
          {
            value: 'gugong',
            label: '故宫'
          },
          {
            value: 'tiantan',
            label: '天坛'
          },
          {
            value: 'wangfujing',
            label: '王府井'
          }
        ]
      }, {
        value: 'jiangsu',
        label: '江苏',
        children: [
          {
            value: 'nanjing',
            label: '南京',
            children: [
              {
                value: 'fuzimiao',
                label: '夫子庙'
              }
            ]
          },
          {
            value: 'suzhou',
            label: '苏州',
            children: [
              {
                value: 'zhuozhengyuan',
                label: '拙政园'
              },
              {
                value: 'shizilin',
                label: '狮子林'
              }
            ]
          }
        ]
      }]
    }
  }
}
</script>
