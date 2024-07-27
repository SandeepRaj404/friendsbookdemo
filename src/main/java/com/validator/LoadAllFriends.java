package com.validator;

import com.beans.JsonConverter;
import com.beans.User;
import com.dbutils.FriendsDAO;
import com.response.beans.LoadFriendsResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/loadallfriends")
public class LoadAllFriends extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        LoadFriendsResponse response = null;
        if (user == null) {
            resp.sendRedirect("index.jsp");
        }else{
            int userId = Integer.parseInt(req.getParameter("userId"));
             response = new LoadFriendsResponse();
            FriendsDAO.loadAllFriends(userId,response);
        }

        resp.getWriter().write(JsonConverter.toJson(response));
    }
}
