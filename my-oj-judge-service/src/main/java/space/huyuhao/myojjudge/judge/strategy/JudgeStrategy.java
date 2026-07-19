package space.huyuhao.myojjudge.judge.strategy;

import space.huyuhao.myojmodel.model.codesandbox.JudgeInfo;

/**
 * 判题策略
 */
public interface JudgeStrategy {

    /**
     * 执行判题
     * @param judgeContext 判题上下文
     * @return 判题结果信息
     */
    JudgeInfo doJudge(JudgeContext judgeContext);
}
