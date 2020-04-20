<template>
  <div>
    <Row>
      <i-col :span='8'>
      <u-tree v-model='selectTreeItem' multiple :tree='tree'  :showCheckbox='options.showCheckbox'></u-tree>
      </i-col>
      <i-col :span='8'>
        <div>
          {{selectTreeItem?selectTreeItem.name:''}}
        </div>
      </i-col>
    </Row>
  </div>

</template>

<script type='application/javascript'>

export default {

  data () {
    return {
      tree: [
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

      selectTreeItem: null// 当前选择的树节点
      //  sort:{'sort':'desc','field':'id'}
    }
  },
  methods: {

  }
}
</script>
