
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@include file="jspf/header.jspf.jsp" %>
<%@include file="jspf/head.jspf.jsp" %>
<body>

<div id="site_content">

  <div id="banner"></div>

<%--  <%@include file="jspf/side_menu.jspf.jsp" %>--%>

  <div id="content">

    <h1><fmt:message key="enrollment"/><c:if test="${not empty enrollment}"> </c:if>:
      <c:choose>
      <c:when test="${enrollment.state=='OPENED'}"><font color="#008b8b"><fmt:message key="${enrollment.state}"/></font></h1>
    <p id="infoMessage"><fmt:message key="start_date"/>: <font color="#008b8b"><fmt:formatDate value="${enrollment.startDate}"
                                                                                               pattern="dd-MM-yyyy mm:mm"/></font></p>
    </c:when>
    <c:otherwise><span style="color: #b22222; "><fmt:message key="CLOSED"/></span>
      <c:if test="${not empty enrollment}"><p id="infoMessage"><fmt:message key="end_date"/>: <font color="#b22222"><fmt:formatDate value="${enrollment.endDate}"
                                                                                                                                    pattern="dd-MM-yyyy mm:mm"/></font></p></c:if>
      <p id="infoMessage"><span style="color: #b22222; "><fmt:message key="come_back_later"/></span></p>
    </c:otherwise>
    </c:choose>
  </div>
</div>

<%@ include file="jspf/footer.jspf.jsp" %>

</body>
</html>

