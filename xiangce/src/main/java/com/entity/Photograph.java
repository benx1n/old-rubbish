package com.entity;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

/* 读取表结构信息 */
public class Photograph {
    
    public Photograph() {

    }
    
    /* 照片编号  */
    private Integer id;
    /* 照片  */
    private String image;
    /* 相册  */
    private Integer pid;
    /* 添加时间  */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")        
    private Date createtime;
    
    public Integer getId() {
        return this.id;
    }
    public String getImage() {
        return this.image;
    }
    public Integer getPid() {
        return this.pid;
    }
    public Date getCreatetime() {
        return this.createtime;
    }
   
    public void setId(Integer id) {
        this.id = id;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public void setPid(Integer pid) {
        this.pid = pid;
    }
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
  
}