package space.huyuhao.myoj.judge.codesandbox.impl;

import space.huyuhao.myoj.judge.codesandbox.CodeSandbox;
import space.huyuhao.myoj.judge.codesandbox.model.ExecuteCodeRequest;
import space.huyuhao.myoj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * 第三方代码沙箱（调用网上现成的代码沙箱）
 */
public class ThirdPartyCodeSandbox implements CodeSandbox {

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("第三方代码沙箱");
        return null;
    }
}
