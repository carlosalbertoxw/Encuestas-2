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
            <p class="text-center">${poll.description}</p>
            <br>
            <div class="row">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-condensed">
                        <thead>
                            <tr>
                                <th>Estrellas</th>
                                <th>Comentarios</th>
                            </tr>
                        </thead>
                        <c:forEach items="${answers}" var="answer">
                            <tr>
                                <td>${answer.stars}</td>
                                <td>${answer.comment}</td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>
        </div>
        <jsp:include page="/template/session/scripts.jsp" />
    </body>
</html>