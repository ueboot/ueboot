/**
 * Created by yangkui on 2017/10/18.
 * 常用工具类，如将数组转换为树装结构的数据
 */
export default {
  /**
   * 获取树状数据
   * @param data 原始数组数据，必须包含name,value,id,parentId 几个属性
   * @param parent 根节点对象，必须要有ID值
   * @returns {Array} 树状结构的数据，id,label,value,parentId,attr,children
   */

  getTreeData(data, parent){
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
          o.children = this.getTreeData(data, o);
          child.push(o);
        }
      });
      //是否需要排序
     /* if (sort) {
        child.sort((a, b) => {
          if (sort["sort"] === "desc") {
            return b[sort["field"]] - a[sort["field"]];
          } else {
            return b[sort["field"]] - a[sort["field"]];
          }
        })
      }*/
      return child;
  },

  getCascaderData(data, parentId) {
    //1.查找root
    let items = [];
    data.forEach((item) => {
      if (item['parentId'] == parentId) {
        let value = {
          id: item.id,
          label: item.name,
          value: item.value,
          attr: item,
          parentId: item.parentId,
          children: []
        };
        value.children = this.getCascaderData(data, item.id);
        items.push(value);
      }
    });
    return items;
  },
}
