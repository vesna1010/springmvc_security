<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="myTag" tagdir="/WEB-INF/tags/tag/"%>

<div class="row">
	<div class="col-sm-7 ">
		<sec:authorize access="isAuthenticated()">
			<sec:authentication property="principal" var="principal" />
			<fmt:message key="movie.login" /> : ${principal.username}
            [<c:forEach var="authority" items="${principal.authorities}"
				varStatus="status">${authority.authority} 
                 <c:if test="${not status.last}">,</c:if>
			</c:forEach>] 
	         <br>
		</sec:authorize>
		<fmt:message key="movie.language" />
		&nbsp;:&nbsp;<a href="?language=en">English</a>&nbsp;|&nbsp;<a
			href="?language=sr">Srpski</a>
	</div>

	<div class="col-sm-3 text-right">
		<fmt:message key="movie.date" />
		&nbsp;:&nbsp;
		<myTag:date></myTag:date>
	</div>

	<div class="col-sm-1">
		<sec:authorize access="!isAuthenticated()">
			<a class="btn btn-default" href="<c:url value='/login'/>"> <fmt:message
					key="movie.login" /></a>
		</sec:authorize>

		<sec:authorize access="isAuthenticated()">
			<form action="${pageContext.request.contextPath}/logout"
				method="post">
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" /> <input class="btn btn-default"
					type="submit" value="<fmt:message key="movie.logout" />" />
			</form>
		</sec:authorize>
	</div>
</div>