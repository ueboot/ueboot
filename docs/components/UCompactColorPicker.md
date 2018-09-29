> 颜色选择器，可用于表单和formGird当中，显示一个颜色选择器，并绑定到表单字段当中

## 1.图例
![img](../images/color-picker.png)

## 2.在FormGrid当中使用

```javascript
{
    type: "compactColorPicker",
    name: "compactColorPicker",
    label: "颜色",
}
```
- 设定type值为compactColorPicker即可

## 3.单独使用
//TODO

## 4.额外属性

| Props        | Type           | Default  |  Describe  |
| ------------- |:-------------:|:-----:|:--------------------------------------------------------|
| fixed      | Boolean |  | 是否固定显示，默认为点击输入框后才弹出颜色面板层  |
| v-model |    Object   |    false |  v-model双向绑定，获取用户选择的项目，返回的结果格式为 {"name": "一级子节点", id: 3,parentId:1}|
| colorAccount | Array      |    false |  颜色列表 |
| defaultColor | String      |    #000 |  默认选择的颜色 |
| lineMaxAccount | Number      |    12 |  每行最大显示的颜色数量  |
| isPickerShow | Boolean      |    true | 是否显示面板  |

