<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% session.setAttribute("secAtual", "digital");%>

<!DOCTYPE html>
<html lang='pt-BR'>
    <head>
        <meta charset="UTF-8"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="description"  content="Página de educação digital que contém dois links contendo a cartilha sobre o projeto e as revistas digitais"/> 
        <meta name="viewport" content="width=device-width, maximum-scale=1.0, minimum-scale=1.0, initial-scale=1.0">
        <title>Ame+Ani+ - Página educação digital</title>
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
        <script src="js/contraste.js" type="text/javascript" language="javascript"></script> 
    </head>
    <body>
        <%@include file="include/header.jsp"%>
        
<div class="container contraste">
            <div class="col-md-8 col-sm-12 thumbJogos contraste">
		</div>
    <article class="thumbnail col-md-3 col-md-offset-1 col-sm-12  thumbnail contraste">
                               
                <center><h3 class="tituloh3">Cartilha</h3></center>
        
                    <h4 class="tituloh4 text-center"><a href="jogos.jsp">Cartilha do projeto</a></h4>
                           
                    <img src="images/cartilha.jpg"  class="col-md-12 img-responsive" alt="logo do mascote do projeto.">
                                    
                <p><center><a href="cartilha.jsp" class="btn btn-primary" role="button">Ver cartilha completa</a></p></center>
        </article>
</div>

        <%@include file="include/footer.jsp"%>

    </body>
</html>

