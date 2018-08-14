<template>
  <el-card class="box-card">
    <div slot="header" class="clearfix">
      <span>首页</span>
      <!--<el-button style="float: right; padding: 3px 0" type="text" @click="addSliderImage">添加图片</el-button>-->
    </div>
    <el-row style="margin: 15px">
      <el-col :span="16">
        <div style="margin-top: 15px;">
          所属省份:
          <el-select v-model="province" filterable placeholder="请选择省份" style="width: 200px" @change="changeProvince">
            <el-option
              v-for="item in provinces"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </div>
      </el-col>
    </el-row>
    <el-row type="flex"  justify="center" style="margin-top: 100px">
      <el-col :span="3"></el-col>
      <el-col :span="4">
         <el-card :body-style="{height:'100px',textAlign:'center',lineHeight:'60px'}">
              <div slot="header" style="text-align: center">
                <span>用户总数</span>
              </div>
              <div>{{dataInfo.userTotalNum}}</div>
         </el-card>
      </el-col>
      <el-col :span="3"> </el-col>
      <el-col :span="4">
        <el-card  :body-style="{height:'100px',textAlign:'center',lineHeight:'60px'}">
          <div slot="header" style="text-align: center">
            <span>客户总数</span>
          </div>
          <div>{{dataInfo.cstTotalNum}}</div>
        </el-card>
      </el-col>
      <el-col :span="3">
      </el-col>
      <el-col :span="4">
        <el-card :body-style="{height:'100px',textAlign:'center',lineHeight:'60px'}">
          <div slot="header" style="text-align: center">
            <span>签到人数</span>
          </div>
          <div>{{dataInfo.signTotalNum}}</div>
        </el-card>
      </el-col>
      <el-col :span="3"> </el-col>
    </el-row>
    <el-row type="flex"  justify="center" style="margin-top: 100px">
      <el-col :span="3"> </el-col>
      <el-col :span="4" >
        <el-card :body-style="{height:'100px',textAlign:'center',lineHeight:'60px'}" >
          <div slot="header" style="text-align: center">
            <span>拍房子总数</span>
          </div>
          <div> {{dataInfo.houseTotalNum}} </div>
        </el-card>
      </el-col>
      <el-col :span="3"> </el-col>
      <el-col :span="4">
        <el-card  :body-style="{height:'100px',textAlign:'center',lineHeight:'60px'}">
          <div slot="header" style="text-align: center">
            <span>收资料总数</span>
          </div>
          <div> {{dataInfo.dataTotalNum}} </div>
        </el-card>
      </el-col>
      <el-col :span="3"> </el-col>
      <el-col :span="4">
        <el-card  :body-style="{height:'100px',textAlign:'center',lineHeight:'60px'}">
          <div slot="header" style="text-align: center">
            <span>排板子总数</span>
          </div>
          <div> {{dataInfo.roofTotalNum}} </div>
        </el-card>
      </el-col>
      <el-col :span="3"> </el-col>
    </el-row>
    <el-row style="margin-bottom: 100px"></el-row>
  </el-card>
</template>

<script>
import axios from 'axios'
import Provinces from '../utils/Provinces'

export default {
  data () {
    return {
      province: 'all',
      provinces: Provinces.provinces,
      dataInfo: {}
    }
  },
  methods: {
    initData () {
      axios.get('/backend/security/index/' + this.province).then(resp => {
        this.dataInfo = resp
      })
    },
    changeProvince (val) {
      console.log(val)
      this.initData()
    }
  },
  mounted () {
    this.initData()
  }
}

</script>

<style>

</style>
