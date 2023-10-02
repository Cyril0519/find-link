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
    private String id;    //用户ID
    private String name;  //用户名字
    private boolean accountExpired;
    private boolean accountLocked;
    private boolean credentialsExpired;
    private boolean disabled;
    private List<GrantedAuthority> authorities;


    public UserJwt(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public UserJwt(String username, String id, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.id = id;
    }

    public UserJwt(String id,
                   String username,
                   String password,
                   boolean enabled,
                   boolean accountNonExpired,
                   boolean accountNonLocked,
                   Collection<? extends GrantedAuthority> authorities
                   ) {
        super(username,password,enabled,accountNonExpired,true,accountNonLocked,authorities);
        this.id = id;
    }

    @Override
    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }
}
