package org.Lin.constant;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 常量类
 * @author: Lin-RAGAgent
 **/

public class Constant {

    public static final String PREFIX_DISTINCTION_NAME = "prefix.distinction.name";

    public static final String DEFAULT_PREFIX_DISTINCTION_NAME = "Lin-RagAgent";

    public static final String SPRING_INJECT_PREFIX_DISTINCTION_NAME = "${"+PREFIX_DISTINCTION_NAME+":"+DEFAULT_PREFIX_DISTINCTION_NAME+"}";

}
