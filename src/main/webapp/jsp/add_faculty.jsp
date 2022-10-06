
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

        <h1 style="margin-bottom: 2%"><fmt:message key="add_faculty"/>:</h1>

        <c:if test="${error!=null}"><p class="register_field" style="color: firebrick;text-align: center"><fmt:message
                key="${error}"/></p>
            <c:remove var="error" scope="session"/>
        </c:if>

        <form action="Committee" method="post">
            <input type="hidden" name="command" value="add_faculty"/>

            <div style="margin: 0px auto 20px 30%"><input type="text" name="faculty_name" placeholder="<fmt:message
            key="faculty_name"/>"/>
                <input type="number" name="capacity" placeholder="<fmt:message key="capacity"/>"/> ${error}
            </div>
            <c:remove var="error" scope="session"/>
            <c:forEach items="${subjects}" var="subject">
                <input style="margin-left: 45%" type="checkbox" name="subject_id"
                       value="${subject.subjectId}"/><c:out value="${subject.name}"/>
                <br/>
            </c:forEach>
            <input style="margin: 15px auto 30px 45%" type="submit" value="<fmt:message key="add"/>"/>
        </form>
    </div>
</div>

<%@ include file="jspf/footer.jspf.jsp" %>

</body>
</html>