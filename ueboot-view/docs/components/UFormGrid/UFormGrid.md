# UFormGrid 一个组件实现增删改查的功能
## 使用标签
```html
   <u-form-grid :data="formGrid" :filterParams="filterParams"></u-form-grid>
```

## 最简单的使用方式

```javascript

export default {
    data () {
      return {
        //grid查询条件。在grid查询时会使用到这个对象，将对象当中的值传递到后端
        filterParams:{},
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
