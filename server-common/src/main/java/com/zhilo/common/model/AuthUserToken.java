package com.zhilo.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Set;

/**
 * 认证用户信息 .
 *
 * @author BibiChen
 * @version v1.0
 * @since 2021/2/9
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthUserToken implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private Set<GrantedAuthority> authorities;

    public AuthUserToken(AuthUser authUser) {
        this.id = authUser.getId();
        this.username = authUser.getUsername();
        this.authorities = authUser.getAuthorities();
    }

}
