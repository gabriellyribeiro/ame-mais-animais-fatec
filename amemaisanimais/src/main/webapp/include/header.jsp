<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                        void(0);" style="cursor: pointer;" src="images/font_up.png" alt="Aumentar o tamanho da fonte" title="Aumentar o tamanho da fonte"></li> 
                <li><img onclick="controlFontSize('-');
                        void(0);" style="cursor: pointer;" src="images/font_down.png" alt="Diminuir o tamanho da fonte" title="Diminuir o tamanho da fonte"></li> 
                <li><a href="#" id="bt_contraste" title="Link do contraste das cores do site"><img style="cursor: pointer;" src="images/contr.png" alt="botão para mudar o contraste do site" title="botão para mudar o contraste do site"></li></a>
                <li><img onclick="window.location.reload()" style="cursor: pointer;" src="images/font.png" alt="Voltar o site ao normal" title="Voltar o site ao normal"></li> 
            </ul>
        </div>	

        <img src="images/logo.png"  class="col-md-4 col-sm-4 col-xs-8 img-responsive" alt="logo ame+ ani+">

        <div class="hidden-sm hidden-md  hidden-lg pull-right">
            <button type="button" class="hidden-md hidden-lg btn-xs btn-primary pull-right margin-button" onclick="controlFontSize( '0' ); void( 0 );" style="cursor: pointer;">Normal</button>
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
                <li <c:if test="${sessionScope.secAtual == 'home'}">class="active"</c:if>><a  href="index.jsp">Home</a></li>
                <li <c:if test="${sessionScope.secAtual == 'dicas'}">class="active"</c:if>><a href="dicas.jsp">Dicas</a></li>
                <li <c:if test="${sessionScope.secAtual == 'digital'}">class="active"</c:if>><a href="digital.jsp">Interatividade</a></li>
                <li <c:if test="${sessionScope.secAtual == 'legislacao'}">class="active"</c:if>><a href="legislacao.jsp">Legislação</a></li>
                <li <c:if test="${sessionScope.secAtual == 'receita'}">class="active"</c:if>><a href="receita.jsp">Receitas</a></li>
                <li <c:if test="${sessionScope.secAtual == 'contato'}">class="active"</c:if>><a href="contato.jsp">Contato</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li <c:if test="${sessionScope.secAtual == 'login'}">class="active"</c:if>><a href="login.jsp">Login<span class="sr-only">(current)</span></a></li>
            </ul>
        </div>
    </div>
</nav>
