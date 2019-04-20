package name.guyue.backend.service;

import java.util.List;
import java.util.Map;
import name.guyue.backend.model.Response;
import name.guyue.backend.model.User;
import org.springframework.stereotype.Service;

/**
 * @author hujia
 * @date 2019-03-25
 */
@Service
public interface UserService {
    /** 普通用户登录 */
    Response<User> login(String openId, String openGid, String token);

    /** 管理员登录 */
    Response<User> adminLogin(Long id, String openGid, String password);

    /** 通过id或openId查询用户信息（部分字段显示） */
    Response<User> query(Long id, String openId);

    /** 创建一个普通帐号，并把帐号返回给前端 */
    Response<User> createNormalUser(String openId);

    /** 创建一个管理员帐号，并把帐号返回给前端 */
    Response<User> createAdminUser(String password);

    /** 更新用户的信息， key/字段名 value/新值 */
    Response<User> update(Long id, Map<String, Object> fields);

    /** 待审核用户列表 */
    Response<List<User>> usersToVerify();
}
