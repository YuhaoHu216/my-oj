package space.huyuhao.myoj.judge.strategy;

import lombok.Data;
import space.huyuhao.myoj.entity.Question;
import space.huyuhao.myoj.entity.QuestionSubmit;
import space.huyuhao.myoj.entity.dto.question.JudgeCase;
import space.huyuhao.myoj.entity.dto.questionsubmit.JudgeInfo;

import java.util.List;

/**
 * 上下文（用于定义在策略中传递的参数）
 */
@Data
public class JudgeContext {

    private JudgeInfo judgeInfo;

    private List<String> inputList;

    private List<String> outputList;

    private List<JudgeCase> judgeCaseList;

    private Question question;

    private QuestionSubmit questionSubmit;
}
