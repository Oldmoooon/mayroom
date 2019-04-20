package name.guyue.backend.db;

import java.util.List;
import name.guyue.backend.enums.HouseStateTypeEnum;
import name.guyue.backend.model.House;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * @author hujia
 * @date 2019-04-20
 */
public interface HouseRepository extends CrudRepository<House, Long>, JpaSpecificationExecutor<House> {
    List<House> findHousesByStateEquals(HouseStateTypeEnum state);
}
