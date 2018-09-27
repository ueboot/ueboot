> 下拉框树结构组件，可用于表单当中，显示一个下拉框树结构，并绑定到表单字段当中

## 1.图例
![img](../images/treeselect.gif)

## 2.UTreeSelect 使用方式

```html
<template>
  <div>
    <Row>
      <Col :span="8">
      <u-tree-select :tree="tree" fixed refName="s1" v-model="selectTreeItem"></u-tree-select>
      </Col>
      <Col :span="8">
        <u-tree-select :tree="tree2" refName="s2"  v-model="selectTreeItem"></u-tree-select>
      </Col>
    </Row>
  </div>
</template>
```

> 如果同一个页面存在2个以上的UTreeSelect组件时，一定要加上refName属性，且不能同名

```javascript
  //外部数据，数据格式与UTree需要的一致
  import treeData from '../../assets/treeBefore'
  export default {
    components: {UTreeSelect},
    data () {
      return {
        tree: treeData.body,
        tree2:[
          {id: 1, "name": "根节点1", parentId: null,opened:true},
          {"name": "一级子节点", id: 2, parentId: 1,icon:'fa fa-check icon-state-success'},
          {"name": "一级子节点", id: 3, parentId: 1},
          {"name": "二级子节点", id: 4, parentId: 2},
          {"name": "二级子节点", id: 5, parentId: 3},
          {id: 6, "name": "根节点2", parentId: null},
          {"name": "一级子节点", id: 7, parentId: 6},
          {"name": "一级子节点", id: 8, parentId: 6},
          {"name": "二级子节点", id: 9, parentId: 7},
          {"name": "二级子节点", id: 10, parentId: 8},

        ],
        options:{
          showCheckbox:true,
          multiple:true,
          allowBatch:true,
          wholeRow:true
        },

        selectTreeItem: null,//当前选择的树节点
      //  sort:{"sort":"desc","field":"id"}
      }
    },

  }
```

## 3.API

| Props        | Type           | Default  |  Describe  |
| ------------- |:-------------:|:-----:|:--------------------------------------------------------|
| fixed      | Boolean |  | 是否固定显示，默认为点击输入框后才弹出下拉框层  |
| value |    Object   |    false |  v-model双向绑定，获取用户选择的项目，返回的结果格式为 {"name": "一级子节点", id: 3,parentId:1}|
| tree | Array      |    false |  数组格式的树数据，必须要有name,id,parentId三个属性 |
| collapse | Boolean      |    false |  设置节点是否全部展开 |
| multiple | Boolean      |    true |  是否允许多选  |
| allow-batch | Boolean      |    false |   |
| klass | String      |     |  追加自定义tree的样式 |
| maxHeight | Number      | 400    |  设置树组件弹出框最大高度，超过后自动出现滚动条 |
| refName | String      | tree1    |   如果同一个页面存在2个以上的UTreeSelect组件时，一定要加上refName属性，且不能同名|

tree数据格式示例：
```javascript
[
          {id: 1, "name": "根节点1", parentId: null,opened:true},
          {"name": "一级子节点", id: 2, parentId: 1,icon:'fa fa-check icon-state-success'},
          {"name": "一级子节点", id: 3, parentId: 1},
          {"name": "二级子节点", id: 4, parentId: 2},
          {"name": "二级子节点", id: 5, parentId: 3},
          {id: 6, "name": "根节点2", parentId: null},
          {"name": "一级子节点", id: 7, parentId: 6},
          {"name": "一级子节点", id: 8, parentId: 6},
          {"name": "二级子节点", id: 9, parentId: 7},
          {"name": "二级子节点", id: 10, parentId: 8},

        ]
```

## 4.事件

**@item-click(node, item, e)**


**node** : 当前选择的节点

**item** : 当前选择的节点数据，返回的value值包含{id:id,value:name}

**e** : event

## 5.tree数据格式参见UTree组件的介绍

参见[UTree](./UTree.md)

## 6.获取用户选择的节点数据
- 通过v-model方式获取，默认会返回`id` 格式
- 通过监听item-click事件获取，第二个参数的item就是当前选择的节点
```javascript
    <u-tree-select :tree="tree2" refName="s2"  v-model="selectTreeItem" @item-click=youItemClickEvent></u-tree-select>
    
    methods:(){
        youItemClickEvent(node, item, e){
            
        }
    }
```
