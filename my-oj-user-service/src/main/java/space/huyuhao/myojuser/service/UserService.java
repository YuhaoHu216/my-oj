package space.huyuhao.myojuser.service;


import com.baomidou.mybatisplus.extension.service.IService;
import space.huyuhao.myojcommon.common.ResponseResult;
import space.huyuhao.myojmodel.model.entity.User;
import space.huyuhao.myojmodel.model.dto.UserInfoDto;
import space.huyuhao.myojmodel.model.dto.UserLoginDto;
import space.huyuhao.myojmodel.model.dto.UserRegisterDto;
import space.huyuhao.myojmodel.model.vo.UserVO;

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
     * 判断是否为管理员
     */
    boolean isAdmin(User user);
}
