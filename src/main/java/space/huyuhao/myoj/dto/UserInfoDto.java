package space.huyuhao.myoj.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserInfoDto {
    private Long id;
    private String username;
    private String email;
    private String avatar;
    private String profile;
    private String role;
    private LocalDateTime createTime;
    private Integer status;
}
