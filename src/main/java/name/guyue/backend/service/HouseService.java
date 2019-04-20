package name.guyue.backend.service;

import java.util.List;
import java.util.Map;
import name.guyue.backend.model.House;
import name.guyue.backend.model.Response;
import name.guyue.backend.model.User;
import org.springframework.stereotype.Service;

/**
 * @author hujia
 * @date 2019-04-20
 */
@Service
public interface HouseService {

    Response<List<House>> housesVerified();

    Response<List<House>> housesNotVerified();

    Response<House> houseById(Long id);

    Response<House> update(Long id, Map<String, Object> fields);

    Response<House> create(User user);
}
