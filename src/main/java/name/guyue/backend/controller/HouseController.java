package name.guyue.backend.controller;

import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import name.guyue.backend.db.UserRepository;
import name.guyue.backend.model.Response;
import name.guyue.backend.model.User;
import name.guyue.backend.service.HouseService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 房源：拉取已通过列表、拉取未通过列表、根据id查询特定、修改、新增
 * @author hujia
 * @date 2019-04-20
 */
@RestController
public class HouseController {
    private final HouseService service;
    private final UserRepository userRepository;

    public HouseController(@Qualifier("houseServiceImpl") HouseService service, UserRepository userRepository) {
        this.service = service;
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/house/not_verified",method = RequestMethod.GET)
    public Response houseNotVerified() {
        return service.housesNotVerified();
    }

    @RequestMapping(value = "/house/verified",method = RequestMethod.GET)
    public Response houseVerified() {
        return service.housesNotVerified();
    }

    @RequestMapping(value = "/house/get",method = RequestMethod.GET)
    public Response get(
        @RequestParam(name = "id", required = false) Long id
    ) {
        return service.houseById(id);
    }

    @RequestMapping(value = "/house/create",method = RequestMethod.POST)
    public Response create(HttpSession session) {
        Object userId = session.getAttribute(session.getId());
        Optional<User> user = userRepository.findById((Long)userId);
        return service.create(user.orElse(null));
    }

    @RequestMapping(value = "/house/update/{id}",method = RequestMethod.PATCH)
    public Response update(
        @PathVariable Long id,
        @RequestBody Map<String, Object> fields
    ) {
        return service.update(id, fields);
    }
}
