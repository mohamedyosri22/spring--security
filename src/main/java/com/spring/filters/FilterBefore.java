package com.spring.filters;

import jakarta.servlet.*;

import java.io.IOException;

public class FilterBefore implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Inside filter before");
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
