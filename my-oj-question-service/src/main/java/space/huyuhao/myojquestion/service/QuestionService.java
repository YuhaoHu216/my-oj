package space.huyuhao.myojquestion.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import space.huyuhao.myojmodel.model.entity.Question;
import space.huyuhao.myojmodel.model.dto.question.QuestionQueryRequest;
import space.huyuhao.myojmodel.model.vo.QuestionVO;

/**
 * 题目服务
 */
public interface QuestionService extends IService<Question> {

    void validQuestion(Question question, boolean add);

    QueryWrapper<Question> getQueryWrapper(QuestionQueryRequest questionQueryRequest);

    QuestionVO getQuestionVO(Question question);

    Page<QuestionVO> getQuestionVOPage(Page<Question> questionPage);
}
