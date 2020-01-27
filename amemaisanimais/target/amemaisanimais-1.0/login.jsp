<%@page import="util.App"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% session.setAttribute("secAtual", "login");%>
<!DOCTYPE html>
<html lang="pt-BR">
    <head>
        <meta charset="UTF-8"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="description"  content="Página de Login"/> 
        <meta name="viewport" content="width=device-width, maximum-scale=1.0, minimum-scale=1.0, initial-scale=1.0">
        <link rel="icon" href="images/icon.png" type="image/x-icon" />
        <link rel="stylesheet" href="css/bootstrap.min.css"/>
        <link rel="stylesheet" href="css/style.css">
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
        <title>Login | <%=App.APP_NAME%></title>
    </head>

    <body>

        <%@include file="include/header.jsp"%>

        <div class="container">		
            <article class="thumbnail col-md-6 font col-md-offset-3">
                <form class="proForm proForm--login" action="UsuarioLogarController" method="post">

                    <%
                        String erro = (String) request.getAttribute("erro");
                        if (erro != null) {
                    %>

                    <section class="no-rounded-border alert alert-danger">
                        <button type='button' class='close' data-dismiss='alert' aria-hidden='true' title='Fechar'>&times;</button>
                        <p><strong>Erro ao logar.</strong> Verifique o nome de usuário e a senha!</p>
                    </section>

                    <%
                        }
                    %>

                    <div class="form-group">
                        <label class="proForm__label" 
                               for="txtUsername">Username:
                        </label>
                        <input class="col-md-12
                               form-control
                               proForm__input 
                               proForm__input--login" 
                               id="txtUsername" 
                               name="txtUsername" 
                               type="text" 
                               placeholder="Username" 
                               autocomplete="off" 
                               required 
                               autofocus>
                    </div>

                    <div class="form-group">
                        <label proForm__label" 
                               for="pswPassword">Senha:
                        </label>
                        <input class="col-md-12
                               form-control
                               proForm__input" 
                               id="pswPassword" 
                               name="pswPassword" 
                               type="password" 
                               placeholder="Senha" 
                               autocomplete="off" 
                               required>
                    </div>
                    
                    <button class="proButton 
                            no-rounded-border 
                            col-md-5 col-sm-6 
                            col-xs-12 
                            btn btn-primary" 
                            type="submit">
                        <i class="fa fa-sign-in fa-lg"></i> Entrar
                    </button>
                    
                    <button class="proButton
                            pull-right
                            no-rounded-border 
                            col-md-5 col-sm-6 
                            col-xs-12 
                            btn btn-primary">
                        <i class="fa fa-sign-out fa-lg"></i> Voltar
                    </button>
                </form>
            </article>
        </div>

        <%@include file="include/footer.jsp"%>

    </body>
</html>