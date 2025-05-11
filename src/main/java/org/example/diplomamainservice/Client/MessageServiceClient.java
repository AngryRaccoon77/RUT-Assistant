package org.example.diplomamainservice.Client;

import org.example.userservice.ui.dto.MessageDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "message-service", url = "http://localhost:8083")
public interface MessageServiceClient {

    @PostMapping(path = "/api/messages", consumes = MediaType.APPLICATION_JSON_VALUE)
    MessageDTO sendMessage(@RequestBody MessageDTO messageDTO);

    @GetMapping(path = "/api/messages/{chatId}")
    List<MessageDTO> getMessagesByChat(@PathVariable("chatId") Long chatId);
}
