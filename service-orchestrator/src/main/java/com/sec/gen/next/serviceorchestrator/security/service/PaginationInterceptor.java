package com.sec.gen.next.serviceorchestrator.security.service;

import com.sec.gen.next.serviceorchestrator.common.pagination.Pagination;
import com.sec.gen.next.serviceorchestrator.common.pagination.PaginationContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class PaginationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        PaginationContext.setPagination(new Pagination(
                request.getParameter("page"),
                request.getParameter("size"),
                request.getParameter("sort"),
                request.getParameter("order")
        ));

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        PaginationContext.clear();
    }
}
