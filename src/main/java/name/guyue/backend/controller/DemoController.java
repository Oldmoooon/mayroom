package name.guyue.backend.controller;

import name.guyue.backend.service.DemoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hujia
 * @date 2019-03-21
 */
@RestController
public class DemoController {
    private final DemoService demoService;

    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }

    @RequestMapping("/demo")
    public Boolean demoHandler(
        @RequestParam(name = "id") Long id,
        @RequestParam(name = "password") String password
    ) {
        if (id == null || password == null) {
            return false;
        }
        return demoService.demoHandler(id, password);
    }
}
