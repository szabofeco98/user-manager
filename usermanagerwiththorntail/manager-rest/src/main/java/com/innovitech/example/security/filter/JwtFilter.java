package com.innovitech.example.security.filter;


import com.innovitech.example.services.JwtTokenProviderService;

import javax.inject.Inject;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

import java.io.IOException;



@WebFilter(urlPatterns = "/*")
public class JwtFilter extends HttpFilter {

    @Inject
    private JwtTokenProviderService jwtTokenProvider;

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String url = req.getRequestURL().toString();
        String endPoint = url.substring(url.lastIndexOf('/'));
        if (req.getMethod().equals("OPTIONS")) {
            super.doFilter(req, res, chain);
            return;
        }

        if (!endPoint.equals("/login")) {
            String token = req.getHeader("Authorization");
            if (!jwtTokenProvider.validateToken(token)) {
                res.sendError(403);
            }
        }
        super.doFilter(req, res, chain);
    }
}

