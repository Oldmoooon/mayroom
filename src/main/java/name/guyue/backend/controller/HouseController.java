package name.guyue.backend.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import name.guyue.backend.db.HouseRepository;
import name.guyue.backend.db.UserRepository;
import name.guyue.backend.enums.HouseStateTypeEnum;
import name.guyue.backend.enums.ResponseStatusEnum;
import name.guyue.backend.model.House;
import name.guyue.backend.model.Response;
import name.guyue.backend.model.User;
import name.guyue.backend.util.JsonUtil;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 房源：拉取已通过列表、拉取未通过列表、根据id查询特定、修改、新增
 *
 * @author hujia
 * @date 2019-04-20
 */
@RestController
public class HouseController {

    private final UserRepository userRepository;
    private final HouseRepository repository;

    public HouseController(UserRepository userRepository, HouseRepository repository) {
        this.userRepository = userRepository;
        this.repository = repository;
    }

    @RequestMapping(value = "/house/not_verified", method = RequestMethod.GET)
    public Response houseNotVerified() {
        return housesByState(HouseStateTypeEnum.NotVerify);
    }

    @RequestMapping(value = "/house/verified", method = RequestMethod.GET)
    public Response houseVerified() {
        return housesByState(HouseStateTypeEnum.Verified);
    }

    @RequestMapping(value = "/house/get", method = RequestMethod.GET)
    public Response get(
        @RequestParam(name = "id", required = false) Long id
    ) {
        Response<House> response = new Response<>();
        Optional<House> house = repository.findById(id);
        response.setStatus(ResponseStatusEnum.Ok);
        response.setData(house.orElse(null));
        return response;
    }

    @RequestMapping(value = "/house/create", method = RequestMethod.POST)
    public Response create(HttpSession session) {
        Object userId = session.getAttribute(session.getId());
        Optional<User> user = userRepository.findById((Long) userId);
        Response<House> response = new Response<>();
        House house = new House();
        house.setAuthor(user.orElseThrow());
        house.setState(HouseStateTypeEnum.NotVerify);
        repository.save(house);
        response.setStatus(ResponseStatusEnum.Ok);
        response.setData(house);
        return response;
    }

    @RequestMapping(value = "/house/update/{id}", method = RequestMethod.PATCH)
    public Response update(
        @PathVariable Long id,
        @RequestBody Map<String, Object> fields
    ) {
        Response<House> response = new Response<>();
        repository.findById(id).ifPresentOrElse(house -> {
            var save = JsonUtil.merge(house, fields, House.class);
            repository.save(save);
            response.setStatus(ResponseStatusEnum.Ok);
            response.setData(save);
        }, () -> {
            response.setStatus(ResponseStatusEnum.HouseNotFound);
            response.setMessage("房源不存在");
        });
        return response;
    }

    private Response<List<House>> housesByState(HouseStateTypeEnum state) {
        Response<List<House>> response = new Response<>();
        List<House> data = repository.findHousesByStateEquals(state);
        response.setStatus(ResponseStatusEnum.Ok);
        response.setData(data);
        return response;
    }
}
