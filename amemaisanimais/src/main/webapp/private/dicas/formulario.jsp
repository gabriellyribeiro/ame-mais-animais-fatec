
<form id="frmDica" class="proForm col-md-12" action="" method="post">
    <c:choose>
        <c:when test="${param.acao == 'alterar'}">
            <h4>Alterar dica</h4>
        </c:when>
        <c:otherwise>
            <h4>Cadastrar dica</h4>        
        </c:otherwise>
    </c:choose>

    <input id="txtID" name="txtID" type="hidden" autocomplete="off" readonly value="${dica.id}"/>    

    <div class="form-group col-md-7">
        <label class="proForm__label" 
               for="txtTitulo">Título:
        </label>
        <input class="form-control" 
               id="txtTitulo" 
               name="txtTitulo" 
               type="text" 
               placeholder="Título" 
               autocomplete="off" 
               required 
               maxlength="100" 
               autofocus
               value="${dica.titulo}">
    </div>

    <div class="form-group col-md-5">
        <label class="proForm__label" 
               for="selPerfil">Categoria:
        </label>
        <select class="proForm__input 
                form-control" 
                id="selCategoria" 
                name="selCategoria" 
                required>
            <option disabled selected value="">Categoria</option>
            <c:forEach items="${categoria.getCategorias(false)}" var="c">
                <option value="${c.id}" <c:if test="${dica.categoria.id == c.id}">selected</c:if>>${c.descricao}</option>
            </c:forEach>
        </select>

    </div>

    <div class="form-group">
        <label class="proForm__label" 
               for="txtDescricao">Descrição:
        </label>
        <textarea class="form-control" 
                  id="txtDescricao" 
                  name="txtDescricao" 
                  type="text" 
                  placeholder="Descrição" 
                  autocomplete="off" 
                  required>${dica.descricao}
        </textarea>

        <script>
            CKEDITOR.replace('txtDescricao');
        </script>

    </div>

    <c:choose>
        <c:when test="${param.acao == 'alterar'}">
            <button class="proButton no-rounded-border btn btn-primary" type="submit">Salvar alterações</button>
        </c:when>
        <c:otherwise>
            <button class="proButton no-rounded-border btn btn-primary" type="submit">Cadastrar</button>
        </c:otherwise>
    </c:choose>
    <a class="btn no-rounded-border btn-default" href="<%=App.BASE_URL%>/private/usuarios/">Cancelar</a>
</form>