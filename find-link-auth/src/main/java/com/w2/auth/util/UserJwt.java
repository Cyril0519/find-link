package com.w2.auth.util;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class UserJwt extends User {
    /*
    username – the username presented to the DaoAuthenticationProvider
    password – the password that should be presented to the DaoAuthenticationProvider
    enabled – set to true if the user is enabled
    accountNonExpired – set to true if the account has not expired
    credentialsNonExpired – set to true if the credentials have not expired
    accountNonLocked – set to true if the account is not locked
    authorities – the authorities that should be granted to the caller if they presented the correct username and password and the user is enabled. Not null.
    */
    private String uid;    //用户ID


    public UserJwt(String id,
                   String username,
                   String password,
                   boolean enabled,
                   boolean accountNonExpired,
                   boolean accountNonLocked,
                   Collection<? extends GrantedAuthority> authorities
                   ) {
        super(username,password,enabled,accountNonExpired,true,accountNonLocked,authorities);
        this.uid = id;
    }
}
