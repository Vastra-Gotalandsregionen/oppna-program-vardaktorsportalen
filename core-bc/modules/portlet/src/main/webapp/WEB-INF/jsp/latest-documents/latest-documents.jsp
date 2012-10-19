<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://liferay.com/tld/portlet" prefix="portlet" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="maxLengthDescription" value="300"/>

<c:if test="${isLoggedIn}">
	<div class="vgr-boxed-content vap-latest-documents">
		<div class="hd">
			<span>Senaste sökningar</span>
		</div>
		<div class="bd">
		    <c:if test="${not empty errorMessage}">
		        <span class="portlet-msg-error">${errorMessage}</span>
		    </c:if>
		    
		    <div class="vgr-list-view-wrap">	
				<ul class="vgr-list-view vgr-list-view-condensed">
				    <c:forEach items="${documents}" var="document" varStatus="status">
				        
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
				                    <a href="${documentUrl}" target="_blank">
				                        <c:out value="${document.title}" escapeXml="false"/>
				                    </a>
				                </h3>
				            </div>
				            <div class="bd description">
				            	<%-- 
			                    <c:choose>
			                        <c:when test="${fn:length(document.description) <= maxLengthDescription }">
			                            <c:out value="${document.description}" escapeXml="false"/>
			                        </c:when>
			                        <c:otherwise>
			                            <c:out value="${fn:substring(document.description, 0, maxLengthDescription)}" escapeXml="false"/>...
			                        </c:otherwise>
			                    </c:choose>
			                    --%>
				            </div>
				            <div class="ft">
				                <c:choose>
				                    <c:when test="${not empty document.revisiondateAsDateObject}">
				                        Publiceringsdatum:&nbsp;
				                        <span class="date"><fmt:formatDate value="${document.revisiondateAsDateObject}" pattern="yyyy-MM-dd"/></span>&nbsp;
				                    </c:when>
				                </c:choose>
				
								<%-- 
				                <portlet:actionURL var="documentUrl">
				                    <portlet:param name="action" value="interceptDocumentSourceClick"/>
				                    <portlet:param name="targetUrl" value="${document.url}"/>
				                    <portlet:param name="documentId" value="${document.id}"/>
				                </portlet:actionURL>
				
				                <c:choose>
				                    <c:when test="${not empty document.source}">
				                        <span class="source">${document.source}: <a href="${documentUrl}" target="_BLANK">G&aring; till k&auml;lla</a></span>&nbsp;
				                    </c:when>
				                    <c:otherwise>
				                        <span class="source">K&auml;lla: <a href="${documentUrl}" target="_BLANK">G&aring; till k&auml;lla</a></span>&nbsp;
				                    </c:otherwise>
				                </c:choose>
				                --%>
				            </div>
				        </li>
				    </c:forEach>
				</ul>
			</div>
			
		</div>
	</div>
</c:if>