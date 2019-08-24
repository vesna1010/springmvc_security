<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="row">
	<div class="col-sm-12">
		<h1>
			<fmt:message key="movie.userList" />
		</h1>
		<a href="<c:url value='/userForm'/>" class="btn btn-success"><span
			class="glyphicon glyphicon-plus"></span>&nbsp;<fmt:message
				key="movie.addNewUser" /></a> <a href="<c:url value='/'/>"
			class="btn btn-primary"><span class="glyphicon glyphicon-th-list"></span>&nbsp;<fmt:message
				key="movie.movieRanking" /></a>
		<button class="btn btn-default" data-ng-click="getLoggedUsers()"
			data-toggle="modal" data-target="#showUsers">
			<fmt:message key="movie.loggedUsers" />
		</button>
	</div>
</div>

<div class="row">
	<div class="col-sm-12">
		<div class="table-responsive">
			<table class="table table-bordered table-striped table-hover">
				<tr class="success">
					<th><fmt:message key="movie.username" /></th>
					<th><fmt:message key="movie.authorities" /></th>
					<th><fmt:message key="movie.status" /></th>
					<th><fmt:message key="movie.hasVoted" /></th>
					<th><fmt:message key="movie.manage" /></th>
				</tr>
				<c:forEach items="${users}" var="user">
					<tr>
						<td>${user.username}</td>
						<td><c:forEach items="${user.authorities}" var="authority">${authority.name}<br>
							</c:forEach></td>
						<td>${user.enabled}</td>
						<td>${user.vote}</td>
						<td><a href="<c:url value='/disableUser/${user.username}'/>"
							class="btn btn-primary"><span
								class="glyphicon glyphicon-plus"></span>&nbsp;<fmt:message
									key="movie.disable" /></a><a
							href="<c:url value='/deleteUser/${user.username}'/>"
							class="btn btn-danger"><span
								class="glyphicon glyphicon-remove"></span>&nbsp;<fmt:message
									key="movie.delete" /></a><a
							href="<c:url value='/resetVote/${user.username}'/>"
							class="btn btn-default"><fmt:message key="movie.resetVote" /></a>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>

	</div>

	<c:import url="modal.jsp"></c:import>

</div>