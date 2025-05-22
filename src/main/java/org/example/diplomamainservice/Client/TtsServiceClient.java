package org.example.diplomamainservice.Client;

import org.example.diplomamainservice.Dto.TtsRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "tts-service", url = "${tts.service.url}")
public interface TtsServiceClient {

    @PostMapping("/generate-speech")
    ResponseEntity<byte[]> generateSpeech(@RequestBody TtsRequest request);
}