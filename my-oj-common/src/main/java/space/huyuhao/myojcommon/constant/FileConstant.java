package space.huyuhao.myojcommon.constant;

/**
 * 文件常量
 */
public interface FileConstant {

    /**
     * 文件上传大小限制（字节）：10MB
     */
    long FILE_MAX_SIZE = 10 * 1024 * 1024;

    /**
     * 允许的文件类型
     */
    String[] ALLOWED_FILE_TYPES = {"jpg", "jpeg", "png", "gif", "pdf", "txt"};
}
