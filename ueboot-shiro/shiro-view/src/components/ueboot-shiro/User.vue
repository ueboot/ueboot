<template>
  <div>
    <u-form-grid :data="formGrid" ref="formGrid"></u-form-grid>
    <Modal v-model="resetPwdModal">
      <p slot="header">
        请注意，正在对<span style="color:red">{{formCustom.userName}}</span> 用户进行密码重置操作！
      </p>
      <Form ref="formCustom" :model="formCustom" :rules="ruleCustom" :label-width="80">
        <FormItem label="新密码" prop="password" required>
          <i-input type="password" v-model="formCustom.password"></i-input>
        </FormItem>
        <FormItem label="确认密码" prop="passwdCheck">
          <i-input type="password" v-model="formCustom.passwdCheck"></i-input>
        </FormItem>
      </Form>
      <div slot="footer">
        <Button type="primary" @click="handleSubmit('formCustom')">提交</Button>
        <Button @click="handleReset('formCustom')" style="margin-left: 8px">重置</Button>
      </div>
    </Modal>
  </div>
</template>

<script>
  export default {
    name: 'User',
    data() {
      const validatePassCheck = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('确认密码为必填'));
        } else if (value !== this.formCustom.password) {
          callback(new Error(`两次输入的内容不一致!`));
        } else {
          callback();
        }
      }
      return {
        resetPwdModal: false,
        formCustom: {
          id: null,
          userName:'',
          password: '',
          passwdCheck: '',
        },
        ruleCustom: {
          password:[{required: true, message: '密码为必填', trigger: 'blur',type:'string'}],
          passwdCheck: [{type: 'string', trigger: 'blur', required: true},
            {validator: validatePassCheck, trigger: 'blur'}
          ],
        },
        formGrid: {
          options: {
            url: {
              page: '/ueboot/user/page',
              save: '/ueboot/user/save',
              delete: '/ueboot/user/delete'
            }
          },
          form: {
            labelWidth: 120,
            modal: {
              title: '用户管理'
            },
            columns: [
              {label: '用户名', type: 'text', name: 'userName', required: true, edit: {disabled: true},},
              {label: '密码', type: 'password', name: 'password', required: true, view: {show: false},edit: {show: false}},
              {
                label: '重复密码', type: 'password', name: 'password2', required: true, view: {show: false},edit: {show: false}
                , equalsTo: 'password'
              },
              {
                label: '是否被锁', type: 'select', name: 'locked',
                data: [{'name': '是', 'value': 'true'}, {'name': '否', 'value': 'false'}], init: 'false'
              },
              {label: '密码过期时间', type: 'datetime', name: 'credentialExpiredDate'}

            ]
          },
          table: {
            operation: {
              primaryKey: 'id',
              buttons: [
                {
                  key: "view", show: true, "label": "查看", "theme": "primary", click: (row, index, _this) => {
                    _this.optViewClick(row, index);
                  }
                }, {
                  key: "edit", show: true, "label": "编辑", "theme": "primary", click: (row, index, _this) => {
                    _this.optEditClick(row, index);
                  }
                }, {
                  key: "delete", show: true, "label": "删除", "theme": "default", click: (row, index, _this) => {
                    _this.optDeleteClick(row, index);
                  }
                }, {
                  show: true, "label": "重置密码", "theme": "primary", click: (row, index, _this) => {
                    //这里写自己的业务逻辑，比如弹出窗口进行表单录入
                    this.resetPwdModal = true
                    this.formCustom.id = row['id']
                    this.formCustom.userName = row['userName']
                  }
                }
              ]
            },
            columns: [
              {title: 'id', key: 'id', minWidth: 60, align: 'center'},
              {title: '用户名', key: 'userName', minWidth: 140},
              {
                title: '是否被锁', key: 'locked', minWidth: 80, align: 'center', fieldFormat: (value, row) => {
                  if (value) {
                    return {value: '是', cellClassName: 'table-cell-red'}
                  } else {
                    return '否'
                  }
                }
              },
              {title: '密码过期时间', key: 'credentialExpiredDate', minWidth: 200, align: 'center'}
            ]
          }
        }
      }
    },
    methods: {
      handleSubmit(name) {
        this.$refs[name].validate((valid) => {
          if (valid) {
            this.$axios.post("/ueboot/user/save", this.formCustom).then((response) => {
              this.resetPwdModal = false
              this.$Message.success('密码重置成功！');
            })
          } else {
            this.$Message.error('表单校验失败');
          }
        })
      },
      handleReset(name) {
        this.$refs[name].resetFields();
      }
    }
  }
</script>
