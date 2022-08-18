package com.example.auxiliary.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * @author ztx
 * @date 2021-04-27 16:09
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class Library {
    private String id;
    private String imageUrl;
    private String author;
    private String catory;
    private String intro;
    private String name;
    private String status;
    private String press;
    @TableField(exist = false)
    private List<String> tags;
    @TableField(exist = false)
    private String search;
    @TableField(exist = false)
    private String date;

}
