package name.guyue.backend.controller;

import name.guyue.backend.model.Response;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 审核任务：拉取列表、管理员选取任务、管理员结束任务、管理员查看任务详情
 * @author hujia
 * @date 2019-04-20
 */
public class ReviewController {
    @RequestMapping(value = "/mission/list",method = RequestMethod.GET)
    public Response list() {
        return null;
    }

    @RequestMapping(value = "/mission/choose",method = RequestMethod.GET)
    public Response choose() {
        return null;
    }

    @RequestMapping(value = "/mission/finish",method = RequestMethod.GET)
    public Response finish() {
        return null;
    }

    @RequestMapping(value = "/mission/info",method = RequestMethod.POST)
    public Response info() {
        return null;
    }
}
