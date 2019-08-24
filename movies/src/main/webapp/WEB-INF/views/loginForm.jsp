<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="row">

	<h2 class="text-center">
		<fmt:message key="movie.login" />
	</h2>

	<div class="col-sm-4 col-sm-offset-5">
		<h4 class="text-danger text-center">
			<c:out
				value="${sessionScope['SPRING_SECURITY_LAST_EXCEPTION'].message}" />
		</h4>
	</div>

	<form action="${pageContext.request.contextPath}/login"
		class="form-horizontal" method="post">

		<div class="form-group">
			<label class="control-label col-sm-2 col-sm-offset-3" for="username"><fmt:message
					key="movie.username" /></label>
			<div class="col-sm-4">
				<input class="form-control" name="username" />
			</div>
		</div>

		<div class="form-group">
			<label class="control-label col-sm-2 col-sm-offset-3" for="password"><fmt:message
					key="movie.password" /></label>
			<div class="col-sm-4">
				<input class="form-control" type="password" name="password" />
			</div>
		</div>

		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />

		<div class="form-group">
			<div class="col-sm-4 col-sm-offset-5">
				<button type="submit" class="btn btn-default">
					<fmt:message key="movie.login" />
				</button>
			</div>
		</div>

	</form>

</div>
