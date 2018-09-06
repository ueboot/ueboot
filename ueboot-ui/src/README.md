# 框架发布注意事项


## 0.发布历史记录

## 1.5 版本更新记录
 > 更新iview组件到3.0，去除部分无用代码，部分组件和方法不兼容之前的版本
 - v1.5.0
   - 更新iview到3.0
   - 去除1.4当中的setSuperFilter事件，改为新的方式实现，参见‘事件监听’说明
   - 删除Ajax组件，全面采用axios
   - 删除filterParams属性，不再监听改值
   - 优化部分代码
    
## 1.taobao
npm config set registry https://registry.npm.taobao.org/
## 2.npm
npm config set registry http://registry.npmjs.org 

## 3.ueboot整合框架
封装了tree\formGrid组件，使用时无需编写过多的vue代码，即可实现整个grid的增删改查功能。

## 生成用于发布到npm的包（先看内容是否准确，再决定是否上传）
- 执行 `npm run prepublish`

## 4.发布到npm官方仓库流程
- 本地修改npm镜像为官方镜像，执行命令`npm config set registry http://registry.npmjs.org `
- 修改package.json文件当中的version版本号，根据约定规则来增加版本
  - bug修复，增加最后的版本号
  - 新组件和大的新功能增加，增加第二位版本号，并且能兼容老版本
  - 有版本无法兼容时，增加大版本号。
- 本地执行 `npm publish`
  - 如果是第一次执行，可能要求进行登录。
  - 执行 `npm login` 输入用户名: ueboot 密码:&UJM8ik,  email:ok@xiqiao.io
  - 登录成功后再次执行 `npm publish`即可。

## 5.本地启动并测试
- 测试代码在examples目录下
- 在项目根目录下，执行npm run dev 即可
- 如果有新的组件增加，需要在src/index.js当中import一下新的组件。

## 6.本地文档
- 启动命令 docsify serve docs
## 7.自定义样式
- datepicker当中设置默认宽度为100%
- custom.less 修改了主颜色

## 更新样式
- 安装`npm install iview-theme -g`
- 执行`iview-theme init ue-theme` 事先备份一下ue-theme/custom.less文件
- 修改custom.less文件，在里面修改相关的样式
- 执行`iview-theme build -o dist/`


