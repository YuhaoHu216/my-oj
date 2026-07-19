package space.huyuhao.myojquestion.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import space.huyuhao.myojmodel.model.entity.QuestionSubmit;
import space.huyuhao.myojmodel.model.entity.User;
import space.huyuhao.myojmodel.model.dto.questionsubmit.QuestionSubmitAddRequest;
import space.huyuhao.myojmodel.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import space.huyuhao.myojmodel.model.vo.QuestionSubmitVO;

/**
 * 题目提交服务
 */
public interface QuestionSubmitService extends IService<QuestionSubmit> {

    long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);

    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest);

    QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User loginUser);

    Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser);
}
