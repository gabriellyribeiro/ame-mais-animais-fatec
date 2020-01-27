<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="util.App"%>
<script src="<%=App.BASE_URL%>/js/jquery-2.1.4.min.js"></script>
<script src="<%=App.BASE_URL%>/js/jquery.min.js"></script>
<script src="<%=App.BASE_URL%>/js/proBootstrap.min.js"></script>
<script src="<%=App.BASE_URL%>/js/css_browser_selector.js"></script>
<script src="<%=App.BASE_URL%>/js/proAme_Formulario.js"></script>
<script src="<%=App.BASE_URL%>/js/proAme_Main.js"></script>
<script src="<%=App.BASE_URL%>/js/proAme_Table.js"></script>
<script src="<%=App.BASE_URL%>/js/jquery.isotope.js" type="text/javascript"></script>
<script src="<%=App.BASE_URL%>/js/proAme_Funcoes.js"></script>
<script src="<%=App.BASE_URL%>/js/proInputMask.js"></script>


<c:set var="exception" value="${sessionScope.exception}" />
<c:if test="${exception}">
    <div class="modal fade in" id="modalException" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">Atenção</h4>
                </div>
                <div class="modal-body">
                    <p>Ops! Ocorreu um erro, tente mais tarde novamente.</p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-danger no-rounded-border" data-dismiss="modal">OK</button>
                </div>
            </div>
        </div>
    </div>
</c:if>
<c:remove var="exception" scope="session" />