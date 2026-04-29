package com.byteentropy.observability_core.web;

import io.micrometer.tracing.Tracer;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class TraceResponseFilter extends OncePerRequestFilter {
    private final Tracer tracer;

    public TraceResponseFilter(Tracer tracer) {
        this.tracer = tracer;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) 
            throws ServletException, IOException {
        var currentSpan = tracer.currentSpan();
        if (currentSpan != null) {
            res.addHeader("X-Trace-Id", currentSpan.context().traceId());
        }
        chain.doFilter(req, res);
    }
}