
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

        <h1><fmt:message key="completed_enrollments"/>:</h1>

        <c:choose>
            <c:when test="${not empty enrollments}">
                <table id="myTable" class="display">
                    <thead>
                    <tr>
                        <th><fmt:message key="enrollment"/></th>
                        <th><fmt:message key="start_date"/></th>
                        <th><fmt:message key="end_date"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${enrollments}" var="enrollment">
                        <tr>
                            <td>
                                <a style="color: black" href="Committee?command=view_applicants&id=${enrollment.enrollmentId}">
                                    <fmt:message key="enrollment"/> №<c:out value="${enrollment.enrollmentId}"/></a>
                            </td>
                            <td><font color="#008b8b"><fmt:formatDate value="${enrollment.startDate}"
                                                                      pattern="dd-MM-yyyy HH:mm"/></font></td>
                            <td><font color="#008b8b"><fmt:formatDate value="${enrollment.endDate}"
                                                                      pattern="dd-MM-yyyy HH:mm"/></font></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <p style="text-align: center"><font color="#b22222"><fmt:message key="no_enrollments"/></font>
                </p>
            </c:otherwise>
        </c:choose>

    </div>

</div>

<%@ include file="jspf/footer.jspf.jsp" %>

</body>
</html>