<template>
  <div>
    <u-form-grid :data="formGrid" ref="formGrid" v-if="initComplete"></u-form-grid>
    <tree-model @item-ok="confirm" :dataOwner="dataOwner" :date="date" ref="treeModel"></tree-model>
  </div>
</template>

<script>
import values from '../../util/values'
import TreeModel from '../../util/TreeModal'
import service from '../../service/service'
const defaultDate = new Date()
export default {
  name: 'RecommendationRelation',
  components: {TreeModel},
  data () {
    return {
      // 初始化数据是否完成
      initComplete: false,
      jobData: [],
      dataOwner: null,
      date: defaultDate,
      formGrid: {
        options: {
          autoLoad: false,
          url: {
            page: '/structure/recommendationRelation/page'
          }
        },
        toolbar: {
          button: [
            {
              label: '导出全部', key: 'exportAllData'
            }
          ],
          create: {
            show: false
          },
          delete: {
            show: false
          },
          filter: {
            show: false,
            name: 'empNo',
            placeholder: '请输入工号'
          },
          groups: {
            export: {
              label: '导出全部',
              show: true,
              fileName: '增员关系',
              columns: [
                { title: '分公司代码', key: 'subBranchName' },
                { title: '推荐人姓名', key: 'recPartyName' },
                { title: '推荐人工号', key: 'recPartyNo' },
                { title: '推荐人职级', key: 'recJobCodeDef' },
                { title: '推荐人状态', key: 'recStatusDef' },
                { title: '被推荐人姓名', key: 'partyName' },
                { title: '被推荐人工号', key: 'partyNo' },
                { title: '被推荐人签约日期', key: 'joinDate' },
                { title: '被推荐人状态', key: 'statusDef' },
                { title: '增员类型', key: 'raiseType' },
                { title: '生效日期', key: 'beginTime' },
                { title: '二级机构', key: 'secondLevelOrg', minWidth: 100 },
                { title: '三级机构', key: 'thirdLevelOrg', minWidth: 150 },
                { title: '四级机构', key: 'fourthLevelOrg', minWidth: 220 }
              ]
            }
          },
          superFilter: {
            show: true,
            label: '高级搜索',
            width: 500,
            labelWidth: 90,
            columns: [
              {
                type: 'month',
                label: '结算月',
                format: 'yyyy-MM',
                name: 'date',
                placement: 'top-start',
                clearable: false,
                editable: false,
                required: true,
                icon: 'plus',
                init: defaultDate,
                onChange: (value) => {
                  this.date = value
                }
              },
              {
                type: 'text',
                label: '机构名称',
                name: 'orgName',
                required: true,
                readonly: true,
                data: '',
                placeholder: '请选择机构',
                onFocus: () => {
                  this.$refs.treeModel.show()
                }
              }, {
                type: 'hidden',
                label: '机构',
                name: 'org',
                init: ''
              }, {
                type: 'hidden',
                label: '分公司',
                name: 'dataOwner'
              },
              {
                type: 'select',
                label: '渠道',
                name: 'chaType',
                clearable: false,
                filterable: false,
                multiple: false,
                required: true,
                data: '',
                init: ''
              },
              {
                type: 'text',
                label: '推荐人工号',
                name: 'recPartyNo',
                maxlength: 10,
                placeholder: '请输入推荐人工号 例如:BEJL8000',
                required: false
              },
              {
                type: 'select',
                label: '推荐人职级',
                name: 'recJobCode',
                clearable: true,
                filterable: true,
                required: false,
                placeholder: '请先选择机构再选择推荐人职级',
                data: []
              },
              {
                type: 'select',
                label: '推荐人状态',
                name: 'recStatus',
                clearable: true,
                filterable: false,
                multiple: false,
                placeholder: '请选择推荐人状态',
                data: values.status
              },
              {
                type: 'text',
                label: '被推荐人工号',
                name: 'partyNo',
                maxlength: 10,
                placeholder: '请输入被推荐人工号 例如:BEJL8000',
                required: false
              }
            ],
            submit: {theme: 'primary', label: '查询', icon: 'ios-search'}
          }
        },
        table: {
          size: 'small',
          showCheckbox: false,
          height: document.documentElement.clientHeight - 345,
          operation: {
            show: false,
            primaryKey: 'id'
          },
          columns: [
            { title: '分公司代码', key: 'subBranchName', minWidth: 150 },
            { title: '推荐人姓名', key: 'recPartyName', minWidth: 100 },
            { title: '推荐人工号', key: 'recPartyNo', minWidth: 120 },
            { title: '推荐人职级', key: 'recJobCodeDef', minWidth: 180 },
            { title: '推荐人状态', key: 'recStatusDef', minWidth: 100 },
            { title: '被推荐人姓名', key: 'partyName', minWidth: 120 },
            { title: '被推荐人工号', key: 'partyNo', minWidth: 120 },
            { title: '被推荐人签约日期', key: 'joinDate', minWidth: 150 },
            { title: '被推荐人状态', key: 'statusDef', minWidth: 110 },
            { title: '增员类型', key: 'raiseType', minWidth: 100 },
            { title: '生效日期', key: 'beginTime', minWidth: 100 },
            { title: '二级机构', key: 'secondLevelOrg', minWidth: 100 },
            { title: '三级机构', key: 'thirdLevelOrg', minWidth: 150 },
            { title: '四级机构', key: 'fourthLevelOrg', minWidth: 220 }
          ]
        }
      }
    }
  },
  methods: {
    /**
     * 树形模态框获取
     */
    confirm: function (node) {
      this.$log.i(node)
      this.formGrid.toolbar.superFilter.columns.forEach((c) => {
        if (c.name === 'orgName') {
          this.$set(c, 'init', node.name)
        }
        if (c.name === 'dataOwner') {
          this.$set(c, 'init', node.origin.dataOwner)
        }
        if (c.name === 'org') {
          this.$set(c, 'init', node.origin)
        }
      })
      let req = {
        dataOwner: node.origin.dataOwner
      }
      this.getJobData(req)
    },
    // 动态获取下拉框
    getJobData: function (req) {
      this.$axios.post('/structure/department/listDataOwnerJob', req).then(response => {
        this.$log.i(response.body)
        this.jobData = response.body
        this.formGrid.toolbar.superFilter.columns.forEach((c) => {
          if (c.name === 'recJobCode') {
            c.data = this.jobData
          }
        })
      })
    }
  },
  created () {
    const initData = service.getInitData(this, defaultDate)
    initData.then((data) => {
      this.dataOwner = data.dataOwner
      // 初始化数据赋值
      this.formGrid.toolbar.superFilter.columns.forEach((c) => {
        if (c.name === 'orgName') {
          c.init = data.currentOrg.name
        } else if (c.name === 'org') {
          c.init = data.currentOrg
        } else if (c.name === 'chaType') {
          if (data.chaTypes && data.chaTypes.length > 0) {
            c.init = data.chaTypes[0].value
          }
          c.data = data.chaTypes
        } else if (c.name === 'dataOwner') {
          c.init = this.dataOwner
        }
      })
      this.initComplete = true
      let req = {
        'dataOwner': this.dataOwner,
        route: 'MAINTAIN'
      }
      this.getJobData(req)
    })
  }

}
</script>
