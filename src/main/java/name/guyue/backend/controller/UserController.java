package name.guyue.backend.controller;

import java.util.Map;
import javax.servlet.http.HttpSession;
import name.guyue.backend.model.Response;
import name.guyue.backend.service.UserService;
import name.guyue.backend.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * vx自动登录、管理员登录、查询用户信息、新建管理员用户、修改用户信息
 *
 * @author hujia
 * @date 2019-03-25
 */
@RestController
public class UserController {

    private final UserService service;

    public UserController(@Qualifier("userServiceImpl") UserService service) {
        this.service = service;
    }

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public Response login(
        @RequestParam(name = "openId") String openId,
        @RequestParam(name = "openGid") String openGid,
        @RequestParam(name = "token") String token,
        HttpSession session
    ) {
        var resp = service.login(openId, openGid, token);
        if (ResponseUtil.isOk(resp)) {
            var id = resp.getData().getId();
            session.setAttribute(session.getId(), id);
        }
        return resp;
    }

    @RequestMapping(value = "/user/admin/login", method = RequestMethod.POST)
    public Response adminLogin(
        @RequestParam("id") Long id,
        @RequestParam("openGid") String openGid,
        @RequestParam("password") String password,
        HttpSession session
    ) {
        var resp = service.adminLogin(id, openGid, password);
        if (ResponseUtil.isOk(resp)) {
            session.setAttribute(session.getId(), resp.getData().getId());
        }
        return resp;
    }

    @RequestMapping(value = "/user/create", method = RequestMethod.POST)
    public Response create(
        @RequestParam(name = "openId", required = false) String openId
    ) {
        return service.createNormalUser(openId);
    }

    @RequestMapping(value = "/user/query", method = RequestMethod.GET)
    public Response query(
        @RequestParam(name = "id", required = false) Long id,
        @RequestParam(name = "openId", required = false) String openId
    ) {
        return service.query(id, openId);
    }

    @RequestMapping(value = "/user/admin/create", method = RequestMethod.POST)
    public Response createAdminUser(
        @RequestParam(name = "password") String password,
        HttpSession session
    ) {
        Object userId = session.getAttribute(session.getId());
        return service.createAdminUser((Long) userId, password);
    }

    @RequestMapping(value = "/user/update/{id}", method = RequestMethod.PATCH)
    public Response update(
        @PathVariable Long id,
        @RequestBody Map<String, Object> fields
    ) {
        return service.update(id, fields);
    }

    @RequestMapping(value = "/user/list/not_verified", method = RequestMethod.GET)
    public Response update() {
        return service.usersToVerify();
    }
}
