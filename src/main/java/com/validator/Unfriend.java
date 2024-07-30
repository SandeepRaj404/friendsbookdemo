package com.validator;

import com.beans.JsonConverter;
import com.beans.User;
import com.dbutils.FriendsDAO;
import com.response.beans.UnfriendResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/unfriend")
public class Unfriend extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");
        UnfriendResponse response = null;

        if (user == null) {
            resp.sendRedirect("index.jsp");
        }else{
            int friend_user_id = Integer.parseInt(req.getParameter("friend_user_id"));
            int userId = user.getUserId();
            response = new UnfriendResponse();
            FriendsDAO.unfriend(userId,friend_user_id,response);


        }
        resp.getWriter().write(JsonConverter.toJson(response));
    }
}












