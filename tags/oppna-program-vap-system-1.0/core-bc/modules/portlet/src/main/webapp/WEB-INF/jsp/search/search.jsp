<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<portlet:defineObjects />


<portlet:actionURL var="searchUrl">
    <portlet:param name="action" value="search"/>
</portlet:actionURL>

<portlet:resourceURL id="autosuggest" var="autoSuggestUrl"/>

<div class="vap-search-wrap clearfix">
	<form action="${searchUrl}" method="post">
		<div class="search-box-left">
			<div class="search-box-right">
				<div class="search-box-center">
				    <div id="<portlet:namespace />queryWrap" class="search-input-wrap clearfix">
				    	<aui:button type="submit" value="search" cssClass="vap-search-button" />
				        <aui:input name="searchTerm" type="text" cssClass="vap-search-input" label="" value="${searchTerm}" />          
				    </div>
				</div>
			</div>
		</div>
	</form>
</div>

<liferay-util:html-bottom>
	<script type="text/javascript" src="<%= request.getContextPath() %>/js/vap-search.js"></script>
	<script type="text/javascript">
	
		AUI().ready('aui-base','vap-search', function (A) {
			
			var vapSearch = new A.VapSearch({
				autoCompleteContentBox: '#<portlet:namespace />queryWrap',
				autoCompleteInput: '#<portlet:namespace />searchTerm',
				portletNamespace: '<portlet:namespace />',
				urlAutoComplete: '${autoSuggestUrl}'
			});
			
			vapSearch.render();
			
		});
		
	</script>
</liferay-util:html-bottom>
