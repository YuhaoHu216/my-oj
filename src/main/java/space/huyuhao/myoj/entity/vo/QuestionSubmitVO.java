package space.huyuhao.myoj.entity.vo;

import cn.hutool.json.JSONUtil;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import space.huyuhao.myoj.dto.UserVO;
import space.huyuhao.myoj.entity.QuestionSubmit;
import space.huyuhao.myoj.judge.codesandbox.model.JudgeInfo;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 题目提交封装类
 */
@Data
public class QuestionSubmitVO implements Serializable {

    private Long id;
    private String language;
    private String code;
    private JudgeInfo judgeInfo;
    private Integer status;
    private Long questionId;
    private Long userId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private UserVO userVO;
    private QuestionVO questionVO;

    public static QuestionSubmit voToObj(QuestionSubmitVO questionSubmitVO) {
        if (questionSubmitVO == null) {
            return null;
        }
        QuestionSubmit questionSubmit = new QuestionSubmit();
        BeanUtils.copyProperties(questionSubmitVO, questionSubmit);
        JudgeInfo judgeInfoObj = questionSubmitVO.getJudgeInfo();
        if (judgeInfoObj != null) {
            questionSubmit.setJudgeInfo(JSONUtil.toJsonStr(judgeInfoObj));
        }
        return questionSubmit;
    }

    public static QuestionSubmitVO objToVo(QuestionSubmit questionSubmit) {
        if (questionSubmit == null) {
            return null;
        }
        QuestionSubmitVO questionSubmitVO = new QuestionSubmitVO();
        BeanUtils.copyProperties(questionSubmit, questionSubmitVO);
        String judgeInfoStr = questionSubmit.getJudgeInfo();
        questionSubmitVO.setJudgeInfo(JSONUtil.toBean(judgeInfoStr, JudgeInfo.class));
        return questionSubmitVO;
    }

    private static final long serialVersionUID = 1L;
}
