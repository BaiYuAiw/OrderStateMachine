package com.example.statemachine.state.enums;

import lombok.AllArgsConstructor;

/**
 * @author sugar-pocket (2565477149@qq.com)
 * @date 2023/7/24 23:25
 */
@AllArgsConstructor
public enum OrderChangeEventEnum {
    CREATE_ORDER("创建订单"),
    PAY_ORDER("支付订单"),
    PAY_CANCEL("取消支付"),
    SEND_GOODS("发货"),
    RECEIVE_GOODS("收货"),
    ;
    private final String name;
}
