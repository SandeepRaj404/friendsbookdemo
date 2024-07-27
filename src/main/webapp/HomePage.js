
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
                                '<span class="ms-2">'+item.userName+'</span><br>'+
                                '<span class="ms-2">'+formattedDate+'</span>'+
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
                          location.reload();



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



          $(document).on("click", ".dp", function(event){
            console.log("like clicked.");
           
            event.preventDefault();
                  var userId = $(this).data('id');
                  
                  // Redirect to the profile page with the userId as a parameter
                  window.location.href = 'loadprofile?userId=' + encodeURIComponent(userId);
        });






      });




