package com.example.ragchat.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitFilter extends OncePerRequestFilter {

    private static final long ALLOWED_REQUESTS = 100; // per minute
    private static final long TIME_WINDOW_MS = 60 * 1000;

    private final Map<String, UserRequestInfo> requestCounts = new ConcurrentHashMap<>();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String clientIp = request.getRemoteAddr();
        long currentTime = Instant.now().toEpochMilli();

        requestCounts.putIfAbsent(clientIp, new UserRequestInfo(0, currentTime));
        UserRequestInfo info = requestCounts.get(clientIp);

        if (currentTime - info.startTime > TIME_WINDOW_MS) {
            info.startTime = currentTime;
            info.requests = 0;
        }

        info.requests++;

        if (info.requests > ALLOWED_REQUESTS) {
            response.setStatus(org.springframework.http.HttpStatus.TOO_MANY_REQUESTS.value());
            response.getWriter().write("Rate limit exceeded. Try again later.");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private static class UserRequestInfo {
        long requests;
        long startTime;

        UserRequestInfo(long requests, long startTime) {
            this.requests = requests;
            this.startTime = startTime;
        }
    }
}
