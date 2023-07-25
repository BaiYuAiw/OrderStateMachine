package com.example.statemachine.state.manager;

import com.example.statemachine.state.builder.StateMachineBuildFactory;
import com.example.statemachine.state.model.PersisterDO;
import com.example.statemachine.state.model.StateMachineConstants;
import com.example.statemachine.state.model.StatusModelDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineUtils;
import org.springframework.statemachine.transition.Transition;
import org.springframework.statemachine.trigger.DefaultTriggerContext;
import org.springframework.statemachine.trigger.Trigger;

/**
 * @author sugar-pocket (2565477149@qq.com)
 * @date 2023/7/25 22:21
 */
public abstract class AbstractStateMachineEventManager<S, E> implements IStateMachineEventManager<E> {
    @Autowired
    private StateMachineBuildFactory<S, E> stateMachineBuildFactory;

    //用于状态机上下文持久化
    public abstract void stateMachinePersist(StateMachine<S, E> stateMachine, PersisterDO persisterDO) throws Exception;

    //用于状态机上下文初始化
    public abstract StateMachine<S, E> stateMachineRestore(StateMachine<S, E> stateMachine, PersisterDO persisterDO) throws Exception;

    @Override
    @SuppressWarnings("unchecked")
    public <R> R sendStatusChangeEvent(StatusModelDO statusModelDO, E eventEnum) {
        //初始化当前状态机
        StateMachine<S, E> stateMachine = initializeMachine(statusModelDO);
        //执行状态机事件
        Boolean result = statusChangeExecute(stateMachine, statusModelDO, eventEnum);
        if (!result) {
            throw new RuntimeException("状态机状态执行失败");
        }
        //执行过程异常处理
        RuntimeException exception = (RuntimeException) stateMachine.getExtendedState().getVariables().get(RuntimeException.class);
        if (exception != null) {
        }
        try {
            //持久化状态机
            PersisterDO persisterDO = new PersisterDO();
            persisterDO.setOrderNumber(statusModelDO.getOrderNumber());
            stateMachinePersist(stateMachine, persisterDO);
        } catch (Exception e) {
            throw new RuntimeException("状态机持久化失败");
        }

        return (R) stateMachine.getExtendedState().getVariables().get(StateMachineConstants.RETURN_PARAM);
    }

    /**
     * 获取状态机对象
     *
     * @param statusModelDO 状态模型对象
     * @return 状态机
     */
    private StateMachine<S, E> initializeMachine(StatusModelDO statusModelDO) {
        //构造状态机模板
        StateMachine<S, E> stateMachine;
        try {
            //构造初始化状态机
            StateMachine<S, E> srcStateMachine = stateMachineBuildFactory.createStateMachine(getStateMachineType());
            //构造当前执行状态
            PersisterDO persisterDO = new PersisterDO();
            persisterDO.setOrderNumber(statusModelDO.getOrderNumber());
            persisterDO.setCurrentState(statusModelDO.getCurrentState());
            //恢复当前状态机的上下文内容
            stateMachine = stateMachineRestore(srcStateMachine, persisterDO);
        } catch (Exception e) {
            throw new RuntimeException("初始化状态机失败");
        }
        if (stateMachine == null) {
            throw new RuntimeException("没有找到可用的状态机");
        }

        return stateMachine;
    }

    private boolean statusChangeExecute(StateMachine<S, E> stateMachine, StatusModelDO statusModelDO, E eventEnum) {

        //构建消息对象
        Message<E> eventMsg = MessageBuilder.withPayload(eventEnum)
                .setHeader(StateMachineConstants.STATE_MODEL_DTO, statusModelDO).build();
        if (!acceptEvent(stateMachine, eventMsg)) {
            throw new RuntimeException("找不到对应状态机事件触发定义");
        }

        return stateMachine.sendEvent(eventMsg);
    }

    /**
     * 事件执行条件判断
     */
    private static <S, E> boolean acceptEvent(StateMachine<S, E> stateMachine, Message<E> eventMsg) {
        //获取当前状态
        State<S, E> currentState = stateMachine.getState();

        for (Transition<S, E> transition : stateMachine.getTransitions()) {
            State<S, E> source = transition.getSource();
            Trigger<S, E> trigger = transition.getTrigger();
            if (currentState != null && trigger != null &&
                    StateMachineUtils.containsAtleastOne(source.getIds(), currentState.getIds()) &&
                    trigger.evaluate(new DefaultTriggerContext<>(eventMsg.getPayload()))) {
                return true;
            }
        }

        return false;
    }

    /**
     * 获取状态机类型
     *
     * @return 状态机类型
     */
    public abstract String getStateMachineType();
}
