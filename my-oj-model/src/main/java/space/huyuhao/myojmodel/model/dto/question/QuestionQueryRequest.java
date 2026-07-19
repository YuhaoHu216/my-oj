package space.huyuhao.myojmodel.model.dto.question;

import lombok.Data;
import lombok.EqualsAndHashCode;
import space.huyuhao.myojcommon.common.PageRequest;

import java.io.Serializable;
import java.util.List;

/**
 * 查询请求
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QuestionQueryRequest extends PageRequest implements Serializable {

    private Long id;
    private String title;
    private String content;
    private List<String> tags;
    private String answer;
    private Long userId;

    private static final long serialVersionUID = 1L;
}
