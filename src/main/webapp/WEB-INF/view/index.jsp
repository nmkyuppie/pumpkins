<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
	<head>
		<meta charset="utf-8">
		<title>
			Pumpkins
		</title>
		<link href="resources/bootstrap.css" rel="stylesheet">
		<link href="resources/style.css" rel="stylesheet">
		<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.1.0/css/all.css" integrity="sha384-lKuwvrZot6UHsBSfcMvOkWwlCMgc0TaWr+30HWe3a4ltaBwTZhyTEggF5tJv8tbt" crossorigin="anonymous">
		<script src="resources/script.js" type="text/javascript"></script>
	</head> 
	<body onload="init()">
	<div class="header">
		<p class="title">
			<img src="resources/pumpkins.png" style="width: 35px;height: 35px;vertical-align: sub;margin-right: 5px;margin-top: 5px;">Pumpkins
		</p>
		<!-- <span class="offset-sm-9 col-sm-1">
			<button class="btn btn-primary" style="margin-top:5px; float:right;margin-right:10px;">
				<span class="fa-1x"><i class="fas fa-clipboard-list"></i></span>
			</button>
		</span> -->
	</div>
	<div class="container-fluid">
		<div class="row flex-xl-nowrap">
			<div class="col-12 col-md-3 col-xl-2 bd-sidebar sideBar" id="sideBar">
				<c:forEach items="${versions}" var="temp">
	            	<a class="btn btn-link anchorHover" href="?version=${temp}">${temp}</a><hr style="margin:0;">
	            </c:forEach>
			</div>
			<main class="col-12 col-md-6 col-xl-6 py-md-3 pl-md-5 bd-content">
				<form>
				  <div class="form-group">
				    <label for="groupId">Group Id*</label>
				    <input type="text" class="form-control" id="groupId" placeholder="com.example" value="${groupId}">
				  </div>
				  <div class="form-group">
				    <label for="artifactId">Artifact Id*</label>
				    <input type="text" class="form-control" id="artifactId" placeholder="Artifact Id" value="${artifactId}">
				  </div>
				  <div class="form-group">
				    <label for="version">Version*</label>
				    <input type="text" class="form-control" id="version" placeholder="Version" value="${version}">
				  </div>	
				   <div class="form-group">
				      <label for="environment">Deploy In*</label>
				      <select id="environment" class="form-control">
				        <option value="dev" selected>Dev</option>
				        <option value="qa">QA</option>
				      </select>
				    </div>
				  <button id="deployBtn" type="button" class="btn btn-primary" onclick="deploy()">Deploy</button><br><br>
				  <div class="alert alert-danger d-none" role="alert" id="error"></div>
				  <div class="alert alert-success d-none" role="alert" id="success">
				  </div>
				</form>
			</main>
		</div>
	</div>
	</body>
</html>
