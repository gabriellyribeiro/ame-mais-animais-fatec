
<form id="frmQuemSomos" class="proForm col-md-12" action="" method="post">

    <c:choose>
        <c:when test="${quemSomos.id != ''}">
            <h4>Alterar quem somos</h4>
        </c:when>
        <c:otherwise>
            <h4>Cadastrar quem somos</h4>        
        </c:otherwise>
    </c:choose>


    <input id="txtID" name="txtID" type="hidden" autocomplete="off" readonly value="${quemSomos.id}"/>

    <div class="form-group">
        <label for="txtDescricao">Descrição:</label>
        <textarea type="text" 
                  class="form-control contraste" 
                  placeholder="Descrição"
                  id="txtDescricao" 
                  name="txtDescricao"   
                  autocomplete="off" 
                  required>${quemSomos.descricao}</textarea>
    </div>


    <script>
        CKEDITOR.replace('txtDescricao');
    </script>

    <c:choose>
        <c:when test="${quemSomos.id != ''}">
            <button class="proButton no-rounded-border btn btn-primary" type="submit">Salvar alterações</button>
        </c:when>
        <c:otherwise>
            <button class="proButton no-rounded-border btn btn-primary" type="submit">Cadastrar</button>
        </c:otherwise>
    </c:choose>
    <a class="btn no-rounded-border btn-default" href="<%=App.BASE_URL%>/private/quemsomos/">Cancelar</a>
</form>