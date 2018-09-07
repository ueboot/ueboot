<template>
  <u-form-grid :data="formGrid"></u-form-grid>
</template>
<script>
  //    import common from '../../utils/staticCommon'
  export default {
    data () {
      //校验账号
      let validateUserName = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('账号不能为空'));
        }
        if (!/^[a-zA-Z0-9_\s]*$/.test(value)) {
          return callback(new Error('账号只能包含数字，字母或下划线组合'));
        } else {
          /* this.$ajax.post("/admin/api/customer/find",{userName:JSON.parse(sessionStorage.getItem(common.storage.user)).code+":"+value}).then((data)=>{
           if(data.body && data.body != null && data.body.id != undefined){
           return  callback(new Error('账号已存在'));
           }else{
           callback();
           }
           });*/
        }
      };
      //校验姓名
      let validateRealName = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('姓名不能为空'));
        }
        if (!/^[\u4e00-\u9fa5]*$/.test(value)) {
          return callback(new Error('姓名必须为中文'));
        }
        return callback();
      };
      //校验手机号
      let validateMobile = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('手机号不能为空'));
        }
        if (!/^(13|14|15|18|17)[0-9]{9}/.test(value)) {
          return callback(new Error('手机号格式错误'));
        }
        return callback();
      };


      return {
        formGrid: {
          options: {
            url: {
              page: "/admin/api/customer/page",//记录查询地址，必须使用分页查询并返回分页结果
              save: "/admin/api/customer/save",//记录添加和修改提交的地址，如果提交的对象有id则为修改
              delete: "/admin/api/customer/delete",//记录删除的提交地址，通过提交id的集合来实现一个或者多个记录
              get: "/admin/api/customer/find",//查看和编辑时用于查询对象的接口，参数为定义的keys
            }
          },
          tips: {"title": "功能提示", "content": "展示当前系统所有的客服基本信息，可以对相关客服进行相关操作"},
          //工具栏，每个里面可以有一个button对象，属性为iView Button属性，外加click事件
          toolbar: {
            //关键字搜索框
            filter: {
              show: true,
              name: "keyWord",
              placeholder: "根据关键字搜索"
            },
            superFilter: {
              label: "高级搜索",
              columns: [
                {type: "text", label: "账号", name: "userName", icon: "plus", placeholder: "请填写账号"},
                {type: "text", label: "姓名", name: "realName", icon: "plus", placeholder: "请填写姓名"},
                {type: "text", label: "电话", name: "mobile", icon: "plus", placeholder: "请填写电话"},

              ],
            }
          },
          form: {
            labelWidth: 100,
            modal: {
              title: "编辑客服"
            },
            columns: [
              {
                "label": "账号",
                "type": "text",
                "name": "userName",
                maxlength: 12,
                prepend: "111",
                rule: {required: true, validator: validateUserName, trigger: 'blur'},
                add: {
                  show: true
                },
                edit: {
                  show: false, disabled: false
                },
                view: {
                  show: true
                }
              },
              {
                "label": "姓名",
                "type": "text",
                "name": "realName",
                maxlength: 5,
                rule: {required: true, validator: validateRealName, trigger: 'change'}
              },
              {"label": "昵称", "type": "text", "name": "nickName", maxlength: 15, required: true},
              {"label": "性别", "type": "select", "name": "sex", required: true, data: "im:性别"},
              {
                "label": "手机",
                "type": "text",
                "name": "mobile",
                maxlength: 11,
                rule: {required: true, validator: validateMobile, trigger: 'change'}
              },
              {
                "label": "邮箱",
                "type": "text",
                "name": "email",
                rule: {required: true, type: "email", message: "邮箱格式错误", trigger: 'change'}
              },
              {
                "label": "初始密码", "type": "text", "name": "password", maxlength: 18, required: true,
                add: {
                  show: true
                },
                edit: {
                  show: false, disabled: false
                },
                view: {
                  show: false
                }
              },
              {"label": "最大接待量", "type": "number", max: 10, min: 1, step: 1, init: 1, "name": "maxReception"},

            ]
          },
          table: {
            operation: {
              primaryKey: ["id"],//调用查看、编辑、删除接口时，需要传递的参数主键名称，可以有多个。默认为id
              remote: false,//查看、编辑功能是否通过远程查询方式获取。默认为false直接用当前行数据展示
              column: {
                title: '操作',
                key: 'action',
                width: 200

              }
            },
            columns: [
              {
                title: '账号',
                key: 'userName',

              },
              {
                title: '姓名',
                key: 'realName',

              },
              {
                title: '昵称',
                key: 'nickName',

              },
              {
                title: '性别',
                key: 'sex',

              },
              {
                title: '手机号',
                key: 'mobile',

              },
              {
                title: '邮箱',
                key: 'email',
              },
              {
                title: '最大接待量',
                key: 'maxReception',
              },

            ]
          }
        },
        sexSelect: [],//性别下拉
      }
    },
    methods: {},

    mounted: function () {

    }
  }
</script>
