package name.guyue.backend.service;

import com.google.gson.Gson;
import java.util.Map;
import name.guyue.backend.db.UserRepository;
import name.guyue.backend.enums.GroupEnum;
import name.guyue.backend.enums.ResponseStatusEnum;
import name.guyue.backend.enums.UserStateTypeEnum;
import name.guyue.backend.model.Response;
import name.guyue.backend.model.User;
import name.guyue.backend.util.TypeUtil;
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

    @Override public Response<User> login(String openId, String openGid, String token) {
        return null;
    }

    @Override public Response<User> adminLogin(String id, String openGid, String password) {
        Response<User> response = new Response<>();
        repository.findById(Long.valueOf(id)).ifPresentOrElse(user -> {
            if (user.getPassword().equals(password)) {
                response.setStatus(ResponseStatusEnum.Ok);
                response.setData(user);
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

    @Override public Response<User> query(String id, String openId) {
        Response<User> response = new Response<>();
        if (StringUtils.isEmpty(id)) {
            repository.findById(Long.valueOf(id)).ifPresent(response::setData);
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
        Gson gson = new Gson();
        Response<User> response = new Response<>();
        repository.findById(id).ifPresentOrElse(user -> {
            var json = gson.toJson(user);
            Map<String, Object> map = gson.fromJson(json, TypeUtil.map(String.class, Object.class));
            map.putAll(fields);
            json = gson.toJson(map);
            var save = gson.fromJson(json, User.class);
            repository.save(save);
            response.setStatus(ResponseStatusEnum.Ok);
            response.setData(save);
        }, () -> {
            response.setStatus(ResponseStatusEnum.UserNotFound);
            response.setMessage("用户不存在");
        });
        return response;
    }
}
