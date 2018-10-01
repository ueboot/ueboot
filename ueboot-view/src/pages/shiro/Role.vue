<template>
    <div>
        <u-form-grid :data="formGrid" ref="formGrid"></u-form-grid>
        <Modal v-model="permissionModal" size="large">
            <p slot="header">
                请注意，您当前正在对<span style="color:red; vertical-align: top;">{{selectRoleName}}</span>角色进行授权操作！
            </p>
            <div>
                <u-tree v-model="selectTreeItem" :tree="tree" :showCheckbox="treeOptions.showCheckbox"
                        :multiple="treeOptions.multiple"
                        :allow-batch="treeOptions.allowBatch"
                        ref="utree" v-show="!loadingTree"></u-tree>
                <Spin v-if="loadingTree">
                    <Icon type="ios-loading" size=18 class="demo-spin-icon-load"></Icon>
                    <div>加载中</div>
                </Spin>
            </div>
            <div slot="footer">
                <Button type="primary" @click="handleSubmit()" :loading="loading">提交</Button>
                <Button @click="()=>{this.permissionModal = false;}" style="margin-left: 8px" :disabled="loading">取消
                </Button>
            </div>
        </Modal>
    </div>
</template>

<script>
    export default {
        name: 'Role',
        data() {
            return {
                permissionModal: false,
                selectRoleName: '',
                selectRoleId: null,
                // 当前勾选的树数据
                selectTreeItem: null,
                loadingTree: false,
                loading: false,
                // 树结构数据
                tree: [],
                treeOptions: {
                    showCheckbox: true,
                    multiple: true,
                    allowBatch: true
                },
                formGrid: {
                    options: {
                        url: {
                            page: '/ueboot/role/page',
                            save: '/ueboot/role/save',
                            delete: '/ueboot/role/delete'
                        }
                    },
                    toolbar: {
                        refresh: {
                            show: false
                        },
                        delete: {
                            show: false
                        },
                        superFilter: {
                            columns: [
                                {type: 'text', name: 'name', label: '角色名称'}
                            ]
                        }
                    },
                    form: {
                        modal: {
                            title: '角色管理'
                        },
                        columns: [
                            {label: '角色名称', type: 'text', name: 'name', required: true},
                            {label: '角色描述', type: 'text', name: 'description'},
                            {
                                label: '是否启用',
                                type: 'select',
                                name: 'available',
                                required: true,
                                data: [{'name': '启用', 'value': 'true'}, {'name': '不启用', 'value': 'false'}],
                                init: 'true'
                            }
                        ]
                    },
                    table: {
                        rowClick:(row,index)=>{
                            this.$refs["formGrid"].$refs["dataTable"].toggleSelect(index)
                        },
                        operation: {
                            primaryKey: 'id',
                            buttons: [
                                {
                                    show: true,
                                    'label': '授权',
                                    'theme': 'primary',
                                    click: (row, index, _this) => {
                                        this.permissionModal = true;
                                        this.selectRoleName = row['name'];
                                        this.selectRoleId = row['id'];
                                        this.fetchTreeData();
                                    }
                                }, {
                                    key: 'edit',
                                    show: true,
                                    'label': '编辑',
                                    'theme': 'primary',
                                    click: (row, index, _this) => {
                                        _this.optEditClick(row, index);
                                    }
                                }, {
                                    key: 'delete',
                                    show: true,
                                    'label': '删除',
                                    'theme': 'primary',
                                    ghost: true,
                                    click: (row, index, _this) => {
                                        _this.optDeleteClick(row, index);
                                    }
                                }
                            ]
                        },
                        columns: [
                            {title: 'ID', key: 'id', minWidth: 60},
                            {title: '角色名称', key: 'name', minWidth: 160},
                            {title: '角色描述', key: 'description', minWidth: 100},
                            {
                                title: '是否启用',
                                key: 'available',
                                minWidth: 120,
                                fieldFormat: (value, row) => {
                                    if (value) {
                                        return '是';
                                    } else {
                                        return {value: '否', cellClassName: 'table-cell-red'};
                                    }
                                }
                            }
                        ]
                    }
                }
            };
        },
        methods: {
            fetchTreeData() {
                let newTree = [];
                this.loadingTree = true;
                let p1 = new Promise((resolve, reject) => {
                    if (this.tree.length !== 0) {
                        newTree = [...this.tree];
                        newTree.forEach((t) => {
                            t.selected = false;
                        });
                        resolve();
                        return;
                    }
                    this.$axios.post('/ueboot/resources/list', {}).then((response) => {
                        if (!response.body) {
                            reject(response);
                            return;
                        }
                        response.body.forEach((t) => {
                            if (t.resourceType === '功能') {
                                t.icon = 'fa fa-file';
                            }
                        });
                        newTree = response.body;
                        resolve();
                    });
                });
                p1.then(() => {
                    this.$axios.post('/ueboot/permission/findByRoleId', {roleId: this.selectRoleId}).then((response) => {
                        this.loadingTree = false;
                        if (response.body && response.body.length > 0) {
                            let resourceIds = [];
                            response.body.forEach((t) => {
                                return resourceIds.push(t.resourceId);
                            });
                            // 循环树节点，设置为选中
                            newTree.forEach((t) => {
                                if (resourceIds.includes(t.id)) {
                                    t.selected = true;
                                }
                            });
                        }
                        this.tree = newTree;
                    });
                });
            },
            handleSubmit() {
                this.loading = true;
                let resourceIds = this.$refs['utree'].getCheckedNodes();
                this.$axios.post('/ueboot/permission/save', {
                    roleId: this.selectRoleId,
                    resourceIds: resourceIds
                }).then((response) => {
                    this.$Message.success('角色授权成功！');
                    this.loading = false;
                    this.permissionModal = false;
                });
            }
        }
    };
</script>
