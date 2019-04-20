package name.guyue.backend.service;

import java.util.List;
import java.util.Map;
import name.guyue.backend.db.UserRepository;
import name.guyue.backend.enums.GroupEnum;
import name.guyue.backend.enums.ResponseStatusEnum;
import name.guyue.backend.enums.UserStateTypeEnum;
import name.guyue.backend.model.Response;
import name.guyue.backend.model.User;
import name.guyue.backend.util.JsonUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author hujia
 * @date 2019-03-30
 */
@Component
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    /** TODO 做登录验证、只有在群里的玩家才能登录 */
    @Override public Response<User> login(String openId, String openGid, String token) {
        return null;
    }

    @Override public Response<User> adminLogin(Long id, String openGid, String password) {
        Response<User> response = new Response<>();
        repository.findById(id).ifPresentOrElse(user -> {
            if (user.getPassword().equals(password)) {
                response.setStatus(ResponseStatusEnum.Ok);
                response.setData(user);
                // TODO 管理员在某个群，所以把这个群的gid加入到白名单
            } else {
                response.setStatus(ResponseStatusEnum.AuthFailure);
                response.setMessage("密码错误");
            }
        }, () -> {
            response.setStatus(ResponseStatusEnum.AuthFailure);
            response.setMessage("没有找到该用户");
        });
        return response;
    }

    @Override public Response<User> query(Long id, String openId) {
        Response<User> response = new Response<>();
        if (StringUtils.isEmpty(id)) {
            repository.findById(id).ifPresent(response::setData);
        }
        if (StringUtils.isEmpty(openId)) {
            repository.findUserByOpenId(openId).ifPresent(response::setData);
        }
        if (response.getData() == null) {
            response.setStatus(ResponseStatusEnum.UserNotFound);
            response.setMessage("找不到该用户");
        }
        return response;
    }

    @Override public Response<User> createNormalUser(String openId) {
        User user = new User();
        user.setGroup(GroupEnum.Normal);
        user.setState(UserStateTypeEnum.NotVerify);
        User save = repository.save(user);
        Response<User> response = new Response<>();
        response.setData(save);
        response.setStatus(ResponseStatusEnum.Ok);
        return response;
    }

    /** TODO 只有May帐号能做这个操作 */
    @Override public Response<User> createAdminUser(String password) {
        User user = new User();
        user.setPassword(password);
        user.setGroup(GroupEnum.Admin);
        user.setState(UserStateTypeEnum.Normal);
        User save = repository.save(user);
        Response<User> response = new Response<>();
        response.setData(save);
        response.setStatus(ResponseStatusEnum.Ok);
        return response;
    }

    @Override public Response<User> update(Long id, Map<String, Object> fields) {
        Response<User> response = new Response<>();
        repository.findById(id).ifPresentOrElse(user -> {
            var save = JsonUtil.merge(user, fields, User.class);
            repository.save(save);
            response.setStatus(ResponseStatusEnum.Ok);
            response.setData(save);
        }, () -> {
            response.setStatus(ResponseStatusEnum.UserNotFound);
            response.setMessage("用户不存在");
        });
        return response;
    }

    @Override public Response<List<User>> usersToVerify() {
        List<User> users = repository.findUsersByState(UserStateTypeEnum.NotVerify);
        Response<List<User>> response = new Response<>();
        response.setStatus(ResponseStatusEnum.Ok);
        response.setData(users);
        return response;
    }
}
