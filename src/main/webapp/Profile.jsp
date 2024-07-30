<%@ page import="com.beans.User" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    
    <style>
      body{
        background-color: gainsboro;
       
      }
      a{
        text-decoration: none;
    
      }
        .cover-photo {
            width: 100%;
            height: 300px;
            background: url('https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_960_720.png') no-repeat center center;
            background-size: contain;
            position: relative;
            border: 5px solid white;
        }
        .profile-photo {
            width: 150px;
            height: 150px;
            border-radius: 50%;
            border: 5px solid white;
            position: absolute;
            bottom: -75px; /* Half of the profile photo height */
            left: 50%;
            transform: translateX(-50%);
        }
        .profile-info {
            position: absolute;
            bottom: -105px; /* Align with profile photo */
            left: 50%;
            transform: translateX(-50%);
            text-align: center;
            width: 100%;
        }
        .username {
            margin-top: 15px;
            font-size: 1.5rem;
        
        }
       
       
    </style>
</head>
<%


    User userprofile =(User) session.getAttribute("userprofile");
    String dp= null;
    String username = null;
    if(userprofile==null){
        session.invalidate();
        response.sendRedirect("index.jsp");
    }else{
        dp=userprofile.getDp();
        username = userprofile.getUserName();
    }



%>
<body>
    <jsp:include page="header.jsp" />

    <div class="container mt-5">
        <div class="cover-photo">
            <img  id="profilePic" src="<%= dp%>" alt="Profile Photo" class="profile-photo">
            <div class="profile-info">
                <div id="username" data-id = "<%=userprofile.getUserId()%>" class="username"><%= username%></div>
            </div>
        </div>
    </div>
    
    <div class="container" style="border: 1px none black; margin-top: 100px;">
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container-fluid">
              <div class="navbar-collapse" id="navbarNavAltMarkup">
                <div class="navbar-nav d-flex flex-row">
                  <button type="button" id="posts" class="nav-link ms-2 btn btn-primary  " aria-current="page" href="#">Posts</button>
                  <button class="nav-link ms-4 btn" data-bs-toggle="modal" data-bs-target="#friendsModal">Friends</button>                  
                  <button class="nav-link ms-4 btn" href="#">About</button>
                </div>
              </div>
            </div>
          </nav>
    </div>
            <div class="postcontainer col" style="background-color: white;">
                <div id="post" class="container d-flex flex-column align-items-center justify-content-center">

                </div>
        </div>
       
    </div>

<!--List of friends-->

<!-- Button trigger modal -->
  
  <!-- Modal -->
  <div class="modal fade" id="friendsModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Friends List</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <!--  your friends list content here -->
                <div class="container" style="border: 1px none black; height: 300px;overflow: scroll;">
                    <form>
                        <input type="text" class="form-control" placeholder="Search friends">
                   
                    </form>
                    <div  class="row align-items-center justify-content-center"></div>
                    <div id="ListFriends" class="col col-sm-2">
                       <!-- <div class="card mt-3" style="width: 15rem;">
                            <div class="d-flex">
                                <img class="img-thumbnail" src="images/usrpost2.jpeg" alt="..." style="height: 70px; width: 70px;">
                               <a class="ms-3" href=""><b>Username</b></a><br>
                               
                               
                            </div>
                            <div class="d-flex align-items-center justify-content-start">
                                <button class="btn btn-sm btn-secondary ms-2" style="width: 100px;">Unfriend</button>
                                <button class="btn btn-sm btn-primary ms-2" style="width: 100px;">View</button>
            
                            </div>
                        
                    </div>
                   
                         -->
                    </div>
                   
                </div>
                <!--friends list ends-->
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary">Save changes</button>
            </div>
        </div>
    </div>
</div>

<!--Modal for likes and comment load-->

<div class="modal fade" id="comments"  tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h1 class="modal-title fs-5" id="exampleModalLabel">Comments</h1>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body" style="overflow-y: scroll;height: 300px;" id="commentBody">
           
            <!--test-->
            
            <!--test ends-->
            
        </div>
        <div class="modal-footer">
            <input id="commentText" type="text" class="form-control" style="box-shadow: none;" placeholder="comment your thoughts on this...">
            
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
          <button id="commentSend" type="button" class="btn btn-primary">Comment->></button>
        </div>
      </div>
    </div>
  </div>


  <div class="modal fade" id="likesInfo"  tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h1 class="modal-title fs-5" id="exampleModalLabel">Likes</h1>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body" style="overflow-y: scroll;height: 300px;" id="likesBody">
           
            <!--test-->
            
            <!--test ends-->
            
        </div>
        <div class="modal-footer">
            
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
        </div>
      </div>
    </div>
  </div>




<!---->


    <script src="Profile.js"></script>
    
</body>
</html>