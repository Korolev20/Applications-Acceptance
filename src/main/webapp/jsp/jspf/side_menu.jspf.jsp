
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="side_menu">

    <h2><fmt:message key="menu"/>:</h2>
    <ul>
        <li>
            <h4>
                <a href="Committee?command=view_faculties" style="color: black"><fmt:message key="show_faculties"/></a>
            </h4>
        </li>
<%--        <li>--%>
<%--            <h4>--%>
<%--                <a href="Committee?command=view_enrollments" style=" color: black">--%>
<%--                    <fmt:message key="enrollments_results"/>--%>
<%--                </a>--%>
<%--            </h4>--%>
<%--        </li>--%>
    </ul>

    <c:if test="${user.role=='ADMIN'}">
        <h3 style="margin-top: 20px"><font color="#008b8b"><fmt:message key="admin_menu"/>:</font></h3>
        <ul style="text-align: left">
            <li>
                <h4>
                    <a href="Committee?command=view_subjects" style="color: black">
                        <fmt:message key="show_subjects"/>
                    </a>
                </h4>
            </li>
            <li>
                <h4>
                    <a href="Committee?command=view_applicants" style="color: black">
                        <fmt:message key="current_applications"/>
                    </a>
                </h4>
            </li>
                        <li>
                            <h4>
                                <a href="Committee?command=view_enrollments" style=" color: black">
                                    <fmt:message key="enrollments_results"/>
                                </a>
                            </h4>
                        </li>
        </ul>

    </c:if>
</div>