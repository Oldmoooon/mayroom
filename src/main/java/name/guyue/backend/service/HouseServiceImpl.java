package name.guyue.backend.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import name.guyue.backend.db.HouseRepository;
import name.guyue.backend.enums.HouseStateTypeEnum;
import name.guyue.backend.enums.ResponseStatusEnum;
import name.guyue.backend.model.House;
import name.guyue.backend.model.Response;
import name.guyue.backend.model.User;
import name.guyue.backend.util.JsonUtil;
import org.springframework.stereotype.Component;

/**
 * @author hujia
 * @date 2019-04-20
 */
@Component
public class HouseServiceImpl implements HouseService {

    private final HouseRepository repository;

    public HouseServiceImpl(HouseRepository repository) {
        this.repository = repository;
    }

    @Override
    public Response<List<House>> housesVerified() {
        return housesByState(HouseStateTypeEnum.Verified);
    }

    @Override
    public Response<List<House>> housesNotVerified() {
        return housesByState(HouseStateTypeEnum.NotVerify);
    }

    @Override
    public Response<House> houseById(Long id) {
        Response<House> response = new Response<>();
        Optional<House> house = repository.findById(id);
        response.setStatus(ResponseStatusEnum.Ok);
        response.setData(house.orElse(null));
        return response;
    }

    @Override
    public Response<House> update(Long id, Map<String, Object> fields) {
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

    @Override
    public Response<House> create(User user) {
        Response<House> response = new Response<>();
        if (user == null) {
            response.setStatus(ResponseStatusEnum.UserNotFound);
            response.setMessage("查询已登录用户失败");
            return response;
        }
        House house = new House();
        house.setAuthor(user);
        house.setState(HouseStateTypeEnum.NotVerify);
        repository.save(house);
        response.setStatus(ResponseStatusEnum.Ok);
        response.setData(house);
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
