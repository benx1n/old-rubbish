package com.example.auxiliary.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

/**
 * @author ztx
 * @date 2021-04-27 16:09
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class UserLibrary {
    private String id;
    private String libraryId;
    private String userId;
    private LocalDate startDatetime;
    private LocalDate endDatetime;
    private String status;
    private String remindStatus;
    @TableField(exist = false)
    private Library library;
    private Double price;

}
