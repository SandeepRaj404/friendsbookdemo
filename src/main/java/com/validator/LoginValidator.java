package com.validator;

import com.beans.JsonConverter;
import com.beans.User;
import com.constants.AppContants;
import com.constants.CommonErros;
import com.dbutils.UserDAO;
import com.response.beans.UserLoginRequest;
import com.response.beans.UserLoginResponse;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
@WebServlet("/login")
public class LoginValidator extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter out = resp.getWriter();


        String email = null;
        String password = null;

        email = req.getParameter("email");
        password = req.getParameter("password");
        UserLoginResponse loginResponse=null;
        HttpSession session = req.getSession(true);
        if(email!=null && email.matches(AppContants.EMAIL_REGEX) && password!=null && password.length()>7){
            UserLoginRequest loginRequest=new UserLoginRequest(email,password);
            loginResponse=new UserLoginResponse();
            UserDAO.LoginDao(loginRequest,loginResponse);
            if(loginResponse.getStatus()==AppContants.SUCCESS_CODE){


                session.setAttribute("user", loginResponse.getUser());

                //end
                req.getRequestDispatcher("homepage.jsp").forward(req,resp);

            }else
                session.setAttribute("loginResponse",loginResponse);

            }else {
                session.setAttribute("loginResponse",loginResponse);
            }

            req.getRequestDispatcher("index.jsp").include(req,resp);
        }




    }

