package com.validator;

import com.beans.JsonConverter;
import com.beans.User;
import com.constants.AppContants;
import com.constants.CommonErros;
import com.dbutils.UserDAO;
import com.mysql.cj.exceptions.StreamingNotifiable;
import com.response.beans.DPUploadResponse;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@WebServlet("/uploadDp")
@MultipartConfig
public class DpUploadServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    	User user = (User) req.getSession(false).getAttribute("user");
        DPUploadResponse response = new DPUploadResponse();
        if (user == null) {
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        }


        Part part=req.getPart("file");
        String fileType=part.getContentType();
        if(AppContants.ALLOWED_TYPES.contains(fileType)) {
        	String filename = "usrdp"+String.valueOf(user.getUserId())+getExtension(fileType);
        	FileOutputStream fout = null;
            InputStream fin = null;
            try {
                fin = part.getInputStream();
                byte[] images = new byte[fin.available()];
                fin.read(images);
                ServletContext con = getServletContext();
                String dpPath = con.getInitParameter("UserdpPath");
                String path=dpPath+filename;
                fout = new FileOutputStream(path);
                fout.write(images);
                user.setDp(filename);

                UserDAO.dpUploaddao(user,response);

                user.setDp(AppContants.USER_DP_BASE_ADDR+user.getDp());


            }catch (IOException e){
                resp.getWriter().println("Something went wrong File can't be uploaded.");
            }finally {
                try {
                    fout.flush();
                    fout.close();
                    fin.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }else {
                response.setStatus(CommonErros.BAD_REQUEST);
                response.setMessage(AppContants.IMAGE_TYPE_NOT_ALLOWED);
                response.setData(null);
        }
        
        resp.getWriter().write(JsonConverter.toJson(response));

    }
    
    public static String getExtension(String type) {
    	return "."+type.split("/")[1];
    }
    
}
