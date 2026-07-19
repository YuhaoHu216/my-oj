package space.huyuhao.myojjudge.judge.codesandbox.impl;

import lombok.extern.slf4j.Slf4j;
import space.huyuhao.myojjudge.judge.codesandbox.CodeSandbox;
import space.huyuhao.myojmodel.model.codesandbox.ExecuteCodeRequest;
import space.huyuhao.myojmodel.model.codesandbox.ExecuteCodeResponse;
import space.huyuhao.myojmodel.model.codesandbox.JudgeInfo;
import space.huyuhao.myojmodel.model.enums.JudgeInfoMessageEnum;
import space.huyuhao.myojmodel.model.enums.QuestionSubmitStatusEnum;

import java.util.List;

/**
 * 示例代码沙箱（仅为了跑通业务流程）
 */
@Slf4j
public class ExampleCodeSandbox implements CodeSandbox {

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        List<String> inputList = executeCodeRequest.getInputList();
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setOutputList(inputList);
        executeCodeResponse.setMessage("测试执行成功");
        executeCodeResponse.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMessage(JudgeInfoMessageEnum.ACCEPTED.getText());
        judgeInfo.setMemory(100L);
        judgeInfo.setTime(100L);
        executeCodeResponse.setJudgeInfo(judgeInfo);
        return executeCodeResponse;
    }
}
