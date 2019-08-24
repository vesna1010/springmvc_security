<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="com.vesna1010.movies.enums.Genre"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<div class="row">

	<h2 class="text-center">
		<fmt:message key="movie.addNewMovie" />
	</h2>
	<br>

	<form:form action="${pageContext.request.contextPath}/saveMovie"
		class="form-horizontal" method="post" commandName="movie">

		<form:hidden path="id" />
		<form:hidden path="votes" />
		<form:hidden path="date" />

		<sec:authentication property="principal" var="principal" />
		<form:hidden path="user" value="${principal.username}" />

		<div class="form-group">
			<form:label path="title"
				class="control-label col-sm-2 col-sm-offset-3">
				<fmt:message key="movie.title" />
			</form:label>
			<div class="col-sm-4">
				<form:input class="form-control" path="title" />
				<form:errors path="title" class="text-danger" />
			</div>
		</div>

		<div class="form-group">
			<form:label path="genre"
				class="control-label col-sm-2 col-sm-offset-3">
				<fmt:message key="movie.genre" />
			</form:label>
			<div class="col-sm-4">
				<form:select path="genre" class="form-control">
					<form:option value=""></form:option>
					<form:options items="${Genre.values()}" />
				</form:select>
				<form:errors path="genre" class="text-danger" />
			</div>
		</div>

		<div class="form-group">
			<form:label path="year"
				class="control-label col-sm-2 col-sm-offset-3">
				<fmt:message key="movie.year" />
			</form:label>
			<div class="col-sm-4">
				<form:input path="year" class="form-control" />
				<form:errors path="year" class="text-danger" />
			</div>
		</div>

		<div class="form-group">
			<form:label path="actors"
				class="control-label col-sm-2 col-sm-offset-3">
				<fmt:message key="movie.actors" />
			</form:label>
			<div class="col-sm-4">
				<form:input path="actors" class="form-control" />
				<form:errors path="actors" cssClass="text-danger" />
			</div>
		</div>

		<div class="form-group">
			<form:label path="url" class="control-label col-sm-2 col-sm-offset-3">
				<fmt:message key="movie.url" />
			</form:label>
			<div class="col-sm-4">
				<form:input path="url" class="form-control" />
				<form:errors path="url" class="text-danger" />
			</div>
		</div>

		<div class="form-group">
			<div class="col-sm-4 col-sm-offset-5">
				<div class="btn-group">
					<input class="btn btn-default" type="submit" value="Save" /><input
						class="btn btn-default" type="button" value="Reset"
						onclick="location.href='<c:url value="/movieForm"/>'" /><input
						class="btn btn-default" type="button" value="Cancel"
						onclick="location.href='<c:url value="/"/>'" />
				</div>
			</div>
		</div>

	</form:form>

</div>