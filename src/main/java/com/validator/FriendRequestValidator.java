package com.validator;

import com.beans.JsonConverter;
import com.beans.User;
import com.constants.AppContants;
import com.constants.CommonErros;
import com.dbutils.FriendsDAO;
import com.response.beans.FriendRequestSentResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/friendrequest")
public class FriendRequestValidator extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

       HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");
        FriendRequestSentResponse response = new FriendRequestSentResponse();

        if (user == null) {
            resp.sendRedirect("index.jsp");

        }else{
            int userId =  Integer.parseInt(req.getParameter("userId"));
            int friendUserId = user.getUserId();

           if(FriendsDAO.doesFriendRequestExist(userId, friendUserId)){
                FriendsDAO.RevokeFriendRequest(userId,friendUserId,response);
           }else{
               FriendsDAO.sendFriendRequest(userId,friendUserId,response);
           }
        }


       resp.getWriter().write(JsonConverter.toJson(response));



    }
}














