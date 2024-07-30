package com.validator;

import com.beans.JsonConverter;
import com.beans.User;
import com.dbutils.FriendsDAO;
import com.response.beans.AcceptFriendRequestResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/acceptfriendrequest")
public class AcceptFriendRequest extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");
        AcceptFriendRequestResponse response = new AcceptFriendRequestResponse();
        if (user == null) {
            resp.sendRedirect("index.jsp");
        }else{
            int friendId = Integer.parseInt(req.getParameter("userId"));
            int userId = user.getUserId();
            FriendsDAO.confirmFriendRequest(userId,friendId,response);

        }
        resp.getWriter().write(JsonConverter.toJson(response));
    }
}
