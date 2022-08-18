package com.entity;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

/* 读取表结构信息 */
public class User {
    
    public User() {

    }
    
    /* 编号  */
    private Integer id;
    /* 用户名  */
    private String username;
    /* 密码  */
    private String password;
    /* 邮箱  */
    private String email;
    /* 地址  */
    private String address;
    /* 联系方式  */
    private String phone;
    /* 姓名  */
    private String realname;
    /* 身份  */
    private Integer type;
    /* 是否禁用  */
    private Integer state;
    /*   */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")        
    private Date createtime;
    
    public Integer getId() {
        return this.id;
    }
    public String getUsername() {
        return this.username;
    }
    public String getPassword() {
        return this.password;
    }
    public String getEmail() {
        return this.email;
    }
    public String getAddress() {
        return this.address;
    }
    public String getPhone() {
        return this.phone;
    }
    public String getRealname() {
        return this.realname;
    }
    public Integer getType() {
        return this.type;
    }
    public Integer getState() {
        return this.state;
    }
    public Date getCreatetime() {
        return this.createtime;
    }
   
    public void setId(Integer id) {
        this.id = id;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void setRealname(String realname) {
        this.realname = realname;
    }
    public void setType(Integer type) {
        this.type = type;
    }
    public void setState(Integer state) {
        this.state = state;
    }
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
  
}