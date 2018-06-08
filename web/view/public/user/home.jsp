<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <jsp:include page="/template/public/head.jsp" />
    </head>
    <body>
        <jsp:include page="/template/public/nav.jsp" />
        <div class="container">
            <div class="row">
                <div class="text-center">
                    <c:if test="${sessionScope.mensaje != null}">
                        ${mensaje}
                        <c:remove scope="session" var="mensaje"></c:remove>
                    </c:if>
                </div>
                <div class="col-md-6 col-sm-12">
                    <h4 class="text-center">Acceso</h4>
                    <form class="form-horizontal" action="${path}" method="post" onsubmit="return sign_in(this)">
                        <div class="form-group">
                            <label class="col-sm-4 control-label" for="email1">Correo electrónico</label>
                            <div class="col-sm-8">
                                <input class="form-control" type="text" name="email" id="email1" maxlength="50">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label" for="password1">Contraseña</label>
                            <div class="col-sm-8"><input class="form-control" type="password" name="password" id="password1" maxlength="50"></div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-4 col-sm-8">
                                <button class="btn btn-primary" type="submit" >Enviar</button>
                            </div>
                        </div>
                        <input type="hidden" name="form" value="sign-in" >
                    </form>
                </div>
                <div class="col-md-6 col-sm-12">
                    <h4 class="text-center">Registro</h4>
                    <form class="form-horizontal" action="${path}" method="post" onsubmit="return sign_up(this)">
                        <div class="form-group">
                            <label class="col-sm-4 control-label" for="email">Correo electrónico</label>
                            <div class="col-sm-8">
                                <input class="form-control" type="text" name="email" id="email" maxlength="50">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label" for="password">Contraseña</label>
                            <div class="col-sm-8"><input class="form-control" type="password" name="password" id="password" maxlength="50"></div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label" for="re_password">Repita contraseña</label>
                            <div class="col-sm-8"><input class="form-control" type="password" name="re_password" id="re_password" maxlength="50"></div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-4 col-sm-8">
                                <button class="btn btn-primary" type="submit" >Enviar</button>
                            </div>
                        </div>
                        <input type="hidden" name="form" value="sign-up" >
                    </form>
                </div>
            </div>
        </div>
        <jsp:include page="/template/public/scripts.jsp" />
    </body>
</html>
