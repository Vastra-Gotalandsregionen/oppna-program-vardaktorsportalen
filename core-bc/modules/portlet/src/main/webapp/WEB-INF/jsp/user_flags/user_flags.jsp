<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://liferay.com/tld/portlet" prefix="portlet" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="maxLengthDescription" value="300"/>

<c:if test="${isLoggedIn}">
	<div class="vgr-boxed-content vap-flags">
		<div class="hd">
			<span>Mina flaggor</span>
		</div>
		<div class="bd">
		    <c:if test="${not empty errorMessage}">
		        <span class="portlet-msg-error">${errorMessage}</span>
		    </c:if>
		    
		    <div class="vgr-list-view-wrap">
		    	<c:choose>
			    	<c:when test="${not empty documents}">
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
							                
										<portlet:actionURL var="removeUserFlagUrl">
											<portlet:param name="action" value="removeUserFlag"/>
											<portlet:param name="documentId" value="${document.id_hash}"/>
										</portlet:actionURL>			            
						                
						                <a class="vap-flag-remove" href="${removeUserFlagUrl}">Ta bort flagga</a>
						            </div>
						            <div class="bd description">
						            </div>
						            <div class="ft">
										<c:choose>
											<c:when test="${document.source eq 'Dokumentlager'}">
												<span class="source"><span class="label">G&auml;ller f&ouml;r:</span> V&auml;stra G&ouml;talandsregionen</span>&nbsp;
											</c:when>
											<c:otherwise>
												<span class="source"><spanc class="label">Källa: </span>${document.source}</span>&nbsp;
											</c:otherwise>
										</c:choose>
						            </div>
						        </li>
						    </c:forEach>
						</ul>			    	
			    	</c:when>
			    	<c:otherwise>
			    		<p>Du har inte lagt till n&aring;gra flaggor &auml;nnu.</p>
			    	</c:otherwise>
		    	</c:choose>

			</div>
			
		</div>
	</div>
</c:if>