package space.huyuhao.myojmodel.model.vo;

import cn.hutool.json.JSONUtil;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import space.huyuhao.myojmodel.model.dto.question.JudgeConfig;
import space.huyuhao.myojmodel.model.entity.Question;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 题目封装类
 */
@Data
public class QuestionVO implements Serializable {

    private Long id;
    private String title;
    private String content;
    private List<String> tags;
    private Integer submitNum;
    private Integer acceptedNum;
    private JudgeConfig judgeConfig;
    private Integer thumbNum;
    private Integer favourNum;
    private Long userId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private UserVO userVO;

    public static Question voToObj(QuestionVO questionVO) {
        if (questionVO == null) {
            return null;
        }
        Question question = new Question();
        BeanUtils.copyProperties(questionVO, question);
        List<String> tagList = questionVO.getTags();
        if (tagList != null) {
            question.setTags(JSONUtil.toJsonStr(tagList));
        }
        JudgeConfig voJudgeConfig = questionVO.getJudgeConfig();
        if (voJudgeConfig != null) {
            question.setJudgeConfig(JSONUtil.toJsonStr(voJudgeConfig));
        }
        return question;
    }

    public static QuestionVO objToVo(Question question) {
        if (question == null) {
            return null;
        }
        QuestionVO questionVO = new QuestionVO();
        BeanUtils.copyProperties(question, questionVO);
        List<String> tagList = JSONUtil.toList(question.getTags(), String.class);
        questionVO.setTags(tagList);
        String judgeConfigStr = question.getJudgeConfig();
        questionVO.setJudgeConfig(JSONUtil.toBean(judgeConfigStr, JudgeConfig.class));
        return questionVO;
    }

    private static final long serialVersionUID = 1L;
}
