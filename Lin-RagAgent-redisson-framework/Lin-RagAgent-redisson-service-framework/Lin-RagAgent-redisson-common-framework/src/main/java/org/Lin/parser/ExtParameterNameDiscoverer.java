package org.Lin.parser;

import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.NativeDetector;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 对DefaultParameterNameDiscoverer进行扩展，添加{@link LocalVariableTableParameterNameDiscoverer}
 * @author: Lin-RAGAgent
 **/
public class ExtParameterNameDiscoverer extends DefaultParameterNameDiscoverer {

    public ExtParameterNameDiscoverer() {
        super();
        if (!NativeDetector.inNativeImage()) {
            addDiscoverer(new LocalVariableTableParameterNameDiscoverer());
        }
    }
}
