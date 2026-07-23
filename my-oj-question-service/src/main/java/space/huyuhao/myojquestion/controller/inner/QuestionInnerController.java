package space.huyuhao.myojquestion.controller.inner;

import org.springframework.web.bind.annotation.*;
import space.huyuhao.myojclient.service.QuestionFeignClient;
import space.huyuhao.myojmodel.model.entity.Question;
import space.huyuhao.myojmodel.model.entity.QuestionSubmit;
import space.huyuhao.myojquestion.service.QuestionService;
import space.huyuhao.myojquestion.service.QuestionSubmitService;

import javax.annotation.Resource;

/**
 * 题目内部 Feign 接口
 */
@RestController
@RequestMapping("/api/question/inner")
public class QuestionInnerController implements QuestionFeignClient {

    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Override
    @GetMapping("/get/id")
    public Question getQuestionById(@RequestParam("questionId") long questionId) {
        return questionService.getById(questionId);
    }

    @Override
    @GetMapping("/question_submit/get/id")
    public QuestionSubmit getQuestionSubmitById(@RequestParam("questionSubmitId") long questionSubmitId) {
        return questionSubmitService.getById(questionSubmitId);
    }

    @Override
    @PostMapping("/question_submit/update")
    public boolean updateQuestionSubmitById(@RequestBody QuestionSubmit questionSubmit) {
        return questionSubmitService.updateById(questionSubmit);
    }
}
