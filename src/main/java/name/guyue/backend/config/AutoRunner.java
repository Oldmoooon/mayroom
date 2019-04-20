package name.guyue.backend.config;

import name.guyue.backend.db.UserRepository;
import name.guyue.backend.enums.GroupEnum;
import name.guyue.backend.enums.UserStateTypeEnum;
import name.guyue.backend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author hujia
 * @date 2019-04-20
 */
@Component
@Order(value = 1)
public class AutoRunner implements ApplicationRunner {
    private final UserRepository repository;

    public AutoRunner(UserRepository repository) {
        this.repository = repository;
    }

    @Override public void run(ApplicationArguments args) {
        if (repository.findById(1L).isEmpty()) {
            User user = new User();
            user.setState(UserStateTypeEnum.Verified);
            user.setGroup(GroupEnum.May);
            user.setPassword("password");
            repository.save(user);
        }
    }
}
