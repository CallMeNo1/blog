package com.xuwei.blog.service;


import com.xuwei.blog.pojo.User;

public interface UserService {

    public User checkUser(String username, String password);
}
