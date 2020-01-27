<%@page import="dao.ReceitaDAO"%>
<%@page import="model.Receita"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% session.setAttribute("secAtual", "receita");%>
<!DOCTYPE html>

<html lang='pt-BR'>
    <head>
        <meta charset="UTF-8"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="description"  content="Página de legislação que traz informações sobre as leis e direitos dos animais"/> 
        <meta name="viewport" content="width=device-width, maximum-scale=1.0, minimum-scale=1.0, initial-scale=1.0">
        <title>Ame+Ani+ - Página de Receitas</title>
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

            <h1 class="tituloh1"><strong>Receitas</strong></h1>	
            <img src="images/legislacao.jpg"  class="col-md-12 img-responsive hidden-xs" alt="">	
            <article class="thumbnail col-md-12 contraste">
                <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">

                    <%
                        for (Receita receita : ReceitaDAO.getInstance().getReceita()) {
                    %>

                    <div class="panel panel-default contraste">

                        <div class="panel-heading" 
                             role="tab" 
                             id="heading<%=receita.getId()%>">
                            <a data-toggle="collapse" 
                               data-parent="#accordion" 
                               href="#collapse<%=receita.getId()%>" 
                               aria-expanded="true" 
                               aria-controls="collapse<%=receita.getId()%>">
                                <h4 class="panel-title">       
                                    <%=receita.getTitulo()%>
                                </h4>
                            </a>
                        </div>

                        <div id="collapse<%=receita.getId()%>" 
                             class="panel-collapse collapse in" 
                             role="tabpanel" 
                             aria-labelledby="heading<%=receita.getId()%>">
                            <div class="panel-heading">
                                <%=receita.getIngrediente()%>
                            </div>
                            <div class="panel-body">
                                <%=receita.getModoPreparo()%>
                            </div>
                        </div>
                    </div>
                    <%
                        }
                    %>
                </div>  

            </article>
        </div>

        <%@include file="include/footer.jsp"%>

    </body>
</html>