package space.huyuhao.myoj.entity.dto.questionsubmit;

import lombok.Data;

import java.io.Serializable;

/**
 * 创建请求
 */
@Data
public class QuestionSubmitAddRequest implements Serializable {

    private String language;
    private String code;
    private Long questionId;

    private static final long serialVersionUID = 1L;
}
