package com.validator;

import com.beans.JsonConverter;
import com.beans.User;
import com.dbutils.FriendsDAO;
import com.response.beans.PeoperYouMayKnowResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
@WebServlet("/peopleYouMayKnowList")
public class ListPeopleYouMayKnow extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");
            PeoperYouMayKnowResponse response=new PeoperYouMayKnowResponse();
        List<User> peopleYouMayKnow= FriendsDAO.getListOfPeopleYouMayKnow(user);
        if(peopleYouMayKnow !=null || peopleYouMayKnow.size()>0){
            response.setMessage("Success");
            response.setStatus("200");
            response.setData(JsonConverter.toJson(peopleYouMayKnow));
        }else{
            response.setError("Failed or NO Data Found");
            response.setStatus("401");
            response.setData(null);
        }
        resp.getWriter().print(JsonConverter.toJson(response));
    }
}
