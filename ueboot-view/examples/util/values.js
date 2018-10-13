/**
 * JS当中定义的静态变量
 */
export default {
  dict: {
    // 性别字典key
    sex: '性别',
    knowledgeType: '知识库分类'
  },

  storage: {
    user: 'user', // 用户登录成功缓存key
    knowledgeRow: 'knowledgeRow', // 知识库
    chatMessageRow: 'chatMessageRow', // 聊天记录
    userId: 'userId'
  },
  versionJob: {
    'A': [
      {value: 'U771', name: '试用区域专员'},
      {value: 'U772', name: '见习区域专员'},
      {value: 'U773', name: '一级区域保险顾问'},
      {value: 'U774', name: '二级区域保险顾问'},
      {value: 'U775', name: '三级区域保险顾问'},
      {value: 'U911', name: '高一级区域保险顾问'},
      {value: 'U912', name: '高二级区域保险顾问'},
      {value: 'U913', name: '高三级区域保险顾问'},
      {value: 'U914', name: '资深一级区域保险顾问'},
      {value: 'U915', name: '资深二级区域保险顾问'},
      {value: 'U916', name: '资深三级区域保险顾问'},
      {value: 'U776', name: '见习室经理'},
      {value: 'U777', name: '初一级区拓室经理'},
      {value: 'U778', name: '初二级区拓室经理'},
      {value: 'U779', name: '初三级区拓室经理'},
      {value: 'U780', name: '中一级区拓室经理'},
      {value: 'U781', name: '中二级区拓室经理'},
      {value: 'U782', name: '中三级区拓室经理'},
      {value: 'U783', name: '高一级区拓室经理'},
      {value: 'U784', name: '高二级区拓室经理'},
      {value: 'U785', name: '高三级区拓室经理'},
      {value: 'U786', name: '筹备区拓室经理'},
      {value: 'U787', name: '筹建室经理'},
      {value: 'U789', name: '准区经理'},
      {value: 'U790', name: '筹建行政区负责人'},
      {value: 'U791', name: '见习行政区经理'},
      {value: 'U792', name: '行政区经理'},
      {value: 'U793', name: '高级区经理'},
      {value: 'U794', name: '资深区经理'},
      {value: 'U795', name: '区域总监'},
      {value: 'U796', name: '高级区域总监'},
      {value: 'U797', name: '资深区域总监'}
    ],
    'B': [
      {value: 'U715', name: '试用区域专员'},
      {value: 'U716', name: '见习区域专员'},
      {value: 'U717', name: '一级区域保险顾问'},
      {value: 'U718', name: '二级区域保险顾问'},
      {value: 'U719', name: '三级区域保险顾问'},
      {value: 'U901', name: '高一级区域保险顾问'},
      {value: 'U902', name: '高二级区域保险顾问'},
      {value: 'U903', name: '高三级区域保险顾问'},
      {value: 'U904', name: '资深一级区域保险顾问'},
      {value: 'U905', name: '资深二级区域保险顾问'},
      {value: 'U906', name: '资深三级区域保险顾问'},
      {value: 'U720', name: '见习区拓服务主任'},
      {value: 'U721', name: '区拓服务主任'},
      {value: 'U722', name: '高级区拓服务主任'},
      {value: 'U723', name: '资深区拓服务主任'},
      {value: 'U724', name: '区拓服务经理'},
      {value: 'U725', name: '高级区拓服务经理'},
      {value: 'U726', name: '资深区拓服务经理'},
      {value: 'U727', name: '区域总监'},
      {value: 'U728', name: '高级区域总监'},
      {value: 'U729', name: '资深区域总监'},
      {value: 'U757', name: '筹备区拓服务主任'},
      {value: 'U758', name: '筹备区拓服务经理'},
      {value: 'U759', name: '筹备区域总监'},
      {value: 'U787', name: '筹建室经理'},
      {value: 'U790', name: '筹建行政区负责人'}
    ]
  },

  branch: [
    {value: '北京市国贸支公司', name: '北京市国贸支公司'}
  ],

  chaTypes: [
    {value: 'U12', name: '顾问营销'},
    {value: 'U13', name: '服务营销'}
  ],
  KfJobCode: [
    {value: 'D1', name: '正式客户经理'},
    {value: 'D2', name: '高级客户经理一级'},
    {value: 'D3', name: '高级客户经理二级'},
    {value: 'D4', name: '高级客户经理三级'},
    {value: 'D5', name: '资深客户经理一级'},
    {value: 'D6', name: '资深客户经理二级'},
    {value: 'D7', name: '资深客户经理三级'},
    {value: 'D8', name: '见习客户服务室经理'},
    {value: 'DF', name: '正式客户服务室主任'},
    {value: 'DO', name: '高级客户服务室经理一级'},
    {value: 'DP', name: '高级客户服务室经理二级'},
    {value: 'DQ', name: '资深客户服务室经理一级'},
    {value: 'DR', name: '资深客户服务室经理二级'}
  ],
  KfRouteCode: [
    {value: 'MAIN_D1', name: '维持正式客户经理'},
    {value: 'MAIN_D2', name: '维持高级客户经理一级'},
    {value: 'MAIN_D3', name: '维持高级客户经理二级'},
    {value: 'MAIN_D4', name: '维持高级客户经理三级'},
    {value: 'MAIN_D5', name: '维持资深客户经理一级'},
    {value: 'MAIN_D6', name: '维持资深客户经理二级'},
    {value: 'MAIN_D7', name: '维持资深客户经理三级'},
    {value: 'MAIN_D8', name: '维持见习客户经理'},
    {value: 'MAIN_DF', name: '维持正式客户服务室主任'},
    {value: 'MAIN_DO', name: '维持高级客户服务室经理一级'},
    {value: 'MAIN_DP', name: '维持高级客户服务室经理二级'},
    {value: 'MAIN_DQ', name: '维持资深客户服务室经理一级'},
    {value: 'MAIN_DR', name: '维持资深客户服务室经理二级'},
    {value: 'MAIN_DT', name: '维持正式客户服务部经理'},
    {value: 'MAIN_DU', name: '维持高级客户服务部经理一级'},
    {value: 'MAIN_DW', name: '维持高级客户服务部经理二级'}

  ],
  status: [
    {value: 'UQ1', name: '在职'},
    {value: 'UQ2', name: '在职预'},
    {value: 'UQ3', name: '禁止新保预'},
    {value: 'UQ4', name: '解约1'},
    {value: 'UQ5', name: '归档'}
  ],
  routeName: {
    'A': {
      'MAINTAIN': [
        {value: '维持试用区域专员', name: '维持试用区域专员'},
        {value: '维持见习区域专员', name: '维持见习区域专员'},
        {value: '维持一级区域保险顾问', name: '维持一级区域保险顾问'},
        {value: '维持二级区域保险顾问', name: '维持二级区域保险顾问'},
        {value: '维持三级区域保险顾问', name: '维持三级区域保险顾问'},
        {value: '维持见习室经理', name: '维持见习室经理'},
        {value: '维持初一级区拓室经理', name: '维持初一级区拓室经理'},
        {value: '维持初二级区拓室经理', name: '维持初二级区拓室经理'},
        {value: '维持初三级区拓室经理', name: '维持初三级区拓室经理'},
        {value: '维持中一级区拓室经理', name: '维持中一级区拓室经理'},
        {value: '维持中二级区拓室经理', name: '维持中二级区拓室经理'},
        {value: '维持中三级区拓室经理', name: '维持中三级区拓室经理'},
        {value: '维持高一级区拓室经理', name: '维持高一级区拓室经理'},
        {value: '维持高二级区拓室经理', name: '维持高二级区拓室经理'},
        {value: '维持高三级区拓室经理', name: '维持高三级区拓室经理'},
        {value: '维持行政区经理', name: '维持行政区经理'},
        {value: '维持高级行政区经理', name: '维持高级行政区经理'},
        {value: '维持资深行政区经理', name: '维持资深行政区经理'},
        {value: '维持区域总监', name: '维持区域总监'},
        {value: '维持高级区域总监', name: '维持高级区域总监'},
        {value: '维持资深区域总监', name: '维持资深区域总监'},
        {value: '维持高一级区域保险顾问', name: '维持高一级区域保险顾问'},
        {value: '维持高二级区域保险顾问', name: '维持高二级区域保险顾问'},
        {value: '维持高三级区域保险顾问', name: '维持高三级区域保险顾问'},
        {value: '维持资深一级区域保险顾问', name: '维持资深一级区域保险顾问'},
        {value: '维持资深二级区域保险顾问', name: '维持资深二级区域保险顾问'},
        {value: '维持资深三级区域保险顾问', name: '维持资深三级区域保险顾问'}
      ],
      'PROMOTION': [
        {value: '晋升一级区域保险顾问', name: '晋升一级区域保险顾问'},
        {value: '晋升二级区域保险顾问', name: '晋升二级区域保险顾问'},
        {value: '晋升三级区域保险顾问', name: '晋升三级区域保险顾问'},
        {value: '晋升高一级区域保险顾问', name: '晋升高一级区域保险顾问'},
        {value: '晋升高二级区域保险顾问', name: '晋升高二级区域保险顾问'},
        {value: '晋升高三级区域保险顾问', name: '晋升高三级区域保险顾问'},
        {value: '晋升资深一级区域保险顾问', name: '晋升资深一级区域保险顾问'},
        {value: '晋升资深二级区域保险顾问', name: '晋升资深二级区域保险顾问'},
        {value: '晋升资深三级区域保险顾问', name: '晋升资深三级区域保险顾问'},
        {value: '晋升初一级区拓室经理', name: '晋升初一级区拓室经理'},
        {value: '晋升初二级区拓室经理', name: '晋升初二级区拓室经理'},
        {value: '晋升初三级区拓室经理', name: '晋升初三级区拓室经理'},
        {value: '晋升中一级区拓室经理', name: '晋升中一级区拓室经理'},
        {value: '晋升中二级区拓室经理', name: '晋升中二级区拓室经理'},
        {value: '晋升中三级区拓室经理', name: '晋升中三级区拓室经理'},
        {value: '晋升高一级区拓室经理', name: '晋升高一级区拓室经理'},
        {value: '晋升高二级区拓室经理', name: '晋升高二级区拓室经理'},
        {value: '晋升高三级区拓室经理', name: '晋升高三级区拓室经理'}
      ]
    },
    'B': {
      'MAINTAIN': [
        {value: '维持试用区域专员', name: '维持试用区域专员'},
        {value: '维持见习区域专员', name: '维持见习区域专员'},
        {value: '维持一级区域保险顾问', name: '维持一级区域保险顾问'},
        {value: '维持二级区域保险顾问', name: '维持二级区域保险顾问'},
        {value: '维持三级区域保险顾问', name: '维持三级区域保险顾问'},
        {value: '维持高一级区域保险顾问', name: '维持高一级区域保险顾问'},
        {value: '维持高二级区域保险顾问', name: '维持高二级区域保险顾问'},
        {value: '维持高三级区域保险顾问', name: '维持高三级区域保险顾问'},
        {value: '维持资深一级区域保险顾问', name: '维持资深一级区域保险顾问'},
        {value: '维持资深二级区域保险顾问', name: '维持资深二级区域保险顾问'},
        {value: '维持资深三级区域保险顾问', name: '维持资深三级区域保险顾问'},
        {value: '维持区拓服务主任', name: '维持区拓服务主任'},
        {value: '维持高级区拓服务主任', name: '维持高级区拓服务主任'},
        {value: '维持资深区拓服务主任', name: '维持资深区拓服务主任'},
        {value: '维持区拓服务经理', name: '维持区拓服务经理'},
        {value: '维持高级区拓服务经理', name: '维持高级区拓服务经理'},
        {value: '维持资深区拓服务经理', name: '维持资深区拓服务经理'},
        {value: '维持区域总监', name: '维持区域总监'},
        {value: '维持高级区域总监', name: '维持高级区域总监'},
        {value: '维持资深区域总监', name: '维持资深区域总监'}
      ],
      'PROMOTION': [
        {value: '晋升一级区域保险顾问', name: '晋升一级区域保险顾问'},
        {value: '晋升二级区域保险顾问', name: '晋升二级区域保险顾问'},
        {value: '晋升三级区域保险顾问', name: '晋升三级区域保险顾问'},
        {value: '晋升高一级区域保险顾问', name: '晋升高一级区域保险顾问'},
        {value: '晋升高二级区域保险顾问', name: '晋升高二级区域保险顾问'},
        {value: '晋升高三级区域保险顾问', name: '晋升高三级区域保险顾问'},
        {value: '晋升资深一级区域保险顾问', name: '晋升资深一级区域保险顾问'},
        {value: '晋升资深二级区域保险顾问', name: '晋升资深二级区域保险顾问'},
        {value: '晋升资深三级区域保险顾问', name: '晋升资深三级区域保险顾问'},
        {value: '晋升区拓服务主任', name: '晋升区拓服务主任'},
        {value: '晋升高级区拓服务主任', name: '晋升高级区拓服务主任'},
        {value: '晋升资深区拓服务经理', name: '晋升资深区拓服务经理'},
        {value: '晋升区拓服务经理', name: '晋升区拓服务经理'},
        {value: '晋升高级区拓服务经理', name: '晋升高级区拓服务经理'},
        {value: '晋升资深区拓服务经理', name: '晋升资深区拓服务经理'},
        {value: '晋升区域总监', name: '晋升区域总监'},
        {value: '晋升高级区域总监', name: '晋升高级区域总监'},
        {value: '晋升资深区域总监', name: '晋升资深区域总监'}
      ]
    }
  }
}
