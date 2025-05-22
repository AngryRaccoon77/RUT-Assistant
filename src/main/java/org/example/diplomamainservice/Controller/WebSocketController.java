package org.example.diplomamainservice.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.diplomamainservice.Client.LlmServiceClient;
import org.example.diplomamainservice.Client.MessageServiceClient;
import org.example.diplomamainservice.Client.TtsServiceClient;
import org.example.diplomamainservice.Dto.TtsRequest;
import org.example.userservice.domain.model.Enum.Role;
import org.example.userservice.ui.dto.MessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Base64;
import java.util.Collections;
import java.util.Map;

@Controller
public class WebSocketController {

    private static final Logger log = LoggerFactory.getLogger(WebSocketController.class);
    @Autowired
    private MessageServiceClient messageServiceClient;

    @Autowired
    private LlmServiceClient llmServiceClient;

    @Autowired
    private TtsServiceClient ttsServiceClient;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload MessageDTO message) {
        messageServiceClient.sendMessage(message);
        log.info(message.toString());
        messagingTemplate.convertAndSend("/topic/public", message);

        if (Role.USER.equals(message.getRole())) {
            try {
                Map<String, String> ragRequest = Map.of(
                        "query", message.getMessage(),
                        "chat_id", message.getChatId().toString()
                );
                String llmResponseJson = llmServiceClient.processWithRag(ragRequest);

                log.info("LLM Response: {}", llmResponseJson);

                Map<String, String> llmResponse = new ObjectMapper().readValue(llmResponseJson, Map.class);
                String botResponse = llmResponse.getOrDefault("response", "Ошибка при получении ответа");

                MessageDTO botMessage = new MessageDTO(null, botResponse, Role.BOT, message.getChatId());

                TtsRequest ttsRequest = new TtsRequest();
                ttsRequest.setText(botResponse);

                ResponseEntity<byte[]> audioResponse = ttsServiceClient.generateSpeech(ttsRequest);
                log.error("TTS service: {}", audioResponse);

                if (audioResponse.getStatusCode().is2xxSuccessful() && audioResponse.hasBody()) {
                    String audioBase64 = Base64.getEncoder().encodeToString(audioResponse.getBody());
                    log.error("TTS service error: {}", audioResponse);
                    botMessage.setAudioData(audioBase64);
                } else {
                    log.error("TTS service error: {}", audioResponse.getStatusCode());
                }

                log.info(botMessage.getChatId().toString() + " " + botMessage.getRole());
                log.info(botResponse + "------------------------------------------");

                messageServiceClient.sendMessage(botMessage);

                messagingTemplate.convertAndSend("/topic/public", botMessage);

            } catch (Exception e) {
                log.error("Ошибка при обработке запроса к LLM сервису:", e);

                String errorMessage = "Ошибка при обработке запроса: " + e.getMessage();

                MessageDTO errorBotMessage = new MessageDTO(null, errorMessage, Role.BOT, message.getChatId());

                messagingTemplate.convertAndSend("/topic/public", errorBotMessage);
            }
        }
    }
}