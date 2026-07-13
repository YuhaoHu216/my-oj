package space.huyuhao.myoj.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import space.huyuhao.myoj.entity.Question;
import space.huyuhao.myoj.entity.dto.question.QuestionQueryRequest;
import space.huyuhao.myoj.entity.vo.QuestionVO;

/**
 * 题目服务
 */
public interface QuestionService extends IService<Question> {

    void validQuestion(Question question, boolean add);

    QueryWrapper<Question> getQueryWrapper(QuestionQueryRequest questionQueryRequest);

    QuestionVO getQuestionVO(Question question);

    Page<QuestionVO> getQuestionVOPage(Page<Question> questionPage);
}
