<%@page import="dao.QuemSomosDAO"%>
<%@page import="model.QuemSomos"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% session.setAttribute("secAtual", "quemsomos");%>
<!DOCTYPE html>

<html lang='pt-BR'>
    <head>
        <meta charset="UTF-8"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="description"  content="Página quem somos que traz informações do projeto Ame+ Ani+"/> 
        <meta name="viewport" content="width=device-width, maximum-scale=1.0, minimum-scale=1.0, initial-scale=1.0">
        <title>Ame+ Ani+ - Página Quem somos</title>
        <link rel="icon" href="images/icon.png" type="image/x-icon" />
        <link rel="stylesheet" href="css/bootstrap.min.css"/>
        <link rel="stylesheet" href="css/style.css">
        <link rel="stylesheet" href="css/fontes.css">
        <link rel="stylesheet" href="css/font-awesome.min.css"/>
        <link rel="stylesheet" href="css/proButton.css"/>
        <link rel="stylesheet" href="css/proFormulario.css"/>
        <link rel="stylesheet" href="css/proMain.css"/>
        <link rel="stylesheet" href="css/proTable.css"/>
        <script src="js/jquery-2.1.4.min.js"></script>
        <script src="js/proBootstrap.min.js"></script>
        <script src="js/css_browser_selector.js"></script>
        <script src="js/proInputMask.js"></script>
        <script src="js/proAme_Formulario.js"></script>
        <script src="js/proAme_Main.js"></script>
        <script src="js/proAme_Table.js"></script>
        <script src="js/jquery.isotope.js" type="text/javascript"></script>
        <script src="js/FuncoesAce.js" type="text/javascript" language="javascript"></script>
    </head>
    <body>
        <%@include file="include/header.jsp"%>

        <div class="container contraste"> 	
            <div class="row">
            
            <article class="col-md-12 thumbnail font contraste" id="principal">
                             <h2 class="tituloh2"><strong>Sobre o projeto</strong></h2>
                             <div class="col-md-5">
                    <%
                        QuemSomos quemSomos = new QuemSomosDAO().buscaQuemSomos();
                    %>
                    <p><%=quemSomos.getDescricao()%></p>
                </div>
                <div class="col-md-7 embed-responsive embed-responsive-16by9">
                        <h3 class="hidden-xs tituloh3"><strong>Veja o vídeo: </strong></h3>
                        <iframe class="hidden-xs video embed-responsive-item" style="height: 415px; width: 675px" src="https://www.youtube.com/embed/bCng__KlOq4" allowfullscreen></iframe>
                </div>
                 </article>
                <div class="row">
                    
                <div class="col-md-4 col-sm-12 thumbJogos contraste">
			<img src="images/games.jpg"  class="col-md-12 col-sm-4 img-responsive " alt="">
			<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod
			tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,
			</p> 
			 <button type="button" class="btn btn-default contraste">jogar</button>
		</div>
			
		<div class="col-md-4 col-sm-12 thumbJogos contraste">
			<img src="images/games.jpg"  class="col-md-12 col-sm-4 img-responsive" alt="">
			<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod
			tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,
			</p> 
			 <button type="button" class="btn btn-default contraste">jogar</button>
		</div>
    
             
        <div class="col-md-3 col-md-offset-1 col-sm-12  thumbnail contraste">
                               
                <center><h3 class="tituloh3">Cartilha</h3></center>
        
                    <h4 class="tituloh4 text-center"><a href="jogos.jsp">Cartilha do projeto</a></h4>
                           
                    <img src="images/cartilha.jpg"  class="col-md-12 img-responsive" alt="logo do mascote do projeto.">
                                    
                <p><center><a href="cartilha.jsp" class="btn btn-primary" role="button">Ver cartilha completa</a></p></center>
        </div>
                    
                </div>
                
            </div>
        </div>
                         
        <%@include file="include/footer.jsp"%>

    </body>
</html>