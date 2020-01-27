<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<% session.setAttribute("secAtual", "legislacoes");%>
<c:set var="titulo" scope="page" value="Legislação" />

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
                <a class="proButton no-rounded-border btn btn-primary btn-large" href="?acao=inserir">
                    <i class="fa fa-plus"></i> Adicionar legislação
                </a>

                <button class="proButton--link btn btn-link" data-toggle="modal" data-target="#modalFiltros" data-backdrop="static">
                    <i class="fa fa-filter"></i> Filtros
                </button>

                <div class="table-responsive proTable__container">

                    <table class="proTable proTable--infinite-scroll table table-striped" 
                           id="tabLegislacoes" data-url="<%=App.BASE_URL%>/private/legislacoes/" 
                           data-scroll-page="2" <c:if test="${param.acao == 'filtrar'}">
                               data-filter='{"acao": "${param.acao}", "txtTitulo": "${param.txtTitulo}"}'</c:if>
                               >
                               <thead>
                                   <tr class="proTable__title">
                                       <th></th>
                                       <th></th>
                                       <th>Título</th>
                                   </tr>
                               </thead>

                               <tbody>
                               <c:forEach items="${listaLegislacao}" var="l">
                                   <tr>
                                       <td class="text-center" data-toggle="modal" data-target="#modalExcluir" data-backdrop="static" 
                                           data-row='{"acao": "excluir", "id": "${l.id}"}' 
                                           data-description="Deseja realmente excluir a legislação ${l.id} - ${l.titulo}?">
                                           <a class="proTooltip proButton--link" data-toggle="tooltip" data-original-title="Excluir" href="#">
                                               <i class="fa fa-trash-o fa-lg"></i>
                                               <span class="visible-sm visible-xs">Excluir</span>
                                           </a>
                                       </td>

                                       <td class="text-center">
                                           <a class="proTooltip proButton--link" 
                                              data-toggle="tooltip" data-original-title="Alterar" href="?acao=alterar&id=${l.id}">
                                               <i class="fa fa-edit fa-lg"></i>
                                               <span class="visible-sm visible-xs">Alterar</span>
                                           </a>
                                       </td>

                                       <td class="proTable__nomeUsuario">${l.titulo}</td>
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
                                       for="txtTitulo">Título:</label>
                                <input class="proForm__input 
                                       proForm__input--filter
                                       form-control" 
                                       id="txtTitulo" 
                                       name="txtTitulo" 
                                       type="text" 
                                       placeholder="Título" 
                                       value="${param.txtTitulo}">
                            </div>
                        </form>
                    </div>

                    <div class="modal-footer">
                        <a class="no-rounded-border btn btn-default pull-left" href="<%=App.BASE_URL%>/private/legislacoes">Limpar Filtros</a>
                        <button class="no-rounded-border btn btn-default" data-dismiss="modal">Cancelar</button>
                        <button id="btnFiltrar" class="proButton no-rounded-border btn btn-primary" 
                                data-loading-text="<i class='fa fa-spinner fa-spin'></i> Filtrando...">Filtrar</button>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal fade" id="modalExcluir">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title">Excluir legislação</h4>
                    </div>

                    <div class="modal-body clearfix"><p class="proModal__description"></p></div>

                    <div class="modal-footer">
                        <a class="proButton--cancel no-rounded-border btn btn-default" data-dismiss="modal" href="#">Cancelar</a>
                        <button class="proButton proButton--confirm no-rounded-border btn btn-primary" 
                                data-url="<%=App.BASE_URL%>/private/legislacoes/" 
                                data-loading-text="<i class='fa fa-spinner fa-spin'></i> Excluindo...">Excluir</button>
                    </div>
                </div>
            </div>
        </div>
        <!-- /Modals -->

        <%@include file="../include/modaisglobais.jsp" %>
        <%@include file="../include/footer.jsp"%>
    </body>
</html>
