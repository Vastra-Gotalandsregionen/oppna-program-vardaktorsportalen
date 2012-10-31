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

<portlet:actionURL var="facetSearchUrl">
    <portlet:param name="action" value="facetSearch"/>
</portlet:actionURL>

<c:if test="${not anySelectableItems and not anyAppliedItems}">
    <c:set var="emptyClass" value="vap-facet-search-wrap-empty"/>
</c:if>

<div class="vap-facet-search-wrap clearfix ${emptyClass}">

	<h2>Begr&auml;nsa din s&ouml;kning</h2>
	<p>Du kan begr&auml;nsa din s&ouml;kning genom att v&auml;lja informationsk&auml;lla nedan.</p>
    
	<c:if test="${anySelectableItems}">
		<aui:field-wrapper label="V&auml;lj informationsk&auml;lla" cssClass="vap-field-wrapper-facets">
			<c:forEach var="entry" items="${searchResult.components.facets.entries}" varStatus="status">
				<ul class="vap-facet-list clearfix">
					<c:forEach var="item" items="${entry.selectableItems}">
						<portlet:actionURL var="facetAddUrl">
						    <portlet:param name="action" value="facetSearch"/>
						    <portlet:param name="searchQuery" value="${item.query}"/>
						    <portlet:param name="searchTerm" value="${searchTerm}"/>
						</portlet:actionURL>
						<li class="clearfix">
							<a class="facet-link facet-link-add" href="${facetAddUrl}" title="L&auml;gg till">${item.displayName} (<fmt:formatNumber type="number" value="${item.count}" />)</a>
						</li>
					</c:forEach>
				</ul>
			</c:forEach>
		</aui:field-wrapper>
	</c:if>    

	<c:if test="${anyAppliedItems}">
		<aui:field-wrapper label="Ta bort filtrering" cssClass="vap-field-wrapper-facets">
			<c:forEach var="entry" items="${searchResult.components.facets.entries}">
				<ul class="vap-facet-list clearfix">
					<c:forEach var="item" items="${entry.appliedItems}">
						<portlet:actionURL var="facetRemoveUrl">
						    <portlet:param name="action" value="facetSearch"/>
						    <portlet:param name="searchQuery" value="${item.query}"/>
						    <portlet:param name="searchTerm" value="${searchTerm}"/>
						</portlet:actionURL>
						<li class="clearfix">
							<a class="facet-link facet-link-remove" href="${facetRemoveUrl}" title="Ta bort">${item.displayName}</a>
						</li>
					</c:forEach>
				</ul>
			</c:forEach>
		</aui:field-wrapper>
	</c:if>

<liferay-util:html-bottom>
	<script type="text/javascript">
		AUI().ready('aui-base', function(A) {
	
			// Fix submit button hover
			var vapInputButtons = A.all('.vap-button input');
			
			vapInputButtons.on('mouseenter', function() { this.addClass('hover'); });
			vapInputButtons.on('mouseleave', function() { this.removeClass('hover'); });
			
		});		
	</script>
</liferay-util:html-bottom>