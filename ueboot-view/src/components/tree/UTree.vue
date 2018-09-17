<template>
  <div class="utree" :style="treeStyles">
    <div :class="classes" role="tree" onselectstart="return false">
      <ul :class="containerClasses" role="group">
        <u-tree-item v-for="(child, index) in treeData"
                     :key="index"
                     :data="child"
                     :text-field-name="textFieldName"
                     :value-field-name="valueFieldName"
                     :children-field-name="childrenFieldName"
                     :item-events="itemEvents"
                     :whole-row="wholeRow"
                     :show-checkbox="showCheckbox"
                     :allow-transition="allowTransition"
                     :height="sizeHeight"
                     :parent-item="treeData"
                     :draggable="draggable"
                     :drag-over-background-color="dragOverBackgroundColor"
                     :on-item-click="onItemClick"
                     :on-item-toggle="onItemToggle"
                     :on-item-drag-start="onItemDragStart"
                     :on-item-drag-end="onItemDragEnd"
                     :on-item-drop="onItemDrop"
                     :klass="index === treeData.length-1?'tree-last':''">
          <template slot-scope="_">
            <slot :vm="_.vm" :model="_.model">
              <i :class="_.vm.themeIconClasses" role="presentation" v-if="!_.model.loading"></i>
              <Tooltip max-width="300" :content="_.model.tip" v-if="_.model.tip!==''">
                  <span v-html="_.model[textFieldName]"></span>
              </Tooltip>
              <span v-html="_.model[textFieldName]" v-else></span>
            </slot>
          </template>
        </u-tree-item>
      </ul>
    </div>
  </div>

</template>
<style>
  /* 防止tooltip不换行 */
  .utree .ivu-tooltip-inner {
    white-space: normal;
  }
</style>
<script>
import UTreeItem from './UTreeItem.vue';
import Utils from '../../utils/Utils';

let ITEM_ID = 0;
let ITEM_HEIGHT_SMALL = 18;
let ITEM_HEIGHT_DEFAULT = 24;
let ITEM_HEIGHT_LARGE = 32;
export default {
    name: 'UTree',
    props: {
    // 组件使用v-model即可获取返回值
        value: {
            type: [String, Number, Array],
            default: null
        },
        tree: {type: Array},
        size: {type: String, validator: value => ['large', 'small'].indexOf(value) > -1},
        showCheckbox: {type: Boolean, default: false},
        wholeRow: {type: Boolean, default: false},
        noDots: {type: Boolean, default: false},
        collapse: {type: Boolean, default: false},
        multiple: {type: Boolean, default: false},
        allowBatch: {type: Boolean, default: true},
        allowTransition: {type: Boolean, default: true},
        textFieldName: {type: String, default: 'text'},
        valueFieldName: {type: String, default: 'value'},
        childrenFieldName: {type: String, default: 'children'},
        itemEvents: {
            type: Object,
            default: function () {
                return {};
            }
        },
        async: {type: Function},
        loadingText: {type: String, default: 'Loading...'},
        draggable: {type: Boolean, default: false},
        dragOverBackgroundColor: {type: String, default: '#C9FDC9'},
        klass: String,
        maxHeight: {
            type: Number,
            default: 400
        }
    },
    data () {
        return {
            treeData: [],
            draggedItem: undefined,
            draggedElm: undefined,
            treeStyles: `max-height:${this.maxHeight}px;overflow: auto;`
        };
    },
    watch: {
    // 监听原始数据，发生变化后重新初始化值，并重新渲染树结构
        tree: function (newValue, oldValue) {
            let start = new Date().getMilliseconds();

            this.treeData = Utils.getTreeData(newValue);
            let end = new Date().getMilliseconds();
            this.$log.d('tree 构造耗时:%o,treeData', end - start, this.treeData);
            this.initializeData(this.treeData);
        }
    },
    computed: {

        classes () {
            return [
        {'tree': true},
        {'tree-default': !this.size},
        {[`tree-default-${this.size}`]: !!this.size},
        {'tree-checkbox-selection': !!this.showCheckbox},
        {[this.klass]: !!this.klass}
            ];
        },
        containerClasses () {
            return [
        {'tree-container-ul': true},
        {'tree-children': true},
        {'tree-wholerow-ul': !!this.wholeRow},
        {'tree-no-dots': !!this.noDots}
            ];
        },
        sizeHeight () {
            switch (this.size) {
                case 'large':
                    return ITEM_HEIGHT_LARGE;
                case 'small':
                    return ITEM_HEIGHT_SMALL;
                default:
                    return ITEM_HEIGHT_DEFAULT;
            }
        }
    },
    methods: {

        initializeData (items) {
            if (items && items.length > 0) {
                for (let i in items) {
                    items[i] = this.initializeDataItem(items[i]);
                    this.initializeData(items[i][this.childrenFieldName]);
                }
            }
        },
        initializeDataItem (item) {
            function Model (item, textFieldName, valueFieldName, childrenFieldName, collapse) {
                this.id = item.id || ITEM_ID++;
                this.parentId = item.parentId || null;
                this[textFieldName] = item[textFieldName] || '';
                this[valueFieldName] = item[valueFieldName] || item[textFieldName];
                this.icon = item.icon || '';
                this.tip = item.tip || '';
                this.opened = item.opened || collapse;
                this.selected = item.selected || false;
                this.disabled = item.disabled || false;
                this.loading = item.loading || false;
                this[childrenFieldName] = item[childrenFieldName] || [];
                if (this[childrenFieldName].length === 0) {
                    this.isLeaf = true;
                }
            }

            let node = Object.assign(new Model(item, this.textFieldName, this.valueFieldName, this.childrenFieldName, this.collapse), item);
            let self = this;
            node.addBefore = function (data, selectedNode) {
                let newItem = self.initializeDataItem(data);
                let index = selectedNode.parentItem.findIndex(t => t.id === node.id);
                selectedNode.parentItem.splice(index, 0, newItem);
            };
            node.addAfter = function (data, selectedNode) {
                let newItem = self.initializeDataItem(data);
                let index = selectedNode.parentItem.findIndex(t => t.id === node.id) + 1;
                selectedNode.parentItem.splice(index, 0, newItem);
            };
            node.addChild = function (data) {
                let newItem = self.initializeDataItem(data);
                node.opened = true;
                node[self.childrenFieldName].push(newItem);
            };
            node.openChildren = function () {
                node.opened = true;
                self.handleRecursionNodeChildren(node, node => {
                    node.opened = true;
                });
            };
            node.closeChildren = function () {
                node.opened = false;
                self.handleRecursionNodeChildren(node, node => {
                    node.opened = false;
                });
            };
            return node;
        },
        initializeLoading () {
            let item = {};
            item[this.textFieldName] = this.loadingText;
            item.disabled = true;
            item.loading = true;
            return this.initializeDataItem(item);
        },
        handleRecursionNodeChilds (node, func) {
            if (func(node) !== false) {
                if (node.$children && node.$children.length > 0) {
                    for (let childNode of node.$children) {
                        if (!childNode.disabled) {
                            this.handleRecursionNodeChilds(childNode, func);
                        }
                    }
                }
            }
        },
        handleRecursionNodeChildren (node, func) {
            if (func(node) !== false) {
                if (node[this.childrenFieldName] && node[this.childrenFieldName].length > 0) {
                    for (let childNode of node[this.childrenFieldName]) {
                        this.handleRecursionNodeChildren(childNode, func);
                    }
                }
            }
        },
        onItemClick (oriNode, oriItem, e) {
            if (this.multiple) {
                if (this.allowBatch) {
                    this.handleBatchSelectItems(oriNode, oriItem);
                }
            } else {
                this.handleSingleSelectItems(oriNode, oriItem);
            }
      // 更新v-model属性的值
            this.$emit('input', oriItem.id);
            this.$emit('item-click', oriNode, oriItem, e);
        },
        handleSingleSelectItems (oriNode, oriItem) {
            this.handleRecursionNodeChilds(this, node => {
                if (node.model) node.model.selected = false;
            });
            oriNode.model.selected = true;
        },
        handleBatchSelectItems (oriNode, oriItem) {
            this.handleRecursionNodeChilds(oriNode, node => {
                if (node.model.disabled) return;
                node.model.selected = oriNode.model.selected;
            });
        },
        onItemToggle (oriNode, oriItem, e) {
            if (oriNode.model.opened) {
                this.handleAsyncLoad(oriNode.model[this.childrenFieldName], oriNode, oriItem);
            }
            this.$emit('item-toggle', oriNode, oriItem, e);
        },
        handleAsyncLoad (oriParent, oriNode, oriItem) {
            let self = this;
            if (this.async) {
                if (oriParent[0].loading) {
                    this.async(oriNode, (data) => {
                        if (data.length > 0) {
                            for (let i in data) {
                                if (!data[i].isLeaf) {
                                    if (typeof data[i][self.childrenFieldName] !== 'object') {
                                        data[i][self.childrenFieldName] = [self.initializeLoading()];
                                    }
                                }
                                let dataItem = self.initializeDataItem(data[i]);
                                self.$set(oriParent, i, dataItem);
                            }
                        } else {
                            oriNode.model[self.childrenFieldName] = [];
                        }
                    });
                }
            }
        },
        onItemDragStart (e, oriNode, oriItem) {
            if (!this.draggable || oriItem.dragDisabled) { return false; }
            e.dataTransfer.effectAllowed = 'move';
            e.dataTransfer.setData('text', null);
            this.draggedElm = e.target;
            this.draggedItem = {
                item: oriItem,
                parentItem: oriNode.parentItem,
                index: oriNode.parentItem.findIndex(t => t.id === oriItem.id)
            };

            this.$emit('item-drag-start', oriNode, oriItem, e);
        },
        onItemDragEnd (e, oriNode, oriItem) {
            this.draggedItem = undefined;
            this.draggedElm = undefined;
            this.$emit('item-drag-end', oriNode, oriItem, e);
        },
        onItemDrop (e, oriNode, oriItem) {
            if (!this.draggable || !!oriItem.dropDisabled) { return false; }
            this.$emit('item-drop-before', oriNode, oriItem, !this.draggedItem ? undefined : this.draggedItem.item, e);
            if (!this.draggedElm || this.draggedElm === e.target || this.draggedElm.contains(e.target)) {
                return;
            }
            if (this.draggedItem) {
                if (this.draggedItem.parentItem === oriItem[this.childrenFieldName] ||
            this.draggedItem.item === oriItem ||
            (oriItem[this.childrenFieldName] && oriItem[this.childrenFieldName].findIndex(t => t.id === this.draggedItem.item.id) !== -1)) {
                    return;
                }
                if (oriItem[this.childrenFieldName]) {
                    oriItem[this.childrenFieldName].push(this.draggedItem.item);
                } else {
                    oriItem[this.childrenFieldName] = [this.draggedItem.item];
                }
                oriItem.opened = true;
                let draggedItem = this.draggedItem;
                this.$nextTick(() => {
                    draggedItem.parentItem.splice(draggedItem.index, 1);
                });
                this.$emit('item-drop', oriNode, oriItem, draggedItem.item, e);
            }
        },
    /**
       * 获取当前勾选的节点ID列表,供外部组件调用
       * @returns {Array} id列表
       */
        getCheckedNodes () {
      // 递归查询获取所有被勾选的节点
            function getChecked (item, result) {
                if (item.selected) {
                    result.push(item.id);
                }
                item.children.forEach((c) => {
                    getChecked(c, result);
                });
            }
            let result = [];
            this.treeData.forEach((t) => {
                getChecked(t, result);
            });
            return result;
        }
    },
    created () {

    },
    mounted () {
        if (this.async) {
            this.$set(this.treeData, 0, this.initializeLoading());
            this.handleAsyncLoad(this.treeData, this);
        }
    // 构建一次树
        this.treeData = Utils.getTreeData(this.tree);
        this.initializeData(this.treeData);
    },
    components: {
        UTreeItem
    }
};
</script>
<style lang="less">
  @import "./less/style";
</style>
