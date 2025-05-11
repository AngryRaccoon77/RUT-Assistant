package org.example.diplomamainservice.Controller;

import org.example.diplomamainservice.Client.ChatServiceClient;
import org.example.diplomamainservice.Client.MessageServiceClient;
import org.example.diplomamainservice.Client.UserServiceClient;
import org.example.userservice.ui.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class GatewayController {

    private final UserServiceClient userClient;
    private final ChatServiceClient chatClient;
    private final MessageServiceClient messageClient;

    private static final Logger log = LoggerFactory.getLogger(GatewayController.class);

    @Autowired
    public GatewayController(
            UserServiceClient userClient,
            ChatServiceClient chatClient,
            MessageServiceClient messageClient) {
        this.userClient = userClient;
        this.chatClient = chatClient;
        this.messageClient = messageClient;
    }

//    // === PUBLIC ENDPOINTS ===
//
//    @GetMapping("/public/chats/all")
//    public GetAllChatsDTO getAllChatsPublic() {
//        return userClient.getAllChats();
//    }
//
//    // === SECURE ENDPOINTS ===
//
//    @GetMapping("/secure/user/{userId}")
//    public ResponseEntity<UserDTO> getUserById(Authentication authentication, @PathVariable Long userId) {
//        log.info("Получен запрос от пользователя: {}, Role: {}", authentication.getName(), authentication.getAuthorities());
//        return ResponseEntity.ok(userClient.getUserById(userId));
//    }

    @GetMapping("/secure/users/email/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userClient.getUserByEmail(email));
    }

    @PostMapping("/secure/users")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userClient.createUser(userDTO));
    }

    @GetMapping("/secure/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userClient.getAllUsers());
    }

    // --- Чаты ---

    @PostMapping("/secure/chats")
    public ResponseEntity<ChatDTO> createChat(@RequestBody ChatDTO chatDTO) {
        return ResponseEntity.ok(chatClient.createChat(chatDTO));
    }

    @GetMapping("/secure/chats")
    public ResponseEntity<List<ChatDTO>> getChatsByUser(@RequestParam("userId") Long userId) {
        return ResponseEntity.ok(chatClient.getChatsByUser(userId));
    }

    @GetMapping("/secure/chats/{chatId}")
    public ResponseEntity<ChatDTO> getChatById(@PathVariable("chatId") Long chatId) {
        return ResponseEntity.ok(chatClient.getChatById(chatId));
    }

    @DeleteMapping("/secure/chats/{chatId}")
    public ResponseEntity<Void> deleteChat(@PathVariable("chatId") Long chatId) {
        chatClient.deleteChat(chatId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/secure/messages")
    public ResponseEntity<MessageDTO> sendMessage(@RequestBody MessageDTO messageDTO) {
        return ResponseEntity.ok(messageClient.sendMessage(messageDTO));
    }

    @GetMapping("/secure/messages/{chatId}")
    public ResponseEntity<List<MessageDTO>> getMessagesByChat(@PathVariable("chatId") Long chatId) {
        return ResponseEntity.ok(messageClient.getMessagesByChat(chatId));
    }

    @GetMapping("/secure/hello")
    public String secureEndpoint(Authentication authentication) {
        log.info("Детали аутентификации: {}", authentication);
        return "Hello from SECURE endpoint (valid token required)! Authenticated as: " + authentication.getName();
    }
}