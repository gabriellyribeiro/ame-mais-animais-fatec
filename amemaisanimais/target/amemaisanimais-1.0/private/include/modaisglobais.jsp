<%@page import="util.App"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Modals -->
        <div class="modal fade" id="modalLogout">
            <div class="modal-dialog">
                
                    <div class="modal-header">
                        <h4 class="modal-title">Desconectar</h4>
                    </div>
                    <div class="modal-body">
                        Deseja realmente sair do sistema?
                    </div>
                    <div class="modal-footer">
                        <button class="no-rounded-border btn btn-default" data-dismiss="modal">Cancelar</button>
                        <a class="proButton no-rounded-border btn btn-primary btn-danger" href="<%=App.BASE_URL%>/private/usuarios/?acao=logout">Sair</a>
                    </div>
                
            </div>
        </div>
                    
        <c:set var="msg" value="${sessionScope.msg}" />
        <c:if test="${msg != null}">
            <div class="modal fade" id="modalMensagem" tabindex="-1" role="dialog" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title">Atenção</h4>
                        </div>

                        <div class="modal-body">
                            <c:choose>
                                <c:when test="${msg[0]}">
                                    <p>${msg[1]} com sucesso!</p>
                                </c:when>
                                <c:otherwise>
                                    <p>Não foi possível ${msg[1]}. Um log do erro será gerado!</p>
                                </c:otherwise>
                            </c:choose>
                        </div>

                        <div class="modal-footer">
                            <a href="#" class="proButton btn btn-primary no-rounded-border" data-dismiss="modal">OK</a>
                        </div>
                    </div>
                </div>
            </div>
        </c:if>