package com.zhilo.usercenter.controller.user;

import com.zhilo.common.model.AuthUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户控制器 .
 *
 * @author BibiChen
 * @version v1.0
 * @since 2020-02-05
 */
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/{id}")
    public AuthUser findById(@PathVariable Integer id) {
        log.info("我被请求了...");
        AuthUser user = new AuthUser();
        user.setId(1L);
        user.setUsername("admin");
        return user;
    }

}
