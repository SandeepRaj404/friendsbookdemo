package com.validator;

import com.beans.JsonConverter;
import com.beans.User;
import com.dbutils.PostDAO;
import com.response.beans.CommentPostedResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/commentonpost")
public class CommentOnPost extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");
        CommentPostedResponse response = null;
        if (user == null) {
            resp.sendRedirect("index.jsp");
        }else{
            int userId = user.getUserId();
            String PostId = req.getParameter("postId");
            String comment = req.getParameter("comment");
            response = new CommentPostedResponse();

            PostDAO.commentOnPost(userId,PostId,comment,response);


        }

        resp.getWriter().write(JsonConverter.toJson(response));

    }
}
