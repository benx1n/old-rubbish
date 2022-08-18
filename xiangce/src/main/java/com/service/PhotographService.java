package com.service;

import com.entity.Photograph;
import org.springframework.beans.factory.annotation.Autowired;
import com.dao.PhotographDao;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.InitializingBean;

@Service
public class PhotographService extends BaseService<Photograph,Integer> implements InitializingBean {
    
    @Autowired
    private PhotographDao photographDao;



    public void afterPropertiesSet() throws Exception {
        super.baseDao =  photographDao;;
    }

    
}