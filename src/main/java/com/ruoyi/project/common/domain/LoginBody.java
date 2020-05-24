package com.ruoyi.project.common.domain;

import com.ruoyi.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户登录对象
 *
 * @author wangdong
 * @date 2020-04-27
 */
@Getter
@Setter
@ApiModel(value = "用户登录对象", description = "用户登录对象")
public class LoginBody {

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名",example = "\"lihaoapp1\"")
    private String username;

    /**
     * 用户密码
     */
    @ApiModelProperty(value = "用户密码",example = "\"12345678\"")
    private String password;

    /**
     * 用户类型（01：后台端用户；02：手机端用户）
     */
    @ApiModelProperty(value = "用户类型（01：后台端用户；02：手机端用户）",example = "\"02\"")
    private String userType;

}
