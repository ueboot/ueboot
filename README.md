# ueboot快速开发平台
基于spring boot + vue2.0+iview2.0开发的前后端分离类型的，快速开发框架。
分钟级别即可完成单表的CRUD功能，对后台管理类型的系统非常友好。前后端分离的方式满足任意系统开发需要。

## 模块
### ueboot-core
    ueboot核心功能，包含了基于spring boot所需要的基础的bean定义，集成了apache shiro权限框架。
    
包含的功能如下（有一些约定规则）：    
- 对所有请求和返回的json内容提供日志打印
- 对所有请求内容进行xss拦截\sql注入
- 封装了统一的异常拦截，对外输出标准的错误码和报文
- 对请求内容做统一的格式化校验（基于hibernate validate）,返回统一的错误提示
- 集成了redis缓存，用于保存session会话和spring cache 方法级别的缓存注解。
- 统一了所有接口返回的对象，都使用Response包装内进行包装，保证前端接口获取到的报文体格式一致
- 基于Spring Data JPA 提供了基础的BaseRepository和BaseService,实现了单一Entity类的CRUD功能
- 集成了apache shiro功能，只需要实现ShiroService类当中的几个方法即可完成方法级别的权限认证和授权
- 提供了部分常用工具类

   - 验证码图片输出工具
   - MD5加密工具
   - 固定长度数字格式化成字符工具类，比如：固定订单号长度，不足则用0补
   - 微信接口调用工具类
   - XSS攻击字符过滤工具类  