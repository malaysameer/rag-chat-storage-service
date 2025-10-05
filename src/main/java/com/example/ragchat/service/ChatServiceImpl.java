package com.example.ragchat.service;

import com.example.ragchat.entity.ChatSession;
import com.example.ragchat.entity.ChatMessage;
import com.example.ragchat.repository.ChatSessionRepository;
import com.example.ragchat.repository.ChatMessageRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {
    private final ChatSessionRepository sessionRepo;
    private final ChatMessageRepository messageRepo;

    public ChatServiceImpl(ChatSessionRepository sessionRepo, ChatMessageRepository messageRepo) {
        this.sessionRepo = sessionRepo;
        this.messageRepo = messageRepo;
    }

    @Override
    public ChatSession createSession(String name) {
        ChatSession session = new ChatSession();
        session.setName(name);
        session.setCreatedAt(LocalDateTime.now());
        session.setFavorite(false);
        return sessionRepo.save(session);
    }

    @Override
    public ChatSession getSessionById(Long sessionId) {
        return sessionRepo.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));
    }


    @Override
    public ChatMessage addMessage(Long sessionId, String sender, String content, String context) {
        ChatSession session = sessionRepo.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));
        ChatMessage msg = new ChatMessage();
        msg.setSender(sender);
        msg.setContent(content);
        msg.setContext(context);
        msg.setCreatedAt(LocalDateTime.now());
        msg.setSession(session);
        return messageRepo.save(msg);
    }

    @Override
    public List<ChatMessage> getMessages(Long sessionId, int page, int size) {
        //return messageRepo.findAll(); // TODO: Replace with pagination
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return messageRepo.findBySessionId(sessionId, pageable).getContent();
    }

    @Override
    public void renameSession(Long sessionId, String newName) {
        ChatSession session = sessionRepo.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));
        session.setName(newName);
        sessionRepo.save(session);
    }

    @Override
    public void markFavorite(Long sessionId, boolean favorite) {
        ChatSession session = sessionRepo.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));
        session.setFavorite(favorite);
        sessionRepo.save(session);
    }

    @Override
    public void deleteSession(Long sessionId) {
        sessionRepo.deleteById(sessionId);
    }
}
