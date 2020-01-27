<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<% session.setAttribute("secAtual", "perfis");%>
<c:set var="titulo" scope="page" value="Perfis" />

<!DOCTYPE html>
<html lang='pt-BR'>
    <head>
        <meta charset="UTF-8"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, maximum-scale=1.0, minimum-scale=1.0, initial-scale=1.0">
        <title>Ame+Ani+</title>
        <link rel="stylesheet" href="../../css/bootstrap.min.css"/>
        <link rel="stylesheet" href="../../css/style.css">
        <link rel="stylesheet" href="../../css/font-awesome.min.css"/>
        <link rel="stylesheet" href="../../css/proButton.css"/>
        <link rel="stylesheet" href="../../css/proFormulario.css"/>
        <link rel="stylesheet" href="../../css/proMain.css"/>
        <link rel="stylesheet" href="../../css/proTable.css"/>
        <script src="../../js/jquery-2.1.4.min.js"></script>
        <script src="../../js/proBootstrap.min.js"></script>
        <script src="../../js/css_browser_selector.js"></script>
        <script src="../../js/proInputMask.js"></script>
        <script src="../../js/proAme_Formulario.js"></script>
        <script src="../../js/proAme_Main.js"></script>
        <script src="../../js/proAme_Table.js"></script>
        <script src="../../js/jquery.isotope.js" type="text/javascript"></script>
    </head>
    <body>
        <%@include file="../include/header.jsp"%>

        <section class="proContainer">

            <section class="proWrapper">
                <a class="proButton no-rounded-border btn btn-primary btn-large" href="?acao=inserir"><i class="fa fa-plus">
                    </i> Adicionar perfil</a>
                <button class="proButton--link btn btn-link" data-toggle="modal" data-target="#modalFiltros" data-backdrop="static">
                    <i class="fa fa-filter"></i> Filtros</button>
                <div class="table-responsive proTable__container">

                    <table class="proTable proTable--infinite-scroll table table-striped" id="tabPerfis" 
                           data-url="<%=App.BASE_URL%>/private/perfis/" data-scroll-page="2" <c:if test="${param.acao == 'filtrar'}">
                               data-filter='{"acao": "${param.acao}", "txtDescricao": "${param.txtDescricao}"}'</c:if>
                               >
                               <thead>
                                   <tr class="proTable__title">
                                       <th></th>
                                       <th>Descrição</th>
                                   </tr>
                               </thead>

                               <tbody>
                               <c:forEach items="${listaPerfil}" var="p">
                                   <tr>
                                       <td class="text-center">
                                           <a class="proTooltip proButton--link" data-toggle="tooltip" 
                                              data-original-title="Alterar" href="?acao=alterar&id=${p.id}">
                                               <i class="fa fa-edit fa-lg"></i>
                                               <span class="visible-sm visible-xs">Alterar</span>
                                           </a>
                                       </td>
                                       <td class="proTable__nomeUsuario">${p.descricao}</td>
                                   </tr>
                               </c:forEach>
                           </tbody> 
                    </table>
                </div>
            </section>
        </section>

        <!-- Modals -->
        <div class="modal fade" id="modalFiltros">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title">Filtrar resultados</h4>
                    </div>

                    <div class="modal-body clearfix">
                        <form class="proForm col-md-12" id="formFiltrar" method="POST" action="?acao=filtrar">
                            <div class="form-group">
                                <label class="proForm__label" 
                                       for="txtDescricao">Descrição:</label>
                                <input class="proForm__input 
                                       proForm__input--filter
                                       form-control" 
                                       id="txtDescricao" 
                                       name="txtDescricao" 
                                       type="text" 
                                       placeholder="Descrição" 
                                       value="${param.txtDescricao}">
                            </div>
                        </form>
                    </div>

                    <div class="modal-footer">
                        <a class="no-rounded-border btn btn-default pull-left" href="<%=App.BASE_URL%>/private/perfis">Limpar Filtros</a>
                        <button class="no-rounded-border btn btn-default" data-dismiss="modal">Cancelar</button>
                        <button id="btnFiltrar" class="proButton no-rounded-border btn btn-primary" data-loading-text="<i class='fa fa-spinner fa-spin'></i> Filtrando...">Filtrar</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- /Modals -->

        <%@include file="../include/modaisglobais.jsp" %>
        <%@include file="../include/scripts.jsp" %>
        <%@include file="../include/footer.jsp"%>
    </body>
</html>
