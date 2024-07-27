package com.validator;

import com.beans.User;
import com.dbutils.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/loadprofile")
public class LoadProfile extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            resp.sendRedirect("index.jsp");
        }else{
            int userId = Integer.parseInt(req.getParameter("userId"));
            User Userprofile = new User();
            Userprofile = UserDAO.getUser(userId);
            session.setAttribute("userprofile", Userprofile);
            req.getRequestDispatcher("Profile.jsp").forward(req, resp);
        }
    }
}
















