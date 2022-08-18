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
public class SitReservation {
    private String id;
    private String userId;
    private String sitId;
    private LocalDate reservationDatetime;
    @TableField(exist = false)
    private String status;
    @TableField(exist = false)
    private String date;

}
