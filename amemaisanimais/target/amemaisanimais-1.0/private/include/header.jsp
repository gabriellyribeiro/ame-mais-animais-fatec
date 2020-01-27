<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="util.App"%>
<header class="jumbotron" id="topo">
    <h1 class="esconder"> Cabeçalho com logo e fundo com imagem de um cachorro em um campo de trigo em dia ensolarado, contém também dois menus de acessibilidade</h1>
    <div class="container">
        <ul class="navBarra esconder" id="atalhos">   
            <li><a href="#principal" accesskey="1" title="Link para o conteúdo, com um acesskey 1">Ir para Conteúdo</a></li>   
            <li><a href="#iniciodomenu"  accesskey="2" title="Link para o menu de navegação, com um acesskey 2">Ir para Menu</a></li>   
            <li><a href="#busca"  accesskey="3" title="Link para campo de busc, com um acesskey 3">Ir para busca</a></li>   
        </ul>

        <div class="barraAceDesk pull-right">	
            <ul class="navBarra hidden-xs  pull-right" id="menuAcessi">   			
                <li><img onclick="controlFontSize('+');
                        void(0);" style="cursor: pointer;" src="<%=App.BASE_URL%>/images/font_up.png" alt="Aumentar o tamanho da fonte" title="Aumentar o tamanho da fonte"></li> 
                <li><img onclick="controlFontSize('-');
                        void(0);" style="cursor: pointer;" src="<%=App.BASE_URL%>/images/font_down.png" alt="Diminuir o tamanho da fonte" title="Diminuir o tamanho da fonte"></li> 
                <li><a href="#" id="bt_contraste" title="Link do contraste das cores do site"><img style="cursor: pointer;" src="<%=App.BASE_URL%>/images//contr.png" alt="Diminuir o tamanho da fonte" title="Diminuir o tamanho da fonte"></li></a>
                <li><img onclick="window.location.reload()" style="cursor: pointer;" src="<%=App.BASE_URL%>/images/font.png" alt="Voltar o site ao normal" title="Voltar o site ao normal"></li> 
            </ul>
        </div>	

        <img src="<%=App.BASE_URL%>/images/logo.png"  class="col-md-4 col-sm-5 col-xs-8 img-responsive" alt="logo portal">

        <div class="hidden-sm hidden-md  hidden-lg pull-right">
            <button type="button" class="hidden-md hidden-lg btn-xs btn-primary pull-right margin-button"  onclick="window.location.reload()">Normal</button>
            <button type="button" class="hidden-md hidden-lg btn-xs btn-primary pull-right margin-button" id="contrasteSmart">Contraste</button>
            <button type="button" class="hidden-md hidden-lg btn-xs btn-primary pull-right margin-button" onclick="controlFontSize('+');
                    void(0);" style="cursor: pointer;">A+</button>
            <button type="button" class="hidden-md hidden-lg btn-xs btn-primary pull-right margin-button" onclick="controlFontSize('-');
                    void(0);" style="cursor: pointer;">A-</button>
        </div>


    </div>
</header>

<nav class="navbar navbar-inverse" id="iniciodomenu">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>

        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li <c:if test="${sessionScope.secAtual == 'usuarios'}">class="active"</c:if>><a href="<%=App.BASE_URL%>/private/usuarios">Usuário</a></li>
                <li <c:if test="${sessionScope.secAtual == 'categorias'}">class="active"</c:if>><a href="<%=App.BASE_URL%>/private/categorias">Categoria</a></li>
                <li <c:if test="${sessionScope.secAtual == 'legislacoes'}">class="active"</c:if>><a href="<%=App.BASE_URL%>/private/legislacoes">Legislação</a></li>
                <li <c:if test="${sessionScope.secAtual == 'perfis'}">class="active"</c:if>><a href="<%=App.BASE_URL%>/private/perfis">Perfil</a></li>
                <li <c:if test="${sessionScope.secAtual == 'quemSomos'}">class="active"</c:if>><a href="<%=App.BASE_URL%>/private/quemsomos">Quem Somos</a></li>
                <li <c:if test="${sessionScope.secAtual == 'contato'}">class="active"</c:if>><a href="<%=App.BASE_URL%>/private/contatos">Contato</a></li>
                <li <c:if test="${sessionScope.secAtual == 'receita'}">class="active"</c:if>><a href="<%=App.BASE_URL%>/private/receitas">Receitas</a></li>
                <li <c:if test="${sessionScope.secAtual == 'dicas'}">class="active"</c:if>><a href="<%=App.BASE_URL%>/private/dicas">Dicas</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a data-target="#modalLogout" data-toggle="modal" href="#" title="Desconectar">Log Out<span class="sr-only">(current)</span></a></li>
            </ul>
        </div>
    </div>
</nav>
