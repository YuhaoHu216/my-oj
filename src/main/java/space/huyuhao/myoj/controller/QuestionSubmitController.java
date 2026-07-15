package space.huyuhao.myoj.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import space.huyuhao.myoj.common.ErrorCode;
import space.huyuhao.myoj.common.ResponseResult;
import space.huyuhao.myoj.context.UserContext;
import space.huyuhao.myoj.entity.QuestionSubmit;
import space.huyuhao.myoj.entity.User;
import space.huyuhao.myoj.entity.dto.questionsubmit.QuestionSubmitAddRequest;
import space.huyuhao.myoj.entity.dto.questionsubmit.QuestionSubmitQueryRequest;
import space.huyuhao.myoj.entity.vo.QuestionSubmitVO;
import space.huyuhao.myoj.exception.BusinessException;
import space.huyuhao.myoj.service.QuestionSubmitService;
import space.huyuhao.myoj.service.UserService;

/**
 * 题目提交接口
 *
 * @deprecated 请使用 {@link QuestionController} 中的提交接口
 */
@Deprecated
@RestController
//@RequestMapping("/question_submit")
public class QuestionSubmitController {

    @Autowired
    private QuestionSubmitService questionSubmitService;

    @Autowired
    private UserService userService;

//    /**
//     * 提交题目
//     */
//    @PostMapping("/")
//    public ResponseResult<Long> doQuestionSubmit(
//            @RequestBody QuestionSubmitAddRequest questionSubmitAddRequest) {
//        if (questionSubmitAddRequest == null || questionSubmitAddRequest.getQuestionId() <= 0) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR);
//        }
//        final User loginUser = userService.getById(UserContext.getUserId());
//        long questionSubmitId = questionSubmitService.doQuestionSubmit(questionSubmitAddRequest, loginUser);
//        return ResponseResult.success(questionSubmitId);
//    }
//
//    /**
//     * 分页获取题目提交列表（脱敏）
//     */
//    @PostMapping("/list/page")
//    public ResponseResult<Page<QuestionSubmitVO>> listQuestionSubmitByPage(
//            @RequestBody QuestionSubmitQueryRequest questionSubmitQueryRequest) {
//        long current = questionSubmitQueryRequest.getCurrent();
//        long size = questionSubmitQueryRequest.getPageSize();
//        Page<QuestionSubmit> questionSubmitPage = questionSubmitService.page(new Page<>(current, size),
//                questionSubmitService.getQueryWrapper(questionSubmitQueryRequest));
//        final User loginUser = userService.getById(UserContext.getUserId());
//        return ResponseResult.success(questionSubmitService.getQuestionSubmitVOPage(questionSubmitPage, loginUser));
//    }
}
