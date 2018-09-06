> 用于统一提交异步请求到服务端，默认的组件已经解决异常处理、权限处理的问题

## 1.axios工具类
本组件的formGrid需要用到ajax请求，所以顺便提供了ajax功能。主要基于axios进行了封装
## 2.使用方式
只要使用了Vue.use(ueboot)组件，就可以在任意vue文件里面直接使用this.$axios对象

```javascript
    //最简单的使用（后端接口使用配套的后台框架ueboot）
    this.$axios.post("/api/",{}).then((response)=>{
        //处理成功返回值
    })
    
    //处理异常
   this.$axios.post("/api/",{}).then((response)=>{
        //处理成功返回值
    },(error)=>{
      //处理异常
      return true; //一定要返回true
    })
```

### 2.1 分页查询,url带参数
>某些场景下如分页查询，要求将分页参数放在url当中，这个时候可以使用post请求当中的第3个参数来实现 

```javascript
//这里参数通过json格式提交
let data  = {}
//这里的参数将追加到url后面
let params = {page:1,size:15}

 this.$axios.post(this.formGrid.options.url.page, data, {params: params}).then(response => {
      this.formGrid.table.loading = false
      this.formGrid.table.noDataText = "抱歉！已努力查询，但还是没有找到您要的数据。";
      this.formGrid.table.data = response.body.content
      Log.d("接口返回对象,%o", response);
      this.formGrid.pageable.total = response.body.totalElements;
      this.$forceUpdate()
  }).catch(response => {
    this.formGrid.table.loading = false
    this.formGrid.table.data = []
    this.formGrid.table.noDataText = "数据查询出现异常，需要管理员查看后台日志，寻找原因。";
    this.noticeError("数据查询出现异常", response.message ? response.message : "系统或网络异常");
    this.$forceUpdate()
    return false;
  });

```

## 3. 全局配置
  全局配置请求路径的上下文、未登录的情况下跳转到的登录地址在main.js当中设置
  

```javascript
 
import ueboot from 'ueboot'
//设置上下文，和未登录后页面跳转的地址
ueboot.Axios.init({ baseURL: "/console", unauthorizedUrl: "/#/login"});


```

