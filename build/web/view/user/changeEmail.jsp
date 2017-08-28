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
            <div class="row">
                <div class="col-md-offset-3 col-md-6 col-sm-12">
                    <h4 class="text-center">${title}</h4>
                    <form class="form-horizontal" action="${path}User" method="post" onsubmit="return change_email(this)">
                        <div class="form-group">
                            <label class="col-sm-4 control-label" for="email">Correo electrónico</label>
                            <div class="col-sm-8">
                                <input class="form-control" value="${sessionScope.s_email}" type="text" name="email" id="email" maxlength="50">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label" for="password">Contraseña</label>
                            <div class="col-sm-8"><input class="form-control" type="password" name="password" id="password" maxlength="50"></div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-4 col-sm-8">
                                <button class="btn btn-primary" type="submit" >Enviar</button>
                            </div>
                        </div>
                        <input type="hidden" name="form" value="change-email" >
                    </form>
                </div>
            </div>
        </div>
        <jsp:include page="/template/session/scripts.jsp" />
    </body>
</html>