<template>
  <div>
    <Row v-if="formGrid.tips.title">
      <Alert type="success">
        {{formGrid.tips.title}}
        <span slot="desc" v-if="formGrid.tips.content"> {{formGrid.tips.content}}</span>
      </Alert>
    </Row>
    <!--toobar-->
    <Row :gutter="5" type="flex" justify="start">
      <i-col v-if="formGrid.toolbar.refresh&&formGrid.toolbar.refresh.show">
        <Button type="primary" icon="android-refresh" @click="pageData()">{{formGrid.toolbar.refresh.label}}</Button>
      </i-col>
      <i-col v-if="formGrid.toolbar.create &&formGrid.toolbar.create.show">
        <Button type="success" icon="plus" @click="formAdd">{{formGrid.toolbar.create.label}}</Button>
      </i-col>
      <i-col v-if="formGrid.toolbar.delete &&formGrid.toolbar.delete.show">
        <Button type="error" icon="android-close" @click="batchDelete" :loading="formGrid.toolbar.delete.loading">
          <span v-if="!formGrid.toolbar.delete.loading">  {{formGrid.toolbar.delete.label}}</span>
          <span v-else>{{formGrid.toolbar.delete.label}}中...</span>
        </Button>
      </i-col>
      <!--按钮-->
      <i-col v-for="(button,index) in formGrid.toolbar.button" :key="index" v-if="formGrid.toolbar.button">
        <Button :type="button.theme" :icon="button.icon" @click="toolbarClick(button.click)"
                :disabled="button.disabled"
                :loading="button.loading" :size="button.size" :shape="button.shape" :long="button.long">{{button.label}}
        </Button>
      </i-col>

      <!--导入导出-->
      <i-col v-if="formGrid.toolbar.groups.show">
        <Dropdown v-if="formGrid.toolbar.groups">
          <Button type="primary">
            <Icon type="social-buffer"></Icon>
            {{formGrid.toolbar.groups.label}}
            <Icon type="arrow-down-b"></Icon>
          </Button>
          <Dropdown-menu slot="list">
            <Dropdown-item v-if="formGrid.toolbar.groups.export && formGrid.toolbar.groups.export.currentShow">
              <a href="javascript:void(0)" @click="exportCurrentPageData">
                <Icon type="code-download"></Icon>
                {{formGrid.toolbar.groups.export.currentLabel}}
              </a>
            </Dropdown-item>

            <Dropdown-item v-if="formGrid.toolbar.groups.export && formGrid.toolbar.groups.export.show">
              <a href="javascript:void(0)" @click="exportData">
                <Icon type="android-download"></Icon>
                {{formGrid.toolbar.groups.export.label}}
              </a>
            </Dropdown-item>

            <Dropdown-item v-if="formGrid.toolbar.groups.import && formGrid.toolbar.groups.import.show">
              <a href="javascript:void(0)" @click="showImportView">
                <Icon type="upload"></Icon>
                {{formGrid.toolbar.groups.import.label}}
              </a>
            </Dropdown-item>

          </Dropdown-menu>
        </Dropdown>
      </i-col>
      <!--更多操作-->
      <i-col v-if="formGrid.toolbar.buttons">
        <Dropdown v-if="formGrid.toolbar.buttons">
          <Button :type="formGrid.toolbar.buttons.theme" :icon="formGrid.toolbar.buttons.icon">
            {{formGrid.toolbar.buttons.label}}
            <Icon type="arrow-down-b"></Icon>
          </Button>
          <Dropdown-menu slot="list">
            <template v-for="(menu,index) in formGrid.toolbar.buttons.items">
              <Dropdown-item :disabled="menu.disabled"
                             :divided="menu.divided"
                             :key="index">
                <a href="javascript:void(0)" @click="toolbarClick(menu.click)">
                  <Icon :type="menu.icon"></Icon>
                  {{menu.label}}
                </a>
              </Dropdown-item>
            </template>

          </Dropdown-menu>
        </Dropdown>
      </i-col>
      <i-col v-if="formGrid.toolbar.filter.show">
        <i-input :placeholder="formGrid.toolbar.filter.placeholder"
                 v-model="formGrid.pageable[formGrid.toolbar.filter.name]"
                 @on-enter="pageData(1)">
          <Button slot="append" icon="ios-search" @click="pageData(1)"></Button>
        </i-input>
      </i-col>
      <i-col>
        <Button type="text" shape="circle" v-if="formGrid.toolbar.superFilter.columns.length>0"
                @click="showSuperFilterModal">{{formGrid.toolbar.superFilter.label}}
          <Icon :type="formGrid.toolbar.superFilter.icon"></Icon>
        </Button>
        <!--高级搜索表单-->
        <Modal
          v-model="modal.superFilterModal"
          :title="formGrid.toolbar.superFilter.label"
          :closable="modal.closable"
          :mask-closable="modal.maskClosable"
          :loading="modal.loading"
          :scrollable="modal.scrollable"
          :width="formGrid.toolbar.superFilter.width?formGrid.toolbar.superFilter.width:modal.superFilterWidth">
          <div>
            <Form label-position="left" :label-width="formGrid.form.labelWidth">
              <Row v-for="(item,index) in formGrid.toolbar.superFilter.columns" :key="index">
                <Form-item :label="item.label" :prop="item.name" v-if="item.type!='hidden'">

                  <i-input v-if="item.type == 'text'" v-model="queryParams[item.name]" :icon="item.icon"
                           :placeholder="item.placeholder"
                           :maxlength="item.maxlength" :readonly="item.readonly">
                    <span slot="prepend" v-if="item.prepend">{{item.prepend}}</span>
                    <span slot="append" v-if="item.append">{{item.append}}</span>
                  </i-input>
                  <!--隐藏表单-->
                  <input type="hidden" v-if="item.type=='hidden'" v-model="queryParams[item.name]">
                  <InputNumber v-if="item.type == 'number'" :type="item.type" v-model="queryParams[item.name]"
                               :icon="item.icon" :placeholder="item.placeholder"
                               :disabled="item.disabled" :max="item.max" :min="item.min" :step="item.step"
                               style="width: 100%">
                  </InputNumber>

                  <Input v-if="item.type == 'email'" :type="item.type" v-model="queryParams[item.name]"
                         :icon="item.icon"
                         :maxlength="item.maxlength" :readonly="item.readonly" :placeholder="item.placeholder"/>

                  <i-switch v-model="queryParams[item.name]" @on-change="item.change"
                            v-if="item.type == 'switch'"></i-switch>

                  <Cascader v-if="item.type == 'cascader'" :data="item.items" v-model="queryParams[item.name]"
                            :render-format="item.format?item.format:cascaderFormat"
                            :change-on-select="item.changeOnSelect ? true:false"
                            @on-change="item.onChange?item.onChange:toolbarClick"></Cascader>
                  <Select v-if="item.type == 'select'" :clearable="item.clearable" :filterable="item.filterable"
                          :multiple="item.multiple" v-model="queryParams[item.name]" :placeholder="item.placeholder">
                    <Option v-for="(option,index) in item.items" :value="option.value" :key="index">{{ option.name }}
                    </Option>
                  </Select>

                  <DatePicker v-if="item.type == 'date'" type="date" :placeholder="item.placeholder?item.placeholder:''"
                              :format="item.format?item.format:'yyyy-MM-dd'" :option="item.option"
                              :placement="item.placement" :options="item.options" :confirm="item.confirm"
                              :open="item.open" :size="item.size" :clearable="item.clearable" :readonly="item.readonly"
                              :editable="item.editable" :transfer="item.transfer"
                              v-model="queryParams[item.name]"></DatePicker>
                  <DatePicker v-if="item.type == 'daterange'" type="daterange"
                              :placeholder="item.placeholder?item.placeholder:''"
                              :format="item.format?item.format:'yyyy-MM-dd'"
                              v-model="queryParams[item.name]"></DatePicker>
                  <DatePicker v-if="item.type == 'datetime'" type="datetime"
                              :placeholder="item.placeholder?item.placeholder:''"
                              :format="item.format?item.format:'HH:mm:ss'"
                              :placement="item.placement" :options="item.options" :confirm="item.confirm"
                              :open="item.open" :size="item.size" :clearable="item.clearable" :readonly="item.readonly"
                              :editable="item.editable" :transfer="item.transfer"
                              v-model="queryParams[item.name]"></DatePicker>
                  <DatePicker v-if="item.type == 'datetimerange'" type="datetimerange"
                              :placeholder="item.placeholder?item.placeholder:''"
                              :format="item.format?item.format:'HH:mm:ss'"
                              :placement="item.placement" :options="item.options" :confirm="item.confirm"
                              :open="item.open" :size="item.size" :clearable="item.clearable" :readonly="item.readonly"
                              :editable="item.editable" :transfer="item.transfer"
                              v-model="queryParams[item.name]"></DatePicker>
                </Form-item>
              </Row>
            </Form>
          </div>
          <div slot="footer">
            <Button v-if="formGrid.toolbar.superFilter.submit" :type="formGrid.toolbar.superFilter.submit.theme"
                    :icon="formGrid.toolbar.superFilter.submit.icon"
                    @click="superFilterSearch(1)"
                    :disabled="formGrid.toolbar.superFilter.submit.disabled"
                    :loading="formGrid.toolbar.superFilter.submit.loading"
                    :size="formGrid.toolbar.superFilter.submit.size" :shape="formGrid.toolbar.superFilter.submit.shape"
                    :long="formGrid.toolbar.superFilter.submit.long">{{formGrid.toolbar.superFilter.submit.label}}
            </Button>
          </div>
        </Modal>
      </i-col>
    </Row>
    <!--table-->
    <Row style="margin-top: 10px;">
      <Table :width="formGrid.table.width" :height="formGrid.table.height" border :columns="formGrid.table.columns"
             :data="formGrid.table.data" :stripe="formGrid.table.stripe"
             :no-data-text="formGrid.table.noDataText" @on-selection-change="onSelectionChange" :ref="tableRef"
             @on-select="onSelect" @on-select-all="onSelectAll"></Table>
    </Row>
    <!--page-->
    <Row style="margin-top: 10px;" justify="end" type="flex">
      <Page :total="formGrid.pageable.total" show-elevator show-sizer show-total @on-change="changePage"
            :current="formGrid.pageable.page" :pageSize="formGrid.pageable.size"
            @on-page-size-change="changePageSize" placement="top"></Page>
    </Row>

    <!--form表单-->
    <Modal
      v-model="modal.editModal"
      :title="formGrid.form.modal.title"
      :closable="modal.closable"
      :mask-closable="modal.maskClosable"
      :loading="modal.loading"
      :scrollable="modal.scrollable"
      :width="modal.width">
      <Form :model="formGrid.form.data" :label-position="formGrid.form.labelPosition"
            :label-width="formGrid.form.labelWidth"
            :rules="ruleValidate" :ref="formGrid.form.name">
        <Row>
          <i-col :span="24/formGrid.form.colNumber" :key="index" v-for="(item,index) in formColumns">
            <Form-item :label="item.label" :prop="item.name" v-if="item.show">

              <i-input v-model="formGrid.form.data[item.name]" :type="item.type" :placeholder="item.placeholder"
                       v-if="item.type=='text'" :disabled="item.disabled" @on-focus="toolbarClick(item.focus)"
                       :maxlength="item.maxlength" :readonly="item.readonly"
              >
                <span slot="prepend" v-if="item.prepend">{{item.prepend}}</span>
                <span slot="append" v-if="item.append">{{item.append}}</span>
              </i-input>

              <i-input v-model="formGrid.form.data[item.name]" :type="item.type" :placeholder="item.placeholder"
                       v-if="item.type=='password'" :disabled="item.disabled">
              </i-input>
              <!--隐藏表单-->
              <input type="hidden" v-if="item.type=='hidden'" v-model="formGrid.form.data[item.name]">
              <i-switch v-model="formGrid.form.data[item.name]" @on-change="item.change"
                        v-if="item.type == 'switch'"></i-switch>

              <InputNumber v-model="formGrid.form.data[item.name]" :type="item.type" :placeholder="item.placeholder"
                           v-if="item.type=='number'" :disabled="item.disabled" :max="item.max" :min="item.min"
                           :step="item.step" style="width: 100%">
              </InputNumber>

              <Input v-model="formGrid.form.data[item.name]" :type="item.type" :placeholder="item.placeholder"
                     v-if="item.type=='email'" :disabled="item.disabled"/>

              <Cascader v-if="item.type == 'cascader'" :data="item.items" v-model="formGrid.form.data[item.name]"
                        :render-format="cascaderFormat" :change-on-select="item.changeOnSelect ? true:false"
                        @on-change="item.onChange?item.onChange:toolbarClick" :disabled="item.disabled"></Cascader>

              <!--<Cascader v-else-if="item.type == 'cascader'" :data="item.items" v-model="formGrid.form.data[item.name]"
                        :render-format="cascaderFormat"
                        @on-change="item.onChange?item.onChange:toolbarClick" :disabled="item.disabled"></Cascader>-->

              <template v-else-if="item.type=='select'">
                <Select v-model="formGrid.form.data[item.name]" :placeholder="item.placeholder"
                        :disabled="item.disabled">
                  <Option :value="o.value" v-for="(o,index) in item.items" :key="index">{{o.name}}</Option>
                </Select>
              </template>

              <template v-else-if="item.type=='radio'">
                <Radio-group v-model="formGrid.form.data[item.name]" :disabled="item.disabled">
                  <Radio :label="o.label" v-for="(o,index) in item.items" :key="index">{{o.name}}</Radio>
                </Radio-group>
              </template>

              <template v-else-if="item.type=='date'">
                <DatePicker type="date" :placeholder="item.placeholder?item.placeholder:'选择日期'"
                            v-model="formGrid.form.data[item.name]" :disabled="item.disabled"
                            :format="item.format" :placement="item.placement" :options="item.options"
                            :confirm="item.confirm"
                            :open="item.open" :size="item.size" :clearable="item.clearable" :readonly="item.readonly"
                            :editable="item.editable" :transfer="item.transfer"
                ></DatePicker>
              </template>

              <template v-else-if="item.type=='datetime'">
                <DatePicker type="datetime" :placeholder="item.placeholder?item.placeholder:'选择日期和时间'"
                            v-model="formGrid.form.data[item.name]" :disabled="item.disabled"
                            :format="item.format" :placement="item.placement" :options="item.options"
                            :confirm="item.confirm"
                            :open="item.open" :size="item.size" :clearable="item.clearable" :readonly="item.readonly"
                            :editable="item.editable" :transfer="item.transfer"
                ></DatePicker>
              </template>

              <template v-else-if="item.type=='time'">
                <TimePicker type="time" :placeholder="item.placeholder?item.placeholder:'选择时间'"
                            v-model="formGrid.form.data[item.name]" :disabled="item.disabled"></TimePicker>
              </template>

              <template v-else-if="item.type=='checkbox'">
                <Checkbox-group v-model="formGrid.form.data[item.name]" :disabled="item.disabled">
                  <Checkbox :label="o.label" v-for="(o,index) in item.items" :key="index">{{o.name}}</Checkbox>
                </Checkbox-group>
              </template>

              <template v-else-if="item.type=='textarea'">
                <!--  :autosize="item.autosize" 有BUG-->

                <i-input type="textarea" :placeholder="item.placeholder?item.placeholder:'请输入...'"
                         v-model="formGrid.form.data[item.name]" :rows="item.rows"
                         :icon="item.icon" :maxlength="item.maxlength" :readonly="item.readonly"
                         :disabled="item.disabled"
                ></i-input>
              </template>
              <template v-else>

              </template>

            </Form-item>
          </i-col>
        </Row>
        <Row>
          <Form-item>

          </Form-item>
        </Row>
      </Form>
      <div slot="footer">
        <Button type="primary" @click="handleSubmit()" :loading="formGrid.form.loading">
          <span v-if="!formGrid.form.loading">提交</span>
          <span v-else>提交中...</span>
        </Button>
        <Button type="ghost" @click="handleReset()" style="margin-left: 8px">重置</Button>
        <Button @click="cancel()" style="margin-left: 8px">取消</Button>
      </div>
    </Modal>

    <!--查看详情界面-->
    <Modal
      v-model="modal.viewModal"
      :title="formGrid.form.modal.title"
      :closable="modal.closable"
      :mask-closable="modal.maskClosable"
      :loading="modal.loading"
      :scrollable="modal.scrollable"
      :width="modal.width">
      <Form :label-position="formGrid.form.labelPosition"
            :label-width="formGrid.form.labelWidth" :ref="formGrid.form.name+'edit'">
        <Row>
          <i-col :span="24/formGrid.form.colNumber" :key="index" v-for="(item,index) in formColumns">
            <Form-item :label="item.label" :prop="item.name" v-if="item.show">
              <template v-if="item.type=='checkbox'">
                <Checkbox-group v-model="formGrid.form.data[item.name]">
                  <Checkbox :label="o.label" v-for="(o,index) in item.items" :key="index">{{o.name}}</Checkbox>
                </Checkbox-group>
              </template>

              <template v-else-if="item.type=='textarea'">
                <i-input type="textarea" :placeholder="item.placeholder?item.placeholder:'请输入...'"
                         v-model="formGrid.form.data[item.name]" :rows="item.rows"
                         :icon="item.icon" :maxlength="item.maxlength" :readonly="item.readonly" disabled
                ></i-input>
              </template>
              <template v-else>
                <Input v-model="formGrid.form.data[item.name]" disabled/>
              </template>
            </Form-item>
          </i-col>
        </Row>
      </Form>
      <div slot="footer">
        <Button @click="modal.viewModal=!modal.viewModal" style="margin-left: 8px">关闭</Button>
      </div>
    </Modal>
    <!--上传文件弹出框-->
    <Modal
      v-model="modal.importModal"
      :closable="true"
      :mask-closable="false">
      <template>
        <p slot="header" style="color:rgb(60, 65, 81);text-align:center">
          <Icon type="arrow-up-a"></Icon>
          <span>{{formGrid.toolbar.groups.import.modelTitle}}</span>
        </p>
        <div style="text-align:center">
          <ueUpload
            :scope="formGrid.toolbar.groups.import.scope"
            :action="formGrid.toolbar.groups.import.action"
            :max-size="formGrid.toolbar.groups.import.size"
            :only-single="true"
            :file-type="formGrid.toolbar.groups.import.fileType"
            :isShow="formGrid.toolbar.groups.import.isShow"
            :params="formGrid.toolbar.groups.import.params">
          </ueUpload>
        </div>
        <div slot="footer">
          <Button type="primary" @click="showImportView">关闭</Button>
        </div>
      </template>
    </Modal>
  </div>
</template>
<style>
  .ivu-input .ivu-input-disabled {
    color: #7b7777 !important;
  }

  .ivu-input[disabled], fieldset[disabled] .ivu-input {
    color: #7b7777 !important;
  }

  /*自定义样式*/
  .optColumn .ivu-table-cell {
    text-align: center;
    padding-left: 5px;
    padding-right: 5px;
  }

  /*table-cell当中的poptip样式覆盖*/
  .ivu-table-cell .ivu-tooltip {
    display: block;
  }

  .ivu-table-cell .ivu-tooltip-rel {
    display: block;
  }
</style>

<script>
  import axios from 'axios'
  import Log from "../../utils/Log";
  import deepExtend from "deep-extend";
  import defaultData from "./default"
  import util from "core-util-is";
  import ueUpload from '../upload/Upload.vue';

  export default {
    name: "UFormGrid",
    components: {
      'ueUpload': ueUpload
    },
    props: {
      tableRef: {
        type: String,
        default: function () {
          return 'dataTable';
        }
      },
      data: {
        type: Object,
        default: function () {
          return {
            options: {autoLoad: true},
            tips: {"title": "", "content": ""},
            toolbar: [],
            exportParams: [],
            form: {},
            table: {},
            pageable: {
              page: 1,
              size: 10
            }
          }
        }
      },
      //将查询条件放开给父组件，便于父组件动态修改查询条件
      //封装提交到后端的参数
      filterParams: {
        type: Object,
        default: function () {
          return {}
        }
      },

      methods: {
        click() {
          this.$emit('click')
        }
      }
    },
    watch: {
      //监听变量发生变化，将新的变量给查询参数。
      filterParams: {
        handler(curVal, oldVal) {
          this.queryParams = deepExtend({}, curVal);
          Log.d("监听到变化:%o", this.queryParams);
        },
        deep: true
      }
    },
    created() {
      this.queryParams = deepExtend({}, this.filterParams);
      Log.d("初始化queryParams:%o", this.queryParams);
      //生成表单规则校验
      let ruleValidate = {};
      if (this.data.form.columns) {
        this.data.form.columns.forEach((c) => {
          let ruleName = c.name;
          //直接写required的话，采用默认提示规则。写了rule属性则使用rule
          if (c.required && !c.rule) {
            let trigger = "blur";
            //固定的几种类型采用change事件监听
            ["select", "radio", "date", "datetime", "time", "checkbox"].forEach((type) => {
              if (c.type === type) {
                trigger = "change";
              }
            });

            let rule = {required: true, message: c.label + '为必填', trigger: trigger};
            //级联框
            if (c.type === "cascader" || c.type === "checkbox") {
              rule["type"] = "array";
            } else if (c.type === "datetime" || c.type === "date" || c.type === "time") {
              rule["type"] = "date";
            }
            ruleValidate[ruleName] = [rule];
          } else if (c.rule) {
            //其他自定义格式直接采用原生格式（参见iview组件当中的form表单验证格式）
            ruleValidate[ruleName] = c.rule;
          }
        });
      }

      this.ruleValidate = ruleValidate;

      //和默认值进行合并，一定要加一个{},防止修改掉defaultData对象，导致页面切换时数据异常
      this.formGrid = deepExtend({}, defaultData, this.data);
      //追加操作列
      if (this.formGrid.table.operation.show) {
        //为操作添加按钮
        this.renderOpt(this.formGrid.table.operation.column);
        this.formGrid.table.columns.push(this.formGrid.table.operation.column);
      }
      //判断否需要显示复选框
      if (this.formGrid.table.showCheckbox) {
        this.formGrid.table.columns.splice(0, 0, {
          type: 'selection',
          width: 60,
          fixed: 'left',
          align: 'center'
        });
      }
      //设置高级搜索当中，如果存在下拉框的元素，需要进行数据初始化
      if (this.formGrid.toolbar.superFilter && this.formGrid.toolbar.superFilter.columns) {
        this.setSelectItems(this.formGrid.toolbar.superFilter.columns);
        this.setSuperFilterInitValue(this.formGrid.toolbar.superFilter.columns);
      }
      //渲染表格每列特殊情况
      this.renderColumn();

      Log.d("formGrid 初始化对象:%o", this.formGrid);

    },
    data() {
      return {

        file: null,
        self: this,
        loadingStatus: false,
        //将props的data对象进行转换，防止父组件修改子组件的值
        //为防止新增的属性无法被监控到，需要事先定义好所有属性
        formGrid: {
          toolbar: {
            refresh: {},
            create: {},
            delete: {},
            export: {},
            import: {},
            button: null,
            buttons: null,
            filter: {},
            superFilter: {}
          },
          form: {data: {}, modal: {}, columns: []},
          pageable: {},
          table: {operation: {buttons: null, column: {}}, data: []}
        },
        modal: {
          //显示编辑模态窗口
          editModal: false,
          //显示查看模态窗口
          viewModal: false,
          //显示编辑模态窗口
          importModal: false,
          //高级搜索弹出框
          superFilterModal: false,
          superFilterWidth: 500,
          loading: true,
          width: 756,
          scrollable: true,
          maskClosable: false,
          closable: true,
        },

        //表单规则校验
        ruleValidate: {},
        //表格选中的项目集合
        selections: [],
        //渲染表单的元素列表，根据场景要求渲染的列表会有差异
        formColumns: [],
        //grid查询参数
        queryParams: {},
      }
    },
    methods: {
      //初始化搜索框的初始值
      setSuperFilterInitValue(columns) {
        columns.forEach((c) => {
          if (c.init) {
            this.queryParams[c.name] = c.init;
          }
        })
      },
      noticeError(title, desc) {
        this.$Notice.error({
          title: title,
          desc: desc
        });
      },
      validate(key) {
        if (!key || key === null) {
          this.noticeError(key + "不能为空!");
          return false
        }
        return true;
      },
      //获取接口交互的主键
      getPrimaryKey(row) {
        let key = this.formGrid.table.operation.primaryKey;
        if (row[key]) {
          let o = {};
          o[key] = row[key];
          return o;
        } else {
          this.noticeError("请求参数不正确", "无法获取primaryKey值");
          return null;
        }
      },
      //弹出高级搜索框
      showSuperFilterModal() {
        this.modal.superFilterModal = true;
      },
      superFilterSearch(page) {
        this.modal.superFilterModal = false;
        this.pageData(page);
      },
      pageData(page) {
        if (page && (typeof(page) == 'number')) {
          this.formGrid.pageable.page = page;
        }
        //提交到后端接口，默认有一个page对象，包含分页信息。
//        params["page"] = this.formGrid.page;

        this.formGrid.table.noDataText = "正在努力为您加载数据,请稍候...";

        let data = {};
        let params = {};

        if (this.formGrid.options['version'] == undefined || this.formGrid.options['version'] == 'v1') {
          data = this.queryParams;
          data["page"] = this.formGrid.pageable;
          data["page"]['pageNumber'] = data["page"]['page'];
          data["page"]['pageSize'] = data["page"]['size'];
        } else {
          let pageable = deepExtend({}, this.formGrid.pageable);
          params["params"] = pageable
          let page = pageable.page > 0 ? pageable.page - 1 : 0;
          params["params"]["page"] = page;
          data = this.queryParams;
        }
        Log.d("pageData QueryData:%o", data);
        this.$axios.post(this.formGrid.options.url.page, data, params).then(response => {
          this.formGrid.table.noDataText = "已努力查询，但还是没找到！";
          this.formGrid.pageable.total = response.body.totalElements;
          Log.d("接口返回对象,%o", response);
          this.$set(this.formGrid.table, "data", response.body.content)
        }).catch(response => {
          this.$set(this.formGrid.table, "data", []);
          this.formGrid.table.noDataText = "数据查询出现异常，请联系管理员！";
          this.noticeError("数据查询出现异常", response.message ? response.message : "系统或网络异常");
          return false;
        });
      },
      //改变分页
      changePage(page) {
        this.formGrid.pageable.page = page;
        this.pageData();
      },
      //改变页数大小
      changePageSize(size) {
        this.formGrid.pageable.page = 1;
        this.formGrid.pageable.size = size;
        this.pageData();
      },
      //重新从第一页加载数据
      reloadData() {
        this.formGrid.pageable.page = 1;
        this.formGrid.pageable.total = 0;
        this.pageData();
      },
      //模态窗口点击确认按钮事件
      handleSubmit() {
        this.$refs[this.formGrid.form.name].validate((valid) => {
          if (valid) {
            if (!this.formGrid.form.submitBefore(this.formGrid.form.data)) {
              return;
            }
            this.formGrid.form.loading = true;
            this.$axios.post(this.formGrid.options.url.save, this.formGrid.form.data).then(response => {
              this.formGrid.form.loading = false;
              this.$Notice.success({
                title: '表单提交成功',
                desc: ''
              });
              //编辑时，查询当前页
              if (this.formGrid.form.isEdit) {
                this.pageData();
              } else {
                this.reloadData();
              }
              this.formGrid.form.data = {};
              this.modal.editModal = false;
              this.formGrid.form.submitAfter(response.body);
            }).catch(response => {
              this.formGrid.form.loading = false;
              this.noticeError("表单提交失败", response.message ? response.message : "系统或网络异常");
              return false;
            });

          } else {
            this.formGrid.form.loading = false;
            this.noticeError("表单校验失败", "");
          }
        });
      },
      //重置表单
      handleReset() {
        this.$refs[this.formGrid.form.name].resetFields();
      },
      //模态窗口点击取消按钮事件
      cancel() {
        this.modal.editModal = false;
        if (this.formGrid.form.onCancel) {
          this.formGrid.form.onCancel();
        }
      },
      //设置表单的渲染元素，添加、编辑、查看可能要求不同，所以需要重新渲染。
      setFormColumns(type) {
        this.handleReset();
        //从用户设定的元素列表当中copy一份数据给当前操作使用，避免数据污染
        let o = deepExtend({}, {columns: this.formGrid.form.columns});
        for (let c of o.columns) {
          //初始化默认值
          if (type === "add" && c.init) {
            //为number类型设置默认值，避免组件无法使用。
            if (c.type === "number" && !c.init) {
              c.init = 1;
            }
            this.$set(this.formGrid.form.data, c.name, c.init);
          }
          if (c[type]) {
            c = deepExtend(c, c[type]);
          }
          //未定义该属性时，默认为true
          if (c.show === undefined) {
            c.show = true;
          }
        }
        this.formColumns = o.columns;
        this.setSelectItems(this.formColumns);
        //设置级联下拉框
        this.setCascaderData(this.formColumns);
        Log.d("form columns:%o ,form.data:%o", this.formColumns, this.formGrid.form.data);
      },
      /**
       *设置下拉框属性
       @target 数组，需要被设置的数组对象，数组当中每个元素与form表单需要的元素一样
       */
      setSelectItems(target) {
        //针对type为select的数据，进行额外处理
        for (let c of target) {
          if (c.type !== "select") {
            continue;
          }
          Log.d("setSelectItems,%o", c);
          let data = c.data || "";
          if (util.isArray(data)) {
            c.items = data;
          } else if (util.isString(data)) {
            if (data.indexOf("im:") == 0) {
              let value = data.split(":")[1];
              //TODO 字典缓存
              Log.d("select 组件查询数据字典:%s", value);
              this.$axios.post(this.formGrid.options.url.im, {parentValue: value}).then(response => {
                //将值塞入到formCloumns当中。
                this.$set(c, "items", response.body);
              });
            } else if (data.indexOf("url:") == 0) {
              let url = data.split(":")[1];
              this.$axios.post(url, {}).then(response => {
                //将值塞入到formCloumns当中。
                this.$set(c, "items", response.body);
              });
            } else {
              Log.e("当前select定义的data不符合规范,%s", data)
            }
          } else {
            Log.e("当前select未定义data,%o", c)
          }
        }
      },
      //对级联下拉框进行赋值
      setCascaderData(target) {
        //针对type为select的数据，进行额外处理
        for (let c of target) {
          if (c.type !== "cascader") {
            continue;
          }
          Log.d("setCascaderData,%o", c);
          let data = c.data || "";
          if (util.isArray(data)) {
            c.items = this.initCascader(data);
          } else if (util.isString(data)) {
            if (data.indexOf("url:") == 0) {
              let url = data.split(":")[1];
              this.$axios.post(url, {}).then(response => {
                //将值塞入到formCloumns当中。
                //对值进行树状初始化
                this.$set(c, "items", this.initCascader(response.body));
//                console.log("cascader 转化后结果c : %o", c)
              });
            } else {
              Log.e("当前cascader定义的data不符合规范,%s", data)
            }
          } else {
            Log.e("当前cascader未定义data,%o", c)
          }
        }
      },
      //初始化级联
      initCascader(data) {
        //1.查找root
        let root = [];
        data.forEach((item) => {
          if (!item.parentId || item.parentId === null) {
            let o = {
              id: item.id,
              label: item.name,
              value: item.value,
              attr: item,
              parentId: item.parentId,
              children: []
            };
            o.children = this.getCascaderChildren(data, o);
            root.push(o);
          }
        });

//        console.log("cascader data: %o", root)
        //生成树结构数据
        return root;
      },
      //2.递归循环所有节点,将节点加入到父节点当中
      getCascaderChildren(data, parent) {
        let sort = this.sort;
        let child = [];
        data.forEach((item) => {
          if (item.parentId === parent.id) {

            let o = {
              id: item.id,
              label: item.name,
              value: item.value,
              parentId: item.parentId,
              attr: item,
              children: [],
            };
            o.children = this.getCascaderChildren(data, o);
            child.push(o);
          }
        });
        /*child.forEach((item) => {
          item.children = this.getCascaderChildren(data, item.treeId);
        });*/
        //是否需要排序
        if (sort) {
          child.sort((a, b) => {
            if (sort["sort"] === "desc") {
              return b[sort["field"]] - a[sort["field"]];
            } else {
              return b[sort["field"]] - a[sort["field"]];
            }
          })
        }

        return child;
      },
      //toolbar添加按钮事件
      formAdd() {
        //清空表单
        this.formGrid.form.data = {};
        this.setFormColumns("add");
        this.modal.editModal = true;
      },
      //toolbar删除按钮事件
      batchDelete() {
        if (this.selections.length === 0) {
          this.noticeError("数据删除失败", "没有选中任意数据");
          return;
        }
        let keys = [];
        this.selections.forEach((item) => {
          let p = this.getPrimaryKey(item);
          if (p) {
            keys.push(p[this.formGrid.table.operation.primaryKey]);
          }
        });
        if (keys.length == 0) {
          return;
        }
        this.$Modal.confirm({
          title: '删除数据',
          content: '<span style="color:red">确定删除选中的记录吗？</span>',
          onOk: () => {

            let key = this.formGrid.table.operation.primaryKey;
            let data = {};
            let params = {};

            if (this.formGrid.options['version'] == undefined || this.formGrid.options['version'] == 'v1') {
              data[key] = keys;
            } else {
              params["params"] = {};
              params["params"][key] = keys.toString();
            }


            Log.d("要删除的数据:%o", params);
            this.formGrid.toolbar.delete.loading = true;
            this.$axios.post(this.formGrid.options.url.delete, data, params).then(response => {
              this.formGrid.toolbar.delete.loading = false;
              this.$Notice.success({
                title: '删除成功',
                desc: ''
              });
              this.pageData();
            }).catch(response => {
              this.formGrid.toolbar.delete.loading = false;
              this.noticeError("删除失败", response.message ? response.message : "系统或网络异常");
              return false;
            });
          },
          onCancel: () => {
            //this.$Message.info('点击了取消');
          }
        });
      },
      //选中某一项触发，返回值为 selection 和 row，分别为已选项和刚选择的项。
      onSelect(selection) {

      },
      //只要选中项发生变化时就会触发，返回值为 selection，已选项。
      onSelectionChange(selection) {
        this.selections = selection;
        Log.d("####onSelectionChange,%o", selection);
      },
      //点击全选时触发，返回值为 selection，已选项。
      onSelectAll(selection) {

      },
      //按钮点击事件
      toolbarClick(btnClickCB) {
        if (btnClickCB) {
          btnClickCB(this.selections);
        }
      },
      //级联框格式化
      cascaderFormat(labels, selectedData) {
        Log.d("###cascaderFormat,%o", labels);
        return labels.length > 0 ? labels.join("/") : "";
      },
      ccOnChange(value, selectedData) {
      },
      //TODO 返回用户选中的行数据给到按钮操作当中。
      //table当中点击查看按钮事件
      tableViewClick(params) {
      },
      //渲染表格自定义列样式
      renderColumn() {
        this.formGrid.table.columns.forEach((c) => {
          if (c.renderType && !util.isFunction(c.render)) {
            c.render = (h, params) => {
              //使用当前作用域
              let _this = this;
              let value = params["row"][c.key] || "";
              //由用户主动格式化
              if (util.isFunction(c.format)) {
                let row = _this.formGrid.table.data[params.index];
                value = c.format(value, row);
              }

              let tmp = value.split("");
              if (c.renderType === "tooltip") {
                let i = 0;
                let str = [];
                let tmpStr = [];
                for (let t of tmp) {
                  tmpStr.push(t);
                  if (i > 15) {
                    i = 0;
                    str.push(h("p", tmpStr.join("")));
                    tmpStr = [];
                  }
                  i++;
                }
                str.push(h("p", tmpStr.join("")));
                return h('Tooltip', [h("div", {
                  style: {
                    overflow: "hidden",
                    textOverflow: "ellipsis",
                    whiteSpace: "nowrap"
                  }
                }, value), h("p", {slot: 'content',}, str)]);
              } else if (c.renderType === "a") {
                return h("a", {
                  attrs: {href: 'javascript:void(0)'},
                  style: {textDecoration: "underline"},
                  on: {
                    click: function () {
                      let row = _this.formGrid.table.data[params.index];
                      Log.d("列标题点击,row:%o", row);
                      c.click(row, params.index, _this);
                    }
                  }
                }, value);
              }
            }
          }
        })
      },
      //渲染操作列按钮
      renderOpt(column) {
        //为操作列增加render函数
        column.render = (h, params) => {
          //使用当前作用域
          let _this = this;

          function create(h, params, className, btnText, clickEvent) {
            return h('Button', {
              props: {
                type: className,
                size: 'small'
              },
              style: {
                marginRight: "5px"
              },
              on: {
                click: function () {
                  let row = _this.formGrid.table.data[params.index];
                  Log.d("操作功能点击事件,row:%o", row);
                  clickEvent(row, params.index, _this);
                }
              }
            }, btnText)
          }

          let array = [];
          this.formGrid.table.operation.buttons.forEach((b, index) => {

            if (b.show) {
              array.push(create(h, params, b.theme, b.label, b.click))
            }
          });
          return array;
        }
      },
      //获取表单数据
      getFormData(row, type) {
        let data = this.getPrimaryKey(row) || "";
        if (data === "") {
          return;
        }
        let params = {};
        //远程查询接口获取数据，弹出模态框
        if (this.formGrid.table.operation.remote) {
          let key = this.formGrid.table.operation.primaryKey;
          Log.d("远程接口获取FormData")

          if (this.formGrid.options['version'] == undefined || this.formGrid.options['version'] == 'v1') {

          } else {
            params["params"][key] = row[key];
            data = null;
          }

          this.$axios.post(this.formGrid.options.url.get, data, params).then(response => {
            let data = response.body;
            this.formatFormField(data);
            this.formGrid.form.data = data;
            if (type === "view") {
              this.modal.viewModal = true;
            } else {
              this.modal.editModal = true;
            }
          }).catch(response => {
            this.noticeError("获取数据失败", response.body);
            return false;
          });
        } else {
          Log.d("get data :%o", row);
          let data = deepExtend({}, row);
          this.formatFormField(data);
          this.formGrid.form.data = data;
          if (type === "view") {
            this.modal.viewModal = true;
          } else {
            this.modal.editModal = true;
          }
        }
      },
      //操作查看按钮
      optViewClick(row, index) {
        this.formGrid.form.data = {};
        this.setFormColumns("view");
        this.formGrid.form.isView = true;
        this.getFormData(row, "view");
      },
      //操作编辑按钮
      optEditClick(row, index) {
        this.formGrid.form.data = {};
        this.setFormColumns("edit");
        this.formGrid.form.isEdit = true;
        this.getFormData(row, "edit");
      },
      //格式化表单元素
      formatFormField(target) {
        //重新格式化值，应对需要自定义返回值进行额外处理。
        for (let c of this.formColumns) {
          if (util.isFunction(c.fieldFormat) && target[c.name]) {
            target[c.name] = c.fieldFormat(target[c.name], target);
          }
        }
      },
      //操作删除按钮
      optDeleteClick(row, index) {
        this.$Modal.confirm({
          title: '删除数据',
          content: '<span style="color:red">确定删除该记录吗？</span>',
          onOk: () => {
            //拼装成一个数组传递到后端
            let key = this.formGrid.table.operation.primaryKey;
            let keys = [];
            let p = this.getPrimaryKey(row);
            if (p) {
              keys.push(p[key]);
            }
            if (keys.length === 0) {
              return;
            }

            let data = {};
            let params = {};
            if (this.formGrid.options['version'] == undefined || this.formGrid.options['version'] == 'v1') {
              data[key] = keys;
            } else {
              params["params"] = {};
              params["params"][key] = keys.toString();
            }


            Log.d("delete params :%o", params);
            this.$axios.post(this.formGrid.options.url.delete, data, params).then(response => {
              this.pageData();
            }).catch(response => {
              this.noticeError("数据删除失败", response.message ? response.message : "系统或网络异常");
              return false;
            });
          },
          onCancel: () => {
            //this.$Message.info('点击了取消');
          }
        });
      },
      //按条件导出所有数据
      exportData() {
        this.exportPageData(100000);
      },
      //到处当前页数据
      exportCurrentPageData() {
        this.exportPageData();
      },
      //导出查询数据功能方法
      exportPageData(size) {
        // 获取field 获取数据
        let data = {};
        let params = {};


        if (this.formGrid.options['version'] == undefined || this.formGrid.options['version'] == 'v1') {
          data = deepExtend({}, this.queryParams);
          data["page"] = deepExtend({}, this.formGrid.pageable);
          data["page"]['pageSize'] = data["page"]['size'];
          if (size) {
            data["page"]['pageSize'] = size;
          }
          data["page"]['pageNumber'] = data["page"]['page'];
        } else {
          data = deepExtend({}, this.queryParams);
          params['params'] = deepExtend({}, this.formGrid.pageable);
          if (size) {
            params["params"]['size'] = size;
          }
          params["params"]["page"] = this.formGrid.pageable.page > 0 ? this.formGrid.pageable.page - 1 : 0;
        }


        let body = [];
        Log.d("exportPageData QueryData:%o", data);
        this.$axios.post(this.formGrid.options.url.page, data, params).then(response => {
          body = response.body.content;
          let columns = [];
          this.formGrid.table.columns.forEach(function (item) {
              var key = item.key;
              var title = item.title;
              columns.push({key: key, title: title});
            }
          );
          this.defaultExport(columns, body);
        }).catch(response => {
          this.$set(this.formGrid.table, "data", []);
          this.noticeError("数据查询出现异常", "系统服务或网络异常");
        });

      },
      /**
       *
       * @param fileName 导出文件名
       * @param noHeader 到处文件是否不需要表头 Default: false
       */
      defaultExport(columns, data) {
        if (!this.formGrid.toolbar.groups.export) {
          return;
        }
        let fileName = this.formGrid.toolbar.groups.export['fileName'] || "原数据导出";
        this.$refs[this.tableRef].exportCsv({
          filename: fileName, columns: this.formGrid.toolbar.groups.export.columns || columns, data: data
        });
      },
      showImportView() {
        this.modal.importModal = !this.modal.importModal;
      }
    },
    mounted() {
      if (this.formGrid.options.autoLoad) {
        this.pageData();
      }
      this.$nextTick(function () {
        //添加监听器
        //table重新加载
        this.$on('reloadData', () => {
          this.reloadData();
        });
        //修改form表单值
        this.$on("setFormFieldData", (fieldName, data) => {
          this.$set(this.formGrid.form.data, fieldName, data);
        });
        //上传成功
        this.$on("uploadSuccess", (jsonData, scope) => {
          //上传成功的业务逻辑代码
          console.log("uploadSuccess:" + jsonData + "+ scope:" + scope)
        });
        //上传失败
        this.$on("uploadFail", (jsonData, scope) => {
          //上传失败后的业务逻辑代码
          console.log("uploadFail:" + jsonData + "+ scope:" + scope)
        });
      })
    }
    ,
    //
    beforeDestroy() {
      //销毁数据，防止脏读
      this.formGrid = null;
      this.queryParams = null;
      this.modal = null;
      this.ruleValidate = null;
      this.selections = null;
      this.formColumns = null;
    }
  }

</script>
