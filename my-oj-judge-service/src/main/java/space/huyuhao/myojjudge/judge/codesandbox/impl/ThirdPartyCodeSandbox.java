package space.huyuhao.myojjudge.judge.codesandbox.impl;

import space.huyuhao.myojjudge.judge.codesandbox.CodeSandbox;
import space.huyuhao.myojmodel.model.codesandbox.ExecuteCodeRequest;
import space.huyuhao.myojmodel.model.codesandbox.ExecuteCodeResponse;

/**
 * 第三方代码沙箱（调用网上现成的代码沙箱）
 */
public class ThirdPartyCodeSandbox implements CodeSandbox {

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("第三方代码沙箱，暂未实现。敬请期待");
        return null;
    }
}
