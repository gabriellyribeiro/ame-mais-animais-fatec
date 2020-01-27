
<form id="frmUsuario" class="proForm col-md-12" action="" method="post">
    <c:choose>
        <c:when test="${param.acao == 'alterar'}">
            <h4>Alterar usuário</h4>
        </c:when>
        <c:otherwise>
            <h4>Cadastrar usuário</h4>        
        </c:otherwise>
    </c:choose>

    <input id="txtID" name="txtID" type="hidden" autocomplete="off" readonly value="${user.id}"/>    

    <div class="form-group col-md-3">
        <label class="proForm__label" 
               for="txtNome">Nome:
        </label>
        <input class="form-control" 
               id="txtNome" 
               name="txtNome" 
               type="text" 
               placeholder="Nome" 
               autocomplete="off" 
               required 
               maxlength="100" 
               autofocus
               value="${user.nome}">
    </div>

    <div class="form-group col-md-3">
        <label class="proForm__label" 
               for="txtUsername">Username: 
            <i class="fa fa-spin fa-spinner proForm__spinner"></i>
        </label>
        <input class="form-control" 
               id="txtUsername" 
               name="txtUsername" 
               type="text" 
               placeholder="Username" 
               autocomplete="off" 
               required 
               maxlength="30" 
               value="${user.userName}">
    </div>

    <div class="form-group col-md-2">
        <label class="proForm__label" 
               for="pswSenha">Senha:
        </label>
        <input class="form-control
               proForm__input" 
               id="pswSenha" 
               name="pswSenha" 
               type="password" 
               placeholder="Senha" 
               autocomplete="off" 
               required 
               maxlength="30" 
               value="${user.senha}">
    </div>

    <div class="form-group col-md-2">
        <label class="proForm__label" 
               for="pswSenhaRep">Confirme a senha:
        </label>
        <input class="form-control
               proForm__input" 
               id="pswSenhaRep" 
               name="pswSenhaRep" 
               type="password" 
               placeholder="Confirme a senha" 
               autocomplete="off" 
               required 
               maxlength="30" 
               value="${user.senha}">
    </div>

    <div class="form-group col-md-2">
        <label class="proForm__label" 
               for="selPerfil">Perfil:
        </label>
        <select class="proForm__input 
                form-control" 
                id="selPerfil" 
                name="selPerfil" 
                required>
            <option disabled selected value="">Perfil</option>
            <c:forEach items="${perfil.getPerfis(false)}" var="p">
                <option value="${p.id}" <c:if test="${user.perfil.id == p.id}">selected</c:if>>${p.descricao}</option>
            </c:forEach>
        </select>

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