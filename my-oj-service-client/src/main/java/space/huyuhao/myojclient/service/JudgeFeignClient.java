package space.huyuhao.myojclient.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import space.huyuhao.myojmodel.model.entity.QuestionSubmit;

/**
 * 判题服务 Feign 客户端
 */
@FeignClient(name = "my-oj-judge-service", path = "/api/judge/inner")
public interface JudgeFeignClient {

    /**
     * 执行判题
     */
    @PostMapping("/do")
    QuestionSubmit doJudge(@RequestParam("questionSubmitId") long questionSubmitId);
}
