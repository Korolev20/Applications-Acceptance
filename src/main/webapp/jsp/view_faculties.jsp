
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

        <h1><fmt:message key="faculties"/>:</h1>

        <ol >
            <c:forEach var="faculty" items="${faculties}">
                <li><a style="color: darkcyan" href="Committee?command=view_faculty&id=${faculty.id}"><c:out value="${faculty.name}"/></a></li>
            </c:forEach>
        </ol>
        <c:if test="${user.role=='ADMIN'}">
            <form class="submitButton" action="Committee" method="get">
                <input type="hidden" name="command" value="add_faculty"/>
                <input type="submit" value="<fmt:message key="add_faculty"/>"/>
            </form>
        </c:if>

    </div>

</div>

<%@ include file="jspf/footer.jspf.jsp" %>

</body>
</html>