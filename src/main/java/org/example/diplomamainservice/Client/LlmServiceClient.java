package org.example.diplomamainservice.Client;

import org.example.userservice.ui.dto.MessageDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "llm-service", url = "${llm.service.url}")
public interface LlmServiceClient {

    @PostMapping("/rag-process")
    String processWithRag(@RequestBody Map<String, String> request);
}
