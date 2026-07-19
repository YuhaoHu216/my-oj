package space.huyuhao.myojmodel.model.dto.questionsubmit;

import lombok.Data;
import lombok.EqualsAndHashCode;
import space.huyuhao.myojcommon.common.PageRequest;

import java.io.Serializable;

/**
 * 查询请求
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class QuestionSubmitQueryRequest extends PageRequest implements Serializable {

    private String language;
    private Integer status;
    private Long questionId;
    private Long userId;

    private static final long serialVersionUID = 1L;
}
