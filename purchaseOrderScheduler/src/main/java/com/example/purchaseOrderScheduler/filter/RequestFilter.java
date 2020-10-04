package com.example.purchaseOrderScheduler.filter;

import javax.servlet.*;
import java.io.IOException;

public class RequestFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
            //any request specific checks/ Last mile auth etc
    }

    @Override
    public void destroy() {

    }
}
