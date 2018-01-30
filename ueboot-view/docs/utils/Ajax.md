## ajax工具类
本组件的formGrid需要用到ajax请求，所以顺便提供了ajax功能。主要基于vue-resource进行了封装
###  使用方式，在任意vue文件里面可以直接使用
```javascript
    //最简单的使用（后端接口使用配套的后台框架io.dabing）
    this.$ajax.post("/api/",{}).then((response)=>{
        //处理成功返回值
    })
    
    //处理异常
   this.$ajax.post("/api/",{}).then((response)=>{
        //处理成功返回值
    },(error)=>{
      //处理异常
      return true; //一定要返回true
    })
```

### 全局配置请求路径的上下文、未登录的情况下跳转到的登录地址
  再main.js当中设置
  

```javascript
 
import {Ajax} from 'ueboot'
//设置上下文，和未登录地址跳转地址
Ajax.config({context: "/console", unauthorizedUrl: "/"});


```
