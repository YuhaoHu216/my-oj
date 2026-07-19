package space.huyuhao.myojsandbox;


import space.huyuhao.myojsandbox.model.ExecuteCodeRequest;
import space.huyuhao.myojsandbox.model.ExecuteCodeResponse;

/**
 * 代码沙箱接口定义
 */
public interface CodeSandbox {

    /**
     * 执行代码
     *
     * @param executeCodeRequest
     * @return
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
