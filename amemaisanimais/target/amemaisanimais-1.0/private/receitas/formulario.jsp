<form id="frmReceita" 
      role="form" 
      class="proForm col-md-12" 
      action="" 
      method="post">
    <c:choose>
        <c:when test="${param.acao == 'alterar'}">
            <h4>Alterar receita</h4>
        </c:when>
        <c:otherwise>
            <h4>Cadastrar receita</h4>        
        </c:otherwise>
    </c:choose>

    <input id="txtID" name="txtID" type="hidden" autocomplete="off" readonly value="${receita.id}"/>

    <div class="form-group">
        <label class="proForm__label" for="txtTitulo">Título:
            <i class="fa fa-spin fa-spinner proForm__spinner"></i>
        </label>
        <input class="col-md-12
               form-control" 
               id="txtTitulo" 
               name="txtTitulo" 
               type="text" 
               placeholder="Título" 
               autocomplete="off" 
               required
               autofocus
               maxlength="255" 
               value="${receita.titulo}"/>
    </div>

    <div class="form-group">
        <label for="txtIngrediente">Ingredientes:</label>
        <textarea class="col-md-12 
                  form-control"
                  id="txtIngrediente" 
                  name="txtIngrediente" 
                  type="text" 
                  placeholder="Ingredientes" 
                  autocomplete="off" 
                  required>${receita.ingrediente}
        </textarea>

        <script>
            CKEDITOR.replace('txtIngrediente');
        </script>
    </div>

    <div class="form-group">
        <label for="txtModoPreparo">Modo de preparo:</label>
        <textarea class="col-md-12
                  form-control"
                  id="txtModoPreparo" 
                  name="txtModoPreparo" 
                  type="text" 
                  placeholder="Mode de preparo" 
                  autocomplete="off" 
                  required>${receita.modoPreparo}</textarea>

        <script>
            CKEDITOR.replace('txtModoPreparo');
        </script>
    </div>

    <c:choose>
        <c:when test="${param.acao == 'alterar'}">
            <button class="proButton no-rounded-border btn btn-primary" 
                    type="submit">Salvar alterações</button>
        </c:when>
        <c:otherwise>
            <button class="proButton no-rounded-border btn btn-primary" type="submit">Cadastrar</button>
        </c:otherwise>
    </c:choose>
    <a class="btn no-rounded-border btn-default" href="<%=App.BASE_URL%>/private/receitas/">Cancelar</a>
</form>