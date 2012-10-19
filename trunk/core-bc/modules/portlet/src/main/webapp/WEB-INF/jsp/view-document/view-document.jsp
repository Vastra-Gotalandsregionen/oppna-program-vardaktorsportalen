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

<c:if test="${not empty document}">
	<div class="vap-view-document-wrap">
		<h2 class="title">
			${document.title}
		</h2>
		<div class="description">
			<c:out value="${document.description}" />
		</div>
		<div class="extracted-html">
			<c:out value="${document.extracted_html}" escapeXml="false" />
		</div>
	</div>
</c:if>

<liferay-util:html-top>
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/view-document.css" />
</liferay-util:html-top>