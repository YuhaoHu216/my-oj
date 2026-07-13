package space.huyuhao.myoj.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import space.huyuhao.myoj.entity.QuestionSubmit;
import space.huyuhao.myoj.entity.User;
import space.huyuhao.myoj.entity.dto.questionsubmit.QuestionSubmitAddRequest;
import space.huyuhao.myoj.entity.dto.questionsubmit.QuestionSubmitQueryRequest;
import space.huyuhao.myoj.entity.vo.QuestionSubmitVO;

/**
 * 题目提交服务
 */
public interface QuestionSubmitService extends IService<QuestionSubmit> {

    long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);

    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest);

    QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User loginUser);

    Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser);
}
