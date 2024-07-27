<%@ page import="com.beans.User" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="uploadDp.css">
    <script src = "uploadDp.js"></script>
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <style>
      .col-centered {
  float: none;
  margin-right: auto;
  margin-left: auto;
}
    </style>
    <script>
      
    </script>
</head>
<body>
   <jsp:include page="header.jsp" />
    <%
        /* session = request.getSession();
        User user = (User) session.getAttribute("user");
        if(user == null){
            session.invalidate();
        }else{
            session.setAttribute("user", user);
        }*/

    %>
    <!--upload post-->
    <!-- Modal -->

    <!--Upload post - end-->


    <!--Post display-->
   

    <div id="post" class="d-flex flex-column align-items-center justify-content-center">
      
        
        
       
        <!--Tesing code-->
       

        
        <!--Tesing code-->

     
    </div>
  
<script src="HomePage.js"></script>
</body>
</html>