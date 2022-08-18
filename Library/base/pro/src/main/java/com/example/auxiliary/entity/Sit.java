package com.example.auxiliary.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author ztx
 * @date 2021-04-27 16:09
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class Sit {
    private String id;
    private String level;
    private String name;
    @TableField(exist = false)
    private String status;
}
