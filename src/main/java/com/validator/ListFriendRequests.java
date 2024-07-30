package com.validator;

import com.beans.JsonConverter;
import com.beans.User;
import com.dbutils.FriendsDAO;
import com.response.beans.ListFriendRequestResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/listfriendrequest")
public class ListFriendRequests extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");
        ListFriendRequestResponse response = new ListFriendRequestResponse();
        if (user == null) {
            resp.sendRedirect("index.jsp");
        }else{
            int friend_userId = user.getUserId();
            FriendsDAO.getFriendRequests(friend_userId,response);
        }
        resp.getWriter().write(JsonConverter.toJson(response));
    }
}














