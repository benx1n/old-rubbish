package com.example.bean;

import java.io.Serializable;

/**
 * 计划实体类
 *
 * @author Administrator
 */
public class Plane implements Serializable {
    private int pid;
    private String content;
    private String time;
    private String title;
    private int progress;


    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public Plane(int pid, String content, String time, String title) {
        this.pid = pid;
        this.content = content;
        this.time = time;
        this.title = title;
    }

    public Plane() {
        super();
        // TODO Auto-generated constructor stub
    }

}
