package org.Lin.ai.chatagent.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import reactor.core.publisher.Flux;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 服务层
 * @author: Lin-RAGAgent
 **/

@Data
@AllArgsConstructor
public class BootstrapResult {

    private final Flux<String> outbound;

    private final String rejectionMessage;

    public static BootstrapResult ready(Flux<String> outbound) {
        return new BootstrapResult(outbound, "");
    }

    public static BootstrapResult rejected(String rejectionMessage) {
        return new BootstrapResult(Flux.empty(), rejectionMessage);
    }
}
