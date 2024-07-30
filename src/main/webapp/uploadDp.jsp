<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <link rel="stylesheet" href="uploadDp.css">
   
    <script>
       
    </script>
</head>
<body>
   <jsp:include page="header.jsp" />
    <form id="uploaddpform" enctype="multipart/form-data" style="margin-top: 100px;">
	<div class="container col-md-6 mt-4">
        <div class="mb-3">
            <label for="profileImage" class="form-label">Upload Profile Picture</label>
            <input type="file" name = "file" class="form-control" id="profileImage" onchange="previewImage()">
        </div>
        
          <div class="container py-4">
   		 <div class="row g-4 row-cols-1 row-cols-sm-2 row-cols-md-3">
        <div class="col">
            <div class="card rounded-circle">
                <div class="ratio ratio-1x1 rounded-circle overflow-hidden">
                    <img id="preview" src="" class="card-img-top img-cover">
                </div>
                
            </div>
            <button type = "submit"  class = "btn btn-primary mt-2">Upload</button>
        </div>
    </div>
</div>
      
    </div>
    
    </form>

    <script src = "uploadDp.js"></script>
</body>
</html>