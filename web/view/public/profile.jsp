<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <c:if test="${sessionScope.s_id == null}">
            <jsp:include page="/template/public/head.jsp" />
        </c:if>
        <c:if test="${sessionScope.s_id != null}">
            <jsp:include page="/template/session/head.jsp" />
        </c:if>
    </head>
    <body>
        <c:if test="${sessionScope.s_id == null}">
            <jsp:include page="/template/public/nav.jsp" />
        </c:if>
        <c:if test="${sessionScope.s_id != null}">
            <jsp:include page="/template/session/nav.jsp" />
        </c:if>
        <div class="container">
            <div class="text-center">
                <c:if test="${sessionScope.mensaje != null}">
                    ${mensaje}
                    <c:remove scope="session" var="mensaje"></c:remove>
                </c:if>
            </div>
            <h4 class="text-center">${title}</h4>
            <div class="row">
            </div>
        </div>
        <c:if test="${sessionScope.s_id == null}">
            <jsp:include page="/template/public/scripts.jsp" />
        </c:if>
        <c:if test="${sessionScope.s_id != null}">
            <jsp:include page="/template/session/scripts.jsp" />
        </c:if>
    </body>
</html>