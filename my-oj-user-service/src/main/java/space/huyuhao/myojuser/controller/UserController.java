package space.huyuhao.myojuser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import space.huyuhao.myojuser.utils.UserContext;
import space.huyuhao.myojuser.service.UserService;
import space.huyuhao.myojcommon.common.ResponseResult;
import space.huyuhao.myojmodel.model.dto.UserInfoDto;
import space.huyuhao.myojmodel.model.dto.UserLoginDto;
import space.huyuhao.myojmodel.model.dto.UserRegisterDto;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseResult<String> register(@RequestBody UserRegisterDto userRegisterDto) {
        return userService.register(userRegisterDto);
    }

    @PostMapping("/login")
    public ResponseResult<String> login(@RequestBody UserLoginDto userLoginDto) {
        return userService.login(userLoginDto);
    }

    @GetMapping("/me")
    public ResponseResult<UserInfoDto> getCurrentUser() {
        return userService.getCurrentUser(UserContext.getUserId());
    }

    @PostMapping("/logout")
    public ResponseResult<String> logout() {
        return userService.logout();
    }
}
