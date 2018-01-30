<template>
  <div>
    <Row>
      <Col :span="24">
      <Button type="primary" icon="plus" @click="addType">添加分类</Button>
      <Button type="error" icon="close">删除分类</Button>
      </Col>
    </Row>
    <Row>
      <Col :span="8">
      <u-tree :tree="tree" @itemClick="itemClick" :showCheckbox="true"></u-tree>
      </Col>
      <Col :span="16">
      <div>
        使用方式：
        1. 将components下的tree文件copy到工程当中（最好同目录）
      </div>
      </Col>
    </Row>
  </div>
</template>

<script type="application/javascript">

  export default {
    data () {
      return {
        tree: [
          {id: 1, "name": "根节点", parentId: null},
          {"name": "一级子节点", id: 2, parentId: 1},
          {"name": "一级子节点", id: 3, parentId: 1},
          {"name": "二级子节点", id: 4, parentId: 2},
          {"name": "二级子节点", id: 5, parentId: 3},
        ],
        selectTreeItem: null,//当前选择的树节点
      //  sort:{"sort":"desc","field":"id"}
      }
    },
    methods: {
      itemClick(item){
        this.selectTreeItem = item;
      },
      addType(){
        if (this.selectTreeItem == null) {
          this.$Message.error('请先在下面选择一个节点!');
        } else {
          //弹出添加窗口
          this.$Modal.confirm({
            render: (h) => {
              return h('Input', {
                props: {
                  value: this.value,
                  autofocus: true,
                  placeholder: 'Please enter your name...'
                },
                on: {
                  input: (val) => {
                    this.value = val;
                  }
                }
              })
            }
          })
        }
      },
    }
  }
</script>
