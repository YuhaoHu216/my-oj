package space.huyuhao.myoj.entity.dto.question;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 更新请求
 */
@Data
public class QuestionUpdateRequest implements Serializable {

    private Long id;
    private String title;
    private String content;
    private List<String> tags;
    private String answer;
    private List<JudgeCase> judgeCase;
    private JudgeConfig judgeConfig;

    private static final long serialVersionUID = 1L;
}
