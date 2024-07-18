<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
  <%@page import="com.beans.User"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Bootstrap Example</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">

</head>
<body>
  <%

    	User user=(User)session.getAttribute("user");
		if(user==null){
			session.invalidate();
			response.sendRedirect("index.jsp");
		}
		String dp=user.getDp();

	%>

<nav class="navbar navbar-expand-sm bg-dark navbar-dark" style="color:white">
  <div class="container-fluid">
    <a class="navbar-brand" href="#">
          <img src="usersDp/<%= user.getDp()%>" alt="Logo" style="width:40px;" class="rounded-pill">
        </a>
        <span class="ms-2"><%= user.getUserName() %></span>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#collapsibleNavbar">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="collapsibleNavbar" >
      <ul class="navbar-nav">
        <li class="nav-item ms-5">
          <a class="nav-link" href="#" data-bs-toggle="modal" data-bs-target="#exampleModal">New Post</a>
        </li>
        <li class="nav-item ms-5">
          <a class="nav-link" href="makefriends.jsp">Add friends</a>
        </li>
        <li class="nav-item ms-5">
          <a class="nav-link" href="#">Func..</a>
        </li>
        <li class="nav-item dropdown ms-5">
          <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown">
            <img src="" alt="..." style="height: 30px;width: 30px;">
          </a>
          <ul class="dropdown-menu">
            <li><a class="dropdown-item" href="#" data-bs-toggle="modal" data-bs-target="#exampleModal">Profile</a></li>
            <li><a class="dropdown-item" href="#">setting</a></li>
            <li><a class="dropdown-item" href="#">Logout</a></li>
          </ul>
        </li>
      </ul>
    </div>
  </div>
</nav>



</body>
</html>


