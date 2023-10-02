package com.w2.auth.service;


import com.w2.auth.util.AuthToken;

public interface AuthService {
    public AuthToken login(String username, String password, String clientId , String ClientSecret);
}
