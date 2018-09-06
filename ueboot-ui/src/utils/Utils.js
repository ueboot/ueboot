/**
 * Created by yangkui on 2017/10/18.
 * 常用工具类，如将数组转换为树装结构的数据
 */
export default {
  /**
   * 获取树状数据
   * @param tree 原始数组数据，必须包含name,value,id,parentId 几个属性
   * @param handlerItem 回调方法，用于指明如何处理每个节点数据（如何拼装对象），如果未指定则用默认方式处理
   *  function handlerItem(item){
   *      let object = {}
   *      object.text = item.name
   *      object.value = item.id
   *      return object
   *  }
   *
   * @returns {Array} 树状结构的数据，id,label,value,parentId,attr,children
   */

  getTreeData (tree, handlerItem) {
    // 构造树结构
    let roots = []
    // 1.查找每个根节点。或者查找每个父级节点不存在的节点当根节点
    tree.forEach((item) => {
      let isRoot = true
      // 查找一下当前节点所在的父亲节点是否存在，如果不存则当根节点
      for (let i = 0; i < tree.length; i++) {
        if (item.parentId === tree[i].id) {
          isRoot = false
          break
        }
      }
      if (isRoot) {
        let root = assembleItem(item, null)
        let result = getChildren(tree, item.id, item.path)
        root.children = result['child']
        root.opened = result['hasSelected']
        roots.push(root)
      }
    })

    let sort = this.sort
    function assembleItem (item, parentPath) {
      let o = {}
      if (handlerItem) {
        o = handlerItem(item)
      } else {
        if (parentPath) {
          o.path = parentPath + '\\' + item.name
        } else {
          o.path = item.name
        }

        // 搜索的时候，会产生label属性，显示的内容格式与name不一样
        o.text = item.label ? item.label : item.name
        o.value = {id: item.id, name: item.name, parentId: item.parentId}
        o.selected = item.selected || false
        o.disabled = item.disabled || false
        o.loading = item.loading || false
        o.icon = item.icon || ''
        o.tip = item.tip || ''
        o.opened = item.opened || false
      }
      o.id = item.id
      o.parentId = item.parentId
      return o
    }
    // 2.递归循环所有节点,将节点加入到父节点当中
    function getChildren (tree, parentId, parentPath) {
      let result = {}
      let child = []
      // 判断子节点是否有被勾选的情况，如有则父节点设置为打开状态
      let hasSelected = false
      tree.forEach((item) => {
        if (item.parentId === parentId) {
          let o = assembleItem(item, parentPath)
          if (o.selected) {
            hasSelected = true
          }
          child.push(o)
        }
      })
      child.forEach((item) => {
        let result = getChildren(tree, item.id, item.path)
        item.children = result['child']
        item.opened = result['hasSelected']
      })

      // 是否需要排序
      if (sort) {
        this.sort(child, sort)
      }
      result['hasSelected'] = hasSelected
      result['child'] = child
      return result
    }

    if (sort) {
      this.sort(roots, sort)
    }
    return roots
  },

  /**
   * 对数组对象进行排序操作
   * @param array 需要排序的对象数组
   * @param sort 排序对象{field:'id',sort:'desc'}
   */
  sort (array, sort) {
    array.sort((a, b) => {
      if (sort['sort'] === 'desc') {
        return b[sort['field']] - a[sort['field']]
      } else {
        return b[sort['field']] - a[sort['field']]
      }
    })
  }
}
