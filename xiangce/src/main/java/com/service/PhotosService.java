package com.service;

import com.entity.Photos;
import org.springframework.beans.factory.annotation.Autowired;
import com.dao.PhotosDao;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.InitializingBean;

@Service
public class PhotosService extends BaseService<Photos,Integer> implements InitializingBean {
    
    @Autowired
    private PhotosDao photosDao;



    public void afterPropertiesSet() throws Exception {
        super.baseDao =  photosDao;;
    }

    
}