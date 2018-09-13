<template>
    <div>
        <Row v-if="formGrid.tips.title">
            <Alert>
                {{formGrid.tips.title}}
                <span slot="desc" v-if="formGrid.tips.content"> {{formGrid.tips.content}}</span>
            </Alert>
        </Row>
        <Row v-if="formGrid.toolbar.superFilter.rows.length>0">
            <i-form :model="formGrid.table.query" :label-position="formGrid.toolbar.superFilter.labelPosition"
                  :label-width="formGrid.toolbar.superFilter.labelWidth" :ref="formGrid.toolbar.superFilter.name"
                  :rules="searchRuleValidate">
                <Row v-for="(row,index1) in formGrid.toolbar.superFilter.rows" :key="'row'+index1">
                    <i-col v-for="(item,index2) in row" :key="'superfilter'+index2"
                           :span="item.span">
                        <Form-item :label="item.label" :prop="item.name">

                            <i-input v-if="item.type === 'text'" v-model="formGrid.table.query[item.name]" :icon="item.icon"
                                     :placeholder="item.placeholder"
                                     @on-focus="item.onFocus?item.onFocus.apply():()=>{}"
                                     :maxlength="item.maxlength" :readonly="item.readonly"
                                     :disabled="item.disabled">
                                <span slot="prepend" v-if="item.prepend">{{item.prepend}}</span>
                                <span slot="append" v-if="item.append">{{item.append}}</span>
                            </i-input>
                            <i-input v-model="formGrid.table.query[item.name]" :type="item.type"
                                     :placeholder="item.placeholder"
                                     v-if="item.type==='password'" :disabled="item.disabled">
                            </i-input>
                            <i-switch v-model="formGrid.table.query[item.name]" @on-change="item.change"
                                      v-if="item.type === 'switch'" :disabled="item.disabled"></i-switch>
                            <InputNumber v-if="item.type === 'number'" :type="item.type"
                                         v-model="formGrid.table.query[item.name]"
                                         :icon="item.icon" :placeholder="item.placeholder"
                                         :disabled="item.disabled" :max="item.max" :min="item.min" :step="item.step"
                                         style="width: 100%">
                            </InputNumber>

                            <i-input v-if="item.type === 'email'" :type="item.type" v-model="formGrid.table.query[item.name]"
                                     :icon="item.icon" :disabled="item.disabled"
                                     :maxlength="item.maxlength" :readonly="item.readonly"
                                     :placeholder="item.placeholder"/>

                            <Cascader v-if="item.type === 'cascader'" :data="item.items"
                                      v-model="formGrid.table.query[item.name]"
                                      :render-format="item.format?item.format:cascaderFormat"
                                      :change-on-select="!!item.changeOnSelect"
                                      @on-change="(value)=>{item.onChange?item.onChange.call(this,value):()=>{}}"></Cascader>
                            <i-select v-if="item.type === 'select'" :clearable="item.clearable"
                                      :filterable="item.filterable"
                                      :multiple="item.multiple" v-model="formGrid.table.query[item.name]"
                                      :placeholder="item.placeholder"
                                      :value="item.init"
                                      @on-change="(value)=>{item.onChange?item.onChange.call(this,value):()=>{}}"
                                      @on-query-change="(value)=>{item.onQueryChange?item.onQueryChange.call(this,value):()=>{}}"
                                      @on-clear="(value)=>{item.onClear?item.onClear.call(this,value):()=>{}}"
                                      @on-open-change="(value)=>{item.onOpenChange?item.onOpenChange.call(this,value):()=>{}}"
                            >
                                <Option v-for="(option,index) in item.items" :value="option.value" :key="'o'+index">
                                    {{ option.name }}
                                </Option>
                            </i-select>

                            <template v-else-if="item.type==='radio'">
                                <Radio-group v-model="formGrid.table.query[item.name]" :disabled="item.disabled">
                                    <Radio :label="o.label" v-for="(o,index) in item.items" :key="index">
                                        {{o.name}}
                                    </Radio>
                                </Radio-group>
                            </template>
                            <template v-else-if="item.type==='treeSelect'">
                                <u-tree-select :tree="item.tree" :refName="item.name"
                                               :fixed="item.fixed" :size="item.size" :maxHeight="item.maxHeight"
                                               :sort="item.sort"
                                               :klass="item.klass" :collapse="item.collapse" :async="item.async"
                                               v-model="formGrid.table.query[item.name]"></u-tree-select>
                            </template>
                            <DatePicker v-if="item.type === 'date'" type="date"
                                        :placeholder="item.placeholder?item.placeholder:''"
                                        :format="item.format?item.format:'yyyy-MM-dd'" :option="item.option"
                                        :placement="item.placement" :options="item.options" :confirm="item.confirm"
                                        :open="item.open" :size="item.size" :clearable="item.clearable"
                                        :readonly="item.readonly"
                                        :editable="item.editable" :transfer="item.transfer"
                                        v-model="formGrid.table.query[item.name]"></DatePicker>
                            <DatePicker v-if="item.type === 'daterange'"
                                        :type="item.type"
                                        :placeholder="item.placeholder?item.placeholder:''"
                                        :format="item.format?item.format:'yyyy-MM-dd'"
                                        :placement="item.placement" :options="item.options" :confirm="item.confirm"
                                        :open="item.open" :size="item.size" :clearable="item.clearable"
                                        :readonly="item.readonly"
                                        :editable="item.editable" :transfer="item.transfer"
                                        v-model="formGrid.table.query[item.name]"></DatePicker>
                            <DatePicker v-if="item.type === 'datetime' || item.type==='datetimerange'"
                                        :type="item.type"
                                        :placeholder="item.placeholder?item.placeholder:''"
                                        :format="item.format?item.format:'yyyy-MM-dd HH:mm:ss'"
                                        :placement="item.placement" :options="item.options" :confirm="item.confirm"
                                        :open="item.open" :size="item.size" :clearable="item.clearable"
                                        :readonly="item.readonly"
                                        :editable="item.editable" :transfer="item.transfer"
                                        v-model="formGrid.table.query[item.name]"></DatePicker>
                            <DatePicker v-if="item.type==='month' || item.type==='year'"
                                        :type="item.type"
                                        :placeholder="item.placeholder?item.placeholder:''"
                                        :format="item.format?item.format:null"
                                        :placement="item.placement" :options="item.options" :confirm="item.confirm"
                                        :open="item.open" :size="item.size" :clearable="item.clearable"
                                        :readonly="item.readonly"
                                        :editable="item.editable" :transfer="item.transfer"
                                        v-model="formGrid.table.query[item.name]"></DatePicker>
                            <Checkbox-group v-model="formGrid.form.data[item.name]" :disabled="item.disabled"
                                            v-if="item.type==='checkbox'">
                                <Checkbox :label="o.label" v-for="(o,index) in item.items" :key="index">{{o.name}}
                                </Checkbox>
                            </Checkbox-group>
                            <template v-if="item.type === 'queryButton' && formGrid.toolbar.superFilter.submit">
                                <Button v-if="item.type === 'queryButton' && formGrid.toolbar.superFilter.submit"
                                        :type="formGrid.toolbar.superFilter.submit.theme"
                                        :icon="formGrid.toolbar.superFilter.submit.icon"
                                        @click="superFilterSearch(1)"
                                        :disabled="formGrid.toolbar.superFilter.submit.disabled"
                                        :loading="formGrid.toolbar.superFilter.submit.loading"
                                        :size="formGrid.toolbar.superFilter.submit.size"
                                        :ghost="formGrid.toolbar.superFilter.submit.ghost"
                                        :shape="formGrid.toolbar.superFilter.submit.shape"
                                        :long="formGrid.toolbar.superFilter.submit.long">
                                    {{formGrid.toolbar.superFilter.submit.label}}
                                </Button>
                                <Button v-if="formGrid.toolbar.superFilter.reset"
                                        :type="formGrid.toolbar.superFilter.reset.theme"
                                        :icon="formGrid.toolbar.superFilter.reset.icon"
                                        @click="resetSuperFilterSearch"
                                        :disabled="formGrid.toolbar.superFilter.reset.disabled"
                                        :loading="formGrid.toolbar.superFilter.reset.loading"
                                        :size="formGrid.toolbar.superFilter.reset.size"
                                        :ghost="formGrid.toolbar.superFilter.reset.ghost"
                                        :shape="formGrid.toolbar.superFilter.reset.shape"
                                        :long="formGrid.toolbar.superFilter.reset.long">
                                    {{formGrid.toolbar.superFilter.reset.label}}
                                </Button>
                            </template>
                        </Form-item>
                    </i-col>
                </Row>
            </i-form>
        </Row>
        <!--toobar-->
        <Row :gutter="5" type="flex" :justify="formGrid.toolbar.justify" v-if="formGrid.toolbar.show">
            <i-col v-if="formGrid.toolbar.refresh&&formGrid.toolbar.refresh.show">
                <Button type="primary" icon="md-refresh" @click="pageData()">{{formGrid.toolbar.refresh.label}}</Button>
            </i-col>
            <i-col v-if="formGrid.toolbar.create &&formGrid.toolbar.create.show">
                <Button type="primary" icon="md-add" @click="formAdd">{{formGrid.toolbar.create.label}}</Button>
            </i-col>
            <i-col v-if="formGrid.toolbar.delete &&formGrid.toolbar.delete.show">
                <Button type="primary" icon="md-trash" @click="batchDelete" :loading="formGrid.toolbar.delete.loading"
                        ghost>
                    <span v-if="!formGrid.toolbar.delete.loading">  {{formGrid.toolbar.delete.label}}</span>
                    <span v-else>{{formGrid.toolbar.delete.label}}中...</span>
                </Button>
            </i-col>
            <!--按钮-->
            <template v-if="formGrid.toolbar.button">
                <i-col v-for="(button,index) in formGrid.toolbar.button" :key="index">
                    <Button :type="button.theme" :icon="button.icon" :custom-icon="button.customIcon"
                            @click="toolbarClick(button.click)" :ghost="button.ghost"
                            :disabled="button.disabled" :to="button.to" :replace="button.replace"
                            :target="button.target"
                            :loading="button.loading" :size="button.size" :shape="button.shape" :long="button.long">
                        {{button.label}}
                    </Button>
                </i-col>
            </template>

            <!--导入导出-->
            <i-col v-if="formGrid.toolbar.groups.show">
                <Dropdown v-if="formGrid.toolbar.groups">
                    <Button type="primary">
                        <Icon type="logo-buffer"></Icon>
                        {{formGrid.toolbar.groups.label}}
                        <Icon type="md-arrow-dropdown"/>
                    </Button>
                    <Dropdown-menu slot="list">
                        <Dropdown-item
                            v-if="formGrid.toolbar.groups.export && formGrid.toolbar.groups.export.currentShow">
                            <a href="javascript:void(0)" @click="exportCurrentPageData">
                                <Icon type="md-download"></Icon>
                                {{formGrid.toolbar.groups.export.currentLabel}}
                            </a>
                        </Dropdown-item>

                        <Dropdown-item v-if="formGrid.toolbar.groups.export && formGrid.toolbar.groups.export.show">
                            <a href="javascript:void(0)" @click="exportData">
                                <Icon type="md-download"></Icon>
                                {{formGrid.toolbar.groups.export.label}}
                            </a>
                        </Dropdown-item>

                        <Dropdown-item v-if="formGrid.toolbar.groups.import && formGrid.toolbar.groups.import.show">
                            <a href="javascript:void(0)" @click="showImportView">
                                <Icon type="md-cloud-upload"/>
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
                        <Icon type="md-arrow-dropdown"/>
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
                    <Button slot="append" icon="md-search" @click="pageData(1)"></Button>
                </i-input>
            </i-col>
            <i-col>

            </i-col>
        </Row>
        <!--table-->
        <Row style="margin-top: 10px;">
            <Table :width="formGrid.table.width" :height="formGrid.table.height" border
                   :columns="formGrid.table.columns"
                   :data="formGrid.table.data" :stripe="formGrid.table.stripe" :loading="formGrid.table.loading"
                   :size="formGrid.table.size"
                   :no-data-text="table.noDataText" @on-selection-change="onSelectionChange" :ref="tableRef"
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
            :title="modal.title"
            :closable="modal.closable"
            :mask-closable="modal.maskClosable"
            :loading="modal.loading"
            :scrollable="modal.scrollable"
            :width="modal.width">
            <Form :model="formGrid.form.data" :label-position="formGrid.form.labelPosition"
                  :label-width="formGrid.form.labelWidth"
                  :rules="ruleValidate" :ref="formGrid.form.name">
                <Row v-for="(row,index1) in formRows" :key="'formRow'+index1">
                    <i-col v-for="(item,index2) in row" :key="'formItem'+index2"
                           :span="24/formGrid.form.colNumber">
                        <Form-item :label="item.label" :prop="item.name" v-if="item.show">

                            <i-input v-model="formGrid.form.data[item.name]" :type="item.type"
                                     :placeholder="item.placeholder" :icon="item.icon"
                                     v-if="item.type==='text'" :disabled="item.disabled"
                                     @on-focus="toolbarClick(item.focus)"
                                     :maxlength="item.maxlength" :readonly="item.readonly"
                            >
                                <span slot="prepend" v-if="item.prepend">{{item.prepend}}</span>
                                <span slot="append" v-if="item.append">{{item.append}}</span>
                            </i-input>

                            <i-input v-model="formGrid.form.data[item.name]" :type="item.type"
                                     :placeholder="item.placeholder"
                                     v-if="item.type==='password'" :disabled="item.disabled">
                            </i-input>
                            <!--隐藏表单-->
                            <input type="hidden" v-if="item.type==='hidden'" v-model="formGrid.form.data[item.name]">
                            <i-switch v-model="formGrid.form.data[item.name]" @on-change="item.change"
                                      v-if="item.type === 'switch'"></i-switch>

                            <InputNumber v-model="formGrid.form.data[item.name]" :type="item.type"
                                         :placeholder="item.placeholder"
                                         v-if="item.type==='number'" :disabled="item.disabled" :max="item.max"
                                         :min="item.min"
                                         :step="item.step" style="width: 100%">
                            </InputNumber>

                            <i-input v-model="formGrid.form.data[item.name]" :type="item.type"
                                     :placeholder="item.placeholder"
                                     v-if="item.type==='email'" :disabled="item.disabled"/>

                            <Cascader v-if="item.type === 'cascader'" :data="item.items"
                                      v-model="formGrid.form.data[item.name]"
                                      :render-format="cascaderFormat" :change-on-select="!!item.changeOnSelect"
                                      @on-change="(value)=>{item.onChange?item.onChange.call(this,value):()=>{}}"
                                      :disabled="item.disabled"></Cascader>

                            <template v-else-if="item.type==='select'">
                                <i-select v-if="item.type === 'select'" :clearable="item.clearable"
                                          :filterable="item.filterable"
                                          :multiple="item.multiple" v-model="formGrid.form.data[item.name]"
                                          :placeholder="item.placeholder"
                                          @on-change="(value)=>{item.onChange?item.onChange.call(this,value):()=>{}}"
                                          @on-query-change="(value)=>{item.onQueryChange?item.onQueryChange.call(this,value):()=>{}}"
                                          @on-clear="(value)=>{item.onClear?item.onClear.call(this,value):()=>{}}"
                                          @on-open-change="(value)=>{item.onOpenChange?item.onOpenChange.call(this,value):()=>{}}"
                                >

                                    <Option v-for="(option,index) in item.items" :value="option.value" :key="'o'+index">
                                        {{ option.name }}
                                    </Option>
                                </i-select>
                            </template>

                            <template v-else-if="item.type==='radio'">
                                <Radio-group v-model="formGrid.form.data[item.name]" :disabled="item.disabled">
                                    <Radio :label="o.label" v-for="(o,index) in item.items" :key="index">{{o.name}}
                                    </Radio>
                                </Radio-group>
                            </template>

                            <template v-else-if="item.type==='date'">
                                <DatePicker type="date" :placeholder="item.placeholder?item.placeholder:'选择日期'"
                                            v-model="formGrid.form.data[item.name]" :disabled="item.disabled"
                                            :format="item.format?item.format:'yyyy-MM-dd'" :placement="item.placement"
                                            :options="item.options"
                                            :confirm="item.confirm"
                                            :open="item.open" :size="item.size" :clearable="item.clearable"
                                            :readonly="item.readonly"
                                            :editable="item.editable" :transfer="item.transfer"
                                ></DatePicker>
                            </template>

                            <template v-else-if="item.type==='datetime'">
                                <DatePicker type="datetime" :placeholder="item.placeholder?item.placeholder:'选择日期和时间'"
                                            v-model="formGrid.form.data[item.name]" :disabled="item.disabled"
                                            :format="item.format" :placement="item.placement" :options="item.options"
                                            :confirm="item.confirm"
                                            :open="item.open" :size="item.size" :clearable="item.clearable"
                                            :readonly="item.readonly"
                                            :editable="item.editable" :transfer="item.transfer"
                                ></DatePicker>
                            </template>
                            <template v-else-if="item.type==='daterange'">
                                <DatePicker type="daterange"
                                            :placeholder="item.placeholder?item.placeholder:''"
                                            :format="item.format?item.format:'yyyy-MM-dd'"
                                            v-model="formGrid.form.data[item.name]"></DatePicker>
                            </template>
                            <template v-else-if="item.type==='datetime'">
                                <TimePicker type="datetime" :placeholder="item.placeholder?item.placeholder:'选择时间'"
                                            v-model="formGrid.form.data[item.name]"
                                            :disabled="item.disabled"></TimePicker>
                            </template>
                            <template v-else-if="item.type==='time'">
                                <TimePicker type="time" :placeholder="item.placeholder?item.placeholder:'选择时间'"
                                            v-model="formGrid.form.data[item.name]"
                                            :disabled="item.disabled"></TimePicker>
                            </template>

                            <template v-else-if="item.type==='month' || item.type==='year'">
                                <TimePicker :type="item.type" :placeholder="item.placeholder?item.placeholder:''"
                                            v-model="formGrid.form.data[item.name]"
                                            :disabled="item.disabled"></TimePicker>
                            </template>
                            <template v-else-if="item.type==='treeSelect'">
                                <u-tree-select :tree="item.tree" :refName="item.name"
                                               :fixed="item.fixed" :size="item.size" :maxHeight="item.maxHeight"
                                               :sort="item.sort"
                                               :klass="item.klass" :collapse="item.collapse" :async="item.async"
                                               v-model="formGrid.form.data[item.name]"></u-tree-select>
                            </template>

                            <template v-else-if="item.type==='checkbox'">
                                <Checkbox-group v-model="formGrid.form.data[item.name]" :disabled="item.disabled">
                                    <Checkbox :label="o.label" v-for="(o,index) in item.items" :key="index">{{o.name}}
                                    </Checkbox>
                                </Checkbox-group>
                            </template>

                            <template v-else-if="item.type==='textarea'">
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

            </Form>
            <div slot="footer">
                <Button type="primary" @click="handleSubmit()" :loading="formGrid.form.loading">
                    <span v-if="!formGrid.form.loading">提交</span>
                    <span v-else>提交中...</span>
                </Button>
                <Button type="primary" @click="handleReset()" style="margin-left: 8px" :disabled="formGrid.form.loading"
                        ghost>
                    重置
                </Button>
                <Button @click="cancel()" style="margin-left: 8px" :disabled="formGrid.form.loading">取消</Button>
            </div>
        </Modal>

        <!--查看详情界面-->
        <Modal
            v-model="modal.viewModal"
            :title="modal.title"
            :closable="modal.closable"
            :mask-closable="modal.maskClosable"
            :loading="modal.loading"
            :scrollable="modal.scrollable"
            :width="modal.width">
            <Form :label-position="formGrid.form.labelPosition"
                  :label-width="formGrid.form.labelWidth" :ref="formGrid.form.name+'edit'">
                <Row v-for="(row,index1) in formRows" :key="'formRow2'+index1">
                    <i-col v-for="(item,index2) in row" :key="'formEdit'+index2"
                           :span="24/formGrid.form.colNumber">

                        <Form-item :label="item.label" :prop="item.name" v-if="item.show">
                            <template v-if="item.type==='checkbox'">
                                <Checkbox-group v-model="formGrid.form.data[item.name]">
                                    <Checkbox :label="o.label" v-for="(o,index) in item.items" :key="index">{{o.name}}
                                    </Checkbox>
                                </Checkbox-group>
                            </template>

                            <template v-else-if="item.type==='textarea'">
                                <i-input type="textarea" :placeholder="item.placeholder?item.placeholder:'请输入...'"
                                         v-model="formGrid.form.data[item.name]" :rows="item.rows"
                                         :icon="item.icon" :maxlength="item.maxlength" :readonly="item.readonly"
                                         disabled
                                ></i-input>
                            </template>
                            <template v-else-if="item.type==='select'">
                                <i-select disabled v-model="formGrid.form.data[item.name]" readonly>
                                    <Option v-for="(option,index) in item.items" :value="option.value" :key="'o'+index">
                                        {{ option.name }}
                                    </Option>
                                </i-select>
                            </template>
                            <template v-else-if="item.type==='date'">
                                <DatePicker type="date" :placeholder="item.placeholder?item.placeholder:'选择日期'"
                                            v-model="formGrid.form.data[item.name]"
                                            :format="item.format?item.format:'yyyy-MM-dd'" :readonly="item.readonly"
                                ></DatePicker>
                            </template>

                            <template v-else-if="item.type==='datetime'">
                                <DatePicker type="datetime" :placeholder="item.placeholder?item.placeholder:'选择日期和时间'"
                                            v-model="formGrid.form.data[item.name]"
                                            :format="item.format"
                                            :editable="item.editable" :transfer="item.transfer" readonly
                                ></DatePicker>
                            </template>
                            <template v-else-if="item.type==='daterange'">
                                <DatePicker type="daterange"
                                            :placeholder="item.placeholder?item.placeholder:''"
                                            :format="item.format?item.format:'yyyy-MM-dd'" readonly
                                            v-model="formGrid.form.data[item.name]"></DatePicker>
                            </template>
                            <template v-else-if="item.type==='datetime'">
                                <TimePicker type="datetime" :placeholder="item.placeholder?item.placeholder:'选择时间'"
                                            v-model="formGrid.form.data[item.name]" readonly></TimePicker>
                            </template>
                            <template v-else-if="item.type==='time'">
                                <TimePicker type="time" :placeholder="item.placeholder?item.placeholder:'选择时间'"
                                            v-model="formGrid.form.data[item.name]" readonly></TimePicker>
                            </template>

                            <template v-else-if="item.type==='month' || item.type==='year'">
                                <TimePicker :type="item.type" :placeholder="item.placeholder?item.placeholder:''"
                                            v-model="formGrid.form.data[item.name]" readonly></TimePicker>
                            </template>
                            <template v-else>
                                <i-input v-model="formGrid.form.data[item.name]" readonly/>
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
                <p slot="header" style="color:rgb(60, 65, 81); text-align:center">
                    <Icon type="md-arrow-up"></Icon>
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

    .ivu-input[disabled],
    fieldset[disabled] .ivu-input {
        color: #7b7777 !important;
    }

    /* 自定义样式 */
    .optColumn .ivu-table-cell {
        text-align: center;
        padding-left: 5px;
        padding-right: 5px;
    }

    /* table-cell当中的poptip样式覆盖 */
    .ivu-table-cell .ivu-tooltip {
        display: block;
    }

    .ivu-table-cell .ivu-tooltip-rel {
        display: block;
    }
</style>

<script>
    import Log from '../../utils/Log'
    import deepExtend from 'deep-extend'
    import defaultData from './default'
    import util from 'core-util-is'
    import ueUpload from '../upload/Upload.vue'
    import UTreeSelect from '../tree-select/UTreeSelect'

    export default {
        name: 'UFormGrid',
        components: {
            'ueUpload': ueUpload,
            UTreeSelect
        },
        props: {
            tableRef: {
                type: String,
                default: function () {
                    return 'dataTable'
                }
            },
            // 允许调用方修改该属性，从而可以动态控制配置参数
            data: {
                type: Object,
                default: function () {
                    // 为防止新增的属性无法被监控到，需要事先定义好所有属性
                    return {
                        options: {autoLoad: true},
                        tips: {'title': '', 'content': ''},
                        exportParams: [],
                        pageable: {
                            page: 1,
                            size: 15
                        },
                        toolbar: {
                            refresh: {},
                            create: {},
                            delete: {},
                            export: {},
                            import: {},
                            button: null,
                            buttons: null,
                            filter: {},
                            superFilter: {columns: []}
                        },
                        form: {data: {}, modal: {}, columns: []},
                        table: {operation: {buttons: null, column: {}}, data: [],query:{}}
                    }
                }
            }
        },
        data() {
            return {
                file: null,
                self: this,
                loadingStatus: false,
                modal: {
                    title: "",
                    // 显示编辑模态窗口
                    editModal: false,
                    // 显示查看模态窗口
                    viewModal: false,
                    // 显示导入文件模态窗口
                    importModal: false,
                    loading: true,
                    width: 756,
                    scrollable: true,
                    maskClosable: false,
                    closable: true
                },

                // 表单规则校验
                ruleValidate: {},
                // 搜索表单规则校验
                searchRuleValidate: {},
                // 表格选中的项目集合
                selections: [],
                // 渲染表单的元素列表，根据场景要求渲染的列表会有差异
                formRows: [],
                //
                formGrid: {},
                table: {
                    noDataText: '',
                }
            }
        },
        created() {
            this.init()
        },
        watch: {
            // 监听高级搜索条件变化
            'data.toolbar.superFilter': {
                handler: function (newValue, oldValue) {
                    Log.d('监听到data.toolbar.superFilter.columns变化,%o', newValue)
                    this.formGrid.toolbar.superFilter = deepExtend({}, this.formGrid.toolbar.superFilter, newValue)
                    // 对搜索表单数据进行处理
                    this.renderSearchForm()
                },
                deep: true
            },
            'data.form': {
                handler: function (newValue, oldValue) {
                    Log.d('监听到data.form.columns变化,%o', newValue)
                    this.formGrid.form = deepExtend({}, this.formGrid.form, newValue)
                    this.renderForm()
                    if (this.formGrid.form.isView) {
                        this.setFormColumns('view')
                    } else if (this.formGrid.form.isEdit) {
                        this.setFormColumns('edit')
                    } else {
                        this.setFormColumns('add')
                    }
                },
                deep: true
            }
        },
        methods: {
            init() {
                this.formGrid = deepExtend({}, this.formGrid, this.data)
                // 和默认值进行合并，一定要加一个{},防止修改掉defaultData对象，导致页面切换时数据异常
                this.formGrid = deepExtend({}, defaultData, this.formGrid)
                this.table.noDataText = this.formGrid.table.noDataText

                this.renderForm()
                // 对搜索表单数据进行处理
                this.renderSearchForm()
                // 表格数据初始化
                // 追加操作列
                if (this.formGrid.table.operation.show) {
                    // 为操作添加按钮
                    this.renderOpt(this.formGrid.table.operation.column)
                    this.formGrid.table.columns.push(this.formGrid.table.operation.column)
                }
                // 判断否需要显示复选框
                if (this.formGrid.table.showCheckbox) {
                    this.formGrid.table.columns.splice(0, 0, this.formGrid.table.selection)
                }
                // 渲染表格每列特殊情况
                this.renderColumn()
                Log.d('data 初始化对象:%o', this.formGrid)
            },
            // 对添加、编辑、查看表单数据进行初始化
            renderForm() {
                // 生成表单规则校验
                if (this.formGrid.form.columns) {
                    this.ruleValidate = this.getRuleValidate(this.formGrid.form.columns)
                }
            },

            // 对搜索表单数据进行数据加工
            renderSearchForm() {
                // 设置高级搜索当中，如果存在下拉框的元素，需要进行数据初始化
                if (this.formGrid.toolbar.superFilter && this.formGrid.toolbar.superFilter.columns) {
                    this.setSelectItems(this.formGrid.toolbar.superFilter.columns)
                    this.searchRuleValidate = this.getRuleValidate(this.formGrid.toolbar.superFilter.columns)
                    // 对所有列进行动态计算行数，用于页面渲染
                    let rows = []
                    let columns = []
                    let colNumber = this.formGrid.toolbar.superFilter.colNumber
                    // 判断所有列当中是否有非隐藏的元素，如果全部为隐藏元素则不显示查询按钮
                    let allHidden = true
                    let i = 0
                    let span = Math.round(24 / colNumber)
                    this.formGrid.toolbar.superFilter.columns.forEach((c) => {
                        if (i > 0 && (i % colNumber === 0)) {
                            rows.push(columns)
                            columns = []
                        }
                        if (c.type !== 'hidden') {
                            allHidden = false
                            c.span = span
                            columns.push(c)
                            i++
                            this.formGrid.table.query[c.name] = null
                        }
                        //初始化变量，防止表单元素未绑定变量，导致双向绑定在重置表单后出现不可重置
                        this.$set(this.formGrid.table.query, c.name, c.init)
                    })
                    if (columns.length > 0 && columns.length < colNumber) {
                        if (!allHidden) {
                            let queryButtonSpan = (colNumber - columns.length) * span
                            columns.push({'type': 'queryButton', 'span': queryButtonSpan})
                        }
                        rows.push(columns)
                        // 如果最后行数量刚好满员，则新启一行
                    } else if (columns.length === colNumber) {

                        rows.push(columns)
                        columns = []
                        // 查询按钮一直跟在条件最后一个
                        if (!allHidden) {
                            columns.push({'type': 'queryButton', 'span': 24})
                        }
                        rows.push(columns)
                    }
                    this.formGrid.toolbar.superFilter.rows = rows
                }
            },
            // 初始化表单规则
            getRuleValidate(columns) {
                // TODO 当动态修改规则后，页面上还是会显示红色*号
                let rules = {}
                columns.forEach((c) => {
                    let ruleName = c.name
                    // 直接写required的话，采用默认提示规则。写了rule属性则使用rule
                    if (c.required && !c.rules) {
                        let trigger = 'blur';
                        // 固定的几种类型采用change事件监听
                        ['select', 'radio', 'datetime', 'date', 'time', 'month', 'daterange',
                            'datetimerange', 'year', 'checkbox', 'treeSelect'].forEach((type) => {
                            if (c.type === type) {
                                trigger = 'change'
                            }
                        })

                        let rule = {required: true, message: c.label + '为必填', trigger: trigger}
                        // 级联框，时间或日期范围
                        if (['cascader', 'checkbox'].includes(c.type)) {
                            rule['type'] = 'array'
                        } else if (['datetime', 'date', 'time', 'month', 'year'].includes(c.type)) {
                            rule['type'] = 'date'
                        } else if (['daterange', 'datetimerange'].includes(c.type)) {
                            rule['type'] = 'object'
                        } else if (['treeSelect', 'number'].includes(c.type)) {
                            rule['type'] = 'number'
                        } else {
                            rule['type'] = 'string'
                        }
                        let ruleArray = [rule]
                        if (c.equalsTo) {
                            const validatePassCheck = (rule, value, callback) => {
                                if (value === '') {
                                    callback(new Error(c.label + '为必填'))
                                } else if (value !== this.formGrid.form.data[c.equalsTo]) {
                                    callback(new Error(`两次输入的内容不一致!`))
                                } else {
                                    callback()
                                }
                            }
                            let equalsToRule = {validator: validatePassCheck, trigger: trigger}
                            ruleArray.push(equalsToRule)
                        }
                        rules[ruleName] = ruleArray
                    } else if (c.rules) {
                        // 其他自定义格式直接采用原生格式（参见iview组件当中的form表单验证格式）
                        rules[ruleName] = c.rules
                    }
                })
                Log.d('rules:%o', rules)
                return rules
            },
            noticeError(title, desc) {
                this.$Notice.error({
                    title: title,
                    desc: desc
                })
            },
            validate(key) {
                if (!key || key === null) {
                    this.noticeError(key + '不能为空!')
                    return false
                }
                return true
            },
            // 获取接口交互的主键
            getPrimaryKey(row) {
                let key = this.formGrid.table.operation.primaryKey
                if (row[key]) {
                    let o = {}
                    o[key] = row[key]
                    return o
                } else {
                    this.noticeError('请求参数不正确', '无法获取primaryKey值')
                    return null
                }
            },
            // 重置查询条件
            resetSuperFilterSearch() {
                this.$refs[this.formGrid.toolbar.superFilter.name].resetFields()
                if (util.isFunction(this.formGrid.toolbar.superFilter.reset.click)) {
                    this.formGrid.toolbar.superFilter.reset.click()
                }

            },
            // 高级搜索框按钮
            superFilterSearch(page) {
                this.pageData(page)
            },
            pageData(page) {
                if (page && (typeof (page) === 'number')) {
                    this.formGrid.pageable.page = page
                }
                if (this.formGrid.toolbar.superFilter.rows.length > 0) {
                    this.$refs[this.formGrid.toolbar.superFilter.name].validate((valid) => {
                        if (valid) {
                            this.fetchData()
                        } else {
                            this.noticeError('查询条件校验失败', '')
                        }
                    })
                } else {
                    this.fetchData()
                }
            },
            fetchData() {
                this.table.noDataText = this.formGrid.table.tableLoadingText
                let data = this.formGrid.table.query
                Log.d('pageData QueryData:%o', data)
                let page = this.formGrid.pageable.page
                let size = this.formGrid.pageable.size
                // 默认jpa查询从0开始，页面上显示从1开始所以需要减一
                if (page > 0) {
                    page = page - 1
                }
                let params = {page: page, size: size}
                this.formGrid.table.loading = true
                this.$axios.post(this.formGrid.options.url.page, data, {params: params}).then(response => {
                    if (this.formGrid) {
                        this.formGrid.table.loading = false
                        this.table.noDataText = this.formGrid.table.noDataText
                        this.formGrid.table.data = response.body.content
                        this.formGrid.pageable.total = response.body.totalElements
                        this.$forceUpdate()
                    }
                    Log.d('接口返回对象,%o', response)
                }).catch(response => {
                    this.noticeError('数据查询出现异常', response.message ? response.message : '系统或网络异常')
                    if (this.formGrid) {
                        this.formGrid.table.loading = false
                        this.formGrid.table.data = []
                        this.formGrid.table.noDataText = this.formGrid.table.tableLoadedErrorText
                    }
                    this.$forceUpdate()
                })
            },

            // 改变分页
            changePage(page) {
                this.formGrid.pageable.page = page
                this.pageData()
            },
            // 改变页数大小
            changePageSize(size) {
                this.formGrid.pageable.page = 1
                this.formGrid.pageable.size = size
                this.pageData()
            },
            // 重新从第一页加载数据
            reloadData() {
                this.formGrid.pageable.page = 1
                this.formGrid.pageable.total = 0
                this.pageData()
            },
            // 模态窗口点击确认按钮事件
            handleSubmit() {
                this.$refs[this.formGrid.form.name].validate((valid) => {
                    if (valid) {
                        if (!this.formGrid.form.submitBefore(this.formGrid.form.data)) {
                            return
                        }
                        this.formGrid.form.loading = true
                        this.$axios.post(this.formGrid.options.url.save, this.formGrid.form.data).then(response => {
                            this.formGrid.form.loading = false
                            this.$Notice.success({
                                title: '表单提交成功',
                                desc: ''
                            })
                            // 编辑时，查询当前页
                            if (this.formGrid.form.isEdit) {
                                this.pageData()
                            } else {
                                this.reloadData()
                            }
                            this.formGrid.form.data = {}
                            this.modal.editModal = false
                            this.formGrid.form.submitAfter(response.body)
                            this.$forceUpdate()
                        }).catch(response => {
                            this.formGrid.form.loading = false
                            this.noticeError('表单提交失败', response.message ? response.message : '系统或网络异常')
                            this.$forceUpdate()
                            return false
                        })
                    } else {
                        this.formGrid.form.loading = false
                        this.noticeError('表单校验失败', '')
                    }
                })
            },
            // 重置表单
            handleReset() {
                this.$refs[this.formGrid.form.name].resetFields()
                this.$forceUpdate()
            },
            // 模态窗口点击取消按钮事件
            cancel() {
                this.modal.editModal = false
                if (this.formGrid.form.onCancel) {
                    this.formGrid.form.onCancel()
                }
            },
            // 设置表单的渲染元素，添加、编辑、查看可能要求不同，所以需要重新渲染。
            setFormColumns(type) {
                this.handleReset()
                // 从用户设定的元素列表当中copy一份数据给当前操作使用，避免数据污染
                let o = deepExtend({}, {columns: this.formGrid.form.columns})
                for (let c of o.columns) {
                    // 初始化默认值
                    if (type === 'add' && c.init) {
                        // 为number类型设置默认值，避免组件无法使用。
                        if (c.type === 'number' && !c.init) {
                            c.init = 1
                        }
                        this.$set(this.formGrid.form.data, c.name, c.init)
                    }
                    // 获取当前类型下，item元素是否显示以及禁用
                    if (c[type]) {
                        c = deepExtend(c, c[type])
                    }
                    // 未定义该属性时，默认为true
                    if (c.show === undefined) {
                        c.show = true
                    }
                }

                this.setSelectItems(o.columns)
                // 设置级联下拉框
                this.setCascaderData(o.columns)
                // 重新组织格式，便于页面换行显示
                // 对所有列进行动态计算行数，用于页面渲染
                let rows = []
                let columns = []
                let colNumber = this.formGrid.form.colNumber
                let count = 0
                o.columns.forEach((c) => {
                    if (count > 0 && (count % colNumber === 0)) {
                        rows.push(columns)
                        columns = []
                    }
                    // 只有需要显示的才加入
                    if (c.show) {
                        count++
                        columns.push(c)
                    }
                })
                if (columns.length > 0) {
                    rows.push(columns)
                    columns = []
                }
                this.formRows = rows
                //如果title是字符串，则直接使用字符串，否则使用对应类型的标题
                if (util.isString(this.formGrid.form.modal.title)) {
                    this.modal.title = this.formGrid.form.modal.title
                } else {
                    this.modal.title = this.formGrid.form.modal.title[type]
                }
                Log.d('formRows:%o ,form.data:%o', this.formRows, this.formGrid.form.data)
            },
            /**
             *设置下拉框属性
             @target 数组，需要被设置的数组对象，数组当中每个元素与form表单需要的元素一样
             */
            setSelectItems(target) {
                // 针对type为select的数据，进行额外处理
                for (let c of target) {
                    if (c.type !== 'select') {
                        continue
                    }
                    Log.d('setSelectItems,%o', c)
                    let data = c.data || ''
                    if (util.isArray(data)) {
                        c.items = data
                    } else if (util.isString(data)) {
                        if (data.indexOf('im:') === 0) {
                            let value = data.split(':')[1]
                            // TODO 字典缓存
                            Log.d('select 组件查询数据字典:%s', value)
                            this.$axios.post(this.formGrid.options.url.im, {parentValue: value}).then(response => {
                                // 将值塞入到formCloumns当中。
                                this.$set(c, 'items', response.body)
                            })
                        } else if (data.indexOf('url:') === 0) {
                            let url = data.split(':')[1]
                            this.$axios.post(url, {}).then(response => {
                                // 将值塞入到formCloumns当中。
                                this.$set(c, 'items', response.body)
                            })
                        } else {
                            Log.e('当前select定义的data不符合规范,%s', data)
                        }
                    } else {
                        Log.e('当前select未定义data,%o', c)
                    }
                }
            },
            // 对级联下拉框进行赋值
            setCascaderData(target) {
                // 针对type为select的数据，进行额外处理
                for (let c of target) {
                    if (c.type !== 'cascader') {
                        continue
                    }
                    Log.d('setCascaderData,%o', c)
                    let data = c.data || ''
                    if (util.isArray(data)) {
                        c.items = this.initCascader(data)
                    } else if (util.isString(data)) {
                        if (data.indexOf('url:') === 0) {
                            let url = data.split(':')[1]
                            this.$axios.post(url, {}).then(response => {
                                // 将值塞入到formCloumns当中。
                                // 对值进行树状初始化
                                this.$set(c, 'items', this.initCascader(response.body))
                            })
                        } else {
                            Log.e('当前cascader定义的data不符合规范,%s', data)
                        }
                    } else {
                        Log.e('当前cascader未定义data,%o', c)
                    }
                }
            },
            // 初始化级联
            initCascader(data) {
                // 1.查找root
                let root = []
                data.forEach((item) => {
                    if (!item.parentId || item.parentId === null) {
                        let o = {
                            id: item.id,
                            label: item.name,
                            value: item.value,
                            attr: item,
                            parentId: item.parentId,
                            children: []
                        }
                        o.children = this.getCascaderChildren(data, o)
                        root.push(o)
                    }
                })

                // 生成树结构数据
                return root
            },
            // 2.递归循环所有节点,将节点加入到父节点当中
            getCascaderChildren(data, parent) {
                let sort = this.sort
                let child = []
                data.forEach((item) => {
                    if (item.parentId === parent.id) {
                        let o = {
                            id: item.id,
                            label: item.name,
                            value: item.value,
                            parentId: item.parentId,
                            attr: item,
                            children: []
                        }
                        o.children = this.getCascaderChildren(data, o)
                        child.push(o)
                    }
                })
                // 是否需要排序
                if (sort) {
                    child.sort((a, b) => {
                        if (sort['sort'] === 'desc') {
                            return b[sort['field']] - a[sort['field']]
                        } else {
                            return b[sort['field']] - a[sort['field']]
                        }
                    })
                }

                return child
            },
            // toolbar添加按钮事件
            formAdd() {
                // 清空表单
                this.formGrid.form.data = {}
                this.setFormColumns('add')
                this.modal.editModal = true
            },
            // toolbar删除按钮事件
            batchDelete() {
                if (this.selections.length === 0) {
                    this.noticeError('数据删除失败', '没有选中任意数据')
                    return
                }
                let keys = []
                this.selections.forEach((item) => {
                    let p = this.getPrimaryKey(item)
                    if (p) {
                        keys.push(p[this.formGrid.table.operation.primaryKey])
                    }
                })
                if (keys.length === 0) {
                    return
                }
                this.$Modal.confirm({
                    title: '删除数据',
                    content: '<span style="color:red">确定删除选中的记录吗？</span>',
                    onOk: () => {
                        let key = this.formGrid.table.operation.primaryKey
                        let data = {}
                        let params = {}
                        params['params'] = {}
                        params['params'][key] = keys.toString()

                        Log.d('要删除的数据:%o', params)
                        this.formGrid.toolbar.delete.loading = true
                        this.$axios.post(this.formGrid.options.url.delete, data, params).then(response => {
                            this.formGrid.toolbar.delete.loading = false
                            this.$Notice.success({
                                title: '删除成功',
                                desc: ''
                            })
                            this.pageData()
                        }).catch(response => {
                            this.formGrid.toolbar.delete.loading = false
                            this.noticeError('删除失败', response.message ? response.message : '系统或网络异常')
                            this.$forceUpdate()
                            return false
                        })
                    },
                    onCancel: () => {
                        // this.$Message.info('点击了取消');
                    }
                })
            },
            // 选中某一项触发，返回值为 selection 和 row，分别为已选项和刚选择的项。
            onSelect(selection) {

            },
            // 只要选中项发生变化时就会触发，返回值为 selection，已选项。
            onSelectionChange(selection) {
                this.selections = selection
                Log.d('####onSelectionChange,%o', selection)
            },
            // 点击全选时触发，返回值为 selection，已选项。
            onSelectAll(selection) {

            },
            // 按钮点击事件
            toolbarClick(btnClickCB) {
                if (btnClickCB) {
                    btnClickCB(this.selections)
                }
            },
            // 级联框格式化
            cascaderFormat(labels, selectedData) {
                Log.d('###cascaderFormat,%o', labels)
                return labels.length > 0 ? labels.join('/') : ''
            },
            ccOnChange(value, selectedData) {
            },
            // TODO 返回用户选中的行数据给到按钮操作当中。
            // table当中点击查看按钮事件
            tableViewClick(params) {
            },
            // 渲染表格自定义列样式
            renderColumn() {
                this.formGrid.table.columns.forEach((c) => {
                    if (c.renderType && !util.isFunction(c.render)) {
                        c.render = (h, params) => {
                            // 使用当前作用域
                            let _this = this
                            let originKey = c.key
                            // 获取原始值
                            if (c.key.endsWith('_format')) {
                                originKey = c.key.substr(0, c.key.length - 7)
                            }
                            let value = this.tableFieldFormat(c, h, params)
                            let originValue = params['row'][originKey] || ''
                            let tmp = [...originValue]
                            if (c.renderType === 'tooltip') {
                                // 如果存在内容格式化回调，则允许自定义格式内容
                                if (util.isFunction(c.contentFormat)) {
                                    let a = c.contentFormat(originValue, params['row'])
                                    tmp = [...a]
                                }
                                let i = 0
                                let str = []
                                let tmpStr = []
                                for (let t of tmp) {
                                    tmpStr.push(t)
                                    if (i > 15) {
                                        i = 0
                                        str.push(h('p', tmpStr.join('')))
                                        tmpStr = []
                                    }
                                    i++
                                }
                                str.push(h('p', tmpStr.join('')))
                                return h('Tooltip', [h('div', {
                                    style: {
                                        overflow: 'hidden',
                                        textOverflow: 'ellipsis',
                                        whiteSpace: 'nowrap'
                                    }
                                }, value), h('p', {slot: 'content'}, str)])
                            } else if (c.renderType === 'a') {
                                return h('a', {
                                    attrs: {href: 'javascript:void(0)'},
                                    style: {textDecoration: 'underline'},
                                    on: {
                                        click: function () {
                                            let row = _this.formGrid.table.data[params.index]
                                            Log.d('列标题点击,row:%o', row)
                                            c.click(row, params.index, _this)
                                        }
                                    }
                                }, value)
                            }
                        }
                        // 没有自定义render的时候，就添加一个默认的render用来格式化数据
                    } else if (util.isFunction(c.fieldFormat) || util.isFunction(c.format)) {
                        c.render = (h, params) => {
                            let formatValue = this.tableFieldFormat(c, h, params)
                            return h('span', {}, formatValue)
                        }
                    }
                })
            },
            // table表格数据格式化
            tableFieldFormat(c, h, params) {
                // 兼容原有代码
                if (util.isFunction(c.format)) {
                    c.fieldFormat = c.format
                }
                let formatValue = ''
                if (util.isFunction(c.fieldFormat)) {
                    let key = c.key
                    let originKey = key + ''
                    // 默认对key做转换，防止format后将原值进行了修改，导致编辑页面获取不到
                    if (key.endsWith('_format')) {
                        originKey = key.substr(0, key.length - 7)
                    } else {
                        key += '_format'
                    }
                    // 改变定义的key值，用于页面渲染
                    c.key = key
                    let value = params['row'][originKey]
                    let o = c.fieldFormat(value, params['row'])
                    // 返回值如果是对象，且有cellClassName属性，则需要value属性
                    if (util.isObject(o) && util.isString(o['cellClassName'])) {
                        formatValue = o['value']
                        params['row'][key] = formatValue
                        params['row']['cellClassName'] = {}
                        params['row']['cellClassName'][key] = o['cellClassName']
                    } else {
                        formatValue = o
                        params['row'][key] = o
                    }
                }
                return formatValue
            },
            // 渲染操作列按钮
            renderOpt(column) {
                // 为操作列增加render函数
                column.render = (h, params) => {
                    // 使用当前作用域
                    let _this = this

                    function create(h, params, className, btnText, clickEvent, ghost) {
                        let props = {
                            type: className,
                            size: 'small'
                        }
                        if (ghost) {
                            props['ghost'] = ghost
                        }
                        return h('Button', {
                            props: props,
                            style: {
                                marginRight: '5px'
                            },
                            on: {
                                click: function () {
                                    let row = _this.formGrid.table.data[params.index]
                                    Log.d('操作功能点击事件,row:%o', row)
                                    clickEvent(row, params.index, _this)
                                }
                            }
                        }, btnText)
                    }

                    let array = []
                    this.formGrid.table.operation.buttons.forEach((b, index) => {
                        if (b.show) {
                            array.push(create(h, params, b.theme, b.label, b.click, b.ghost))
                        }
                    })
                    return array
                }
            },
            // 获取表单数据
            getFormData(row, type) {
                let data = this.getPrimaryKey(row) || ''
                if (data === '') {
                    return
                }
                let params = {}
                // 远程查询接口获取数据，弹出模态框
                if (this.formGrid.table.operation.remote) {
                    let key = this.formGrid.table.operation.primaryKey
                    Log.d('远程接口获取FormData')
                    params['params'][key] = row[key]
                    data = null

                    this.$axios.post(this.formGrid.options.url.get, data, params).then(response => {
                        let data = response.body
                        this.formatFormField(data)
                        this.formGrid.form.data = data
                        if (type === 'view') {
                            this.modal.viewModal = true
                        } else {
                            this.modal.editModal = true
                        }
                        this.$forceUpdate()
                    }).catch(response => {
                        this.noticeError('获取数据失败', response.body)
                        this.$forceUpdate()
                        return false
                    })
                } else {
                    Log.d('get data :%o', row)
                    let data = deepExtend({}, row)
                    this.formatFormField(data)
                    this.formGrid.form.data = data
                    if (type === 'view') {
                        this.modal.viewModal = true
                    } else {
                        this.modal.editModal = true
                    }
                }
            },
            // 操作查看按钮
            optViewClick(row, index) {
                this.formGrid.form.data = {}
                this.setFormColumns('view')
                this.formGrid.form.isView = true
                this.getFormData(row, 'view')
            },
            // 操作编辑按钮
            optEditClick(row, index) {
                this.formGrid.form.data = {}
                this.setFormColumns('edit')
                this.formGrid.form.isEdit = true
                this.getFormData(row, 'edit')
            },
            // 格式化表单元素
            formatFormField(target) {
                // 重新格式化值，应对需要自定义返回值进行额外处理。
                for (let c of this.formRows) {
                    for (let r of c) {
                        if (util.isFunction(r.fieldFormat)) {
                            target[r.name] = r.fieldFormat(target[r.name], target)
                        }

                        // 防止下拉框无法使用boolean类型的值做初始化
                        let value = target[r.name]
                        if (r.type === 'select' && typeof (value) === 'boolean') {
                            target[r.name] = value ? 'true' : 'false'
                        }
                        // 触发一下change事件,当编辑、查看时需要数据时，已经有相关值了。需要触发一下
                        if (util.isFunction(r.onChange)) {
                            if (value !== '') {
                                r.onChange(value)
                            }
                        }
                    }
                }
            },
            // 操作删除按钮
            optDeleteClick(row, index) {
                this.$Modal.confirm({
                    title: '删除数据',
                    content: '<span style="color:red">确定删除该记录吗？</span>',
                    onOk: () => {
                        // 拼装成一个数组传递到后端
                        let key = this.formGrid.table.operation.primaryKey
                        let keys = []
                        let p = this.getPrimaryKey(row)
                        if (p) {
                            keys.push(p[key])
                        }
                        if (keys.length === 0) {
                            return
                        }

                        let data = {}
                        let params = {}
                        params['params'] = {}
                        params['params'][key] = keys.toString()

                        Log.d('delete params :%o', params)
                        this.$axios.post(this.formGrid.options.url.delete, data, params).then(response => {
                            this.pageData()
                        }).catch(response => {
                            this.noticeError('数据删除失败', response.message ? response.message : '系统或网络异常')
                            this.$forceUpdate()
                            return false
                        })
                    },
                    onCancel: () => {
                        // this.$Message.info('点击了取消');
                    }
                })
            },
            // 按条件导出所有数据
            exportData() {
                this.exportPageData(100000)
            },
            // 到处当前页数据
            exportCurrentPageData() {
                this.exportPageData()
            },
            // 导出查询数据功能方法
            exportPageData(size) {
                // 获取field 获取数据
                if (this.formGrid.toolbar.superFilter.columns.length > 0) {
                    this.$refs[this.formGrid.toolbar.superFilter.name].validate((valid) => {
                        if (valid) {
                            this.fetchExcelData(size)
                        } else {
                            this.noticeError('查询条件校验失败', '')
                        }
                    })
                } else {
                    this.fetchExcelData(size)
                }
            },
            fetchExcelData(size) {
                let data = {}
                data = deepExtend({}, this.formGrid.table.query)
                let page = this.formGrid.pageable.page
                let pageSize = this.formGrid.pageable.size
                if (size) {
                    pageSize = size
                }
                // 默认jpa查询从0开始，页面上显示从1开始所以需要减一
                if (page > 0) {
                    page = page - 1
                }
                let params = {page: page, size: pageSize}

                let body = []
                Log.d('exportPageData QueryData:%o', data)
                this.$axios.post(this.formGrid.options.url.page, data, {params: params}).then(response => {
                    body = response.body.content
                    let columns = []
                    this.formGrid.table.columns.forEach(function (item) {
                            let key = item.key
                            let title = item.title
                            columns.push({key: key, title: title})
                        }
                    )
                    this.defaultExport(columns, body)
                    this.$forceUpdate()
                }).catch(response => {
                    this.$set(this.formGrid.table, 'data', [])
                    this.noticeError('数据查询出现异常', '系统服务或网络异常')
                })
            },
            defaultExport(columns, data) {
                if (!this.formGrid.toolbar.groups.export) {
                    return
                }
                let fileName = this.formGrid.toolbar.groups.export['fileName'] || '原数据导出'
                this.$refs[this.tableRef].exportCsv({
                    filename: fileName, columns: this.formGrid.toolbar.groups.export.columns || columns, data: data
                })
            },
            showImportView() {
                this.modal.importModal = !this.modal.importModal
            }
        },
        mounted() {

            if (this.formGrid.options.autoLoad) {
                this.pageData()
            }
            this.$nextTick(function () {
                // 添加监听器
                // table重新加载
                this.$on('reloadData', () => {
                    this.$nextTick(() => {
                        this.reloadData()
                    })
                })
                // table重新加载当前页数据
                this.$on('refreshData', () => {
                    this.$nextTick(() => {
                        this.pageData()
                    })
                })

                /* //修改form表单值
                  this.$on("setFormFieldData", (fieldName, data) => {
                    this.$set(this.formGrid.form.data, fieldName, data);
                  });
                  //修改高级搜索框的数据
                  this.$on("setSuperFilter", (superFilter) => {
                    //与原来的做合并
                    let o = deepExtend({},this.formGrid.toolbar.superFilter,superFilter)
                    this.$set(this.formGrid.toolbar, "superFilter", o );
                    //重新渲染搜索表单
                    this.renderSearchForm()
                  }); */
                // 上传成功
                this.$on('uploadSuccess', (jsonData, scope) => {
                    // 上传成功的业务逻辑代码
                    Log.d('uploadSuccess:' + jsonData + '+ scope:' + scope)
                })
                // 上传失败
                this.$on('uploadFail', (jsonData, scope) => {
                    // 上传失败后的业务逻辑代码
                    Log.d('uploadFail:' + jsonData + '+ scope:' + scope)
                })
            })
        },
        //
        beforeDestroy() {
            // 销毁数据，防止脏读
            this.formGrid = null
            this.modal = null
            this.ruleValidate = null
            this.selections = null
        }
    }

</script>
