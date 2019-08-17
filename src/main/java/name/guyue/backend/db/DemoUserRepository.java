package name.guyue.backend.db;

import java.util.List;
import name.guyue.backend.model.DemoUser;
import org.springframework.data.repository.CrudRepository;

/**
 * @author hujia
 * @date 2019-03-21
 */
public interface DemoUserRepository extends CrudRepository<DemoUser, Long> {

    List<DemoUser> findByUsername(String username);
}
