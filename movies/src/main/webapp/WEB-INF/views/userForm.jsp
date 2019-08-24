<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="row">

	<div class="col-sm-12">
		<h2 class="text-center">
			<fmt:message key="movie.addNewUser" />
		</h2>
		<br>
	</div>

	<form:form action="${pageContext.request.contextPath}/saveUser"
		class="form-horizontal" method="post" commandName="user">

		<div class="form-group">
			<form:label path="username"
				class="control-label col-sm-2 col-sm-offset-3">
				<fmt:message key="movie.username" />
			</form:label>
			<div class="col-sm-4">
				<form:input path="username" class="form-control" />
				<form:errors path="username" class="text-danger" />
			</div>
		</div>

		<div class="form-group">
			<form:label path="password"
				class="control-label col-sm-2 col-sm-offset-3">
				<fmt:message key="movie.password" />
			</form:label>
			<div class="col-sm-4">
				<form:password path="password" class="form-control" />
				<form:errors path="password" class="text-danger" />
			</div>
		</div>

		<div class="form-group">
			<form:label path="authorities"
				class="control-label col-sm-2 col-sm-offset-3">
				<fmt:message key="movie.authorities" />
			</form:label>
			<div class="col-sm-4">
				<form:select path="authorities" items="${authorities}"
					itemValue="id" itemLabel="name" class="form-control">
					<form:option value=""></form:option>
				</form:select>
				<form:errors path="authorities" class="text-danger" />
			</div>
		</div>

		<div class="form-group">
			<div class="col-sm-4 col-sm-offset-5">
				<div class="btn-group">
					<input class="btn btn-default" type="submit" value="Save" /> <input
						class="btn btn-default" type="button" value="Reset"
						onclick="location.href='<c:url value="/userForm"/>'" /> <input
						class="btn btn-default" type="button" value="Cancel"
						onclick="location.href='<c:url value="/users"/>'" />
				</div>
			</div>
		</div>

	</form:form>
</div>