package space.huyuhao.myoj.judge.codesandbox.impl;

import lombok.extern.slf4j.Slf4j;
import space.huyuhao.myoj.judge.codesandbox.model.JudgeInfo;
import space.huyuhao.myoj.entity.enums.JudgeInfoMessageEnum;
import space.huyuhao.myoj.entity.enums.QuestionSubmitStatusEnum;
import space.huyuhao.myoj.judge.codesandbox.CodeSandbox;
import space.huyuhao.myoj.judge.codesandbox.model.ExecuteCodeRequest;
import space.huyuhao.myoj.judge.codesandbox.model.ExecuteCodeResponse;

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
