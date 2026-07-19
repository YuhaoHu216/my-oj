package space.huyuhao.myojcommon.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用基础响应类
 */
@Data
public class BaseResponse implements Serializable {

    /**
     * 响应码
     */
    private int code;

    /**
     * 响应消息
     */
    private String message;

    private static final long serialVersionUID = 1L;

    public BaseResponse() {
    }

    public BaseResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
