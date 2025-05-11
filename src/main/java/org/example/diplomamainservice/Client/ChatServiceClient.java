package org.example.diplomamainservice.Client;

import org.example.userservice.ui.dto.ChatDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "chat-service", url = "http://localhost:8083")
public interface ChatServiceClient {

    @PostMapping(path = "/api/chats", consumes = MediaType.APPLICATION_JSON_VALUE)
    ChatDTO createChat(@RequestBody ChatDTO chatDTO);

    @GetMapping(path = "/api/chats")
    List<ChatDTO> getChatsByUser(@RequestParam("userId") Long userId);

    @GetMapping(path = "/api/chats/{chatId}")
    ChatDTO getChatById(@PathVariable("chatId") Long chatId);

    @DeleteMapping(path = "/api/chats/{chatId}")
    void deleteChat(@PathVariable("chatId") Long chatId);
}
