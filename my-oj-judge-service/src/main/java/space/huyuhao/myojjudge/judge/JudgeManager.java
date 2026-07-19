package space.huyuhao.myojjudge.judge;

import org.springframework.stereotype.Service;
import space.huyuhao.myojjudge.judge.strategy.DefaultJudgeStrategy;
import space.huyuhao.myojjudge.judge.strategy.JavaLanguageJudgeStrategy;
import space.huyuhao.myojjudge.judge.strategy.JudgeContext;
import space.huyuhao.myojjudge.judge.strategy.JudgeStrategy;
import space.huyuhao.myojmodel.model.codesandbox.JudgeInfo;
import space.huyuhao.myojmodel.model.entity.QuestionSubmit;

/**
 * 判题管理（简化调用）
 */
@Service
public class JudgeManager {

    /**
     * 执行判题
     *
     * @param judgeContext 判题上下文
     * @return 判题结果信息
     */
    JudgeInfo doJudge(JudgeContext judgeContext) {
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if ("java".equals(language)) {
            judgeStrategy = new JavaLanguageJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }
}
