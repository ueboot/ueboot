<template>
  <Form :model="form.data" :label-position="form.labelPosition"
        :label-width="form.labelWidth"
        :rules="ruleValidate" :ref="form.name">
    <Row>
      <i-col :span="24/form.colNumber" :key="index" v-for="(item,index) in form.columns">
      <Form-item :label="item.label" :prop="item.name">

        <i-input v-model="form.data[item.name]" :type="item.type" :placeholder="item.placeholder"
               v-if="item.type=='text'"></i-input>

        <template v-else-if="item.type=='select'">
          <Select v-model="form.data[item.name]" :placeholder="item.placeholder">
            <Option :value="o.value" v-for="o in item.items" :key="o.index">{{o.name}}</Option>
          </Select>
        </template>

        <template v-else-if="item.type=='radio'">
          <Radio-group v-model="form.data[item.name]">
            <Radio :label="o.label" v-for="o in item.items" :key="o.index">{{o.name}}</Radio>
          </Radio-group>
        </template>

        <template v-else-if="item.type=='date'">
          <Date-picker type="date" :placeholder="item.placeholder?item.placeholder:'选择日期'"
                       v-model="form.data[item.name]"></Date-picker>
        </template>

        <template v-else-if="item.type=='time'">
          <Date-picker type="time" :placeholder="item.placeholder?item.placeholder:'选择时间'"
                       v-model="form.data[item.name]"></Date-picker>
        </template>

        <template v-else-if="item.type=='checkbox'">
          <Checkbox-group v-model="form.data[item.name]">
            <Checkbox :label="o.label" v-for="o in item.items" :key="o.index">{{o.name}}</Checkbox>
          </Checkbox-group>
        </template>

        <template v-else-if="item.type=='textarea'">
          <i-input type="textarea" :placeholder="item.placeholder?item.placeholder:'请输入...'"
                 v-model="form.data[item.name]" :rows="item.rows"
                 :autosize="{minRows: item.minRows,maxRows: item.maxRows}"
          ></i-input>
        </template>
        <template v-else>

        </template>

      </Form-item>
      </i-col>
    </Row>
    <Row v-if="form.showOpt">
      <Form-item>
        <Button type="primary" @click="handleSubmit()">提交</Button>
        <Button type="default" @click="handleReset()" style="margin-left: 8px">重置</Button>
      </Form-item>
    </Row>
  </Form>
</template>
<script>
import Log from '../../utils/Log'
import deepExtend from 'deep-extend'

export default{
  name: 'UForm',
  props: {
    data: {
      type: Object
    }
  },
  created () {
    let defaultForm = {
      name: 'formName', // 表单名称，如果同一个页面存在多个表单时，需要指定不同的名称
      // label对齐方式
      labelPosition: 'right',
      // label的宽度
      labelWidth: 80,
      // 每行显示的个数
      colNumber: 2,
      // 显示操作按钮
      showOpt: true,
      columns: [],
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

      },
      data: {}// 表单提交的参数
    }
    this.form = deepExtend(defaultForm, this.data)
    Log.d('form :%o', this.form)
    // 生成表单规则校验
    let ruleValidate = {}
    if (this.form.columns) {
      this.form.columns.forEach((c) => {
        let ruleName = c.name
        // 直接写required的话，采用默认提示规则。写了rule属性则使用rule
        if (c.required && !c.rule) {
          let trigger = 'blur';
          // 固定的几种类型采用change事件监听
          ['select', 'radio', 'date', 'time', 'checkbox'].forEach((type) => {
            if (c.type === type) {
              trigger = 'change'
            }
          })
          let rule = {required: true, message: c.label + '不能为空', trigger: trigger}
          ruleValidate[ruleName] = [rule]
        } else {
          // 其他自定义格式直接采用原生格式（参见iview组件当中的form表单验证格式）
          ruleValidate[ruleName] = c.rule
        }
      })
    }
    this.ruleValidate = ruleValidate
    // 针对type为select的数据，进行额外处理
    /* if (this.form.columns) {
          for(let c of this.form.columns){

          }
      } */
  },
  data () {
    return {
      form: {
        data: {}// 表单提交的参数
      },
      // 表单规则校验
      ruleValidate: {}
    }
  },
  methods: {
    // 模态窗口点击确认按钮事件
    handleSubmit () {
      this.$refs[this.form.name].validate((valid) => {
        if (valid) {
          if (!this.form.submitBefore(this.form.data)) {
            return
          }
          this.$axios.post(this.form.actionUrl, {data: this.form.data}).then(response => {
            this.$Message.success('提交成功!')
            this.handleReset()
            this.form.submitAfter(response)
          })
        } else {
          this.$Message.error('表单验证失败!')
        }
      })
    },
    // 重置表单
    handleReset () {
      this.$refs[this.form.name].resetFields()
    }
  }

}
</script>
