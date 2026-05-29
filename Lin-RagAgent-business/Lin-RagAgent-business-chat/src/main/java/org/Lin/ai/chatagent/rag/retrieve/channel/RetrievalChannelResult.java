package org.Lin.ai.chatagent.rag.retrieve.channel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.ai.document.Document;

import java.util.List;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 结果对象
 * @author: Lin-RAGAgent
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RetrievalChannelResult {

    private String channelName;

    private List<Document> documents;
}
