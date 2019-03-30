package name.guyue.backend.db;

import java.util.Optional;
import name.guyue.backend.model.User;
import org.springframework.data.repository.CrudRepository;

/**
 * @author hujia
 * @date 2019-03-25
 */
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findUserByOpenId(String openId);
}
