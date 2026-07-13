package space.huyuhao.myoj.service;


import com.baomidou.mybatisplus.extension.service.IService;
import space.huyuhao.myoj.common.ResponseResult;
import space.huyuhao.myoj.dto.UserVO;
import space.huyuhao.myoj.entity.dto.UserInfoDto;
import space.huyuhao.myoj.entity.dto.UserLoginDto;
import space.huyuhao.myoj.entity.dto.UserRegisterDto;
import space.huyuhao.myoj.entity.User;

import java.util.Collection;
import java.util.List;

public interface UserService extends IService<User> {
    ResponseResult<String> register(UserRegisterDto userRegisterDto);

    ResponseResult<String> login(UserLoginDto userLoginDto);

    ResponseResult<UserInfoDto> getCurrentUser(Long userId);

    ResponseResult<String> logout();

    User findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    /**
     * 获取用户脱敏视图
     */
    UserVO getUserVO(User user);

    /**
     * 批量获取用户列表
     */
    List<User> listByIds(Collection<Long> userIds);

    /**
     * 判断是否为管理员
     */
    boolean isAdmin(User user);
}
