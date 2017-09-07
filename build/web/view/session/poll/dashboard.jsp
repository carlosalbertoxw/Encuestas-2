<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <jsp:include page="/template/session/head.jsp" />
    </head>
    <body>
        <jsp:include page="/template/session/nav.jsp" />
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
        <jsp:include page="/template/session/scripts.jsp" />
    </body>
</html>