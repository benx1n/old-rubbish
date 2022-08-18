package com.service;

import com.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import com.dao.UserDao;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.InitializingBean;

@Service
public class UserService extends BaseService<User,Integer> implements InitializingBean {
    
    @Autowired
    private UserDao userDao;



    public void afterPropertiesSet() throws Exception {
        super.baseDao =  userDao;;
    }

    
}