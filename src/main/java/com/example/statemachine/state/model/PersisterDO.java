package com.example.statemachine.state.model;

import lombok.Data;

/**
 * @author sugar-pocket (2565477149@qq.com)
 * @date 2023/7/24 23:22
 */
@Data
public class PersisterDO {
    private String orderNumber;
    private String machineId;
    private String currentState;
}
