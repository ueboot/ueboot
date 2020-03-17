<template>
    <div>

        <u-form :data='form'></u-form>
        <u-select v-model='selectValue' dictKey='names'></u-select>
    </div>
</template>
<script>
import USelect from './USelect'
export default {
  components: { USelect },
  data () {
    return {
      selectValue: '',
      form: {

        actionUrl: '',
        columns: [
          { 'label': '渠道', 'type': 'text', 'name': 'channel', required: true },
          { 'label': '用户名', 'type': 'text', 'name': 'createTime', required: true },
          { 'label': '消息', 'type': 'text', 'name': 'message', required: true },
          { 'label': '发送方', 'type': 'text', 'name': 'sender', required: true },
          { 'label': 'toUser', 'type': 'text', 'name': 'toUser', required: true }
        ],
        // 表单提交之前（校验之后），返回如果为false,则不继续执行后续操作
        submitBefore: function (data) {
          return true
        },
        // 表单提交之后
        submitAfter: function (response) {
          return true
        },
        // 点击取消时的操作，默认为关闭窗口
        onCancel: function () {

        }
      }
    }
  },
  watch: {
    selectValue: function (newValue, oldValue) {
    }
  }
}
</script>
