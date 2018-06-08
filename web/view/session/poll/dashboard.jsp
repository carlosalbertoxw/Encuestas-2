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
                <c:forEach items="${polls}" var="poll">
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <c:out value="${poll.title}" />
                            <br>
                            <c:out value="${poll.description}" />
                        </div>
                        <div class="panel-footer">
                            <a href="${path}Poll?page=edit-poll&key=${poll.key}">Editar</a> | <a onclick="return confirm('Estas seguro de borrar este registro')" href="${path}Poll?page=delete-poll&key=${poll.key}">Borrar</a> | <a href="${path}Answer?page=view-answers&key=${poll.key}">Ver respuestas</a> | <a href="${path}Answer?page=add-answer&key=${poll.key}">Responder</a>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
        <jsp:include page="/template/session/scripts.jsp" />
    </body>
</html>