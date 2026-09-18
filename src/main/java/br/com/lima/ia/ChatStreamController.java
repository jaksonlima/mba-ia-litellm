package br.com.lima.ia;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class ChatStreamController {

    private final ChatClient chat;

    public ChatStreamController(ChatClient.Builder builder) {
        this.chat = builder.build();
    }

    @GetMapping(value = "/ask/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> stream(@RequestParam String q) {
        return chat.prompt(q).stream().content();
    }
}
