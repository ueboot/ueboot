<template>
  <div>
    <u-form-grid :data="formGrid" ref="formGrid"></u-form-grid>
    <Modal v-model="resetPwdModal">
      <p slot="header">
        请注意，您当前正在对<span style="color:red;vertical-align: top">{{formCustom.userName}}</span> 用户进行密码重置操作！
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
        <Button type="primary" @click="handleSubmit('formCustom')" :loading="loading">提交</Button>
        <Button @click="()=>{this.resetPwdModal = false}" style="margin-left: 8px" :disabled="loading">取消</Button>
      </div>
    </Modal>
    <Modal v-model="setRoleModal" size="large">
      <p slot="header">
        请注意，您当前正在对<span style="color:red;vertical-align: top">{{formCustom.userName}}</span> 用户进行角色分配操作！
      </p>
      <Form ref="formCustom2" :model="formCustom" :rules="ruleCustom" :label-width="80">
        <FormItem label="所属角色" prop="roles" required>
          <Select v-model="formCustom.roleIds" multiple style="width:260px">
            <Option v-for="item in roleData" :value="item.value" :key="item.value">{{ item.name }}</Option>
          </Select>
        </FormItem>
      </Form>
      <div slot="footer">
        <Button type="primary" @click="handleSubmit('formCustom2')" :loading="loading">提交</Button>
        <Button @click="()=>{this.setRoleModal = false;this.formCustom.roles = ''}" style="margin-left: 8px" :disabled="loading">取消</Button>
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
        loading:false,
        setRoleModal:false,
        roleData:[],
        resetPwdModal: false,
        formCustom: {
          id: null,
          userId:null,
          userName: '',
          password: '',
          passwdCheck: '',
          roleIds:''
        },
        ruleCustom: {
          password: [{required: true, message: '密码为必填', trigger: 'blur', type: 'string'}],
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
              {
                label: '密码',
                type: 'password',
                name: 'password',
                required: true,
                view: {show: false},
                edit: {show: false}
              },
              {
                label: '重复密码',
                type: 'password',
                name: 'password2',
                required: true,
                view: {show: false},
                edit: {show: false}
                ,
                equalsTo: 'password'
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
                  show:true, "label": "分配角色", "theme": "primary", click: (row, index, _this) => {
                    this.setRoleModal = true
                    this.formCustom.userId = row['id']
                    let roleIds = row["roleIds"]||''
                    if(roleIds.includes(",")){
                      let a = []
                      roleIds.split(",").forEach((r)=>{
                        if(r!==''){
                          a.push(parseInt(r))
                        }
                      })
                      this.formCustom.roleIds = a
                    }
                    this.formCustom.userName = row['userName']
                  }
                }, {
                  key: "edit", show: true, "label": "编辑", "theme": "primary", click: (row, index, _this) => {
                    _this.optEditClick(row, index);
                  }
                }, {
                  key: "delete", show: true, "label": "删除", "theme": "primary",ghost:true, click: (row, index, _this) => {
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
              ],
              column: {
                minWidth: 220
              }
            },
            columns: [
              {title: 'id', key: 'id', minWidth: 40, align: 'center'},
              {title: '用户名', key: 'userName', minWidth: 80},
              {title: '所属角色', key: 'roleNames', minWidth: 120},
              {
                title: '是否被锁', key: 'locked', minWidth: 80, align: 'center', fieldFormat: (value, row) => {
                  if (value) {
                    return {value: '是', cellClassName: 'table-cell-red'}
                  } else {
                    return '否'
                  }
                }
              },
              {title: '密码过期时间', key: 'credentialExpiredDate', minWidth: 120, align: 'center'}
            ]
          }
        }
      }
    },
    created(){
      this.fetchRoleData()
    },
    methods: {
      //查询所有角色列表
      fetchRoleData() {
        this.$axios.post('/ueboot/role/list', {}).then((response) => {
          // 默认给出一个根节点数据，后台插入的时候不校验是否存在
          let tree = []
          response.body.forEach((t) => {
            let t1 = { 'name': t.name, 'value': t.id}
            tree.push(t1)
          })
          this.roleData = tree
          //this.$set(this.formGrid.form.columns[3], 'data', this.roleData)
        })
      },
      handleSubmit(name) {
        this.$refs[name].validate((valid) => {
          if (valid) {
            this.loading = true
            if(name === 'formCustom2'){
              this.$axios.post("/ueboot/userRole/save", this.formCustom).then((response) => {
                this.loading = false
                this.setRoleModal = false
                this.formCustom.roleIds = null
                this.$Message.success('角色分配成功！');
                //更新当前页数据
                this.$refs.formGrid.$emit('refreshData');
              }).catch(()=>{
                this.loading = false
              })
            }else{
              this.$axios.post("/ueboot/user/save", this.formCustom).then((response) => {
                this.loading = false
                this.resetPwdModal = false
                this.$Message.success('密码重置成功！');
              }).catch(()=>{
                this.loading = false
              })
            }
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
