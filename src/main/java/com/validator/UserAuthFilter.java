package com.validator;

import com.beans.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
//@WebFilter("/*")
public class UserAuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session=((HttpServletRequest)request).getSession();
        User user=(User) session.getAttribute("user");
        String uri=((HttpServletRequest) request).getRequestURI();
        if(((HttpServletRequest) request).getRequestURI().endsWith("index.jsp") ||
                ((HttpServletRequest) request).getRequestURI().endsWith("login")|| user != null){
            chain.doFilter(request,response);
        }else{
            ((HttpServletResponse)response).sendRedirect("index.jsp");
        }
    }
}
