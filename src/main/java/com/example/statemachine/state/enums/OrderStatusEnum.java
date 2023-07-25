package com.example.statemachine.state.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author sugar-pocket (2565477149@qq.com)
 * @date 2023/7/24 23:24
 */
@AllArgsConstructor
@Getter
public enum OrderStatusEnum {
    CREATE("新建"),
    WAIT_PAYMENT("待付款"),
    WAIT_SEND("待发货"),
    WAIT_RECEIVE("待收货"),
    COMPLETED("已完成"),
    CANCEL("取消");

    private final String name;
}
