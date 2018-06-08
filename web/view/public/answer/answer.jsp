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
            <div class="row">
                <div class="col-md-offset-3 col-md-6 col-sm-12">
                    <h4 class="text-center">${title}</h4>
                    <h5 class="text-center">De: <a href="${path}?profile=${poll.userProfileMDL.user}">${poll.userProfileMDL.name} <small>(${poll.userProfileMDL.user})</small></a></h5>
                    <p class="text-center">${poll.description}</p>
                    <br>
                    <form class="form-horizontal" action="${path}Answer" method="post" onsubmit="return add_poll_answer()">
                        <div class="form-group">
                            <label class="col-sm-4 control-label">Estrellas</label>
                            <div class="col-sm-8">
                                <label class="radio-inline">
                                    <input type="radio" name="stars" id="inlineRadio1" value="1"> 1
                                </label>
                                <label class="radio-inline">
                                    <input type="radio" name="stars" id="inlineRadio2" value="2"> 2
                                </label>
                                <label class="radio-inline">
                                    <input type="radio" name="stars" id="inlineRadio3" value="3"> 3
                                </label>
                                <label class="radio-inline">
                                    <input type="radio" name="stars" id="inlineRadio4" value="4"> 4
                                </label>
                                <label class="radio-inline">
                                    <input type="radio" name="stars" id="inlineRadio5" value="5"> 5
                                </label>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label" for="comment">Comentario</label>
                            <div class="col-sm-8"><textarea rows="1" class="form-control" name="comment" id="comment" maxlength="1000"></textarea></div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-4 col-sm-8">
                                <button class="btn btn-primary" type="submit" >Enviar</button>
                            </div>
                        </div>
                        <input type="hidden" name="user_key" value="${poll.userProfileMDL.userMDL.key}">
                        <input type="hidden" name="poll_key" value="${poll.key}">
                        <input type="hidden" name="user" value="${poll.userProfileMDL.user}">
                        <input type="hidden" name="form" value="add-answer">
                    </form>
                </div>
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