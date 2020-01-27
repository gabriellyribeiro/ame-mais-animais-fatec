
<form id="frmPerfil" class="proForm col-md-12" action="" method="post">
    <c:choose>
        <c:when test="${param.acao == 'alterar'}">
            <h4>Alterar perfil</h4>
        </c:when>
        <c:otherwise>
            <h4>Cadastrar perfil</h4>        
        </c:otherwise>
    </c:choose>

    <input id="txtID" name="txtID" type="hidden" autocomplete="off" readonly value="${perfil.id}"/>

    <div class="form-group">
        <label class="proForm__label" 
               for="txtDescricao">Descrição: 
            <i class="fa fa-spin fa-spinner proForm__spinner"></i>
        </label>
        <input class="proForm__input 
               form-control" 
               id="txtDescricao" 
               name="txtDescricao" 
               type="text" 
               placeholder="Descrição" 
               autocomplete="off" 
               required 
               maxlength="30" 
               autofocus
               value="${perfil.descricao}">
    </div>

    <c:choose>
        <c:when test="${param.acao == 'alterar'}">
            <button class="proButton no-rounded-border btn btn-primary" type="submit">Salvar alterações</button>
        </c:when>
        <c:otherwise>
            <button class="proButton no-rounded-border btn btn-primary" type="submit">Cadastrar</button>
        </c:otherwise>
    </c:choose>
    <a class="btn no-rounded-border btn-default" href="<%=App.BASE_URL%>/private/perfis/">Cancelar</a>
</form>