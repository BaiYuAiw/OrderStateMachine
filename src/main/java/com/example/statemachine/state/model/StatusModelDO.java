package com.example.statemachine.state.model;

import lombok.Data;

/**
 * @author sugar-pocket (2565477149@qq.com)
 * @date 2023/7/25 21:39
 */
@Data
public class StatusModelDO {
    private String orderNumber;
    private String currentState;
    private String goodsNumber;
}
