package space.huyuhao.myojmodel.model.codesandbox;

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
