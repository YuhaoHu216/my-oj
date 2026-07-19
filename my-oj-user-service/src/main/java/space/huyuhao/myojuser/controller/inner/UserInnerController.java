package space.huyuhao.myojuser.controller.inner;

import org.springframework.web.bind.annotation.*;
import space.huyuhao.myojclient.service.UserFeignClient;
import space.huyuhao.myojmodel.model.entity.User;
import space.huyuhao.myojuser.service.UserService;
import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/inner")
public class UserInnerController implements UserFeignClient {

    @Resource
    private UserService userService;

    @Override
    @GetMapping("/get/id")
    public User getById(@RequestParam("userId") long userId) {
        return userService.getById(userId);
    }

    @Override
    @GetMapping("/get/ids")
    public List<User> listByIds(@RequestParam("idList") Collection<Long> idList) {
        return userService.listByIds(idList);
    }
}
