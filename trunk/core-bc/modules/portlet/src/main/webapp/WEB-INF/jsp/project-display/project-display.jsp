<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<portlet:defineObjects/>

<div class="info-box vap-project-display-wrap">
	<c:choose>
		<c:when test="${not empty article}">
			<h1 class="title">${article.title}</h1>
			<liferay-ui:journal-article articleId="${article.articleId}" groupId="${article.groupId}" />
		</c:when>
		<c:otherwise>
			<%-- Do nothing right now --%>
		</c:otherwise>
	</c:choose>
</div>