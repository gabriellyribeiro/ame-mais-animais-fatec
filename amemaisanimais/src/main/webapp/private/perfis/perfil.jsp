<%@page import="util.App"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>

<html lang='pt-BR'>
    <head>
        <meta charset="UTF-8"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, maximum-scale=1.0, minimum-scale=1.0, initial-scale=1.0">
        <title>Ame+Ani+</title>
        <link rel="stylesheet" href="<%=App.BASE_URL%>/css/bootstrap.min.css"/>
        <link rel="stylesheet" href="<%=App.BASE_URL%>/css/style.css">
        <link rel="stylesheet" href="<%=App.BASE_URL%>/css/font-awesome.min.css"/>
        <link rel="stylesheet" href="<%=App.BASE_URL%>/css/proButton.css"/>
        <link rel="stylesheet" href="<%=App.BASE_URL%>/css/proFormulario.css"/>
        <link rel="stylesheet" href="<%=App.BASE_URL%>/css/proMain.css"/>
        <link rel="stylesheet" href="<%=App.BASE_URL%>/css/proTable.css"/>
        <script src="<%=App.BASE_URL%>/js/jquery-2.1.4.min.js"></script>
        <script src="<%=App.BASE_URL%>/js/proBootstrap.min.js"></script>
        <script src="<%=App.BASE_URL%>/js/css_browser_selector.js"></script>
        <script src="<%=App.BASE_URL%>/js/proInputMask.js"></script>
        <script src="<%=App.BASE_URL%>/js/proAme_Formulario.js"></script>
        <script src="<%=App.BASE_URL%>/js/proAme_Main.js"></script>
        <script src="<%=App.BASE_URL%>/js/proAme_Table.js"></script>
        <script src="<%=App.BASE_URL%>/js/jquery.isotope.js" type="text/javascript"></script>
        <script src="<%=App.BASE_URL%>/js/proAme_Perfil.js" type="text/javascript"></script>
    </head>
    <body>

        <c:choose>
            <c:when test="${requestScope.acao == 'alterar'}">
                <c:set var="titulo" scope="page" value="Alterar Perfil" />
            </c:when>
            <c:otherwise>
                <c:set var="titulo" scope="page" value="Cadastrar Perfil" />
            </c:otherwise>
        </c:choose>

        <%@include file="../include/header.jsp"%>

        <section class="proContainer">

            <div class="proWrapper">
                <%@include file="formulario.jsp"%>

                <c:set var="msg" value="${requestScope.formPerfil}" />
                <c:choose>
                    <c:when test="${msg != null && (msg == 'cadastrado' || msg == 'alterado')}">
                        <div class="modal fade" id="modalMensagem" tabindex="-1" role="dialog" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h4 class="modal-title">Atenção</h4>
                                    </div>

                                    <div class="modal-body">
                                        <p>Perfil ${msg} com sucesso!</p>
                                    </div>

                                    <div class="modal-footer">
                                        <a href="#" class="proButton btn btn-primary no-rounded-border" data-dismiss="modal">OK</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:when>
                    <c:when test="${msg != null}">
                        <div class="modal fade" id="modalMensagem" tabindex="-1" role="dialog" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h4 class="modal-title">Atenção</h4>
                                    </div>

                                    <div class="modal-body">
                                        <p>Não foi possível ${msg} o perfil. Um log do erro será gerado!</p>
                                    </div>

                                    <div class="modal-footer">
                                        <a href="#" class="proButton btn btn-primary no-rounded-border" data-dismiss="modal">OK</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:when>
                </c:choose>
            </div>
        </section>

        <%@include file="../include/modaisglobais.jsp" %>
        <%@include file="../include/scripts.jsp" %>
        <%@include file="../include/footer.jsp"%>
    </body>
</html>
