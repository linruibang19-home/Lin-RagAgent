package com.baidu.fsg.uid.worker;

import com.baidu.fsg.uid.utils.ValuedEnum;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 枚举定义
 * @author: Lin-RAGAgent
 **/

public enum WorkerNodeType implements ValuedEnum<Integer> {

    CONTAINER(1), ACTUAL(2);

    private final Integer type;

    private WorkerNodeType(Integer type) {
        this.type = type;
    }

    @Override
    public Integer value() {
        return type;
    }

}
