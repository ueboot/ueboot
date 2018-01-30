<template>
    <div class="dbTreeScroll" style="padding-top: 10px">
        <div class="dbTree jstree-default jstree-checkbox-selection">
            <ul class="jstree-container-ul jstree-children jstree-wholerow-ul jstree-no-dots">
                <u-tree-node :tree="treeNode" :showCheckbox="showCheckbox" v-on:itemClick="listenerItemClick"
                             :parentNode="parentNode"></u-tree-node>
            </ul>
        </div>
    </div>
</template>

<script type="text/jsx">
    import UTreeNode from "./UTreeNode.vue";
    export default {
        name: 'UTree',
        componentName: 'UTree',
        props: {
            showCheckbox: {
                type: Boolean,
                default: false
            },
            tree: Array,
            //排序字段 {sort:"desc|asc",field:"要排序的字段"}
            sort:{
                type:Object,
                default:null
            },
        },
        components: {
          UTreeNode: UTreeNode
        },

        data() {
            return {
                expanded: false,
                parentNode: null,
            };
        },
        computed:{
            treeNode: function () {
               let treeNode =  this.initTree();
                return treeNode;
            }
        },
        created(){
            console.log(this.sort);
        },
        methods: {
            //构造树结构
            initTree(){
                //1.查找root
                let root = null;
                this.tree.forEach((item) => {
                    if (!item.parentId || item.parentId === null) {
                        root = {
                            text: item.name,
                            attr: item,
                            opened: true,
                            iconClass: "icon-state-warning",
                            treeId: item.id
                        };
                    }
                });

                if (!root) {
                    console.warn("tree root is null，请检查数据是否正确");
                    return;
                }
                let sort = this.sort;
                //2.递归循环所有节点,将节点加入到父节点当中
                function getChildren(tree, parentId) {
                    let child = [];
                    tree.forEach((item) => {
                        if (item.parentId === parentId) {
                            let o = {
                                text: item.name,
                                attr: item,
                                children: [],
                                treeId: item.id
                            };
                            o.canSelect = true;
                            child.push(o);
                        }
                    });
                    child.forEach((item) => {
                        item.children = getChildren(tree, item.treeId);
                    });
                    //是否需要排序
                    if(sort){
                      child.sort((a, b) => {
                          if(sort["sort"]==="desc"){
                            return b[sort["field"]]-a[sort["field"]];
                          }else {
                            return b[sort["field"]]-a[sort["field"]];
                          }
                      })
                    }

                    return child;
                }

                //生成树结构数据
                root.children = getChildren(this.tree, root.treeId);
                this.parentNode = root;
                return [root];
            },
            listenerItemClick(item,pNode){
                //通知上级节点，发生变化
                this.$emit('itemClick', item,pNode);
            },
        }
    };
</script>
