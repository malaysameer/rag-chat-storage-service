package com.example.ragchat.controller;

import com.example.ragchat.entity.ChatMessage;
import com.example.ragchat.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sessions/{sessionId}/messages")
public class ChatMessageController {

    private final ChatService chatService;

    public ChatMessageController(ChatService chatService) {
        this.chatService = chatService;
    }

    @Operation(summary = "Add a message to a chat session", security = @SecurityRequirement(name = "X-API-KEY"))
    @PostMapping
    public ChatMessage addMessage(@PathVariable Long sessionId,
                                  @RequestParam String sender,
                                  @RequestParam String content,
                                  @RequestParam(required = false) String context) {
        return chatService.addMessage(sessionId, sender, content, context);
    }

    @Operation(summary = "Get paginated messages for a session", security = @SecurityRequirement(name = "X-API-KEY"))
    @GetMapping
    public List<ChatMessage> getMessages(@PathVariable Long sessionId,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size) {
        return chatService.getMessages(sessionId, page, size);
    }
}
