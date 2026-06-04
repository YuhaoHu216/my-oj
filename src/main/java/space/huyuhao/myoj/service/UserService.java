package space.huyuhao.myoj.service;


import space.huyuhao.myoj.common.ResponseResult;
import space.huyuhao.myoj.dto.UserInfoDto;
import space.huyuhao.myoj.dto.UserLoginDto;
import space.huyuhao.myoj.dto.UserRegisterDto;
import space.huyuhao.myoj.entity.User;

public interface UserService {
    ResponseResult<String> register(UserRegisterDto userRegisterDto);

    ResponseResult<String> login(UserLoginDto userLoginDto);

    ResponseResult<UserInfoDto> getCurrentUser(Long userId);

    ResponseResult<String> logout();

    User findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}