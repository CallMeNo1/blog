package com.xuwei.blog.service.impl;


import com.xuwei.blog.dao.UserDao;
import com.xuwei.blog.pojo.User;
import com.xuwei.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User checkUser(String username, String password) {
//        User user = userDao.queryByUsernameAndPassword(username, MD5Utils.code(password));
        User user = userDao.queryByUsernameAndPassword(username, password);
        return user;
    }
}
