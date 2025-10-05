package com.example.ragchat.controller;

import com.example.ragchat.entity.ChatSession;
import com.example.ragchat.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sessions")
public class ChatSessionController {

    private final ChatService chatService;

    public ChatSessionController(ChatService chatService) {
        this.chatService = chatService;
    }
    @Operation(summary = "Create a new chat session", security = @SecurityRequirement(name = "X-API-KEY"))
    @PostMapping
    public ChatSession createSession(@RequestParam String name) {
        return chatService.createSession(name);
    }

    @Operation(summary = "Get chat session details by ID", security = @SecurityRequirement(name = "X-API-KEY"))
    @GetMapping("/{sessionId}")
    public ChatSession getSession(@PathVariable Long sessionId) {
        return chatService.getSessionById(sessionId);
    }

    @Operation(summary = "Rename a chat session", security = @SecurityRequirement(name = "X-API-KEY"))
    @PutMapping("/{sessionId}/rename")
    public void renameSession(@PathVariable Long sessionId, @RequestParam String newName) {
        chatService.renameSession(sessionId, newName);
    }

    @Operation(summary = "Mark or unmark a session as favorite", security = @SecurityRequirement(name = "X-API-KEY"))
    @PutMapping("/{sessionId}/favorite")
    public void markFavorite(@PathVariable Long sessionId, @RequestParam boolean favorite) {
        chatService.markFavorite(sessionId, favorite);
    }

    @Operation(summary = "Delete a chat session by ID", security = @SecurityRequirement(name = "X-API-KEY"))
    @DeleteMapping("/{sessionId}")
    public void deleteSession(@PathVariable Long sessionId) {
        chatService.deleteSession(sessionId);
    }
}
