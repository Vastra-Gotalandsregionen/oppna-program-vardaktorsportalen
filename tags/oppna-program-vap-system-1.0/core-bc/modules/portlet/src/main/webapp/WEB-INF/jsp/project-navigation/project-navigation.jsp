<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects/>

<div class="vgr-boxed-content vap-project-navigation">
	<div class="hd">
		<span>Välj projekt</span>
	</div>
	<div class="bd">
	    
	    <div class="vgr-list-view-wrap">
	    
			<c:choose>
				<c:when test="${not empty projectArticles}">
				
					<ul class="vgr-list-view vgr-list-view-condensed">
						<c:set var="maxLengthDescription" value="100"/>
						
						<c:forEach var="article" items="${projectArticles}" varStatus="status">
						
							<c:set var="listItemCssClass" value="" scope="page" />
				
							<c:if test="${(status.index)%2 ne 0}">
								<c:set var="listItemCssClass" value="${listItemCssClass} vgr-list-view-item-odd" scope="page" />
							</c:if>
							
							<c:if test="${status.last}">
								<c:set var="listItemCssClass" value="${listItemCssClass} vgr-list-view-item-last" scope="page" />
							</c:if>
					        
					        <li class="vgr-list-view-item ${listItemCssClass}">
					        
								<portlet:renderURL var="showArticleUrl">
								    <portlet:param name="projectArticleUrlTitle" value="${article.urlTitle}" />
								</portlet:renderURL>
								
					            <div class="hd clearfix">
					                <h3 class="title">
					                    <a href="${showArticleUrl}">
					                        ${article.title}
					                    </a>
					                </h3>
					            </div>
					            <div class="bd description">
					            
									<c:choose>
										<c:when test="${fn:length(article.description) <= maxLengthDescription }">
											<c:out value="${article.description}" escapeXml="false"/>
										</c:when>
										<c:otherwise>
											<c:out value="${fn:substring(article.description, 0, maxLengthDescription)}" escapeXml="false"/>...
										</c:otherwise>
									</c:choose>
					            
					            </div>
					            <div class="ft">
					            	<span class="date"><fmt:formatDate value="${article.displayDate}" pattern="yyyy-MM-dd"/></span>
					            </div>
					            
							</li>
						</c:forEach>
					</ul>
				
				</c:when>
				<c:otherwise>
					<p>Det finns inga projekt just nu</p>
				</c:otherwise>
			</c:choose>

		</div>
		
	</div>
</div>