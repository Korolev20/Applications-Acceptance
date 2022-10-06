
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

        <h1><fmt:message key="apply_for"/> <font color="#008b8b"><c:out value="${faculty.name}"/></font>:</h1>

        <form id="infoMessage" action="Committee" method="post">
            <p class="register_field"><fmt:message key="enter_subject_ratings"/>:</p>

            <c:if test="${error!=null}"><p class="register_field" style="color: firebrick"><fmt:message key="${error}"/></p>
                <c:remove var="error" scope="session"/>
            </c:if>
            <input name="command" type="hidden" value="apply"/>

            <c:forEach items="${faculty.requiredSubjects}" var="subject">
                <input class="register_field" name="grade" placeholder="${subject.name}"/><br>
            </c:forEach>
            <input type="hidden" name="id" value="${faculty.id}"/>
            <input class="submitButton" type="submit" value="<fmt:message key="apply"/>"/><br>
        </form>
    </div>

</div>

<%@ include file="jspf/footer.jspf.jsp" %>

</body>
</html>