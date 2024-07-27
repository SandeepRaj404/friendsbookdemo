package com.validator;

import com.beans.JsonConverter;
import com.beans.User;
import com.beans.UserPost;
import com.constants.AppContants;
import com.constants.CommonErros;
import com.dbutils.PostDAO;
import com.dbutils.UserDAO;
import com.response.beans.PostUploadResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;

@WebServlet("/postupload")
@MultipartConfig

public class PostUpload extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ((HttpServletResponse) resp).addHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
        ((HttpServletResponse) resp).addHeader("Access-Control-Allow-Headers", "*");
        ((HttpServletResponse) resp).addHeader("Access-Control-Allow-Methods",
                "GET, OPTIONS, HEAD, PUT, POST, DELETE");

        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect("index.jsp");
        }

        UserPost post = new UserPost();
        PostUploadResponse response = new PostUploadResponse();
        post.setCaption(req.getParameter("caption"));
        post.setDateOfPost(LocalDate.now());

        Part part=req.getPart("picFile");
        String fileType=part.getContentType();
        if(AppContants.ALLOWED_TYPES.contains(fileType)) {
            String filename = "usrpost"+String.valueOf(UserDAO.getCorrespondigId("user_post"))+getExtension(fileType);
            FileOutputStream fout = null;
            InputStream fin = null;
            try {
                fin = part.getInputStream();
                byte[] images = new byte[fin.available()];
                fin.read(images);
                String path="C:\\friendsbookdemo\\src\\main\\webapp\\images\\"+filename;
                post.setImage(filename);
                user.setUserPost(post);
                fout = new FileOutputStream(path);
                fout.write(images);
               // user.setDp(filename);

                PostDAO.makePost(user,response);


            }catch (IOException e){
                e.printStackTrace();
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
            response.setError(CommonErros.INVALID_FILE_TYPE);
            response.setMessage("Invalid file type");
        }

        String jsonResponse = JsonConverter.toJson(response);
        resp.getWriter().write(jsonResponse);

    }
    public static String getExtension(String type) {
        return "."+type.split("/")[1];
    }

}

