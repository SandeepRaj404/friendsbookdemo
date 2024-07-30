$(document).ready(function() {

  $.ajax({

    type: 'GET',
    url: "listfriendrequest",
    success: function (data) {
       var response = JSON.parse(data);
       var items = JSON.parse(response.data);
       console.log(items);

      items.forEach(item => {
          $("#friend-requets").append('<div class="col-sm-2 ms-2 mt-2" style="width: 15rem;">'+
            ' <div class = "card " style="width: 15rem;">'+
             '<img class = "card-img-top" src="'+item.dp+'" alt="...">'+
             '<div class="card-body">'+
                 '<h5 class="card-title">'+item.userName+'</h5>'+
                 '<p class="card-text" id="reqStatus"></p>'+
                 '<button class = "confirm btn btn-primary " data-id="'+item.userId+'">confirm</button>'+
                 ''+
                 '</div>'+
                 '</div>'+
                 '</div>');

       
       
      });
    },
    error: function (data) {
        //(data);
        console.log(data);
        //("error");
    }
});
 
      $.ajax({

        type: 'GET',
        url: "peopleYouMayKnowList",
        success: function (data) {
           var response = JSON.parse(data);
           var items = JSON.parse(response.data);
           console.log(items);

          items.forEach(item => {

  
              $("#friends").append('<div class="request col-sm-2 ms-2 mt-2" style="width: 15rem;">'+
                ' <div class = "card " style="width: 15rem;">'+
                 '<img class = "card-img-top" src="'+item.dp+'" alt="...">'+
                 '<div class="card-body">'+
                     '<h5 class="card-title">'+item.userName+'</h5>'+
                     '<p class="card-text" id="reqStatus"></p>'+
                     '<button class = "addFriend btn btn-primary " data-id="'+item.userId+'">Add friend</button>'+
                     '<button class="remove btn btn-secondary" data-id="'+item.userId+'">Remove</button>'+
                     '</div>'+
                     '</div>'+
                     '</div>');
  
           
           
          });
        },
        error: function (data) {
            //(data);
            console.log(data);
            //("error");
        }
    });
//}

    //Add fiends button click handling.

    $(document).on("click", ".addFriend", function(){
      console.log("add friends clicked.");
      var personId = $(this).data("id");
      var button = $(this);
      $.ajax({
          type: 'GET',
          url: "friendrequest",
          data: {userId: personId},
          success: function (data) {
              var response = JSON.parse(data);
              if(response.status === "200"){
                 
                  button.text(response.data)
                  
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

  $(document).on("click", ".confirm", function(){
    console.log("confirm clicked.");
    var personId = $(this).data("id");
    var button = $(this);
    $.ajax({
        type: 'GET',
        url: "acceptfriendrequest",
        data: {userId: personId},
        success: function (data) {
            var response = JSON.parse(data);
            if(response.status === "200"){
               
                button.text(response.data);
                button.css("background-color","white");
                button.css("color","blue");
                $(this).attr("disabled", "disabled");

                
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

  
$(document).on('click', '.remove', function() {
  $(this).closest('.request').hide();
});


   // });
  });
  
  
  
  