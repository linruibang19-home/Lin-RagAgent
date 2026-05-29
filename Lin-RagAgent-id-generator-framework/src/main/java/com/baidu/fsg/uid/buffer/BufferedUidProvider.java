package com.baidu.fsg.uid.buffer;

import java.util.List;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: /
 * @author: Lin-RAGAgent
 **/

@FunctionalInterface
public interface BufferedUidProvider {

    List<Long> provide(long momentInSecond);
}
