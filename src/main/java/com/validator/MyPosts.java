package com.validator;

import com.beans.JsonConverter;
import com.beans.User;
import com.dbutils.PostDAO;
import com.response.beans.ShowPostResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/myposts")
public class MyPosts extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        ShowPostResponse response = new ShowPostResponse();
        if (user == null) {
            resp.sendRedirect("index.jsp");
        }else{
            final int limit =10 ;
            int offset = Integer.parseInt(req.getParameter("offset"));
            int userId = Integer.parseInt(req.getParameter("userId"));
            PostDAO.getPosts(userId,limit,offset,response);
            session.setAttribute("user",user);
        }
        resp.getWriter().write(JsonConverter.toJson(response));
    }
}
