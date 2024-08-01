package com.sec.gen.next.chatservice.websocket;

import com.next.gen.sec.model.UserModel;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.util.WebUtils;

import java.util.Map;

@Slf4j
@Service
public class HttpSessionHandshakeInterceptor implements HandshakeInterceptor {

    private final UserServiceClient userServiceClient;

    public HttpSessionHandshakeInterceptor(@Lazy UserServiceClient userServiceClient) {
        this.userServiceClient = userServiceClient;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        if (request instanceof ServletServerHttpRequest servletServerRequest) {
            HttpServletRequest servletRequest = servletServerRequest.getServletRequest();
            Cookie accessToken = WebUtils.getCookie(servletRequest, "access_token");

            if(accessToken == null)
                return false;

            UserModel principal = userServiceClient.getAccessToken("Bearer " + accessToken.getValue());
            attributes.put("PRINCIPAL", principal);
        }

        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {}
}
