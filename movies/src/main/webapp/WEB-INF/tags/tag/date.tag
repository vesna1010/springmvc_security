<%@ tag language="java" pageEncoding="ISO-8859-1" import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ attribute name="date" required="false" type="java.util.Date"%>

<c:if test="${empty date}">
	<c:set var="date" value="${Date()}" />
</c:if>

<fmt:formatDate value="${date}" pattern="dd.MM.yyyy" />
