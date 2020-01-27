<%@page import="dao.ContatoDAO"%>
<%@page import="model.Contato"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% session.setAttribute("secAtual", "contato");%>
<!DOCTYPE html>
<html lang='pt-BR'>
    <head>
        <meta charset="UTF-8"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="description"  content="Página de contatos que traz informações de o usuário pode entrar em contato com o projeto"/> 
        <meta name="viewport" content="width=device-width, maximum-scale=1.0, minimum-scale=1.0, initial-scale=1.0">
        <title>Ame+Ani+ - Página de contato</title>
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
        <div class="container">

            <%
                Contato contato = new ContatoDAO().buscaContato();
            %>
	
            <article class="thumbnail col-md-12 contraste">

                <div class="col-md-6">
                    <h1 class="tituloh1">Contato:</h1>
                    <h4><i class="fa fa-map-marker pr-5"></i> <%=contato.getEndereco().getLogradouro()%>, <%=contato.getEndereco().getNumero()%> - <%=contato.getEndereco().getBairro()%></h4>
                    <h4>CEP <%=contato.getEndereco().getCep()%> - <%=contato.getEndereco().getCidade()%>/<%=contato.getEndereco().getUf()%></h4>
                    <h4><i class="fa fa-phone pr-10"></i>Tel: <%=contato.getTelefone()%></h4>
                    <h4><i class="fa fa-envelope-o pr-10"></i><%=contato.getEmail()%></h4>
                </div>

                <form role="form" class="col-md-6"> 
                    <!--Início Campo nome-->
                    <div class="form-group">
                        <label for="nome">Nome:</label>
                        <input type="text" class="form-control contraste" placeholder="Digite aqui seu nome" id="nome">
                    </div>
                    <!--Fim Campo nome-->

                    <!--Início Campo Email-->
                    <div class="form-group">
                        <label for="email">Email:</label>
                        <input type="email" class="form-control contraste" placeholder="Digite aqui seu email" id="email">	
                    </div>
                    <!--Fim Campo Email-->

                    <!--Início Campo nome-->
                    <div class="form-group">
                        <label for="Mensagem">Mensagem:</label>
                        <textarea class="form-control contraste" rows="5" placeholder="Digite aqui sua mensagem" id="Mensagem"></textarea>		
                    </div>
                    <!--Fim Campo nome-->	  
                    <button type="submit" class="btn btn-primary contraste">Enviar</button>
                </form>

            </article>
        </div>

        <%@include file="include/footer.jsp"%>

    </body>
</html>

