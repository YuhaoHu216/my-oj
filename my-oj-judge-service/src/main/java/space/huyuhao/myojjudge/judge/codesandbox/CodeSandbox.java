package space.huyuhao.myojjudge.judge.codesandbox;

import space.huyuhao.myojmodel.model.codesandbox.ExecuteCodeRequest;
import space.huyuhao.myojmodel.model.codesandbox.ExecuteCodeResponse;

/**
 * 代码沙箱接口定义
 */
public interface CodeSandbox {

    /**
     * 执行代码
     *
     * @param executeCodeRequest 执行代码请求
     * @return 执行代码响应
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
