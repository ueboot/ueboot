<template>
    <div>
        <Row v-if="fixed">
            <i-input v-model="inputValue" search @on-keyup="inputOnSearch" @on-search="inputOnSearch"
                     @on-focus="inputFocus"/>
            <u-tree :tree="treeData" :size="size" :collapse="collapse" :async="newAsync" :asyncFun="asyncLoadData"
                    :loadingText="loadingText"
                    :klass="klass"
                    :maxHeight="maxHeight"
                    @item-click="handlerItemClick"
                    :sort="sort" :ref="refName" v-model="selectId"></u-tree>
        </Row>

        <Row v-else>
            <Dropdown trigger="custom" style="width:100%" :visible="visible" class="utree-select"
                      @on-clickoutside="handleClose">
                <i-input v-model="inputValue" search @on-keyup="inputOnSearch" @on-search="inputOnSearch"
                         @on-focus="inputFocus"/>
                <div slot="list">
                    <u-tree v-model="selectId" :tree="treeData" :size="size" :collapse="collapse" :async="newAsync"
                            :asyncFun="asyncLoadData"
                            :loadingText="loadingText" :klass="klass"
                            :maxHeight="maxHeight"
                            @item-click="handlerItemClick"
                            :sort="sort" :ref="refName"></u-tree>

                </div>
            </Dropdown>
        </Row>
    </div>
</template>
<style>
    /* 指定样式下的dropdown宽度设置为100%，避免受iview组件影响 */
    .utree-select .ivu-dropdown-rel {
        width: 100%;
    }
</style>

<script type="text/jsx">
    import UTree from '../tree/UTree'
    import deepExtend from 'deep-extend'
    import Utils from '../../utils/Utils'

    // 自增长ID值，防止一个页面2个以上的treeselect组件，导致refName重名
    import treeId from './treeid'

    export default {
        name: 'UTreeSelect',
        componentName: 'UTreeSelect',
        props: {
            // 是否固定显示，固定则默认带搜索框并全部展示
            fixed: {
                type: Boolean,
                default: false
            },
            // 组件使用v-model即可获取返回值
            value: {
                type: [String, Number],
                default: ''
            },
            tree: {
                type: Array,
                default() {
                    return []
                }
            },
            size: {type: String, validator: value => ['large', 'small'].indexOf(value) > -1},
            collapse: {type: Boolean, default: false},
            async: {type: Boolean, default: false},
            loadingText: {type: String, default: 'Loading...'},
            klass: String,
            maxHeight: {
                type: Number,
                default: 400
            },
            // 排序字段 {sort:"desc|asc",field:"要排序的字段"}
            sort: {
                type: Object,
                default: null
            },
            refName: {
                type: String,
                default: 'tree' + (treeId.getId())
            }
        },
        components: {
            UTree
        },

        data() {
            return {
                // 传给UTree组件的数据
                treeData: [],
                //已经初始化过的树数据，用于页面异步加载的时候初始化显示使用第一层数据。
                initTreeData: [],
                inputValue: null,
                selectTreeItem: null,
                visible: false,
                // 组件的宽度，用于控制搜索结果展示时的长度
                treeWidth: null,
                //用于搜索的树结构，带path的属性
                searchTreeData: null,
                // 当前选择的节点ID，也可以当初始值赋值给utree组件
                selectId: null,
                //tree数据索引
                treeMap: {},
                //标识当前树数据是否为搜索用的tree，还是正常的树
                isSearchTree: false,
                //根据parentId归类的树数据
                parentTreeData: [],
                opt: {
                    showCheckbox: false,
                    multiple: false,
                    allowBatch: false,
                    wholeRow: true
                },
                //使用新的字段保留原始的标识
                newAsync: this.async
            }
        },

        // 监听父节点的值发生变化后，动态修改内部的数据
        watch: {
            value: function (newValue, oldValue) {
                this.selectId = parseInt(newValue)
                this.inputValue = this.treeMap[newValue] ? this.treeMap[newValue].name : ''
            },
            tree: function (newValue, oldValue) {
                this.treeData = newValue
                //tree的数据发生变化时需要重新初始化搜索树数据
                this.initTreeMap(this.treeData, this.value)
            },
            inputValue: function (newValue, oldValue) {
                // 如果没有选择任何内容，则树结构恢复原始状态
                if (newValue === '' || newValue === null) {
                    this.$nextTick(() => {
                        if (!this.async) {
                            // 避免污染this.tree
                            //非异步才给全数据，否则为空，通过异步加载
                            this.treeData = [...this.tree]
                        } else {
                            this.treeData = []
                        }
                    })
                }
            }
        },
        created() {
            this.$log.d("####")
            // 避免污染this.tree
            this.treeData = [...this.tree]
            this.initTreeMap(this.treeData, this.value)
        },

        methods: {
            initTreeMap(treeData, selectId) {
                let start = new Date().getTime();
                let map = {}
                for (let o of treeData) {
                    if (selectId && (o['id'] === selectId)) {
                        o.selected = true
                    } else {
                        o.selected = false
                    }
                    map[o['id'] + ''] = o
                }
                this.treeMap = map
                //构造一个带上下级path的tree二维数组，用于搜索
                this.initTreeData = Utils.getTreeData(this.treeData, null)
                //找到第一层数据

                this.$log.d('treeSelect1 初始化getTreeData耗时:%o,selectId:%o', new Date().getTime() - start, this.selectId);
                start = new Date().getTime();
                let a = []
                this.getTreeChild(this.initTreeData, a)
                this.searchTreeData = a
                if (selectId) {
                    this.selectId = selectId
                    this.inputValue = this.treeMap[selectId] ? this.treeMap[selectId].name : ''
                }
                this.$log.d('treeSelect2 初始化getTreeChild耗时:%o,selectId:%o', new Date().getTime() - start, this.selectId);
                start = new Date().getTime();
                this.parentTreeData = Utils.getParentTreeData(this.treeData, null)
                this.$log.d('treeSelect3 初始化getParentTreeData耗时:%o,selectId:%o', new Date().getTime() - start, this.selectId);
            },
            //将树结构转成二维数组
            getTreeChild(tree, array) {
                tree.forEach(t => {
                    let o = Utils.clone(t)
                    o.children = null
                    array.push(o)
                    if (t.children && t.children.length > 0) {
                        this.getTreeChild(t.children, array)
                    }
                })
            },
            inputOnSearch(event) {
                if (this.inputValue !== '' && this.inputValue !== null) {
                    this.treeData = this.searchTree(this.searchTreeData, this.inputValue)
                    this.isSearchTree = true
                    //搜索时，不做异步加载
                    this.newAsync = false
                } else {
                    //当输入的内容为空时，清空之前选择的值，否则继续保留上次选择的值
                    this.$emit('input', null)
                    this.newAsync = this.async
                    //非异步才给全数据，否则为空，通过异步加载
                    if (!this.async) {
                        this.treeData = [...this.tree]
                    } else {
                        this.treeData = []
                    }
                    this.isSearchTree = false
                }
            },
            // 通过构造一个新的列表方式来展示搜索结果，不直接渲染树
            searchTree(tree, keyWord) {
                let newTree = []
                let count = 0
                // 从原始的数据当中生成的path路径，进行搜索
                for (let i = 0; i < tree.length; i++) {
                    let t = tree[i]
                    if (t.path.indexOf(keyWord) > -1) {
                        // 新复制对象，避免污染
                        let newItem = deepExtend({}, t)
                        let reg = new RegExp('(' + keyWord + ')', 'g')
                        let labelStr = t.path
                        let label = [...t.path]
                        let charWidth = 14
                        // 每个字符占12px
                        let width = label.length * charWidth
                        // 超过组件宽度时，需要截取字符串(从后往前）
                        if (width > this.treeWidth) {
                            let m = (width % charWidth === 0) ? width / charWidth : parseInt(width / charWidth)
                            let n = (this.treeWidth % charWidth === 0) ? this.treeWidth / charWidth : parseInt(this.treeWidth / charWidth)
                            m = Math.min(n - 8, m)
                            let strs = []
                            label.reverse().forEach((c, index) => {
                                if (index < m) {
                                    strs.push(c)
                                }
                            })
                            strs.push('...')
                            labelStr = strs.reverse().join('')
                        }
                        newItem.tip = t.path
                        newItem.origin = deepExtend({}, t.origin)
                        newItem.label = labelStr.replace(reg, '<font color=red>$1</font>')
                        // 干掉所有的ParentId。不按层级显示，全部打平
                        newItem.parentId = null
                        count++
                        // 最多显示15个
                        if (count < 15) {
                            newTree.push(newItem)
                        } else {
                            let tip = '当前搜索结果超过15个，未完全显示。'
                            newItem.label = `<font color=gray>${tip}</font>`
                            newItem.tip = tip
                            newItem.id = null
                            newTree.push(newItem)
                            break
                        }
                    }
                }
                if (newTree.length === 0) {
                    newTree.push({id: null, label: `<font color=gray>没有找到相关数据</font>`, tip: '没有找到相关数据'})
                }
                return newTree
            },

            inputFocus() {
                this.visible = true
                this.$nextTick(() => {
                    this.treeWidth = this.$refs[this.refName].$el.offsetWidth
                })
            },
            handlerItemClick(node, item, e) {
                this.visible = false
                this.selectId = parseInt(item.id)
                this.inputValue = item.name
                // 更新v-model属性的值
                this.$emit('input', parseInt(item.id))
                this.$emit('item-click', node, item, e)
                //点击搜索结果后，树结构变为原始结构
                if (this.isSearchTree) {
                    this.newAsync = this.async
                    this.isSearchTree = false
                    //非异步才给全数据，否则为空，通过异步加载
                    if (!this.async) {
                        this.treeData = [...this.tree]
                        this.initTreeMap(this.treeData, this.selectId)
                    } else {
                        this.treeData = []
                    }
                }
                // 调用一下input的blur事件，用于触发表单校验
                this.$forceUpdate()
            },
            handleClose() {
                this.visible = false
            },
            asyncLoadData(oriNode, callback) {
                let id = oriNode.id ? oriNode.id : 'root'
                if (oriNode.data) {
                    id = oriNode.data.id
                }
                let data = null
                //根节点数据直接获取第一层数据
                if ('root' === id) {
                    data = this.initTreeData
                } else {
                    //根据ID获取子节点数据
                    data = this.parentTreeData[id]
                    if (data) {
                        data = this.parentTreeData[id].children || []
                    }
                }
                if (data) {
                    //判断每个子节点是否还有子节点，没有子节点则设置样式为没有+号
                    data.forEach((c) => {
                        //异步情况下，不执行opened状态
                        c.opened = false
                        //给出有下一级的数据标识，防止不出现+号
                        let o = this.parentTreeData[c.id]
                        c.children = []
                        if (o && o.children) {
                            c.hasChild = true
                        } else {
                            c.hasChild = false
                        }
                    })
                } else {
                    data = []
                }
                callback(data)

            }
        },
        mounted() {
            this.$nextTick(() => {
                this.treeWidth = this.$refs[this.refName].$el.offsetWidth
            })
        }

    }
</script>
