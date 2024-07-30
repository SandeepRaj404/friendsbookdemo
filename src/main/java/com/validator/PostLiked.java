package com.validator;

import com.beans.JsonConverter;
import com.beans.User;
import com.dbutils.PostDAO;
import com.response.beans.PostLikeResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/postlike")
public class PostLiked extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");
        PostLikeResponse response = new PostLikeResponse();

        if(user == null) {
            resp.sendRedirect("index.jsp");
        }else{
            int userId = user.getUserId();
            String postId = (req.getParameter("userPostId"));
            if(PostDAO.isPostLiked(userId, postId)){
                PostDAO.unlikePost(userId,postId,response);

            }else{
                PostDAO.likePost(userId,postId,response);
            }
            PostDAO.countLikes(postId,response);
        }

        resp.getWriter().write(JsonConverter.toJson(response));
    }
}
