
  function previewImage() {
    // Get the selected image file
    const imageFile = document.getElementById('profileImage').files[0];

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
            const imagePreview = document.getElementById('preview');
            imagePreview.src = e.target.result;
            // Enable the upload button (optional)
            document.querySelector('button').disabled = false;
        };

        // Read the image as a data URL
        reader.readAsDataURL(imageFile);
    }
}
  
$(document).ready(function (){
    $("#uploaddpform").submit(function (event){
        event.preventDefault();
        $.ajax({
            type: 'POST',
            url: "uploadDp",
            data:new FormData(this),
            processData: false,
            contentType: false,
            success: function (data) {
                var response=$.parseJSON(data)
                console.log(response);
                if(!(response.status === '200')){
                   // showError//(response.error);
                   alert("dp uploaded sucessfully.")
                   location.href = "index.jsp";
                }else if(!(response.status === '200')){
                    //("dp uploaded successfully.")
                    alert("can't upload dp");
                    location.href = "index.jsp";
                }
            },
            error: function (data) {
                //(data);
                console.log(data);
                //("error");
            }
        });
    });


    


});
