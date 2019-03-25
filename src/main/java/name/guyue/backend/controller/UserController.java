package name.guyue.backend.controller;

import java.util.Map;
import javax.servlet.http.HttpSession;
import name.guyue.backend.model.Response;
import name.guyue.backend.service.UserService;
import name.guyue.backend.util.ResponseUtil;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * vx自动登录、管理员登录、查询用户信息、新建管理员用户、修改用户信息
 * @author hujia
 * @date 2019-03-25
 */
@RestController
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
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

    @RequestMapping(value = "/admin/login",method = RequestMethod.POST)
    public Response adminLogin(
        @RequestParam(name = "id") String id,
        @RequestParam(name = "openGid") String openGid,
        @RequestParam(name = "password") String password,
        HttpSession session
    ) {
        var resp = service.adminLogin(id, openGid, password);
        if (ResponseUtil.isOk(resp)) {
            session.setAttribute(session.getId(), id);
        }
        return resp;
    }

    @RequestMapping(value = "/query",method = RequestMethod.GET)
    public Response query(@RequestParam(name = "id") String id, @RequestParam(name = "openId") String openId) {
        return service.query(id, openId);
    }

    @RequestMapping(value = "/admin/create",method = RequestMethod.POST)
    public Response createAdminUser(
        @RequestParam(name = "password") String password
    ) {
        return service.createAdminUser(password);
    }

    @RequestMapping(value = "/update/{id}",method = RequestMethod.PATCH)
    public Response update(
        @PathVariable Long id,
        @RequestBody Map<String, Object> fields
    ) {
        return service.update(id, fields);
    }
}
