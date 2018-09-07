# ueboot 框架使用示例说明

https://docsify.js.org/#/zh-cn/configuration

## 0.发布版本记录
 - 参见 [update.md](update.md)文件

## 1. 安装

```shell
    npm install ueboot --save-dev
```
### 1.1 更新版本
  当有新版本发布后，本地需要更新时，执行如下命令
  ```shell
    npm update ueboot
  ```
## 2. 安装依赖
 如果缺少其他依赖，请根据提示进行install
 
## 3. 使用
在src/main.js当中增加如下代码
```javascript

import iView from 'iView'
import ueboot from 'ueboot'

/* ueboot封装过的基于iview的自定义样式，也可以引入自己的自定义样式 */
import 'ueboot/ue-theme/dist/iview.css'

/* icon字体图标样式 */
import "font-awesome/css/font-awesome.css"

Vue.use(iView)
Vue.use(ueboot)
//baseURL 为axios异步请求的上下文。unauthorizedUrl为权限校验未通过后页面跳转的地址
ueboot.Axios.init({ baseURL: process.env.CONTEXT, unauthorizedUrl: process.env.CONTEXT_HTML + '/#/login' })

```
## 4.组件
### 4.1 UTree 树
```html
 <u-tree :tree="tree" @itemClick="itemClick" :sort="sort"></u-tree>

```
```javascript
     export default {
       data () {
         return {
           //树结构数据，要求至少三个字段，且字段名称与下面的一致
           tree: [
             {id: 1, "name": "根节点", parentId: null},
             {"name": "一级子节点", id: 2, parentId: 1},
             {"name": "一级子节点", id: 3, parentId: 1},
             {"name": "二级子节点", id: 4, parentId: 2},
             {"name": "二级子节点", id: 5, parentId: 3},
           ],
           selectTreeItem: null,//当前选择的树节点,
           //排序（可选值），sort可选值为:desc|asc  field要排序的字段，建议为Number类型的字段。
           sort:{"sort":"desc","field":"id"}
         }
       },
       methods: {
         itemClick(item,parentItem){
           this.selectTreeItem = item;
         },
       }
     } 

```
### 4.2 UFormGrid 一个组件实现增删改查的功能
```html
   <u-form-grid :data="formGrid"></u-form-grid>
```

  - 最简单的使用方式

```javascript

export default {
    data () {
      return {
        formGrid: {
          options: {
            url: {
              page: "/api/cors/chat/session/findMessage"
            }
          },
          tips: {"title": "功能提示", "content": "FormGrid是一个结合表单查询、修改、删除等全功能的组件。方便快速实现CRUD功能!"},
          form: {
            modal: {
              title: "个人信息"
            },
            columns: [
              {"label": "渠道", "type": "text", "name": "channel", required: true},
              {"label": "用户名", "type": "text", "name": "createTime", required: true},
              {"label": "消息", "type": "text", "name": "message", required: true},
              {"label": "发送方", "type": "text", "name": "sender", required: true},
              {"label": "toUser", "type": "text", "name": "toUser", required: true},
            ]
          },
          table: {
            operation: {
              //调用查看、编辑、删除接口时，需要传递的参数主键名称，可以有多个。默认为id
              primaryKey: "id",
            },
            columns: [
              {
                title: '渠道',
                key: 'channel',
                fixed: 'left',
                width: 100,
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
    }
  }
```
## 4.3 UFormGrid使用说明
### 4.3.1 默认设置 ，使用方可以根据这个配置，修改默认值，只要定义了对应的属性，就会替换默认值
```javascript
let formGrid = {
    options: {autoLoad: true, url: {
        page: "",//记录查询地址，必须使用分页查询并返回分页结果
        save: "",//记录添加和修改提交的地址，如果提交的对象有id则为修改
        delete: "",//记录删除的提交地址，通过提交id的集合来实现一个或者多个记录
        get:"",//查看和编辑时用于查询对象的接口，参数为定义的keys
      }},
      tips: {"title": "", "content": ""},
      toolbar: {
        refresh: {
          show: true, label: "刷新"
        },
        create: {
          show: true, label: "添加"
        },
        delete: {
          show: true, label: "删除"
        },
        buttons: [],
        filter: {
          show: true,
          name: "keyWord",
          placeholder: "根据关键字搜索"
        },
        superFilter: {
          label: "高级搜索",
          items: [],
          submit: {theme: "success", label: "查询", icon: "plus"}
        }
      },
      page: {
        pageNumber: 1,
        pageSize: 10,
        total: 0,
      },
      //表单提交与修改
      form: {
        name: "formName",//表单名称，如果同一个页面存在多个表单时，需要指定不同的名称
        //label对齐方式
        labelPosition: "right",
        //label的宽度
        labelWidth: 80,
        //每行显示的个数
        colNumber: 2,
        modal: {
          title: "",//表单标题，默认为添加『查看、添加、修改』两个字.如果为空，则不显示标题
          showClose: true,//是否显示关闭按钮，默认为true
          width: "",//表单弹出框宽度
        },
        columns: [],
        //表单提交之前（校验之后），返回如果为false,则不继续执行后续操作
        submitBefore: function (data) {
          return true;
        },
        //表单提交之后
        submitAfter: function (response) {
          return true;
        },
        //点击取消时的操作，默认为关闭窗口
        onCancel: function () {
    
        },
        data: {}//表单提交的参数
      },
}
```
### 4.3.2 options 可用参数
```javascript
 options: {
      autoLoad: true,//初始化时查询数据，默认为true
      url: {
        query: "",//记录查询地址，必须使用分页查询并返回分页结果
        submit: "",//记录添加和修改提交的地址，如果提交的对象有id则为修改
        delete: "",//记录删除的提交地址，通过提交id的集合来实现一个或者多个记录
        get: "",//查看和编辑时用于查询对象的接口，参数为定义的keys
      }
    }  
```
- 参数说明
  - autoLoad 是否让页面渲染的时候就去加载table内容。查询的条件为toolbar里面的filter\superFilter指定的参数
  - url   所有地址不要带上下文，上下文在Ajax.config当中配置
    - query: "",//记录查询地址，必须使用分页查询并返回分页结果（返回的格式必须经过Response对象包装）
    - submit: "",//记录添加和修改提交的地址。后台自行根据属性的值来判断是修改还是新增
    - delete: "",//记录删除的提交地址，通过提交id的集合来实现一个或者多个记录
    - get: "",//查看和编辑时用于查询对象的接口，参数为table.operation.keys当中定义的素组。只有当设定的table.operation.remote为true时才有用。


### 4.3.3 tips 可用参数
当前页面的功能说明
```javascript
tips: {"title": "功能提示", "content": "功能提示内容"}

```
  当title为空时，页面不显示tip区域

### 4.3.4 toolbar可以自定义的内容
#### 固定的前面三个按钮（刷新、删除、添加），可以设置是否显示，背景颜色、图标、文字。示例，设置不显示删除按钮
```javascript
toolbar:{
    delete : {
        show :false
    }
}

```

#### 4.3.4.1 button字段定义格式
 ```javascript

    { theme: "success", label: "自定义按钮1", icon: "plus", click: () => {
          console.log("点击事件");
        }
    }
    
```
- 字段说明
  - theme 按钮风格，对应iview里面的type
  - label 按钮文字
  - icon  按钮图标的class名字，可以直接用iview提供的图标名称，也可以使用font-awsome提供的
  - click 点击事件
  - 更多属性参见 iview的Button 定义 https://www.iviewui.com/components/button

#### 4.3.4.2 buttons (当按钮过多时，可以通过一个下拉菜单的形式展示更多的操作)
```javascript
  buttons: {
          theme: "primary", label: "更多操作", icon: "plus", items: [
            {
              label: "导出", icon: "plus", click: function (data) {
              console.log("点击事件");
            }
            },
            {
              label: "定制化按钮", icon: "plus", click: function (data) {
              console.log("点击事件");
            }
            },
          ]
        }

```

#### 4.3.4.3 filter 关键字搜索框
```javascript
    //关键字搜索框
      filter: {
        show: true,
        name: "keyWord",
        placeholder: "根据关键字搜索"
      },
      
```
- 字段说明
    - show 是否显示，默认为true
    - name 提交到后台的参数名称，默认为keyWord,后台根据这个字段去做查询条件。默认会放到page对象当中。
    - placeholder 输入框文字提示
  
### 4.3.4.4 superFilter 高级搜索功能
```javascript
  superFilter: {
        label: "高级搜索",
        columns: [
          {type: "text", label: "姓名", name: "name", icon: "plus", placeholder: "请填写姓名"},
          {type: "text", label: "地址", name: "address", icon: "plus", placeholder: "请填写地址"},
          {type: "text", label: "电话", name: "mobile", icon: "plus", placeholder: "请填写电话"},
          {type: "date", label: "日期", name: "date", placeholder: "选择日期", option: {}, format: ""},
          {type: "daterange", label: "日期范围", name: "daterange", placeholder: "选择日期范围", option: {}, format: ""},
          {type: "datetime", label: "时间", name: "datetime", placeholder: "选择时间", option: {}, format: ""},
          {type: "datetimerange", label: "时间范围", name: "datetimerange", placeholder: "选择时间范围", option: {}, format: ""},

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
          },
          {
            type: "select",
            name: "province",
            label: "创建",
            clearable: true,
            filterable: false,
            multiple: false,
            data: [{label: "哈哈", value: "haha"}, {label: "呵呵", value: "hehe"}]
          },
        ],
        submit: {theme: "success", label: "查询", icon: "plus"}
      
    }

```

- 字段说明
  - label 用于显示的文字
  - columns 点击label后弹出的搜索界面，如果改数组为空，则不会显示label。columns里面的定义格式与form表单里面定义的格式一致。
    详细的字段属性参见iview的定义。支持如下类型的组件，每个组件的属性定义参考iView的定义
    - text 对应iview的 Input
    - date
    - daterange
    - datetime
    - datetimerange
    - cascader
    - select
   - submit 提交按钮定义，可以设置的属性参考button的定义
    

### 4.3.5 form 可用的参数
  form 用于点击『添加』和【编辑】时弹出一个模态框，可以进行数据添加。
 - 参数
 ```javascript

 //表单提交与修改
    form: {
      name: "",//表单名称，如果同一个页面存在多个表单时，需要指定不同的名称
      //label对齐方式
      labelPosition: "right",
      //label的宽度
      labelWidth: "80",
      //每行显示的个数
      colNumber: 2,
      //模态窗口相关配置
      modal: {
        title: "",//表单标题，默认为添加『查看、添加、修改』两个字.如果为空，则不显示标题
        showClose: true,//是否显示关闭按钮，默认为true
        width: "",//表单弹出框宽度
      },
      //表单要显示的元素
      columns: [
        {"label": "姓名", "type": "text", "name": "name", required: true},
        {"label": "年龄", "type": "text", "name": "age", required: true},
        {
          "label": "省份", "type": "select", "name": "provice",
          items: [{"name": "上海市", "value": "上海市"}, {"name": "北京市", "value": "北京市"},],
          rule: {required: true, message: '请选择省份', trigger: 'change'}
        },
      ],
      //表单提交之前（校验之后），返回如果为false,则不继续执行后续操作
      submitBefore: function () {

      },
      //表单提交之后
      submitAfter: function () {

      },
      //点击取消时的操作，默认为关闭窗口
      onCancel: function () {

      }
    }
  
```

#### 4.3.5.1 columns的作用和配置
 form.columns用于渲染添加、查看、编辑按钮弹出的模态窗口的表单使用。支持各种表单元素类型，可以自定义规则、设置初始值
 针对select类型可以设置各种场景的数据获取方式。
 - label :表单元素的标题
 - name :提交到后台的字段名称
 - type :表单元素的类型，支持的格式如下：
    - text 对应iview的 Input
    - date
    - password
    - daterange
    - datetime
    - datetimerange
    - cascader
    - select 下拉框初始值，可以通过一下三种方式获取
      - data:
        - [{"name": "上海市", "value": "上海市"}, {"name": "北京市", "value": "北京市"}] 直接定义个数组
        - "im:typeName"，查询数据字典，其中typeName为要查下的数据库字典类型名称
        - "url:http://www.baidu.com" 查询接口获取数据，其中url:之后的内容为接口查询地址。
      
    
    
#### 4.3.5.2 针对添加、编辑、查看表单需要自定义表单元素的做法
在定义表单的columns时，可以增加如下选项用于控制在不同功能内显示不同的表单元素
```javascript
{
                "label": "渠道", "type": "text", "name": "channel", rule: {}, prepend: "http://", append: ".com",
                add: {
                  show: false
                },
                edit: {
                  show: true, disabled: true
                },
                view: {
                  show: false
                }
              }
```
- add 表单添加时的控制，可以为空，默认show为true,disabled为false.
- edit 表单编辑时候控制
- view 表单查看时候控制。
以上三个属性，可以都为空。


### 4.3.6 table 可用的参数
  table表格现在完全使用iview的组件，除了自定义增加了最后一列的操作功能（默认自带，可以去掉）。没有额外增加定义的内容。
  
```javascript
table: {
      width: "",//默认为100%
      height: "",//默认为100%
      //是否显示复选框
      showCheckbox:true,
      //操作列有关参数
      operation: {
        //调用查看、编辑、删除接口时，需要传递的参数主键名称，可以有多个。默认为id
        primaryKey: "id",
        //是否显示操作列
        show: true,
        //查看、编辑功能是否通过远程查询方式获取。默认为false直接用当前行数据展示
        remote: false,
        //操作按钮事件，针对查看、编辑、删除的事件。定义了事件则使用自定义事件
        events: {},
        //默认的操作列内容。可以通过复写这个来重写自己的操作列
        column: {
          title: '操作',
          key: 'action',
          fixed: 'right',
          width: 160,
          render: (h, params) => {
            return h('div', [
              h('Button', {
                props: {
                  type: 'text',
                  size: 'small'
                },
                on: {
                  click: () => {
                    let row = this.formGrid.table.data[params.index];
                    if (this.formGrid.table.optEvents.view) {
                      this.formGrid.table.optEvents.view(row, params.index);
                    }
                  }
                }
              }, '查看'),
              h('Button', {
                props: {
                  type: 'primary',
                  size: 'small'
                },
                style: {
                  marginRight: '5px'
                },
                on: {
                  click: () => {
                    if (this.formGrid.table.optEvents.edit) {
                      this.formGrid.table.optEvents.edit(params.index);
                    } else {
                      let params = this.getKeys() || "";
                      if (params === "") {
                        return;
                      }
                      //查询接口获取数据，弹出模态框

                    }
                  }
                }
              }, '编辑'),
              h('Button', {
                props: {
                  type: 'error',
                  size: 'small',
                },
                style: {},
                on: {
                  click: () => {
                    if (this.formGrid.table.optEvents.delete) {
                      this.formGrid.table.optEvents.delete(params.index);
                    } else {
                      let row = this.formGrid.table.data[params.index];
                      this.$Modal.confirm({
                        title: '删除数据',
                        content: '<span style="color:red">确定删除该记录吗？</span>',
                        onOk: () => {
                          let params = this.getKeys() || "";
                          if (params === "") {
                            return;
                          }
                          Ajax.post(this.formGrid.options.url.delete, params).then(response => {
                            this.$Loading.finish();
                            this.reloadData();
                          }, error => {
                            this.$Message.error("数据删除失败!" + error.body);
                          });
                        },
                        onCancel: () => {
                          this.$Message.info('点击了取消');
                        }
                      });
                    }
                  }
                }
              }, '删除')
            ]);
          }
        }
      },
    }

```
- 参数说明
  - width 表格宽度
  - height 表格高度
  - showCheckbox 是否在第一列显示复选框
  - operation 最后一列的操作列
    - primaryKey: 字符串类型用于做删除、查看、编辑时调用后台接口传递参数使用。这里的字段来自当前点击的那一行的对象。
    - show: 是否显示操作列
    - remote: false ,查看、编辑功能是否调用远程接口进行查询。
    - events: 操作按钮事件，针对查看、编辑、删除的事件。定义了事件则使用自定义事件，默认事件不再执行。
      - view 点击查看时候的事件
      - edit 点击编辑时候的事件
      - delete 点击删除时候的事件
    - column: 最后一列的操作事件，属性定义参考iview table当中对column的定义
    

###  4.3.7 page 可用的参数
```javascript
    page: { //分页数据，默认为从第一页开始，每页大小为10条
        pageNumber: 1,
        pageSize: 10
      }
```
    

###  4.3.8 一个比较完整的示例
完整的示例当中声明的属性，不一定要全部写出来，有些默认值可以不需要定义。
 
 ```javascript

/**
 * Created by yangkui on 17/7/5.
 */

/**
 * FormGrid参数示例代码
 * @type {{formGrid: {}}}
 */
const demo = {
  data: {
    //整个组件的参数设置
    options: {
      autoLoad: true,//初始化时查询数据，默认为true
      url: {
        page: "",//记录查询地址，必须使用分页查询并返回分页结果
        save: "",//记录添加和修改提交的地址，如果提交的对象有id则为修改
        delete: "",//记录删除的提交地址，通过提交id的集合来实现一个或者多个记录
        get: "",//查看和编辑时用于查询对象的接口，参数为定义的keys
      }
    },
    //文字提示，如果title存在则显示，content可以为空
    tips: {"title": "功能提示", "content": "功能提示内容"},
    //工具栏，每个里面可以有一个button对象，属性为iView Button属性，外加click事件
    toolbar: {
      refresh: {
        show: true,
        theme: "success", label: "刷新", icon: "plus"
      },
      create: {
        show: true,
        theme: "success", label: "添加", icon: "plus"
      },
      delete: {
        show: true,
        theme: "success", label: "删除", icon: "plus"
      },
      button: [
        {
          theme: "success", label: "自定义按钮1", icon: "plus", click: () => {
          console.log("点击事件");
        }
        },
        {
          theme: "success", label: "自定义按钮2", icon: "plus", click: () => {
          console.log("点击事件");
        }
        }
      ],
      buttons: {
        theme: "primary", label: "更多操作", icon: "plus", items: [
          {
            label: "导出", icon: "plus", click: function (data) {
            console.log("点击事件");
          }
          },
          {
            label: "定制化按钮", icon: "plus", click: function (data) {
            console.log("点击事件");
          }
          },
        ]
      }
      ,
      //关键字搜索框
      filter: {
        show: true,
        name: "keyWord",
        placeholder: "根据关键字搜索"
      },
      superFilter: {
        label: "高级搜索",
        columns: [
          {type: "text", label: "姓名", name: "name", icon: "plus", placeholder: "请填写姓名"},
          {type: "text", label: "地址", name: "address", icon: "plus", placeholder: "请填写地址"},
          {type: "text", label: "电话", name: "mobile", icon: "plus", placeholder: "请填写电话"},
          {type: "date", label: "日期", name: "date", placeholder: "选择日期", option: {}, format: ""},
          {type: "daterange", label: "日期范围", name: "daterange", placeholder: "选择日期范围", option: {}, format: ""},
          {type: "datetime", label: "时间", name: "datetime", placeholder: "选择时间", option: {}, format: ""},
          {type: "datetimerange", label: "时间范围", name: "datetimerange", placeholder: "选择时间范围", option: {}, format: ""},

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
          },
          {
            type: "select",
            name: "province",
            label: "创建",
            clearable: true,
            filterable: false,
            multiple: false,
            data: [{label: "哈哈", value: "haha"}, {label: "呵呵", value: "hehe"}]
          },
        ],
        submit: {theme: "success", label: "查询", icon: "search",long:true}

      }
    },
    //表单提交与修改
    form: {
      name: "",//表单名称，如果同一个页面存在多个表单时，需要指定不同的名称
      //label对齐方式
      labelPosition: "right",
      loading:false,//提交按钮loading状态

      //label的宽度
      labelWidth: 80,
      //每行显示的个数
      colNumber: 2,
      //模态窗口相关配置
      modal: {
        title: "",//表单标题，默认为添加『查看、添加、修改』两个字.如果为空，则不显示标题
        showClose: true,//是否显示关闭按钮，默认为true
        width: "",//表单弹出框宽度
      },
      //表单要显示的元素
      columns: [
        {"label": "姓名", "type": "text", "name": "name", required: true},
        {"label": "年龄", "type": "text", "name": "age", required: true},
        {
          "label": "省份", "type": "select", "name": "provice",
          items: [{"name": "上海市", "value": "上海市"}, {"name": "北京市", "value": "北京市"},],
          rule: {required: true, message: '请选择省份', trigger: 'change'}
        },
      ],
      //表单提交之前（校验之后），返回如果为false,则不继续执行后续操作
      submitBefore: function () {

      },
      //表单提交之后
      submitAfter: function () {

      },
      //点击取消时的操作，默认为关闭窗口
      onCancel: function () {

      }
    },
    //结果集，所有属性为iview的Table属性（除data外），默认封装了，数据查询、分页、查看、编辑、删除功能。
    table: {
      width: "",//默认为100%
      height: "",//默认为100%
       //是否显示复选框
      showCheckbox:true,
      //iview当中定义table要用的columns具体参考iview文档
      columns:[],
      //操作列有关参数
      operation: {
         //调用查看、编辑、删除接口时，需要传递的参数主键名称，可以有多个。默认为id
        primaryKey: "id",
        //是否显示操作列
        show: true,
        //查看、编辑功能是否通过远程查询方式获取。默认为false直接用当前行数据展示
        remote: false,
        //操作按钮事件，针对查看、编辑、删除的事件。定义了事件则使用自定义事件
        events: {},
        //默认的操作列内容。可以通过复写这个来重写自己的操作列
        column: {
          title: '操作',
          key: 'action',
          fixed: 'right',
          width: 160,

        }
      },
    },
    page: { //分页数据，默认为从第一页开始，每页大小为10条
      pageNumber: 1,
      pageSize: 10
    }
  }
};





```



### 4.3 ajax工具类
本组件的formGrid需要用到ajax请求，所以顺便提供了ajax功能。主要基于vue-resource进行了封装
- 使用方式，在任意vue文件里面可以直接使用
```javascript
    //最简单的使用（后端接口使用配套的后台框架io.dabing）
    this.$ajax.post("/api/",{}).then((response)=>{
        //处理成功返回值
    })
    
    //处理异常
   this.$ajax.post("/api/",{}).then((response)=>{
        //处理成功返回值
    }).then((error)=>{
      //处理异常
    })
```
- 全局配置请求路径的上下文、未登录的情况下跳转到的登录地址
  再main.js当中设置
  

```javascript
 
import {Ajax} from 'ueboot'
//设置上下文，和未登录地址跳转地址
Ajax.config({context: "/console", unauthorizedUrl: "/"});


```

### 4.4 log 工具类
ueboot自带了一个log日志工具，可以通过设定日志级别来统一查看日志，默认为ALL，可以输出所有日志。
使用方式
```javascript
this.$log.d("tag:%o",Object);
this.$log.d("hello");
this.$log.i("hello"); //info级别
this.$log.e("hello");//error级别

```
- 支持一下级别的日志
  - DEBUG
  - INFO
  - ERROR
- 所有参数支持一到两个，可以是字符串，也可以是对象实际使用方式参见console本身的api

- 设置日志级别
在src/main.js当中设置
```javascript
import {Log} from 'ueboot'
//设置上下文，和未登录地址跳转地址
Log.config({level: 2});
//具体的使用参见下面的定义
const level = {
  NONE: 0,
  ERROR: 1,
  INFO: 2,
  DEBUG: 3,
  ALL: 4,
};

```

### 4.5 websocket
  
