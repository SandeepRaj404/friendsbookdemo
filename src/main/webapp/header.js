function previewImage() {
    // Get the selected image file
    const imageFile = document.getElementById('dpFile').files[0];
    console.log(imageFile.name);

    // Check if a file is selected
    if (imageFile) {

        // Validate image type (optional)
        const allowedTypes = ['image/jpeg', 'image/png', 'image/gif'];
        if (!allowedTypes.includes(imageFile.type)) {
            //('Please select a valid image file (JPEG, PNG, or GIF)');
            return;
        }

        // Create a FileReader object
        const reader = new FileReader();

        // Handle image load event
        reader.onload = function (e) {
            const imagePreview = document.getElementById('dpImage');
            imagePreview.src = e.target.result;
            document.getElementById("pictureCaption").style.visibility= "visible";
            // Enable the upload button (optional)
            document.querySelector('button').disabled = false;
        };

        // Read the image as a data URL
        reader.readAsDataURL(imageFile);
        
    }
}
$(document).ready(function (){

    $("#postForm").submit(function (event){
        event.preventDefault();
        $.ajax({
            type: 'POST',
            url: "postupload",
            data:new FormData(this),
            processData: false,
            contentType: false,
            success: function (data) {
                var response=$.parseJSON(data)
                console.log(response);
                if(!(response.status === '200')){
                    showError//(response.error);
                }else if(response.status === '200'){
                    //("post uploaded successfully.");
                    location.reload();
                }
            },
            error: function (data) {
                //(data);
                console.log(data);
                //("error");
            }
        });
    });

    $(document).on("click", "#home", function(){
        window.location.href = "homepage.jsp";
    });
    
    
    $(document).on("click", "#profile", function(event){
        event.preventDefault();
            var userId = $(this).data('id');
            
            // Redirect to the profile page with the userId as a parameter
            window.location.href = 'loadprofile?userId=' + encodeURIComponent(userId);
      
    });

    $(document).on("click", "#logoutBtn", function(){
        console.log("logout clicked.");
        //var postId = $(this).data("id");
        //var button = $(this);
        $.ajax({
            type: 'GET',
            url: "logout",
            data: {},
            success: function (data) {
                console.log("logged out");
               // window.location.href = "index.jsp"
              
            },
            error: function (data) {
                //("An error occurred: " + data);
                console.log(data);
            }
        });
    });




});
