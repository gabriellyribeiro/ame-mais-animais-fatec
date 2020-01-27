
<form id="frmUsuario" class="proForm col-md-12" action="" method="post">
    <c:choose>
        <c:when test="${param.acao == 'alterar'}">
            <h4>Alterar legisla��o</h4>
        </c:when>
        <c:otherwise>
            <h4>Cadastrar legisla��o</h4>        
        </c:otherwise>
    </c:choose>

    <input id="txtID" name="txtID" type="hidden" autocomplete="off" readonly value="${legislacao.id}"/>

    <div class="form-group">
        <label class="proForm__label" 
               for="txtTitulo">T�tulo:
            <i class="fa fa-spin fa-spinner proForm__spinner"></i>
        </label>
        <input class="proForm__input
               form-control" 
               id="txtTitulo" 
               name="txtTitulo" 
               type="text" 
               placeholder="T�tulo" 
               autocomplete="off" 
               required 
               maxlength="255"
               autofocus
               value="${legislacao.titulo}">
    </div>

    <div class="form-group">
        <label class="proForm__label" 
               for="txtDescricao">Descri��o:
        </label>
        <textarea class="form-control" 
                  id="txtDescricao" 
                  name="txtDescricao" 
                  type="text" 
                  placeholder="Descri��o" 
                  autocomplete="off" 
                  required>${legislacao.descricao}
        </textarea>

        <script>
            CKEDITOR.replace('txtDescricao');
        </script>

    </div>

    <c:choose>
        <c:when test="${param.acao == 'alterar'}">
            <button class="proButton no-rounded-border btn btn-primary" type="submit">Salvar altera��es</button>
        </c:when>
        <c:otherwise>
            <button class="proButton no-rounded-border btn btn-primary" type="submit">Cadastrar</button>
        </c:otherwise>
    </c:choose>
    <a class="btn no-rounded-border btn-default" href="<%=App.BASE_URL%>/private/legislacoes/">Cancelar</a>
</form>