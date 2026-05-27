package org.Lin.ai.controller;

import org.Lin.ai.service.DirectToolService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: 企业级别深度设计 AI Agent。 
 * @description: 控制层
 * @author: Lin-RAGAgent
 **/
@RestController
@RequestMapping("/test")
public class TestController {
    
    private DirectToolService directToolService;

    public TestController(DirectToolService directToolService) {
        this.directToolService = directToolService;
    }

    @GetMapping("/chat")
    public String chat() {
        return directToolService.checkAttendance("0001","04:10");
    }

}
