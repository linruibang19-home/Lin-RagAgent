package org.Lin.ai.controller;

import org.Lin.ai.service.SpringAiAlibabaAgentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: 企业级别深度设计 AI Agent。 
 * @description: 控制层
 * @author: Lin-RAGAgent
 **/
@RestController
@RequestMapping("/agent")
public class SimpleAgentController {

    private final SpringAiAlibabaAgentService agentService;

    public SimpleAgentController(SpringAiAlibabaAgentService agentService) {
        this.agentService = agentService;
    }

    @GetMapping("/hello")
    public String hello(
        @RequestParam(value = "question", defaultValue = "你好，请用三点介绍一下 Spring AI Alibaba ReactAgent 的作用")
        String question) {
        return this.agentService.simpleReply(question);
    }

}
