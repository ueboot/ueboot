# 分支说明
- master 主干分支，最终用于发布最新版本的分支
- 2.0-dev 2.0开发分支，日常代码开发提交的分支，确定发布后合并到master分支上

# ueboot快速开发平台
[详细帮助文档](https://gitee.com/ueboot/ueboot-quick-dev/wikis/pages)
- ueboot是一个基于`spring boot + vue2.0+iview2.0`的基础平台，通过这个平台可以快速完成某些CRUD功能的系统（从前端到后端快速实现）
- 提供代码生成器，生成页面和Java代码，避免纯粹重复劳动。
- 提供最佳实践的开发约定（Maven模块管理、前后端接口、异常、日志、权限等），便于小组协同开发。
- 平台默认提供了安全防范，防止XSS攻击、SQL注入。
- 基于平台进行二次开发，完全不受平台功能限制，平台提供的功能仅仅帮助大家做了一些开发约定和最佳实践，基于这些约定和最佳实践，让开发提高开发效率，并且保持规范一致性，本质上仅仅是一个基础的开发框架，和自己从零开始搭建的开发框架没任何区别。

 
# 包含的功能如下：
### ueboot-core
    ueboot核心功能，包含了基于spring boot所需要的基础的bean定义，集成了apache shiro权限框架。

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

  
### ueboot-generator
    
    代码生成器，通过这个模块提供的功能可以快速生成前后端代码，完成一个单表的CRUD功能。代码生成器提供了UI界面，
    通过UI界面控制要生成的代码。
    
### ueboot-parent
    用于指定项目所必备的jar包声明，在具体的项目当中使用是，需要继承该pom文件，便于管理jar包依赖，
    无需关心ueboot框架需要用到哪些jar文件
    在需要用到的项目当中（maven多模块情况下通常是父模块的pom.xml文件），继承该pom.xml
    示例：
    
```
 <parent>
        <groupId>com.ueboot</groupId>
        <artifactId>ueboot-parent</artifactId>
        <version>2.0.1</version>
        <relativePath/> <!-- lookup parent from api -->
    </parent>
```
    

### ueboot-view 
 - 代码参见 http://git.xiqiao.io/framework/ueboot.git

## 开发帮助文档
- 介绍
    -  [项目描述](https://gitee.com//ueboot/ueboot-quick-dev/wikis/pages?title=项目描述&parent=介绍)

## 快速使用脚手架工程

[脚手架工程](https://gitee.com/ueboot/ueboot-quick-dev-demo.git) https://gitee.com/ueboot/ueboot-quick-dev-demo.git
    脚手架工程代码可以直接拿来使用，在这个基础上进行二次开发。
    
## 关于源码
### 修改版本号命令
在IDEA右侧功能的MavenProjects窗口中执行以下命令:
` 
versions:set -DgroupId=com.ueboot -DartifactId=*   -DnewVersion=2.0.1
`