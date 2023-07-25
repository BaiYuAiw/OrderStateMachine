package com.example.statemachine.state.manager;

import com.example.statemachine.state.model.StatusModelDO;

/**
 * @author sugar-pocket (2565477149@qq.com)
 * @date 2023/7/25 22:14
 */
public interface IStateMachineEventManager<E>{
    /**
     * 状态发生改变变更事件
     * @param statusModelDO
     * @param eventEnum
     * @param <R>
     * @return
     */
    <R> R sendStatusChangeEvent(StatusModelDO statusModelDO, E eventEnum);
}
