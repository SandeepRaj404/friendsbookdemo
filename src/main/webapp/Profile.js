$(document).ready(function() {

   var offset =0;
   var loading = false;
   loadPosts();
   $('#post').on('scroll', function() {
    if ($('#post').scrollTop() + $('#post').innerHeight() >= $('#post')[0].scrollHeight) {
        console.log('Reached bottom of container');
        loadPosts();
    }
});
    var userId;
    userId = $("#username").data("id");
    $.ajax({
    
      type: 'GET',
      url: "loadallfriends",
      data: {userId : userId},
      success: function (data) {
         var response = JSON.parse(data);
         var items = JSON.parse(response.data);
         console.log(items);
  
        items.forEach(item => {
            $("#ListFriends").append( '<div class="card mt-3" style="width: 15rem;">'+
                '<div class="d-flex">'+
                    '<img class="img-thumbnail" src="'+item.dp+'" alt="..." style="height: 70px; width: 70px;">'+
                   '<a class="username ms-3" href="" data-id = "'+item.userId+'"><b>'+item.userName+'</b></a><br>'+
                   
                   
                '</div>'+
                '<div class="d-flex align-items-center justify-content-start">'+
                    '<button data-id = "'+item.userId+'" class="btn btn-sm btn-secondary ms-2" style="width: 100px;">Unfriend</button>'+
                    '<button class="btn btn-sm btn-primary ms-2" style="width: 100px;">View</button>'+

                '</div>'+
            
                '</div>');
  
         
         
        });
      },
      error: function (data) {
          alert(data);
          console.log(data);
          alert("error");
      }
  });
   
       
  function loadPosts(){

    if (loading) return;
    loading = true;

    var userId;
    userId = $("#username").data("id");
    $.ajax({
         
        type: 'GET',
        url: "myposts",
        data: {offset: offset,userId : userId},
        success: function (data) {
            var response = JSON.parse(data);
            var items = JSON.parse(response.data);
            console.log(items);
            if(items.length>0){
                items.forEach(item => {


                    $("#post").append('<div class="card mt-3" style="width: 30rem;">'+
                        '<div class="card-header">'+
                        '<a href="#"><img src="'+item.dp+'" alt="User Profile" class="" width="40" height="40" style="float: left;border-radius: 30%;border: 1px solid black;"></a>'+
                        '<span class="ms-2">'+item.userName+'</span>'+
                        '<span class="ms-2">'+item.userPost.dateOfPost+'</span>'+
                        '</div>'+
                        '<p class="card-title mt-2 ms-3">'+item.userPost.caption+'</p>'+
                        '<img src="'+item.userPost.image+'" class="card-img-top" alt="..." style="width: 30rem;height: 20rem;">'+
                        '<div class="card-body">'+
                        '<div class = "d-flex flex-row">'+
                        '<div class = "">'+item.userPost.likes+' Likes</div>'+
                        '<div class = " ms-5">'+item.userPost.comments+' comments</div>'+
                        '</div>'+
                        '<hr>'+
                        '<div class = "d-flex flex-row">'+
                        '<div class = ""><button class=""  style="border:none;"><i class = "like material-icons" style="color:'+item.userPost.like_status+' " data-id ="'+item.userPost.postId+'">thumb_up</i></button></div>'+
                        ' <div class = " ms-1">Like</div>'+
                        '<div class = " ms-5"><button class="" style="border:none;"><i class = "material-icons">comment</i></button></div>'+
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
            alert(data);
            console.log(data);
            loading = false;
        }
    });

}

  $(document).on("click", ".username", function(event){
      console.log("like clicked.");
     
      event.preventDefault();
            var userId = $(this).data('id');
            
            // Redirect to the profile page with the userId as a parameter
            window.location.href = 'loadprofile?userId=' + encodeURIComponent(userId);
  });



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

                console.log(response.data);
                button.css('color', response.data);



            }else if(response.status === "400"){
                alert("request not sent");
            }
        },
        error: function (data) {
            alert("An error occurred: " + data);
            console.log(data);
        }
    });
});










  
     // });
    });
    
    
    
    