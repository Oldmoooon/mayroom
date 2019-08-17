package name.guyue.backend.service;

import org.springframework.stereotype.Service;

/**
 * @author hujia
 * @date 2019-03-21
 */
@Service
public interface DemoService {

    Boolean demoHandler(Long id, String password);
}
