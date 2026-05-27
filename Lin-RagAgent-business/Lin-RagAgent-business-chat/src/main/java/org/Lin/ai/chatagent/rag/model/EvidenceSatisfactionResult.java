package org.Lin.ai.chatagent.rag.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 证据满足度校验结果
 * @author: Lin-RAGAgent
 **/

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EvidenceSatisfactionResult<T> {

    private boolean satisfied;

    @Builder.Default
    private List<String> notes = new ArrayList<>();

    @Builder.Default
    private List<T> acceptedEvidence = new ArrayList<>();
}
