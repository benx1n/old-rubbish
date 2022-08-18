package com.example.auxiliary.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * @author ztx
 * @date 2021-02-21 13:31
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class User {

    private String id;
    private String gender;
    private String nickName;
    private String province;
    private String openId;
    private String avatarUrl;
    private String type;
    private String status;
    private String name;
    private String grade;
    private String studentId;
}
