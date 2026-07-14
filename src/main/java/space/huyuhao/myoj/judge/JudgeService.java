package space.huyuhao.myoj.judge;

import space.huyuhao.myoj.entity.QuestionSubmit;

/**
 * 判题服务
 */
public interface JudgeService {

    /**
     * 判题
     * @param questionSubmitId 提交记录ID
     * @return 更新后的提交记录
     */
    QuestionSubmit doJudge(long questionSubmitId);
}
