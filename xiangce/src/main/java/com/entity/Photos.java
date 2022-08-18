package com.entity;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

/* 读取表结构信息 */
public class Photos {
    
    public Photos() {

    }
    
    /* 主键  */
    private Integer id;
    /* 相册封面图片  */
    private String image;
    /* 用户  */
    private Integer uid;
    /* 相册名称  */
    private String name;
    /* 描述  */
    private String dis;
    /* 创建时间  */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")        
    private Date createtime;
    
    public Integer getId() {
        return this.id;
    }
    public String getImage() {
        return this.image;
    }
    public Integer getUid() {
        return this.uid;
    }
    public String getName() {
        return this.name;
    }
    public String getDis() {
        return this.dis;
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
    public void setUid(Integer uid) {
        this.uid = uid;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDis(String dis) {
        this.dis = dis;
    }
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
  
}