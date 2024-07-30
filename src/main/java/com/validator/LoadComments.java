package com.validator;

import com.beans.JsonConverter;
import com.beans.User;
import com.dbutils.PostDAO;
import com.response.beans.CommentLoadResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;


@WebServlet("/loadcomments")
public class LoadComments extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");
        CommentLoadResponse response = null;
        if (user == null) {
            resp.sendRedirect("index.jsp");
        }else{
                String postId = req.getParameter("postId");
                response = new CommentLoadResponse();
            PostDAO.comments(postId,response);

        }

        resp.getWriter().write(JsonConverter.toJson(response));
    }
}
