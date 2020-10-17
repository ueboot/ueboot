## 4.发布到npm官方仓库流程
- 本地修改npm镜像为官方镜像，执行命令`npm config set registry http://registry.npmjs.org `
- 修改package.json文件当中的version版本号，根据约定规则来增加版本
  - bug修复，增加最后的版本号
  - 新组件和大的新功能增加，增加第二位版本号，并且能兼容老版本
  - 有版本无法兼容时，增加大版本号。
- 本地执行`npm run publish` 会将源码打包成ueboot.js文件
- 在本地执行 `npm publish` 发布到npmjs.org仓库当中
  - 如果是第一次执行，可能要求进行登录。
  - 执行 `npm login` 输入用户名: ueboot 密码:78  email:ok@xiqiao.io
  - 登录成功后再次执行 `npm publish`即可。

## 5.本地启动并测试
- 测试代码在examples目录下
- 在项目根目录下，执行npm run dev 即可
- 如果有新的组件增加，需要在src/index.js当中import一下新的组件。

## 6.本地文档
- 启动命令 docsify serve docs
## 7.本地安装
本地安装后，修改了文件，实时生效。无效发布后再update
- npm install /Users/yangkui/workspace/ueboot-quick-dev-gitee/ueboot-view 

