package com.example.auxiliary.entity;

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
public class Remind {
    private String id;
    private String type;
    private String content;
    private String status;
    private String userId;
}
