<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html data-ng-app="moviesApp">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.8/angular.min.js"></script>	
<script type="text/javascript"
	src="<c:url value='/resources/js/app.js'/>"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="<c:url value='/resources/css/style.css'/>">
<title><tiles:insertAttribute name="title" /></title>
</head>
<body data-ng-controller="moviesCtrl">
	<div class="container">
		<div class="row">
			<div class="col-sm-12">
				<tiles:insertAttribute name="header" />
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12">
				<tiles:insertAttribute name="body" />
			</div>
		</div>
	</div>
</body>
</html>