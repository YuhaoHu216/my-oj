package space.huyuhao.myojjudge.controller.inner;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import space.huyuhao.myojclient.service.JudgeFeignClient;
import space.huyuhao.myojjudge.judge.JudgeService;
import space.huyuhao.myojmodel.model.entity.QuestionSubmit;

import javax.annotation.Resource;

/**
 * 判题服务内部接口（供 Feign 调用）
 */
@RestController
@RequestMapping("/api/judge/inner")
public class JudgeInnerController implements JudgeFeignClient {

    @Resource
    private JudgeService judgeService;

    @Override
    @PostMapping("/do")
    public QuestionSubmit doJudge(@RequestParam("questionSubmitId") long questionSubmitId) {
        return judgeService.doJudge(questionSubmitId);
    }
}
