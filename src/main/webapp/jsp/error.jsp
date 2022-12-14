<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="jspf/head.jspf.jsp" %>
<body>
<%@include file="jspf/header.jspf.jsp" %>

<div id="site_content">

    <div id="banner"></div>
    <%@include file="jspf/side_menu.jspf.jsp" %>

    <div id="content">

        <h1><fmt:message key="something_went_wrong"/></h1>
        <c:if test="${empty error}">
            <c:if test="${requestScope['javax.servlet.error.status_code']==404}">
                <c:set var="error" value="not_found_error"/>
            </c:if>
            <c:if test="${requestScope['javax.servlet.error.status_code']==503}">
                <c:set var="error" value="internal_error"/>
            </c:if>
        </c:if>
        <c:if test="${not empty error}">
            <p style="color: firebrick"><fmt:message key="${error}"/></p>
        </c:if>
        <c:remove var="error" scope="session"/>
    </div>

</div>

<%@ include file="jspf/footer.jspf.jsp" %>

</body>
</html>