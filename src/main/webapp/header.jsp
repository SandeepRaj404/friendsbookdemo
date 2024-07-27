<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
  <%@page import="com.beans.User"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Bootstrap Example</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

  <style>

   

  </style>

</head>
<body>
  <%

    	User user=(User)session.getAttribute("user");
        String dp= null;
        String username = null;
		if(user==null){
			session.invalidate();
			response.sendRedirect("index.jsp");
		}else{
       dp=user.getDp();
       username = user.getUserName();
    }
	


	%>

<nav  class="navbar navbar-expand-sm bg-dark navbar-dark fixed-top" style="color:white">
  <div class="container-fluid">
    <a class="navbar-brand" href="#" style="color: blue;">
          <i id="logo" class="fa fa-2x fa-facebook">riendsBook</i>
        </a>
        <div></div>
        <span class="ms-2"><%= username%>
          
        </span>
        
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#collapsibleNavbar">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="collapsibleNavbar" >
      <ul class="navbar-nav">
        <li class="nav-item ms-5">
          <a id="home" class="nav-link" href="#" title="Message currently not in service"><i class="material-icons" style="font-size: 40px;">home</i></a>
        </li>

        <li class="nav-item ms-5">
          <a class="nav-link" href="#" title="Make Post" data-bs-toggle="modal" data-bs-target="#exampleModal"><i class="material-icons" style="font-size: 40px;">post_add</i></a>
        </li>
        <li class="nav-item ms-5">
          <a class="nav-link" href="makefriends.jsp" title="Add friends"><i class="material-icons" style="font-size: 40px;">group_add</i></a>
        </li>
        <li class="nav-item ms-5">
          <a class="nav-link" href="#" title="setting"><i class="material-icons" style="font-size: 40px;">settings</i></a>
        </li>
        <li class="nav-item ms-5">
          <a class="nav-link" href="#" title="Message currently not in service"><i class="material-icons" style="font-size: 40px;">chat</i></a>
        </li>
        <li class="nav-item dropdown ms-5">
          <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown">
            <img src="<%= dp%>" alt="..." style="height: 40px;width: 45px;border-radius: 30%">
            <span class="position-absolute translate-middle p-1 bg-success border border-light rounded-circle" style="width: 3px; height: 3px;">
              <span class="visually-hidden">New alerts</span>
            </span>
          </a>
          <ul class="dropdown-menu">
            <li><button type="button" data-id="<%= user.getUserId()%>" id="profile" class="dropdown-item" >Profile</button></li>
            <li><a class="dropdown-item" href="#">setting</a></li>
            <li><a class="dropdown-item" href="#">Logout</a></li>
          </ul>
        </li>
      </ul>
    </div>
  </div>
</nav>

  <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLabel">Post A Picture </h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <form id="postForm" enctype="multipart/form-data">
          <div class="modal-body">
            <input type="file" name = "picFile" id="dpFile"  onchange="previewImage()">

            <div class="img">
              <input  name="caption" type="text" id="pictureCaption" class="form-control" placeholder="Say something about this photo..." style="visibility: hidden;">
              <img id="dpImage" class="img-thumbnail"  src="" alt="..." style="width: 200px;height: 200px;">
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
            <button type="submit" class="btn btn-primary" id="newPost">Post</button>
          </div>
        </form>
      </div>
    </div>
  </div>

  <script src = "header.js"></script>

</body>
</html>


