package com.example.ragchat.service;

import com.example.ragchat.entity.ChatSession;
import com.example.ragchat.entity.ChatMessage;
import java.util.List;

public interface ChatService {
    ChatSession createSession(String name);
    ChatSession getSessionById(Long sessionId);
    ChatMessage addMessage(Long sessionId, String sender, String content, String context);
    List<ChatMessage> getMessages(Long sessionId, int page, int size);
    void renameSession(Long sessionId, String newName);
    void markFavorite(Long sessionId, boolean favorite);
    void deleteSession(Long sessionId);
}
