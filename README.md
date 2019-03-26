# 基于spring-boot/spring-data-jpa/mysql/redis的web应用后端服务框架

思路大致是以下：
- 尽量少的配置，不引入过多的配置文件，不引入格式复杂的配置文件；
- 发挥spring自带注解的功能，尽量少得写模板代码；
- 发挥spring-data-jpa的功能，尽量少写甚至不写sql语句；
- 接口逻辑少耦合，一个接口不要去调另一个接口的逻辑。

