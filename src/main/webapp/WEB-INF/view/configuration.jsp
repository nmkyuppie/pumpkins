<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<html>
	<head>
		<meta charset="utf-8">
		<title>Welcome</title>
	</head> 
	<body>
	
		<div class="container">
			<form>
			  <div class="form-group">
			    <label for="groupId">Group Id</label>
			    <input type="text" class="form-control" id="groupId" placeholder="com.example">
			  </div>
			  <div class="form-group">
			    <label for="artifactId">Artifact Id</label>
			    <input type="text" class="form-control" id="artifactId" placeholder="Artifact Id">
			  </div>
			  <div class="form-group">
			    <label for="version">Version</label>
			    <input type="text" class="form-control" id="version" placeholder="Version">
			  </div>	
			  <button type="submit" class="btn btn-primary">Submit</button>
			</form>
		</div>
	
	</body>
</html>
