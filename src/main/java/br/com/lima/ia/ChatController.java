package br.com.lima.ia;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    private final ChatClient chat;

    public ChatController(ChatClient.Builder builder) {
        this.chat = builder.build();
    }

    @GetMapping("/ask")
    public String ask(@RequestParam String q) {
        return chat.prompt(q).call().content();
    }
}
