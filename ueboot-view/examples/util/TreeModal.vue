<template id="treeModal">
  <div>
    <Modal
      v-model="modal"
      title="组织机构查询"
      @on-ok="ok">
      <div :style="{'height':height+'px'}">
        <i-col :span="24" v-if="tree1&&tree1.length>0">
          <u-tree-select :tree="tree1" fixed refName="s1" :maxHeight="height-50"
                         @item-click="handlerItemClick" async v-model="initSelectId">
          </u-tree-select>
        </i-col>
        <i-col :span="24" v-if="!tree1||tree1.length===0">
          <div>当前考核月无数据</div>
        </i-col>
      </div>
    </Modal>
  </div>
</template>

<script>
import service from '../service/service'

export default {
  name: 'TreeModal',
  props: {
    // 分公司
    treeSelected: String,
    dataOwner: String,
    date: Date,
    topMarking: String
  },
  data () {
    return {
      height: document.documentElement.clientHeight - 500,
      item: null,
      tree1: [],
      modal: false,
      alreadyLoad: false,
      lastDate: this.date,
      initSelectId: this.treeSelected
    }
  },
  methods: {
    ok () {
      this.$emit('item-ok', this.item)
    },
    handlerItemClick (node, item, e) {
      this.item = item
    },
    init (dataOwner, date, topMarking) {
      let p = service.getListDataOwner(this, dataOwner, null, date, topMarking)
      p.then((data) => {
        this.tree1 = data
      })
    },
    show () {
      this.modal = true
      if (!this.alreadyLoad || this.lastDate !== this.date) {
        this.init(this.dataOwner, this.date, this.topMarking)
        this.alreadyLoad = true
        this.lastDate = this.date
      }
    }
  }

}
</script>
