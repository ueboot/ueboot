## 1. 安装

```shell
    npm install ueboot --save-dev
```
### 1.1 更新版本
  当有新版本发布后，本地需要更新时，执行如下命令
  ```shell
    npm update ueboot --save-dev
  ```
## 2. 安装依赖
 如果缺少其他依赖，请根据提示进行install
 
## 3. 使用
在src/main.js当中增加如下代码
```javascript

import ueboot from "ueboot";

/*自定义样式(此处为iview的自定义样式，详情参考iview的文档)*/
import 'ueboot/ue-theme/index.less'
/*ueboot 的组件样式*/
import 'ueboot/ueboot/styles/ueboot.css'
/*字体图标样式*/
import "font-awesome/scss/font-awesome.scss"
Vue.use(ueboot);

```
