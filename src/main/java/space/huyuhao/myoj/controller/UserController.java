package space.huyuhao.myoj.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import space.huyuhao.myoj.context.UserContext;
import space.huyuhao.myoj.service.UserService;
import space.huyuhao.myoj.common.ResponseResult;
import space.huyuhao.myoj.dto.UserInfoDto;
import space.huyuhao.myoj.dto.UserLoginDto;
import space.huyuhao.myoj.dto.UserRegisterDto;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @Operation(summary = "用户注册")
    public ResponseResult<String> register(@Valid @RequestBody UserRegisterDto userRegisterDto) {
        return userService.register(userRegisterDto);
    }

    @PostMapping("/login")
    @Operation(summary = "用户登录")
    public ResponseResult<String> login(@Valid @RequestBody UserLoginDto userLoginDto) {
        return userService.login(userLoginDto);
    }

    @GetMapping("/me")
    @Operation(summary = "获取当前用户信息")
    public ResponseResult<UserInfoDto> getCurrentUser() {
        return userService.getCurrentUser(UserContext.getUserId());
    }

    @PostMapping("/logout")
    @Operation(summary = "用户登出")
    public ResponseResult<String> logout() {
        return userService.logout();
    }

}