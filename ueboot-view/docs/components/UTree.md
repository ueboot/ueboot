### UTree 树
```html
 <u-tree :tree="tree" @itemClick="itemClick" :sort="sort"></u-tree>

```
```javascript
     export default {
       data () {
         return {
           //树结构数据，要求至少三个字段，且字段名称与下面的一致
           tree: [
             {id: 1, "name": "根节点", parentId: null},
             {"name": "一级子节点", id: 2, parentId: 1},
             {"name": "一级子节点", id: 3, parentId: 1},
             {"name": "二级子节点", id: 4, parentId: 2},
             {"name": "二级子节点", id: 5, parentId: 3},
           ],
           selectTreeItem: null,//当前选择的树节点,
           //排序（可选值），sort可选值为:desc|asc  field要排序的字段，建议为Number类型的字段。
           sort:{"sort":"desc","field":"id"}
         }
       },
       methods: {
         itemClick(item,parentItem){
           this.selectTreeItem = item;
         },
       }
     } 

```
