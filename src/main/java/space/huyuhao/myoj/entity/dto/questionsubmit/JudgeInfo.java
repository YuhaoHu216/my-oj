package space.huyuhao.myoj.entity.dto.questionsubmit;

import lombok.Data;

/**
 * 判题信息
 */
@Data
public class JudgeInfo {

    private String message;
    private Long memory;
    private Long time;
}
