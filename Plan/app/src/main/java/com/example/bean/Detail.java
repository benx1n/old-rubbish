package com.example.bean;

import java.io.Serializable;

/**
 * 手账实体类
 *
 * @author Administrator
 */
public class Detail implements Serializable {
    private int pid;
    private int did;
    private String image;
    private String content;
    private int progress;


    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }




    public Detail(int did, int pid, String image, String content, int progress) {
        this.pid = pid;
        this.did = did;
        this.image = image;
        this.content = content;
        this.progress = progress;
    }

    public Detail() {
        super();
        // TODO Auto-generated constructor stub
    }

}
