# mayroom

> 思路大致是以下：
> - 微服务，提供json格式的api；
> - 尽量少的配置，不引入过多的配置文件，不引入格式复杂的配置文件；
> - 发挥spring自带注解的功能，尽量少写模板代码；
> - 发挥spring-data-jpa的功能，尽量少写甚至不写sql语句；
> - 接口逻辑少耦合，一个接口不要去调另一个接口的逻辑。

## 环境需求

- jdk11；
- mysql，默认配置：用户test、库test、密码12345678；
- redis，默认配置即可；

## 框架和技术栈：

- spring-web/spring-jpa-data；
- lombok；
- junit4；
- gradle；

## 分支管理

- git-flow；
- 主开发分支develop，基本功能开发时暂时直接在该分支提交即可，服务稳定后切feature分支做功能开发；
