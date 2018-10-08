<template>
    <div>
        <u-form-grid :data="formGrid" ref="child"></u-form-grid>
    </div>
</template>

<script type="application/javascript">
    import Vue from "vue";

    const tree = [
        {id: 1, "name": "根节点1", parentId: null, opened: true},
        {"name": "一级子节点", id: 2, parentId: 1, icon: 'fa fa-check icon-state-success'},
        {"name": "一级子节点", id: 3, parentId: 1},
        {"name": "二级子节点", id: 4, parentId: 2},
        {"name": "二级子节点", id: 5, parentId: 3},
        {id: 6, "name": "根节点2", parentId: null},
        {"name": "一级子节点", id: 7, parentId: 6},
        {"name": "一级子节点", id: 8, parentId: 6},
        {"name": "二级子节点", id: 9, parentId: 7},
        {"name": "二级子节点", id: 10, parentId: 8},

    ]
    const _this = {
        data() {
            return {
                formGrid: {
                    options: {
                        autoLoad:false,
                        url: {
                            /*query: "http://wodewj.oss-cn-shanghai.aliyuncs.com/dev/data.json"*/
                            page: "/ueboot/user/page"
                        }
                    },
                    tips: {"title": "功能提示", "content": "FormGrid是一个结合表单查询、修改、删除等全功能的组件。方便快速实现CRUD功能!"},
                    toolbar: {
                        superFilter: {
                            label: "高级搜索",
                            colNumber: 4,
                            columns: [
                                {
                                    type: "text",
                                    label: "姓名",
                                    name: "name",
                                    icon: "plus",
                                    placeholder: "请填写姓名",
                                    required: true
                                },
                                {
                                    type: "text",
                                    label: "姓名",
                                    name: "name2",
                                    icon: "plus",
                                    placeholder: "请填写姓名2",
                                    required: true
                                },
                                {
                                    type: "daterange",
                                    label: "日期范围",
                                    name: "daterange",
                                    placeholder: "选择日期范围",
                                    option: {},
                                    format: ""
                                },
                                {
                                    type: "date",
                                    label: "选择日期",
                                    name: "date",
                                    placeholder: "选择日期",
                                    option: {},
                                    format: "",
                                },
                                {
                                    type: "datetime",
                                    label: "时间",
                                    name: "datetime",
                                    placeholder: "选择时间",
                                    option: {},
                                    format: "",

                                },
                                {
                                    type: "month",
                                    label: "月份",
                                    name: "month",
                                    placeholder: "选择月份",
                                    option: {},
                                    format: "",
                                },
                                {
                                    type: "datetimerange",
                                    label: "时间范围",
                                    name: "datetimerange",
                                    placeholder: "选择时间范围",
                                    option: {},
                                    format: ""
                                },

                                {
                                    type: "cascader",
                                    label: "景点",
                                    name: "location",
                                    data: [{
                                        value: 'beijing',
                                        label: '北京',
                                        children: [{value: 'gugong', label: '故宫'}, {value: 'tiantan', label: '天坛'}, {
                                            value: 'wangfujing',
                                            label: '王府井'
                                        }]
                                    }]
                                },/*,
                                {
                                    type: "select",
                                    name: "province",
                                    label: "下拉框1",
                                    clearable: true,
                                    filterable: false,
                                    multiple: false,
                                    required:true,
                                    init:'hehe',
                                    onChange: (value) => {
                                        console.log(value)
                                        this.resetSuperFilter(value)
                                    },
                                    data: [{name: "哈哈", value: "haha"}, {name: "呵呵", value: "hehe"}]
                                },
                                {
                                    type: "select",
                                    name: "province2",
                                    label: "下拉框2",
                                    clearable: true,
                                    filterable: false,
                                    multiple: false,
                                    onChange: (value) => {
                                        console.log("province2",value)
                                    },
                                    data: [{name: "北京", value: "haha"}, {name: "上海", value: "hehe"}]
                                },*/
                                {
                                    type: "treeSelect",
                                    name: "province3",
                                    label: "树状下拉框",
                                    tree: tree,
                                },
                                {
                                    type: "compactColorPicker",

                                    name: "compactColorPicker",
                                    init:'#AB149E',
                                    label: "颜色",
                                },
                            ],
                        },
                        button: [
                            {
                                theme: "success", label: "自定义按钮1", icon: "plus", click: (selections) => {
                                    console.log("自定义按钮1,%o", selections)
                                    this.reload();

                                }
                            },
                            {
                                theme: "success", label: "自定义按钮2", icon: "plus", click: function (selections) {
                                    console.log("自定义按钮2,%o", selections)
                                }
                            },
                            {
                                label: "导出全部",key:'exportAllData'
                            },
                        ],
                        buttons: {
                            theme: "primary", label: "更多操作", icon: "plus", items: [
                                {
                                    label: "导出d", icon: "plus", click: function (selections) {
                                        this.defaultExport("exportTable", "导出数据");
                                    }
                                },
                                {
                                    label: "定制化按钮", icon: "plus", click: function (selections) {
                                        console.log("定制化按钮,%o", selections)
                                    }
                                },
                            ]
                        },
                        groups: {
                            show: true,
                            export: {
                                label: '导出全部',
                                show: true,
                                fileName: '数据导出文件',
                                columns: [
                                    {title: 'ID', key: 'roleId', minWidth: 100},
                                    {title: '姓名', key: 'roleName', minWidth: 80},
                                    {title: '工号', key: 'roleNo', minWidth: 100},
                                    {title: '部门名称', key: 'deptName', minWidth: 350},
                                    {title: '辖属行政区', key: 'secGovernDeptNum', minWidth: 130}
                                ]
                            }
                        },
                    },
                    form: {
                        modal: {
                            title: "个人信息"
                        },
                        columns: [
                            {
                                "label": "渠道",
                                "type": "text",
                                "name": "channel",
                                rule: {},
                                prepend: "http://",
                                append: ".com",
                                add: {
                                    show: false
                                },
                                edit: {
                                    show: true, disabled: true
                                },
                                view: {
                                    show: false
                                }
                            },
                            {"label": "用户名", "type": "text", "name": "username", required: true},
                            /* {"label": "消息", "type": "select", "name": "message",data:"im:性别", required: true,init:"男"},*/
                            {"label": "消息", "type": "text", "name": "message"},
                            {"label": "消息", "type": "textarea", "name": "message1", "rows": 3},
                            {
                                "label": "类型",
                                "type": "select",
                                "name": "sender",
                                required: true,
                                onChange: (value) => {
                                    console.log(value)
                                    this.resetFormField(value)
                                },
                                data: [{name: "哈哈", value: "haha"}, {name: "呵呵", value: "hehe"}]
                            },
                            {"label": "toUser", "type": "text", "name": "toUser", disabled: true},
                            {
                                "label": "最大接待量",
                                "type": "number",
                                "min": 1,
                                "max": 10,
                                "name": "sessionId",
                                init: 3,
                                editFormat: (value) => {

                                }
                            },
                            /*{
                              "label": "类型名称",
                              "type": "cascader",
                              "name": "typeName",
                              data: 'url:/admin/api/knowledgeType/find',
                              onChange: (value, data) => {
                                //console.log("####onchange,%o",value);
                                this.$refs.child.$emit('setFormFieldData', "typeName", value.join("/"));
                              },
                              required: true
                            },*/


                        ]
                    },
                    pageable: {
                        page: 1,
                        size: 100000,
                    },
                    table: {
                        rowClick:(row,index)=>{
                            this.$log.d(row)
                        },
                        noDataText: '已努力查询，但还是没找到！',
                        tableLoadingText:'正在努力为您加载数据,请稍候1...',
                        tableLoadedErrorText:'数据查询出现异常，需要管理员查看后台日志，寻找原因1。',
                        operation: {
                            primaryKey: "createTime",
                            buttons: [{
                                show: true, "label": "查看", "theme": "primary", click: (_this, row, index) => {
                                    _this.optViewClick(row, index);
                                }
                            }, {
                                show: true, "label": "编辑", "theme": "primary", click: (_this, row, index) => {
                                    _this.optEditClick(row, index);
                                }
                            }, {
                                show: true, "label": "删除", "theme": "default", click: (_this, row, index) => {
                                    _this.optDeleteClick(row, index);
                                }
                            }],
                        },
                        columns: [
                            {
                                title: '渠道',
                                key: 'channel',
                            },
                            {
                                title: '会话时间',
                                key: 'createTime',

                            },
                            {
                                title: '用户名',
                                key: 'fromUserName',

                            },
                            {
                                title: '消息',
                                key: 'message',

                            },
                            {
                                title: '发送方',
                                key: 'sender',
                            },
                            {
                                title: 'toUser',
                                key: 'toUser',
                            },
                        ]
                    }
                }
            }
        },
        created() {
            /*  this.$Notice.config({
                top: 50,
                duration: 300
              });*/
        },
        methods: {
            resetSuperFilter(value) {
                console.log("--------change###,%o", value)
                this.formGrid.toolbar.superFilter.columns.forEach((c) => {
                    if (value==='haha'&&c.name === 'province2') {
                      c.data = [{name: "上海", value: "haha1"}, {name: "北京", value: "hehe1"}]
                    }else if (value==='hehe'&&c.name === 'province2') {
                        c.data = [{name: "天津", value: "haha1"}, {name: "广州", value: "hehe1"}]
                    }
                })
            },
            resetFormField(value) {
                if (value === 'haha') {
                    this.formGrid.form.columns.forEach((c) => {
                        if (['username'].includes(c.name)) {
                            c.label = '修改后的用户名'
                            c.required = false
                        }
                    })
                } else {
                    this.formGrid.form.columns.forEach((c) => {
                        if (['username'].includes(c.name)) {
                            c.label = '用户名'
                            c.required = true
                        }
                    })
                }
            },

            reload() {
                this.$refs.child.$emit('reloadData');
                console.log("reload");
            }
        }
    }
    export default _this
</script>
