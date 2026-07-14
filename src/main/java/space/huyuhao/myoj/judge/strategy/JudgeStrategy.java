package space.huyuhao.myoj.judge.strategy;

import space.huyuhao.myoj.entity.dto.questionsubmit.JudgeInfo;

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
