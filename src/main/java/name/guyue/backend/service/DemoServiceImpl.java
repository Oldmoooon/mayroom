package name.guyue.backend.service;

import java.util.Optional;
import name.guyue.backend.db.DemoUserRepository;
import name.guyue.backend.model.DemoUser;
import org.springframework.stereotype.Component;

/**
 * @author hujia
 * @date 2019-03-21
 */
@Component
public class DemoServiceImpl implements DemoService {

    private final DemoUserRepository repository;

    public DemoServiceImpl(DemoUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Boolean demoHandler(Long id, String password) {
        Optional<DemoUser> oUser = repository.findById(id);
        return oUser.map(demoUser -> password.equals(demoUser.getPassword())).orElse(false);
    }
}
