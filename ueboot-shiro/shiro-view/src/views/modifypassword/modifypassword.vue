<template>
  <el-card class="box-card">
    <div slot="header" class="clearfix">
      <span>修改密码</span>
    </div>
    <el-row>
      <el-col :span="24" class="y-Center">
        <el-col :span="2" style="font-size: 14px;">原密码</el-col>
        <el-col :span="5"><el-input size="small" v-model="originalPassword" placeholder="请输入原密码" type="password"></el-input></el-col>
      </el-col>
      <el-col :span="24" class="y-Center" style="margin: 20px 0;">
        <el-col :span="2" style="font-size: 14px;">新密码</el-col>
        <el-col :span="5"><el-input size="small" v-model="newPassword" placeholder="请输入新密码" type="password"></el-input></el-col>
      </el-col>
      <el-col :span="24" class="y-Center">
        <el-col :span="2" style="font-size: 14px;">确认密码</el-col>
        <el-col :span="5"><el-input size="small" v-model="confirmPassword" placeholder="请输入确认密码" type="password"></el-input></el-col>
      </el-col>
      <el-button size="small" @click="submitClick" style="margin-top: 30px;">保存修改</el-button>
    </el-row>
  </el-card>
</template>
<script>
import axios from 'axios'
import md5 from 'md5'
export default {
  data () {
    return {
      originalPassword: '',
      newPassword: '',
      confirmPassword: '',
      username: ''
    }
  },
  methods: {
    submitClick () {
      if (this.newPassword !== this.confirmPassword) {
        this.$message({
          type: 'error',
          message: '密码不一致'
        })
      } else {
        axios.post('/backend/security/updatePassword', {oldPassword: md5(this.originalPassword + this.username), newPassword: md5(this.newPassword + this.username)}).then(res => {
          console.log(res)
          this.$message({
            type: 'success',
            message: '修改成功'
          })
          window.location.href = '#/login'
        })
      }
    }
  },
  mounted () {
    let sessionUser = JSON.parse(sessionStorage.getItem('user')) || {}
    this.username = sessionUser.username
    console.log(this.username)
  }
}
</script>
<style scoped>
  .fl{
    float: left;
  }
  .fr{
    float: right;
  }
  .clearfix:after {
    content: "";
    display: block;
    visibility: hidden;
    height: 0;
    clear: both;
  }
  .clearfix {
    zoom: 1;
  }
  .x-Center{
    display: flex;
    display: -webkit-flex;
    -webkit-justify-content: center;
    justify-content: center;
  }
  .xy-Center{
    display: flex;
    display: -webkit-flex;
    -webkit-align-items: center;
    align-items: center;
    -webkit-justify-content: center;
    justify-content: center;
  }
  .y-Center{
    display: flex;
    display: -webkit-flex;
    -webkit-align-items: center;
    align-items: center;
  }
</style>
