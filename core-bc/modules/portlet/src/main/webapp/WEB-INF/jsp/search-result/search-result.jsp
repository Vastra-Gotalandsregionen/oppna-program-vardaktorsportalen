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

<div id="<portlet:namespace />resultsWrap" class="vap-results-wrap">
	<c:choose>
		<c:when test="${not empty searchResult.components.documents}">

			<div class="vap-search-results-summary">
				${searchResult.numberOfHits} träffar på <span class="search-word">"${searchTerm}"</span>
			</div>

			<div class="vgr-boxed-content">
				<div class="hd">
					Sökresultat
				</div>
				<div class="bd">
					<div class="vgr-list-view-wrap">			
						<ul class="vgr-list-view">
							<c:forEach items="${searchResult.components.documents}" var="document" varStatus="status">
								<c:set var="listItemCssClass" value="" scope="page" />

								<c:if test="${(status.index)%2 ne 0}">
									<c:set var="listItemCssClass" value="${listItemCssClass} vgr-list-view-item-odd" scope="page" />
								</c:if>
								
								<c:if test="${status.last}">
									<c:set var="listItemCssClass" value="${listItemCssClass} vgr-list-view-item-last" scope="page" />
								</c:if>
							
								<li class="vgr-list-view-item ${listItemCssClass}">
								
			                         <portlet:actionURL var="documentUrl">
			                  	       <portlet:param name="action" value="interceptDocumentSourceClick"/>
			                           <portlet:param name="targetUrl" value="${document.url}"/>
			                           <portlet:param name="documentId" value="${document.id_hash}"/>
			                         </portlet:actionURL>					
			                        <div class="hd clearfix">
			                            <h3 class="title">
			                                <a href="${documentUrl}" target="_BLANK">
												<c:out value="${document.title}" escapeXml="false"/>
											</a>
										</h3>
									</div>
									<div class="bd description">
										<c:out value="${document.description}" escapeXml="false"/>
									</div>
									<div class="ft">
									
										<c:choose>
											<c:when test="${document.source eq 'Dokumentlager'}">
												<c:if test="${not empty document.dcDateValidToAsDateObject}">
													<span class="label">Giltigt t o m: </span><span class="date"><fmt:formatDate value="${document.dcDateValidToAsDateObject}" pattern="yyyy-MM-dd"/></span>&nbsp;
												</c:if>
												<span class="source"><span class="label">G&auml;ller f&ouml;r:</span> V&auml;stra G&ouml;talandsregionen</span>&nbsp;
											</c:when>
											<c:otherwise>
												<c:if test="${not empty document.revisiondateAsDateObject}">
													<span class="label">Senast &auml;ndrad: </span><span class="date"><fmt:formatDate value="${document.revisiondateAsDateObject}" pattern="yyyy-MM-dd"/></span>&nbsp;
												</c:if>
												<span class="source"><spanc class="label">Källa: </span>${document.source}</span>&nbsp;
											</c:otherwise>
										</c:choose>
			
									</div>
								</li>
							</c:forEach>
						</ul>
						
						<c:if test="${not empty searchResult.components.documents}">
							<div class="paginator-wrap">
								<portlet:renderURL var="pageIteratorURL">
									<portlet:param name="isPaginatorCall" value="true" />
									<portlet:param name="searchTerm" value="${searchTerm}" />
									<portlet:param name="searchQuery" value="${searchQuery}" />
								</portlet:renderURL>
								
								<c:set var="searchMaxPages" value="9" scope="page" />
								<c:set var="paginatorType" value="rp_paginator_type_2" scope="page" />
								
								<liferay-ui:page-iterator
									cur="${searchOffset}"
									curParam="searchOffset"
									delta="${searchDelta}"
									maxPages="${searchMaxPages}"
									total="${searchResult.numberOfHits}"
									type="${paginatorType}"
									url="${pageIteratorURL}"
								/>
								<c:set var="lastEntryNumber" value="${searchOffset * searchDelta > searchResult.numberOfHits ? searchResult.numberOfHits : searchOffset * searchDelta}"/>
								<p class="search-info">Visar tr&auml;ff ${(searchOffset - 1) * searchDelta + 1} till ${lastEntryNumber} av totalt ${searchResult.numberOfHits} tr&auml;ffar.</p>
								
							</div>
						</c:if>				
						
					</div>
				</div>
			</div>			

		</c:when>
        <c:when test="${not empty errorMessage}">
            <span class="portlet-msg-error">${errorMessage}</span>
        </c:when>
		<c:when test="${empty searchResult.components.documents and not empty searchQuery}">
			<p>Inga s&ouml;kresultat hittades</p>
		</c:when>
	</c:choose>
</div>

<liferay-util:html-bottom>
	<script type="text/javascript" src="<%= request.getContextPath() %>/js/vap-search-results.js"></script>
	<script type="text/javascript">
	
		AUI().ready('aui-base','vap-search-results', function (A) {
			
			var resultsWrap = A.one('#<portlet:namespace />resultsWrap');
			
			var vapSearchResults = new A.VapSearchResults({
				resultsWrap: resultsWrap
			});
			
			vapSearchResults.render();
			
		});
		
	</script>
</liferay-util:html-bottom>