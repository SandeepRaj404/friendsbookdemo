
function convertFormToJSON(form) {
    const array = $(form).serializeArray();
    const json = {};
    $.each(array, function() {
        json[this.name] = this.value || ""; 
    });
    console.log(json);
    return json;
}
function isFormField(form){
    const array = $(form).serializeArray();
    var isValid=true;
    $.each(array, function() {
            if(!this.value.trim().length >0){
                isValid=false;

            }
        });
        return isValid;
}


    

      $(document).ready(function(){
          
        var offset =0;
        var loading = false;
        loadPosts();
        $(window).on('scroll', function() {
            var windowHeight = $(window).height();
            var documentHeight = $(document).height();
            var scrollPosition = $(window).scrollTop();
    
            if (scrollPosition + windowHeight >= documentHeight - 100) {
                loadPosts();
            }
        });


        function loadPosts(){

            if (loading) return;
            loading = true;

            $.ajax({

                type: 'POST',
                url: "fetchpost",
                data: {offset: offset},
                success: function (data) {
                    var response = JSON.parse(data);
                    var items = JSON.parse(response.data);
                    console.log(items);
                    
                    if(items.length>0){
                        items.forEach(item => {
                                                            // Example date string with commas
                                const dateStr = String(item.userPost.dateOfPost);

                                // Replace commas with dots
                                const formattedDate = dateStr.split(',').join('.');

                                console.log(formattedDate); // Output: '20/09/2019. 22:44'


                            $("#post").append('<div class="card mt-3" style="width: 30rem;">'+
                                '<div class="card-header">'+
                                '<a href="#" class="dp" data-id ="'+item.userId+'"><img src="'+item.dp+'" alt="User Profile" class="" width="40" height="40" style="float: left;border-radius: 30%;border: 1px solid black;"></a>'+
                                '<a href = "#" class="username" data-id ="'+item.userId+'"><span class=" ms-2" >'+item.userName+'</span></a><br>'+
                                '<span class="ms-2">'+formattedDate+'</span>'+
                                '</div>'+
                                '<p class="card-title mt-2 ms-3">'+item.userPost.caption+'</p>'+
                                '<img src="'+item.userPost.image+'" class="card-img-top" alt="..." style="width: 30rem;height: 20rem;">'+
                                '<div class="card-body">'+
                                '<div class = "d-flex flex-row">'+
                                '<div class = "likesDetailBtn" data-bs-toggle="modal" data-bs-target="#likesInfo" data-id ="'+item.userPost.postId+'">'+item.userPost.likes+' Likes</div>'+
                                '<div class = " ms-5">'+item.userPost.comments+' comments</div>'+
                                '</div>'+
                                '<hr>'+
                                '<div class = "d-flex flex-row">'+
                                '<div class = ""><button class=""  style="border:none;"><i class = "like material-icons" style="color:'+item.userPost.like_status+' " data-id ="'+item.userPost.postId+'">thumb_up</i></button></div>'+
                                ' <div class = " ms-1">Like</div>'+
                                '<div class = " ms-5"><button type = "button" class="" style="border:none;" data-bs-toggle="modal" data-bs-target="#comments"><i class = "comment material-icons" data-id ="'+item.userPost.postId+'">comment</i></button></div>'+
                                '<div class = " ms-1">comment</div>'+
                                '</div>'+
                                '</div>'+
                                '</div>');



                        });
                        offset+=10;
                        loading = false;
                    }else{
                        $(window).off('scroll');
                    }
                },
                error: function (data) {
                    //(data);
                    console.log(data);
                    loading = false;
                }
            });

        }

          $(document).on("click", ".like", function(){
              console.log("like clicked.");
              var postId = $(this).data("id");
              var button = $(this);
              $.ajax({
                  type: 'GET',
                  url: "postlike",
                  data: {userPostId: postId},
                  success: function (data) {
                      var response = JSON.parse(data);
                      if(response.status === "200"){

                          console.log(response.buttonColor);
                          var likeCountElement = $('.likesDetailBtn[data-id="' + postId + '"]');
                          likeCountElement.text(response.data + ' Likes'); 
                          button.css('color', response.buttonColor);
                          //location.reload();



                      }else if(response.status === "400"){
                          //("request not sent");
                      }
                  },
                  error: function (data) {
                      //("An error occurred: " + data);
                      console.log(data);
                  }
              });
          });



          $(document).on("click", ".dp", function(event){
            console.log("like clicked.");
           
            event.preventDefault();
                  var userId = $(this).data('id');
                  
                  // Redirect to the profile page with the userId as a parameter
                  window.location.href = 'loadprofile?userId=' + encodeURIComponent(userId);
        });



        //loading the comments on the comment button click for every post.

            function sendComment(){
                var commentText = $("#commentText").val();
                if(commentText == "" || commentText == null){
                    $("#commentText").attr("placeholder", "Write Something To comment");
                    $("#commentText").css("border", "2px solid red");
                }else{
              
                $.ajax({

                    type: 'GET',
                    url: "commentonpost",
                    data: {comment: commentText,postId : postId},
                    success: function (data) {
                        var response = JSON.parse(data);
                        
                        console.log(response);
                        loadComments(response.data);
                        $("#commentText").val(null);
                       
                    },
                    error: function (data) {
                        //(data);
                        console.log(data);
                        loading = false;
                    }
                });
            }
            }
            function loadComments(postId){

                $.ajax({
                    type: 'POST',
                    url: "loadcomments",
                    data: {postId: postId},
                    success: function (data) {
    
                        //var response = JSON.parse(data);
                           // //("comments loaded successfully.");
    
                            var response = JSON.parse(data);
                            var items = JSON.parse(response.data);
                            console.log(items);
    
                                            
                                $(document).on("click", "#commentSend", function(event){
                                    console.log("comment clicked.");
                                
                                    event.preventDefault();
                                        
                                    sendComment();         
                                });
                          
                    
    
                            items.forEach(item => {
                                $("#commentBody").append('<div class="row">'+
                                    '<div class="col">'+                                    
                                        '<a href="#" class="dp" data-id ="'+item.userId+'"><img src="'+item.dp+'" alt="User Profile" class="" width="40" height="40" style="float: left;border-radius: 30%;border: 1px solid black;"></a>'+
                                        '<a href = "#" class="username" data-id ="'+item.userId+'" style="font-size:17px"><span class=" ms-2" >'+item.userName+'</span></a><br>'+
                                        '<span data-id="'+item.comments.comment_id+'" class="ms-1" style= "background-color:#F8F8F8">'+item.comments.comment+'</span>'+
                
                                        '</div>'+
                                '</div><hr>');
                                    
                            
                            
                            });
                       
                    },
                    error: function (data) {
                        //("error has been occurred")
                        console.log(data);
                    }
                });
    
            }

        


        var postId =null;

        $(document).on("click", ".comment", function(){

               
                var myDiv = document.getElementById('commentBody');
                while (myDiv.firstChild) {
                    myDiv.removeChild(myDiv.firstChild);
                }
                postId = $(this).data("id");
                loadComments(postId);

            
            console.log("comment clicked.");
            
            
        });




        // code ends for comments loading .


        $(document).on("click", ".likesDetailBtn", function(){
            var myDiv = document.getElementById('likesBody');
            while (myDiv.firstChild) {
                myDiv.removeChild(myDiv.firstChild);
            }
            console.log("like detail btn clicked.");
            var postId = $(this).data("id");
           
            $.ajax({
                type: 'GET',
                url: "likesload",
                data: {postId: postId},
                success: function (data) {
                    var response = JSON.parse(data);
                        var items = JSON.parse(response.data);
                        console.log(items);
                    if(response.status === "200"){

                        items.forEach(item => {
                            $("#likesBody").append('<div class="row">'+
                                '<div class="col">'+
                                    '<img class="img" src="'+item.dp+'" alt="..." style="height: 35px; width: 35px;border-radius: 10px;">'+
                                    '<a class="username ms-3" href="" data-id = "'+item.userId+'"><b>'+item.userName+'</b></a>'+
                                '</div>'+
                            '</div><hr>');
                                
                        
                        
                        });



                    }else if(response.status === "400"){
                        //("could not load likes");
                    }
                },
                error: function (data) {
                    //("An error occurred: " + data);
                    console.log(data);
                }
            });
        });

        $(document).on("click", ".username", function(event){
            console.log("like clicked.");
           
            event.preventDefault();
                  var userId = $(this).data('id');
                  
                  // Redirect to the profile page with the userId as a parameter
                  window.location.href = 'loadprofile?userId=' + encodeURIComponent(userId);
        });


        $(document).on("click", "#commentText", function(){

            box = $(this)
            box.css("border","1px solid blue")
        });

      });




