package org.Lin.ai.chatagent.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.Lin.ai.chatagent.dto.ChatRequestDto;
import org.Lin.ai.chatagent.dto.ConversationExchangeDetailQueryDto;
import org.Lin.ai.chatagent.dto.ConversationIdentityDto;
import org.Lin.ai.chatagent.dto.ConversationSessionListQueryDto;
import org.Lin.ai.chatagent.dto.RetrievalObserveQueryDto;
import org.Lin.ai.chatagent.model.ChannelExecutionView;
import org.Lin.ai.chatagent.model.ConversationExchangeDetailView;
import org.Lin.ai.chatagent.model.ConversationMemorySummaryView;
import org.Lin.ai.chatagent.model.ConversationSessionView;
import org.Lin.ai.chatagent.model.KnowledgeDocumentOptionView;
import org.Lin.ai.chatagent.model.RetrievalResultView;
import org.Lin.ai.chatagent.model.StageBenchmarkView;
import org.Lin.ai.chatagent.service.BusinessChatService;
import org.Lin.ai.chatagent.vo.ConversationResetVo;
import org.Lin.ai.chatagent.vo.ConversationSessionListVo;
import org.Lin.ai.chatagent.vo.ConversationStopVo;
import org.Lin.common.ApiResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 控制层
 * @author: Lin-RAGAgent
 **/
@AllArgsConstructor
@RestController
@RequestMapping("/api/chat")
public class BusinessChatController {

    private final BusinessChatService businessChatService;

    @PostMapping(value = "/stream", produces = "text/event-stream;charset=UTF-8")
    public Flux<String> stream(@Valid @RequestBody ChatRequestDto dto) {
        return businessChatService.openConversationStream(dto);
    }

    @PostMapping("/document/options")
    public ApiResponse<List<KnowledgeDocumentOptionView>> documentOptions() {
        return ApiResponse.ok(businessChatService.listKnowledgeDocumentOptions());
    }

    @PostMapping("/session/stop")
    public ApiResponse<ConversationStopVo> stop(@Valid @RequestBody ConversationIdentityDto dto) {
        return ApiResponse.ok(businessChatService.stopConversation(dto.getConversationId()));
    }

    @PostMapping("/session/detail")
    public ApiResponse<ConversationSessionView> session(@Valid @RequestBody ConversationIdentityDto dto) {
        return ApiResponse.ok(businessChatService.getSession(dto.getConversationId()));
    }

    @PostMapping("/exchange/detail")
    public ApiResponse<ConversationExchangeDetailView> exchange(@Valid @RequestBody ConversationExchangeDetailQueryDto dto) {
        return ApiResponse.ok(businessChatService.getExchangeDetail(dto.getConversationId(), dto.getExchangeId()));
    }

    @PostMapping("/session/list")
    public ApiResponse<ConversationSessionListVo> sessions(@RequestBody(required = false) ConversationSessionListQueryDto dto) {
        return ApiResponse.ok(businessChatService.listSessions(dto));
    }

    @PostMapping("/session/reset")
    public ApiResponse<ConversationResetVo> reset(@Valid @RequestBody ConversationIdentityDto dto) {
        return ApiResponse.ok(businessChatService.resetConversation(dto.getConversationId()));
    }

    @PostMapping("/session/summary/rebuild")
    public ApiResponse<ConversationMemorySummaryView> rebuildSummary(@Valid @RequestBody ConversationIdentityDto dto) {
        return ApiResponse.ok(businessChatService.rebuildConversationSummary(dto.getConversationId()));
    }

    @PostMapping("/exchange/retrieval/results")
    public ApiResponse<List<RetrievalResultView>> retrievalResults(@Valid @RequestBody RetrievalObserveQueryDto dto) {
        return ApiResponse.ok(businessChatService.getRetrievalResults(dto.getConversationId(), Long.parseLong(dto.getExchangeId())));
    }

    @PostMapping("/exchange/channel/executions")
    public ApiResponse<List<ChannelExecutionView>> channelExecutions(@Valid @RequestBody RetrievalObserveQueryDto dto) {
        return ApiResponse.ok(businessChatService.getChannelExecutions(dto.getConversationId(), Long.parseLong(dto.getExchangeId())));
    }

    @PostMapping("/stage/benchmarks")
    public ApiResponse<List<StageBenchmarkView>> stageBenchmarks() {
        return ApiResponse.ok(businessChatService.getStageBenchmarks());
    }
}
