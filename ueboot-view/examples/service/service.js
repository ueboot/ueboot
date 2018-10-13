import moment from 'moment'
import values from '../util/values'

let CACHE_MAP = {}

// 总公司代码
const rootOrg = '2313'

export default {

  getAllDataOwner (vue, date) {
    return this.getListDataOwner(vue, rootOrg, null, date, null)
  },

  getDataOwnerByDivId (vue, divId, date) {
    return this.getListDataOwner(vue, null, divId, date, null)
  },
  /**
   *
   * @param vue
   * @param dataOwner 当需要显示所有分公司时，值为总公司。如果只显示某个分公司，则就是这个分公司的dataOwner
   * @param date
   * @returns {*}
   */
  getDataOwnerByTopMarking (vue, dataOwner, date) {
    return this.getListDataOwner(vue, dataOwner, null, date, 'top')
  },
  /**
   * 查询机构数据
   * @param vue
   * @param dataOwner 父级机构
   * @param divId 机构ID
   * @param date 结算月
   * @param topMarking 只显示分公司节点标
   */
  getListDataOwner (vue, dataOwner, divId, date, topMarking) {
    dataOwner = dataOwner || rootOrg
    return new Promise((resolve, reject) => {
      let dateKey = moment(date).format('YYYYMM') + dataOwner
      let data = CACHE_MAP[dateKey]
      if (topMarking) {
        data = CACHE_MAP[dateKey + 'topMarking']
      }
      if (data && data.length > 0) {
        vue.$log.d('from cache ####')
        // 如果指定了机构id 则只返回此机构对象
        if (divId) {
          resolve(CACHE_MAP[dateKey + 'object'][divId])
          return
        } else {
          resolve(data)
          return
        }
      }

      let req = {
        // 默认只查询一次总公司的所有数据
        dataOwner: dataOwner,
        date: date,
        topMarking: topMarking
      }
      vue.$axios.post('/structure/department/listDataOwner', req).then((data) => {
        if (topMarking) {
          CACHE_MAP[dateKey + 'topMarking'] = data.body
          resolve(data.body)
        } else {
          CACHE_MAP[dateKey] = data.body
          let objects = {}
          data.body.forEach((d) => {
            objects[d.id] = d
          })
          CACHE_MAP[dateKey + 'object'] = objects
          vue.$log.d('from api ####')
          if (divId) {
            resolve(objects[divId])
          } else {
            resolve(data.body)
          }
        }
      })
    })
  },
  /**
   * 获取页面所需要的初始数据
   */
  getInitData (vue, comDate, topMarking) {
    return new Promise(resolve => {
      // 用于获取默认的机构查询节点，比如分公司跳转过来自动选择上了分公司节点
      let dataOwner = sessionStorage.getItem('dataOwner')
      if (!dataOwner) {
        dataOwner = rootOrg
      }

      if (topMarking) {
        this.getDataOwnerByTopMarking(vue, dataOwner, comDate).then((data) => {
          this.resolveInitData(data, dataOwner, resolve)
        })
      } else {
        this.getDataOwnerByDivId(vue, dataOwner, comDate).then((data) => {
          this.resolveInitData(data, dataOwner, resolve)
        })
      }
    })
  },

  resolveInitData (data, dataOwner, resolve) {
    // 当前机构数据
    let currentOrg = null
    currentOrg = data
    // 渠道下拉框默认值
    let chaTypes = values.chaTypes
    // 人管跳转渠道查询框只显示默认渠道
    let chaType = sessionStorage.getItem('chaType')
    let newChaTypes = []
    if (chaType) {
      values.chaTypes.forEach((c) => {
        if (c.value === chaType) {
          newChaTypes.push(c)
        }
      })
    } else {
      newChaTypes = chaTypes
    }
    resolve({currentOrg: currentOrg, chaTypes: newChaTypes, dataOwner: dataOwner})
  }
}
