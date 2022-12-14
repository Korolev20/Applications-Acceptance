
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="header">
    <div id="logo">
        <div id="logo_text">

            <table width="880px">
                <tr>
                    <td>
                        <h1><a href="${currentContext}"; style="text-decoration: none; color: #FFFFFF"><fmt:message key="admissions_committee"/></a></h1></td>
                    <td style="width: 335px">
                        <c:choose>
                            <c:when test="${user!=null}">
                                <a href="Committee?command=personal_settings">
                                    <div style="float: left;padding-left: 30%"><p
                                            style="color: #FFFFFF; margin: unset;float: left;text-decoration: underline"><c:out value="${user.login}"/></p>
                                        <c:choose>
                                            <c:when test="${user.role=='ADMIN'}">
                                                <p style="float: left; margin: unset;color: darkcyan;text-decoration: underline">
                                                &nbsp;(<fmt:message key="admin"/>)</p>
                                        </c:when>
                                            <c:otherwise>
                                                <p style="float: left; margin: unset;color: darkcyan; text-decoration: underline">
                                                    &nbsp;(<fmt:message key="applicant"/>)</p>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </a>
                                <div style="float: right; padding: 0px 5px 0px 0px">
                                    <form action="Committee" method="post">
                                        <input name="command" type="hidden" value="logout"/>
                                        <input type="submit" value="<fmt:message key="log_out"/>"/>
                                    </form>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div id="login_window">
                                    <form action="Committee" method="post">
                                        <input name="command" type="hidden" value="login"/>
                                        <div id="register_massage">
                                            <ins><fmt:message key="register_message"/>
                                                <a href="Committee?command=registration" style="color: darkcyan"><fmt:message key="register_now"/></a>
                                            </ins>
                                        </div>
                                        <div style="padding: 5px 0px 5px 0px">
                                            <input placeholder="<fmt:message key="login"/>"
                                                   style="width: 47%;  margin: 1px" name="login"/>
                                            <input
                                                    placeholder="<fmt:message key="password"/>"
                                                    style="width: 47%; margin: 1px"
                                                    name="password" type="password"/>
                                        </div>
                                        <div style="float: left">
                                            <input type="submit"  style="font-family: Verdana; font-weight: bold;padding: 6px 18px;" value="<fmt:message key="log_in"/>"/>
                                        </div>
                                        <div style=" float:right;  color: crimson"><c:if test="${not empty loginError}">
                                            <p style="margin: unset;padding: 0% 10px 0% 0%"><fmt:message key="${loginError}"/></p>
                                            <c:remove var="loginError" scope="session"/></c:if>
                                        </div>
                                    </form>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </table>
<%--            <div style="float: right; margin-right: 15px"><a id="rus"><img class="locale" alt="ru" src="style/ru.png"/></a><a--%>
<%--                    id="eng"><img class="locale" alt="eng" src="style/eng.png"/></a></div>--%>
<%--            <script src="js/changeLocale.js"></script>--%>
        </div>
    </div>
</div>
