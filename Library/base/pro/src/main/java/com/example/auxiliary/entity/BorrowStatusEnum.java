package com.example.auxiliary.entity;

public enum BorrowStatusEnum {

    NOTTOBORROW("0", "未借阅"),
    BORROWING("1", "借阅中"),
    OVERDUE("2", "逾期"),
    OVERDUEBORROW("3", "逾期归还"),
    BORROW("4", "已归还"),
    SIT("5", "座位提醒"),
    LIBRARY("6", "图书归还提醒"),
    WAITERBORROW("7","待确认归还");
    private final String value;
    private final String name;

    private BorrowStatusEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
