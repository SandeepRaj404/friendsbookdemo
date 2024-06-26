
  function previewImage() {
    // Get the selected image file
    const imageFile = document.getElementById('dpFile').files[0];

    // Check if a file is selected
    if (imageFile) {

        // Validate image type (optional)
        const allowedTypes = ['image/jpeg', 'image/png', 'image/gif'];
        if (!allowedTypes.includes(imageFile.type)) {
            alert('Please select a valid image file (JPEG, PNG, or GIF)');
            return;
        }

        // Create a FileReader object
        const reader = new FileReader();

        // Handle image load event
        reader.onload = function (e) {
            const imagePreview = document.getElementById('dpImage');
            imagePreview.src = e.target.result;
            // Enable the upload button (optional)
            document.querySelector('button').disabled = false;
        };

        // Read the image as a data URL
        reader.readAsDataURL(imageFile);
    }
}
  