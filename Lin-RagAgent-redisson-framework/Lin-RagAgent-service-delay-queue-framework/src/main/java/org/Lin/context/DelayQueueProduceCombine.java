package org.Lin.context;

import org.Lin.core.DelayProduceQueue;
import org.Lin.core.IsolationRegionSelector;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 延迟队列 发送者 分片选择
 * @author: Lin-RAGAgent
 **/
public class DelayQueueProduceCombine {

    private final IsolationRegionSelector isolationRegionSelector;

    private final List<DelayProduceQueue> delayProduceQueueList = new ArrayList<>();

    public DelayQueueProduceCombine(DelayQueueBasePart delayQueueBasePart,String topic){
        Integer isolationRegionCount = delayQueueBasePart.getDelayQueueProperties().getIsolationRegionCount();
        isolationRegionSelector =new IsolationRegionSelector(isolationRegionCount);

        for(int i = 0; i < isolationRegionCount; i++) {
            delayProduceQueueList.add(new DelayProduceQueue(delayQueueBasePart.getRedissonClient(),topic + "-" + i));
        }
    }

    public void offer(String content,long delayTime, TimeUnit timeUnit){
        int index = isolationRegionSelector.getIndex();

        delayProduceQueueList.get(index).offer(content, delayTime, timeUnit);
    }
}
