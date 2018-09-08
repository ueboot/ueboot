## log 工具类
ueboot自带了一个log日志工具，可以通过设定日志级别来统一查看日志，默认为ALL，可以输出所有日志。
### 使用方式
```javascript
this.$log.d("tag:%o",Object);
this.$log.d("hello");
this.$log.i("hello"); //info级别
this.$log.e("hello");//error级别

```
- 支持一下级别的日志
  - DEBUG
  - INFO
  - ERROR
- 所有参数支持一到两个，可以是字符串，也可以是对象实际使用方式参见console本身的api

### 设置日志级别

在src/main.js当中设置
```javascript
import {Log} from 'ueboot'
//设置上下文，和未登录地址跳转地址
Log.config({level: 2});
//具体的使用参见下面的定义
const level = {
  NONE: 0,
  ERROR: 1,
  INFO: 2,
  DEBUG: 3,
  ALL: 4,
};

```
