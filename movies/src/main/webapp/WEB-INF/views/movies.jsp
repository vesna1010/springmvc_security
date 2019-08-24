<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="myTag" tagdir="/WEB-INF/tags/tag/"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<div class="row">
	<div class="col-sm-12">
		<h1>
			<fmt:message key="movie.moviePoll" />
		</h1>

		<sec:authorize access="isAuthenticated()">
			<a href="<c:url value='/movieForm'/>" class="btn btn-success"> <span
				class="glyphicon glyphicon-plus"></span>&nbsp;<fmt:message
					key="movie.addNewMovie" />
			</a>
			<c:if test="${not empty movies}">
				<a class="btn btn-primary" data-ng-click="toggle()" href="#"><span
					class="glyphicon glyphicon-pencil"></span>&nbsp;<fmt:message
						key="movie.editMovieList" /></a>
			</c:if>
		</sec:authorize>

		<sec:authorize access="hasAuthority('ADMIN')">
			<a href="<c:url value='/users'/>" class="btn btn-primary"> <span
				class="glyphicon glyphicon-user"></span>&nbsp;<fmt:message
					key="movie.editUsers" />
			</a>
		</sec:authorize>

	</div>
</div>

<div class="row">
	<div class="col-sm-12">

		<c:if test="${not empty movies}">
			<div class="table-responsive">
				<table class="table table-bordered table-striped table-hover">

					<tr class="success">
						<th><fmt:message key="movie.rank" /></th>
						<th><fmt:message key="movie.votes" /></th>
						<th><fmt:message key="movie.title" /></th>
						<th><fmt:message key="movie.genre" /></th>
						<th><fmt:message key="movie.year" /></th>
						<th><fmt:message key="movie.actors" /></th>
						<th><fmt:message key="movie.url" /></th>
						<th><fmt:message key="movie.addedBy" /></th>
						<th><fmt:message key="movie.date" /></th>

						<sec:authorize access="isAuthenticated()">
							<th><fmt:message key="movie.vote" /></th>
						</sec:authorize>

						<th data-ng-show="showEditButtons"><fmt:message
								key="movie.manage" /></th>
					</tr>

					<c:forEach items="${movies}" var="movie" varStatus="status">
						<tr>
							<td>${status.index+1}</td>
							<td class="text-danger">${movie.votes}</td>
							<td>${movie.title}</td>
							<td>${movie.genre.name()}</td>
							<td>${movie.year}</td>
							<td>${movie.actors}</td>
							<td><a href="${movie.url}"><span
									class="glyphicon glyphicon-share-alt"></span><strong
									class="text-primary">IMDb</strong></a></td>
							<td>${movie.user.username}</td>
							<td><myTag:date date="${movie.date}" /></td>

							<sec:authorize access="isAuthenticated()">
								<td><a href="<c:url value='/saveVote/${movie.id}'/>"
									class="btn btn-primary"><span
										class="glyphicon glyphicon-thumbs-up"></span></a></td>
								<td data-ng-show="showEditButtons"><a
									href="<c:url value='/editMovie/${movie.id}'/>"
									class="btn btn-primary"><span
										class="glyphicon glyphicon-plus"></span>&nbsp;<fmt:message
											key="movie.edit" /></a><a
									href="<c:url value='/deleteMovie/${movie.id}'/>"
									class="btn btn-danger"><span
										class="glyphicon glyphicon-remove"></span>&nbsp;<fmt:message
											key="movie.delete" /></a></td>
							</sec:authorize>
						</tr>

					</c:forEach>

				</table>
			</div>
		</c:if>

	</div>
</div>