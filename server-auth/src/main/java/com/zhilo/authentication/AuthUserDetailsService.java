package com.zhilo.authentication;

import com.zhilo.common.model.AuthUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * UserDetailsService 实现，获取数据库用户信息 .
 *
 * @author BibiChen
 * @version v1.0
 * @since 2019/9/18
 */
@Service("userDetailsService")
public class AuthUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        // return User.withUsername(s)
        //         .password(passwordEncoder.encode("123456"))
        //         .authorities("/orders")
        //         .roles("ADMIN", "VIP")
        //         .build();

        AuthUser authUser = new AuthUser();
        authUser.setId(1L);
        authUser.setUsername(s);
        authUser.setPassword("{bcrypt}$2a$10$cr7OU9Igw5MwAk7xjFbRduvaykVauvauodcJ7jSGhB8maG.BVeiji");
        authUser.setEnabled(true);
        Set<GrantedAuthority> set = new HashSet<>();
        set.add(new SimpleGrantedAuthority(String.valueOf("/aaa")));
        set.add(new SimpleGrantedAuthority(String.valueOf("/bbb")));

        authUser.setAuthorities(set);
        return authUser;
    }


}
