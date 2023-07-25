package com.example.statemachine.state.builder;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author sugar-pocket (2565477149@qq.com)
 * @date 2023/7/25 20:52
 */
@Component
public class StateMachineBuildFactory<S, E> implements ApplicationContextAware {

    @Autowired
    private BeanFactory beanFactory;
    /**
     * 用来存储builder-name及builder的map
     */
    public static final Map<String, IStateMachineBuilder> builderMap = new ConcurrentHashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        String[] beanNamesForType = applicationContext.getBeanNamesForType(IStateMachineBuilder.class);
        for (String beanName : beanNamesForType) {
            IStateMachineBuilder bean = (IStateMachineBuilder) applicationContext.getBean(beanName);
            builderMap.put(bean.getName(), bean);
        }
    }

    public StateMachine<S, E> createStateMachine(String machineType) throws Exception{
        if (StringUtils.isBlank(org.apache.commons.lang3.StringUtils.trim(machineType))) {
            throw new RuntimeException("无效的状态机类型");
        }
        IStateMachineBuilder builder = builderMap.get(machineType);
        StateMachine<S, E> stateMachine;
        try {
            stateMachine = builder.build(beanFactory);
        } catch (Exception e) {
            throw new RuntimeException("创建状态机异常");
        }

        return stateMachine;
    }
}
