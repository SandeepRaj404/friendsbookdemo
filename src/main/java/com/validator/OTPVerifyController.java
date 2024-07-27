package com.validator;

import com.beans.JsonConverter;
import com.beans.User;
import com.constants.AppContants;
import com.constants.CommonErros;
import com.dbutils.UserDAO;
import com.response.beans.UserRegistrationResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
@WebServlet("/otpVerify")
public class OTPVerifyController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session=req.getSession(false);
        UserRegistrationResponse response=new UserRegistrationResponse();
        if(session !=null && session.getAttribute("otp")!=null){
            String expectedOTP=(String)session.getAttribute("otp");
            String actualInputOTP=req.getParameter("otp");
            if(expectedOTP.equals(actualInputOTP.trim())){
                User user=(User)session.getAttribute("user");
                UserDAO.registerUsersss(user,response);
                user.setDp(AppContants.USER_DP_BASE_ADDR+user.getDp());
                session.setAttribute("user",user);
                response.setMessage("User Registered Successfuly");
                response.setStatus("200");
            }else{
                response.updateResponse("OTP verification failed ", CommonErros.BAD_REQUEST,null);
            }
        }
        String jsonResponse = JsonConverter.toJson(response);
        resp.getWriter().print(jsonResponse);
    }
}
