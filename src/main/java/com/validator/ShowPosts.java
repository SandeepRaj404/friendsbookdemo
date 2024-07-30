package com.validator;

import com.beans.JsonConverter;
import com.beans.Misc;
import com.beans.User;
import com.beans.UserPost;
import com.dbutils.PostDAO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.response.beans.ShowPostResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/fetchpost")
public class ShowPosts extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");
        ShowPostResponse response = new ShowPostResponse();
        if(user == null) {
            resp.sendRedirect("index.jsp");
        }else{
            final int limit =10 ;
            int offset = Integer.parseInt(req.getParameter("offset")); ;
            PostDAO.getPosts(user,limit,offset,response);
            session.setAttribute("user",user);
        }


        resp.getWriter().write(JsonConverter.toJson(response));



    }



}
