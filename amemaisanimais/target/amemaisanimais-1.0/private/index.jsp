<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% session.setAttribute("secAtual", "home");%>
<c:set var="titulo" scope="page" value="Início" />


<html lang='pt-BR'>
    <head>
        <meta charset="UTF-8"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, maximum-scale=1.0, minimum-scale=1.0, initial-scale=1.0">
        <title>Ame+Ani+</title>
        <link rel="stylesheet" href="../css/bootstrap.min.css"/>
        <link rel="stylesheet" href="../css/style.css">
        <link rel="stylesheet" href="../css/font-awesome.min.css"/>
        <link rel="stylesheet" href="../css/proButton.css"/>
        <link rel="stylesheet" href="../css/proFormulario.css"/>
        <link rel="stylesheet" href="../css/proMain.css"/>
        <link rel="stylesheet" href="../css/proTable.css"/>
        <script src="../js/jquery-2.1.4.min.js"></script>
        <script src="../js/proBootstrap.min.js"></script>
        <script src="../js/css_browser_selector.js"></script>
        <script src="../js/proInputMask.js"></script>
        <script src="../js/proAme_Formulario.js"></script>
        <script src="../js/proAme_Main.js"></script>
        <script src="../js/proAme_Table.js"></script>
        <script src="../js/jquery.isotope.js" type="text/javascript"></script>
    </head>
    <body>
        <%@include file="include/header.jsp"%>
        <section class="no-rounded-border alert alert-success">
            <button type='button' class='close' data-dismiss='alert' aria-hidden='true' title='Fechar'>&times;</button>
            <p><strong>Login efetuado com sucesso!</strong></p>
        </section>

        <%@include file="include/modaisglobais.jsp" %>
        <%@include file="include/scripts.jsp"%>
        <%@include file="include/footer.jsp"%>
    </body>
</html>


