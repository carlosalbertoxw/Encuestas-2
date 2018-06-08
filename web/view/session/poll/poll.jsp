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
            <div class="col-md-offset-3 col-md-6 col-sm-12">
                <h4 class="text-center">${title}</h4>
                <form class="form-horizontal" action="${path}Poll" method="post" onsubmit="return poll(this)">
                    <div class="form-group">
                        <label class="col-sm-4 control-label" for="title">Título</label>
                        <div class="col-sm-8"><input value="${poll.title}" class="form-control" type="text" name="title" id="title" maxlength="250"></div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label" for="description">Descripcón</label>
                        <div class="col-sm-8"><textarea rows="1" class="form-control" name="description" id="description" maxlength="500">${poll.description}</textarea></div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label" for="position">Posición</label>
                        <div class="col-sm-8"><input value="${poll.position}" class="form-control" type="text" name="position" id="position" maxlength="6"></div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-4 col-sm-8">
                            <button class="btn btn-primary" type="submit" >Enviar</button>
                        </div>
                    </div>
                    <c:if test="${poll.key != null}">
                        <input type="hidden" name="key" value="${poll.key}">
                        <input type="hidden" name="form" value="edit-poll">
                    </c:if>
                    <c:if test="${poll.key == null}">
                        <input type="hidden" name="form" value="add-poll">
                    </c:if>
                </form>
            </div>
        </div>
        <jsp:include page="/template/session/scripts.jsp" />
    </body>
</html>