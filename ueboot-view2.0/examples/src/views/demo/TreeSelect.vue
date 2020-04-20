<template>
    <div>
        <Row>
            <i-col :span='8'>
                <u-tree-select :tree='tree' fixed async refName='s1' @item-click='itemClick' v-model='selectTreeItem'></u-tree-select>
            </i-col>
            <i-col :span='8'>
              <!--  <u-tree-select :tree='tree' async refName='s2' v-model='selectTreeItem2'
                               @item-click='itemClick'></u-tree-select>-->
            </i-col>
            <i-col :span='8'>
                <!--<u-tree-select :tree='tree' fixed refName='s3' ></u-tree-select>-->
            </i-col>
        </Row>
        <Row>
                <i-col span='16'>
                    <br/>
                    <br/>
                    <br/>
                    <Form  ref='testForm' name='testForm' :data='dataForm'>
                            <color-picker v-model='dataForm.color' ></color-picker>
                            <i-button type='primary' @click='resetColor'>重置</i-button>
                    </Form>
                </i-col>
        </Row>
    </div>
</template>

<script type='application/javascript'>

import UTreeSelect from '../../../../src/components/tree-select/UTreeSelect'
import ColorPicker from './ColorPicker'
export default {
  components: { UTreeSelect, ColorPicker },
  data () {
    return {
      color: '',
      dataForm: {
        color: ''
      },
      tree: [],
      tree2: [
        { id: 1, 'name': '根节点1', parentId: null, opened: true },
        { 'name': '一级子节点', id: 2, parentId: 1, icon: 'fa fa-check icon-state-success' },
        { 'name': '一级子节点', id: 3, parentId: 1 },
        { 'name': '二级子节点', id: 4, parentId: 2 },
        { 'name': '二级子节点', id: 5, parentId: 3 },
        { id: 6, 'name': '根节点2', parentId: null },
        { 'name': '一级子节点', id: 7, parentId: 6 },
        { 'name': '一级子节点', id: 8, parentId: 6 },
        { 'name': '二级子节点', id: 9, parentId: 7 },
        { 'name': '二级子节点', id: 10, parentId: 8 }

      ],
      options: {
        showCheckbox: true,
        multiple: true,
        allowBatch: true,
        wholeRow: true
      },

      selectTreeItem: 1217, // 当前选择的树节点
      selectTreeItem2: null// 当前选择的树节点
      //  sort:{'sort':'desc','field':'id'}
    }
  },
  methods: {
    itemClick (node, item, e) {
      this.$log.d('选择的item', item)
    },
    resetColor () {
      this.$refs['testForm'].resetFields()
      this.dataForm.color = ''
    }
  },
  created () {
    //  this.$utils.getTreeData(treeData.tree)
  },
  watch: {
    selectTreeItem: function (newValue, oldValue) {
      this.$log.d('parent newValue:%o', newValue)
    }
  },
  mounted () {
    this.$nextTick(() => {
      this.$log.d('父组件加载完成')
    })
    // 模拟异步加载树数据
    setTimeout(() => {
      // this.tree = service.queryTreeData(this)
    }, 1000)
  }

}
</script>
