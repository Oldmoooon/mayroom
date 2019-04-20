package name.guyue.backend.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import name.guyue.backend.db.UserRepository;
import name.guyue.backend.enums.GroupEnum;
import name.guyue.backend.enums.ResponseStatusEnum;
import name.guyue.backend.enums.UserStateTypeEnum;
import name.guyue.backend.model.Response;
import name.guyue.backend.model.User;
import name.guyue.backend.util.ConstantsUtil;
import name.guyue.backend.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author hujia
 * @date 2019-03-30
 */
@Component
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final StringRedisTemplate redis;
    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserServiceImpl(UserRepository repository, StringRedisTemplate redis) {
        this.repository = repository;
        this.redis = redis;
    }

    @Override public Response<User> login(String openId, String openGid, String token) {
        Response<User> response = new Response<>();
        Boolean isMember = redis.opsForSet().isMember(ConstantsUtil.WHITE_LIST_OF_GROUP_REDIS_KEY, openGid);
        if (isMember == null || !isMember) {
            response.setStatus(ResponseStatusEnum.GroupError);
            response.setMessage("需要在群里打开");
        }
        Optional<User> user = repository.findUserByOpenId(openId);
        user.ifPresentOrElse(u -> {
            response.setStatus(ResponseStatusEnum.Ok);
            response.setData(u);
        }, () -> {
            // TODO 暂时创建一个正常用户
            User normalUser = createNormalUser(openId).getData();
            normalUser.setState(UserStateTypeEnum.Normal);
            repository.save(normalUser);
            logger.error("不存在的用户尝试登录");
        });
        return null;
    }

    @Override public Response<User> adminLogin(Long id, String openGid, String password) {
        Response<User> response = new Response<>();
        repository.findById(id).ifPresentOrElse(user -> {
            if (user.getPassword().equals(password)) {
                response.setStatus(ResponseStatusEnum.Ok);
                response.setData(user);
                redis.opsForSet().add(ConstantsUtil.WHITE_LIST_OF_GROUP_REDIS_KEY, openGid);
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

    /** 只有May帐号能做这个操作 */
    @Override public Response<User> createAdminUser(Long userId, String password) {
        Optional<User> admin = repository.findById(userId);
        if (admin.isPresent() && admin.get().getGroup().equals(GroupEnum.May)) {
            User user = new User();
            user.setPassword(password);
            user.setGroup(GroupEnum.Admin);
            user.setState(UserStateTypeEnum.Normal);
            User save = repository.save(user);
            Response<User> response = new Response<>();
            response.setData(save);
            response.setStatus(ResponseStatusEnum.Ok);
            return response;
        } else {
            Response<User> response = new Response<>();
            response.setStatus(ResponseStatusEnum.PermissionError);
            response.setMessage("只有May权限的用户才能进行这个操作");
            return response;
        }
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
