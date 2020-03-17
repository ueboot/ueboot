<template>
    <i-select :clearable='item.clearable'
              :filterable='item.filterable'
              :multiple='item.multiple' v-model='selectValue'
              :placeholder='item.placeholder'
              @on-change='onChange'

    >
        <Option v-for='(option,index) in item.items' :value='option.value' :key="'o'+index">
            {{ option.name }}
        </Option>
    </i-select>

</template>

<script type='application/javascript'>

export default {
  props: {
    value: {
      type: [String, Number, Array],
      default: null
    },
    dictKey: {
      type: String,
      default: null
    },
    options: {
      type: Object,
      default: function () {
        return {
          clearable: true,
          filterable: false,
          multiple: false,
          placeholder: ''
        }
      }
    }
  },
  data () {
    return {
      selectValue: null,
      item: {
        name: 'test',
        clearable: true,
        filterable: false,
        multiple: false,
        placeholder: '',
        items: []
      }
    }
  },
  created () {
    this.queryDict()
  },
  methods: {
    queryDict () {
      setTimeout(() => {
        this.item.items = [{ 'name': '北京', 'value': '北京' }, { 'name': '上海', 'value': '上海' }]
      }, 1000)
    },
    onChange (value) {
      this.$emit('on-change', value)
      this.$emit('input', value)
    }
  }
}
</script>
