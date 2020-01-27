
<form id="frmContato" class="proForm col-md-12" action="" method="post">
    <c:choose>
        <c:when test="${contato.id != ''}">
            <h4>Alterar contato</h4>
        </c:when>
        <c:otherwise>
            <h4>Cadastrar contato</h4>        
        </c:otherwise>
    </c:choose>

    <input id="txtID" name="txtID" type="hidden" autocomplete="off" readonly value="${contato.id}"/>

    <div class="form-group
         col-md-4">
        <label class="proForm__label" 
               for="emaEmail">e-mail:
        </label>
        <input class="proForm__input
               form-control" 
               id="emaEmail" 
               name="emaEmail" 
               type="email" 
               placeholder="e-mail" 
               autocomplete="off"
               autofocus
               required
               value="${contato.email}"/>
    </div>

    <div class="form-group
         col-md-4">
        <label class="proForm__label" 
               for="telTelefone">Telefone:
        </label>
        <input class="proForm__input 
               maskTelefone 
               form-control" 
               id="telTelefone" 
               name="telTelefone" 
               type="text" 
               placeholder="Telefone" 
               autocomplete="off" 
               required
               value="${contato.telefone}"/>
    </div>

    <div class="form-group
         col-md-4">
        <label class="proForm__label" 
               for="telCelular">Celular:</label>
        <input class="proForm__input 
               form-control
               maskCelular" 
               id="telCelular" 
               name="telCelular" 
               value="${contato.celular}" 
               type="text" 
               placeholder="Celular" 
               autocomplete="off"/>
    </div>

    <div class="form-group
         col-md-2">
        <label class="proForm__label"
               for="txtCEP">CEP:</label>
        <input class="proForm__input
               form-control
               maskCEP"
               id="txtCEP"
               name="txtCEP"
               value="${contato.endereco.cep}"
               type="text"
               required
               placeholder="CEP"
               autocomplete="off"/>

    </div>

    <div class="form-group
         col-md-8">
        <label class="proForm__label"
               for="txtLogradouro">Logradouro:</label>
        <input class="proForm__input
               form-control"
               id="txtLogradouro"
               name="txtLogradouro"
               value="${contato.endereco.logradouro}"
               type="text"
               required
               placeholder="Logradouro"
               autocomplete="off"/>

    </div>

    <div class="form-group
         col-md-2">
        <label class="proForm__label"
               for="txtNumero">Número:</label>
        <input class="proForm__input
               form-control"
               id="txtNumero"
               name="txtNumero"
               value="${contato.endereco.numero}"
               type="text"
               required
               placeholder="Número"
               autocomplete="off"/>

    </div>

    <div class="form-group
         col-md-5">
        <label class="proForm__label"
               for="txtBairro">Bairro:</label>
        <input class="proForm__input
               form-control"
               id="txtBairro"
               name="txtBairro"
               value="${contato.endereco.bairro}"
               type="text"
               required
               placeholder="Bairro"
               autocomplete="off"/>

    </div>

    <div class="form-group
         col-md-5">
        <label class="proForm__label"
               for="txtCidade">Cidade:</label>
        <input class="proForm__input
               form-control"
               id="txtCidade"
               name="txtCidade"
               value="${contato.endereco.cidade}"
               type="text"
               required
               placeholder="Cidade"
               autocomplete="off"/>

    </div>

    <div class="form-group
         col-md-2">
        <label class="proForm__label"
               for="txtUF">Estado:</label>
        <input class="proForm__input
               form-control"
               id="txtUF"
               name="txtUF"
               value="${contato.endereco.uf}"
               type="text"
               required
               placeholder="Estado"
               autocomplete="off"/>

    </div>

    <c:choose>
        <c:when test="${contato.id != ''}">
            <button class="proButton no-rounded-border btn btn-primary" type="submit">Salvar alterações</button>
        </c:when>
        <c:otherwise>
            <button class="proButton no-rounded-border btn btn-primary" type="submit">Cadastrar</button>
        </c:otherwise>
    </c:choose>
    <a class="btn no-rounded-border btn-default" href="<%=App.BASE_URL%>/private/quemsomos/">Cancelar</a>
</form>