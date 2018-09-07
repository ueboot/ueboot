<template>
  <div>
    <Row>
      <Col :span="24">
      <Button type="primary" icon="plus" @click="addCategoryType">添加类目</Button>
      </Col>
    </Row>
    <Row>
      <Col :span="8">
      <u-tree :tree="tree" @itemClick="itemClick"></u-tree>
      </Col>
      <Col :span="12" v-if="form.isShow == true">
      <Form ref="form" v-bind:model="form.data" label-position="left" :label-width="80" :rules="ruleValidate">
        <Form-item :label="form.data.fullPathLabel" prop="fullPathName">
          <Input v-model="form.data.fullPathName" :disabled="true"></Input>
        </Form-item>
        <Form-item label="类目名称" prop="name">
          <Input v-model="form.data.name" :disabled="false"></Input>
        </Form-item>
        <Form-item label="商品类型" prop="goodsType">
          <Select v-model="form.data.goodsType">
            <Option v-for="item in goodsType" :value="item.value" :key="item.value">{{ item.name }}</Option>
          </Select>
        </Form-item>
        <Form-item style="float: right;" :labelWidth="20" v-if="handle.showDelBtn">
          <Button @click="delCategoryType" type="error">删除</Button>
        </Form-item>
        <Form-item style="float: right;" :labelWidth="20">
          <Button @click="resetForm" type="ghost">重置</Button>
        </Form-item>
        <Form-item style="float: right;" :labelWidth="20">
          <Button @click="submitFormData('form')" type="primary">提交</Button>
        </Form-item>
      </Form>
      </Col>
    </Row>
    <Row style="margin-top:30px;">
      <specification v-if="form.isShow" :filterParams="this.filterParams" ref="spec"></specification>
    </Row>
  </div>
</template>
<script type="application/javascript">
  import deepExtend from "deep-extend";
  import axios from 'axios'
  import Specification from "./Specification.vue";

  export default {
    components: {
      "Specification": Specification
    },
    data () {
      const validateName = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('类目名称不能为空'));
        }
        if (value.indexOf("/") > -1) {
          return callback(new Error('类目名称不能包含字符/'));
        }
        return callback();
      };
      return {
        filterParams:{"categoryId":null},//当前选择的分类ID
        handle: {
          operation: "add",
          showDelBtn: true
        },
        goodsType: "",
        form: {
          isShow: false,
          data: {
            fullPathLabel: "节点路径",
            fullPathName: "",
            name: "",
            goodsType: "",
            id: null,
            fullPathId: "",
            parentId: null,
          },
          buttons: {
            isShowBtn: false
          }
        },
        ruleValidate: {
          name: [
            {required: true, validator: validateName, trigger: 'blur'},
          ],
          goodsType: [{required: true,message:"不能为空"}]
        },
        tree: [],
        selectTreeItem: null,//当前选择的树节点
        parentTreeItem: null,//父节点对象
      }
    },
    methods: {
      itemClick(item, parent){
        //根节点不编辑
        this.selectTreeItem = item;
        this.parentTreeItem = parent;
        if (!item.attr.parentId) {
          this.form.isShow = false;
          return;
        }
        this.handle.operation = "edit";
        this.$set(this.filterParams,"categoryId",item.attr.id);
        //设置表单提交时，增加一个字段，便于添加记录时，可以获取当前类目ID
        window.sessionStorage.setItem("temp_categoryId", this.filterParams.categoryId);

        if(this.form.isShow){
          //延迟一定时间在执行，防止上面修改的值还未传到子组件，导致查询条件未生效
          window.setTimeout(()=>{
            this.$refs.spec.$emit("reloadData");
          },500);
        }
        this.showDetail(item.attr)
      },
      addCategoryType(){
        if (this.selectTreeItem == null) {
          this.$Message.error('请先在下面选择一个节点!');
          return;
        }
        if (this.selectTreeItem != null && this.selectTreeItem != undefined) {
          this.form.data.fullPathName = this.selectTreeItem.attr.fullPathName;
          this.form.data.fullPathId = this.selectTreeItem.attr.fullPathId;
          this.form.data.parentId = this.selectTreeItem.attr.id;
          this.form.data.fullPathLabel = "上级节点";
          this.form.data.id = null;
        }
        this.form.data.name = "";
        this.form.isShow = true;
        this.handle.operation = "add";
        this.handle.showDelBtn = false;
      },
      delCategoryType(){
        //判断是否有父节点
        if (this.selectTreeItem.children && this.selectTreeItem.children.length > 0) {
          this.$Message.error('删除失败，请先删除子节点!');
          return;
        }
        this.$Modal.confirm({
          title: '系统提示',
          content: '您确认要删除当前节点吗？</p>',
          onOk: () => {
            axios.post("/admin/category/delete",null,{params:{ids:[this.selectTreeItem.treeId].toString()}}).then((data) => {
              this.$Message.success('操作成功');
              this.form.isShow = false;
              this.getCategoryTypeData();
            }, (response) => {
              this.$log.d(response);
              this.$Notice.error({
                title: "系统异常",
                desc: response.message ? response.message : "系统或网络异常"
              });
            });
          }
        });
      },
      getCategoryTypeData(){
        axios.get("/admin/category/find").then((data) => {
          this.tree = data.body;
        }, (response) => {
          this.$log.d(response);
          this.$Notice.error({
            title: "系统异常",
            desc: response.message ? response.message : "系统或网络异常"
          });
        });
      },
      //显示详情
      showDetail(item){
        this.form.data = deepExtend({}, item);
        this.form.data.fullPathLabel = "节点路径";
        //打开form表单
        this.form.isShow = true;
        this.handle.showDelBtn = true;
      },
      submitFormData(name){
        this.$refs[name].validate((valid) => {
          if (valid) {
            let formData = deepExtend({}, this.form.data);
            axios.post("/admin/category/save",null, formData).then((data) => {
              this.$Message.success('操作成功');
              this.getCategoryTypeData();
            }, (response) => {
              this.$log.d(response);
              this.$Notice.error({
                title: "系统异常",
                desc: response.message ? response.message : "系统或网络异常"
              });
            });
          }
        })
      },
      resetForm(){
        this.form.data.name = "";
        this.form.isShow = true;
      },
    }, mounted: function () {
      this.getCategoryTypeData();
    }
  }
</script>
