package space.huyuhao.myojjudge.judge.strategy;

import lombok.Data;
import space.huyuhao.myojmodel.model.codesandbox.JudgeInfo;
import space.huyuhao.myojmodel.model.dto.question.JudgeCase;
import space.huyuhao.myojmodel.model.entity.Question;
import space.huyuhao.myojmodel.model.entity.QuestionSubmit;

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
