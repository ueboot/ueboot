<template>
  <div>
    <template v-for="item in treeNode">
      <li class="jstree-node"
          :class="{'jstree-open':item.opened,'jstree-leaf':!item.children||item.children.length==0,'jstree-last':item.islast,'jstree-closed':!item.opened}">
        <div class="jstree-wholerow"
             :class="{'jstree-wholerow-clicked':item.selected,'jstree-wholerow-hovered':item.hovered}"
        >&nbsp;
        </div>
        <i class="jstree-icon jstree-ocl" v-on:click="itemExpanded(item)"></i>
        <a class="jstree-anchor" :class="{'jstree-clicked':item.selected,'jstree-disabled':item.disabled}"
           v-on:click="itemClick(item)">
          <i class="jstree-icon jstree-checkbox" :class="{'jstree-undetermined':item.undetermined}"
             v-if="showCheckbox"></i>
          <i class="jstree-icon jstree-themeicon fa fa-folder icon-lg jstree-themeicon-custom"
             :class="item.iconClass"></i>{{item.text}}</a>
        <ul class="jstree-children" :class="{'collapse':!item.opened}"
            v-if="item.children&&item.children.length>0">
          <u-tree-node :tree="item.children" v-on:itemClick="listenerItemClick" :parentNode="item"
                       :showCheckbox="showCheckbox"></u-tree-node>
        </ul>
      </li>
    </template>
  </div>
</template>

<script type="text/jsx">
  export default {
    name: 'UTreeNode',
    componentName: 'UTreeNode',
    props: {
      showCheckbox: {
        type: Boolean,
        default: false
      },
      tree: Array,
      parentNode: Object,
    },
    data() {
      return {
        expanded: false,
      };
    },
    computed: {
      treeNode: function () {
        return this.tree || [];
      }
    },
    methods: {
      //展开子节点
      itemExpanded(item) {
        this.$set(item, "opened", !item.opened);
        if (item.opened) {//叶子节点点击时不展开
          item.opened = item.children && item.children.length != 0;
        }
      },
      //递归设置子节点状态
      setChildStatus(item){
        if (item.children) {
          item.children.forEach((child) => {
            if (this.showCheckbox) {
              this.$set(child, "selected", item.selected);
              this.$set(child, "undetermined", false);
            } else {
              this.$set(child, "selected", false);
              this.$set(child, "undetermined", false);
            }
            this.setChildStatus(child);
          });
        }
      },
      itemClick: function (item) {
        if (item.disabled) {
          return;
        }

        //先处理当前节点的父级节点，再通知上级节点
        if (this.parentNode) {
          this.setNodeStatus(this.parentNode);
          //通知上级节点，发生变化
          this.$emit('itemClick', item, this.parentNode);
        }
        this.$set(item, "selected", !item.selected);
        if (this.showCheckbox && item.selected) {
          this.$set(item, "undetermined", false);
        }

        //展开子节点，并设置子节点状态
        this.itemExpanded(item);
        //同级节点关闭
        if (this.parentNode) {
          this.parentNode.children.forEach((child) => {
            if (child != item) {
              this.$set(child, "opened", false);
            }
          });
        }
      },
      //监听下级节点点击事件
      listenerItemClick(item, pNode){
        if (this.parentNode) {
          this.setNodeStatus(this.parentNode);
          //通知上级节点，发生变化
          this.$emit('itemClick', item, pNode);
        }
      },
      //节点状态
      setNodeStatus(item){
        //半选状态
        let undetermined = false;
        //选中状态
        let selected = true;
        //判断子节点是否都勾选了
        if (item.children) {
          let count = 0;
          item.children.forEach((child) => {
            if (!child.selected) {
              selected = false;
            } else {
              count++;
            }
            if (child.undetermined) {
              undetermined = true;
            }
          });
          //判断是否为半选状态
          //当子节点有任一个半选，或者有部分选中时，父节点也是半选状态
          if (!undetermined) {
            undetermined = count > 0 ? count != item.children.length : false;
          } else {
            undetermined = count != item.children.length;
          }
        }
        if (this.showCheckbox) {
          this.$set(item, "selected", selected);
          this.$set(item, "undetermined", undetermined);
        } else {
          this.$set(item, "selected", false);
          this.$set(item, "undetermined", false);
        }
        this.setChildStatus(item);
      }

    }

  };
</script>
