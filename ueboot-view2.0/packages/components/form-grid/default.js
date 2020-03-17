/**
 * Created by yangkui on 17/7/5.
 * formGrid的默认设置项
 */
export default {
  options: {
    autoLoad: true,
    url: {
      save: '', // 记录添加和修改提交的地址，如果提交的对象有id则为修改;
      delete: '', // 记录删除的提交地址，通过提交id的集合来实现一个或者多个记录;
      page: '', // 记录查询地址，必须使用分页查询并返回分页结果;
      get: '', // 查看和编辑时用于查询对象的接口，参数为定义的keys;
      im: '/admin/api/dic/find/parent' // 查询数据字典的接口地址，返回格式为List,对象必须包含name,value两个属性
    }
  },
  tips: { 'title': '', 'content': '' },
  toolbar: {
    // 是否显示工具栏
    show: true,
    // 按钮排列布局方式，默认为start可以设置为end
    justify: 'start',
    refresh: {
      show: true, label: ''
    },
    create: {
      show: true, label: '添加'
    },
    delete: {
      show: true, label: '删除', loading: false
    },
    groups: {
      show: false,
      label: '导入/导出',
      import: {
        show: false, size: 50 * 1024, modelTitle: '上传文件', templatePath: '', params: {}
      }
    },
    // 更多按钮
    buttons: null,
    // 关键字搜索框
    filter: {
      show: false,
      name: 'keyWord',
      placeholder: '根据关键字搜索'
    },
    // 高级搜索框，如果columns为空，则不显示。items里面的定义和form表单的columns一致
    superFilter: {
      show: false,
      label: '高级搜索',
      labelWidth: 80,
      name: 'searchForm',
      colNumber: 4,
      columns: [],
      validateErrorMsg: '查询条件校验失败',
      // 根据columns动态计算出来的值，无需传入
      rows: {},
      submit: { theme: 'primary', label: '查询', icon: 'md-search', long: false },
      reset: { theme: 'primary', label: '重置', icon: 'md-close', long: false, ghost: true },
      submitBefore: () => { return true },
      // 分页查询后的回调方法
      submitAfter: () => {}
    }
  },
  pageable: {
    page: 1,
    size: 20
  },
  // 表单提交与修改
  form: {
    message: {
      success: '表单提交成功'
    },
    name: 'formName', // 表单名称，如果同一个页面存在多个表单时，需要指定不同的名称
    // label对齐方式
    labelPosition: 'right',
    loading: false, // 提交按钮loading状态
    // label的宽度
    labelWidth: 80,
    // 每行显示的个数
    colNumber: 2,
    modal: {
      title: '', // 表单标题，默认为添加『查看、添加、修改』两个字.如果为空，则不显示标题;
      showClose: true, // 是否显示关闭按钮，默认为true;
      width: '' // 表单弹出框宽度
    },

    columns: [],
    // 表单提交之前（校验之后），返回如果为false,则不继续执行后续操作
    submitBefore: function () {
      return true
    },

    // 表单提交之后
    submitAfter: function () {
      return true
    },

    // 点击取消时的操作，默认为关闭窗口
    onCancel: function () {

    },
    data: {}// 表单提交的参数
  },
  table: {
    rowClick: () => {

    },
    rowDblClick: () => {},
    expand: () => {},
    height: '', // 默认为100%
    // 是否显示复选框
    showCheckbox: true,
    // 第一列复选框样式，可以覆盖默认值
    selection: {
      type: 'selection',
      width: 60,
      align: 'center'
    },
    stripe: true,
    notLoadingText: '请点击查询按钮进行查询',
    noDataText: '已努力查询，但还是没找到！',
    tableLoadingText: '正在努力为您加载数据,请稍候...',
    tableLoadedErrorText: '数据查询出现异常，需要管理员查看后台日志，寻找原因。',
    // 操作列有关参数
    operation: {
      // 调用查看、编辑、删除接口时，需要传递的参数主键名称，可以有多个。默认为id
      keys: ['id'],
      // 是否显示操作列
      show: true,
      // 查看、编辑功能是否通过远程查询方式获取。默认为false直接用当前行数据展示
      remote: false,
      // 操作按钮，针对查看、编辑、删除的事件。定义了事件则使用自定义事件
      buttons: [{
        key: 'view',
        show: true,
        'label': '查看',
        'theme': 'primary',
        click: (row, index, _this) => {
          _this.optViewClick(row, index)
        }
      }, {
        key: 'edit',
        show: true,
        'label': '编辑',
        'theme': 'primary',
        click: (row, index, _this) => {
          _this.optEditClick(row, index)
        }
      }, {
        key: 'delete',
        show: true,
        'label': '删除',
        'theme': 'primary',
        ghost: true,
        click: (row, index, _this) => {
          _this.optDeleteClick(row, index)
        }
      }],
      // 默认的操作列内容。可以通过复写这个来重写自己的操作列
      column: {
        title: '操作',
        key: 'action',
        className: 'optColumn',
        align: 'center',
        minWidth: 160
      }
    },
    // 表格数据，从服务端返回的数据
    data: []
  },
  queryParams: {}

}
