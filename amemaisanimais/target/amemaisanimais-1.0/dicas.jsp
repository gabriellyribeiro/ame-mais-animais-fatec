<%@page import="dao.CategoriaDAO"%>
<%@page import="model.Categoria"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% session.setAttribute("secAtual", "dicas");%>
<%@page import="dao.DicaDAO"%>
<%@page import="model.Dica"%>

<!DOCTYPE html>
<html lang='pt-BR'>
    <head>
        <meta charset="UTF-8"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="description"  content="Página de dicas que tra informações sobre tratamento e doenças"/> 
        <meta name="viewport" content="width=device-width, maximum-scale=1.0, minimum-scale=1.0, initial-scale=1.0">
        <title>Ame+Ani+ - Página de dicas</title>
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
        <script src="js/jquery.isotope.js" type="text/javascript"></script>
        <script src="js/FuncoesAce.js" type="text/javascript" language="javascript"></script>
    </head>
    <body>
        <%@include file="include/header.jsp"%>
        <div class="container">			
            <article class="thumbnail col-md-12 contraste">
                <div class="portfolioFilter">
                    <ul class="nav nav-pills">	
                        <li><a href="#" data-filter="*">Todas as Categorias</a></li>

                        <%
                            for (Categoria categoria : CategoriaDAO.getInstance().getCategorias(false)) {
                        %>
                        <li><a href="#" data-filter=".<%=categoria.getDescricao()%>"><%=categoria.getDescricao()%></a></li>
                            <%
                                }
                            %>
                    </ul>
                </div>
                <hr>

                <div class="portfolioContainer contraste col-offset-4">

                    <%
                        for (Dica dica : DicaDAO.getInstance().getDicas()) {
                    %>
                    <div class="image-box <%=dica.getCategoria().getDescricao()%>">
                        <div class="overlay-container">
                            <img src="images/trat1_min.jpg" 
                                 alt="image">
                            <a class="overlay" 
                               data-toggle="modal" 
                               data-target="#<%=dica.getCategoria().getDescricao()%><%=dica.getId()%>">
                                <i class="fa fa-search-plus"></i>
                                <span><%=dica.getTitulo()%></span>
                            </a>
                        </div>
                        <a class="btn btn-default btn-block" 
                           data-toggle="modal" 
                           data-target="#<%=dica.getCategoria().getDescricao()%><%=dica.getId()%>"><%=dica.getCategoria().getDescricao()%><%=dica.getId()%></a>			
                    </div>
                    <%
                        }
                    %>

                </div>

                <!-- início Modal1 -->
                <div class="modal fade" id="trat1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                    <div class="modal-dialog modal-lg" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title" id="myModalLabel">Tratamento</h4>
                            </div>
                            <div class="modal-body">
                                <img src="images/modal.jpg" class="col-md-6" alt="image">
                                <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod
                                    tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,
                                    quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo
                                    consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse
                                    cillum dolore eu fugiat nulla pariatur.</p> 

                                <p>Morbi pellentesque id neque a malesuada. Suspendisse potenti. Phasellus sit amet fringilla sem. Suspendisse nec diam ante. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Quisque in odio felis. Nam sed tempus odio, eu consectetur orci. Nullam dapibus, nulla ac rutrum eleifend, erat libero consequat elit, nec facilisis magna ligula in velit. Donec at congue purus, ac interdum lectus. Integer vitae urna eu sem blandit lobortis ut sit amet lectus. Integer tristique eros id.</p>

                                <p>Cras malesuada sapien et varius bibendum. Maecenas leo nisl, auctor sed sodales eget, blandit id augue. Fusce nec est tempor, rhoncus nulla eget, molestie massa. Proin fermentum scelerisque ultricies. Cras vel arcu vitae lectus elementum commodo. Proin ac lobortis est. In hac habitasse platea dictumst. Mauris nec orci sodales, imperdiet libero ullamcorper, tincidunt nibh. Sed purus neque, pretium rhoncus rutrum a, iaculis nec diam. Nam arcu nisi, convallis id aliquet tincidunt, congue eget odio. Fusce auctor hendrerit ullamcorper. </p>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal">fechar</button>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- fim Modal1 -->

                <!-- início Modal2 -->
                <div class="modal fade" id="trat2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                    <div class="modal-dialog modal-lg" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title" id="myModalLabel">Tratamento</h4>
                            </div>
                            <div class="modal-body">
                                <img src="images/modal.jpg" class="col-md-6" alt="image">
                                <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod
                                    tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,
                                    quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo
                                    consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse
                                    cillum dolore eu fugiat nulla pariatur.</p> 

                                <p>Morbi pellentesque id neque a malesuada. Suspendisse potenti. Phasellus sit amet fringilla sem. Suspendisse nec diam ante. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Quisque in odio felis. Nam sed tempus odio, eu consectetur orci. Nullam dapibus, nulla ac rutrum eleifend, erat libero consequat elit, nec facilisis magna ligula in velit. Donec at congue purus, ac interdum lectus. Integer vitae urna eu sem blandit lobortis ut sit amet lectus. Integer tristique eros id.</p>

                                <p>Cras malesuada sapien et varius bibendum. Maecenas leo nisl, auctor sed sodales eget, blandit id augue. Fusce nec est tempor, rhoncus nulla eget, molestie massa. Proin fermentum scelerisque ultricies. Cras vel arcu vitae lectus elementum commodo. Proin ac lobortis est. In hac habitasse platea dictumst. Mauris nec orci sodales, imperdiet libero ullamcorper, tincidunt nibh. Sed purus neque, pretium rhoncus rutrum a, iaculis nec diam. Nam arcu nisi, convallis id aliquet tincidunt, congue eget odio. Fusce auctor hendrerit ullamcorper. </p>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal">fechar</button>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- fim Modal2 -->

                <!-- início Modal3 -->
                <div class="modal fade" id="trat3" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                    <div class="modal-dialog modal-lg" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title" id="myModalLabel">Tratamento</h4>
                            </div>
                            <div class="modal-body">
                                <img src="images/modal.jpg" class="col-md-6" alt="image">
                                <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod
                                    tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,
                                    quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo
                                    consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse
                                    cillum dolore eu fugiat nulla pariatur.</p> 

                                <p>Morbi pellentesque id neque a malesuada. Suspendisse potenti. Phasellus sit amet fringilla sem. Suspendisse nec diam ante. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Quisque in odio felis. Nam sed tempus odio, eu consectetur orci. Nullam dapibus, nulla ac rutrum eleifend, erat libero consequat elit, nec facilisis magna ligula in velit. Donec at congue purus, ac interdum lectus. Integer vitae urna eu sem blandit lobortis ut sit amet lectus. Integer tristique eros id.</p>

                                <p>Cras malesuada sapien et varius bibendum. Maecenas leo nisl, auctor sed sodales eget, blandit id augue. Fusce nec est tempor, rhoncus nulla eget, molestie massa. Proin fermentum scelerisque ultricies. Cras vel arcu vitae lectus elementum commodo. Proin ac lobortis est. In hac habitasse platea dictumst. Mauris nec orci sodales, imperdiet libero ullamcorper, tincidunt nibh. Sed purus neque, pretium rhoncus rutrum a, iaculis nec diam. Nam arcu nisi, convallis id aliquet tincidunt, congue eget odio. Fusce auctor hendrerit ullamcorper. </p>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal">fechar</button>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- fim Modal3 -->

                <!-- início Modal4 -->
                <div class="modal fade" id="trat4" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                    <div class="modal-dialog modal-lg" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title" id="myModalLabel">Tratamento</h4>
                            </div>
                            <div class="modal-body">
                                <img src="images/modal.jpg" class="col-md-6" alt="image">
                                <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod
                                    tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,
                                    quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo
                                    consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse
                                    cillum dolore eu fugiat nulla pariatur.</p> 

                                <p>Morbi pellentesque id neque a malesuada. Suspendisse potenti. Phasellus sit amet fringilla sem. Suspendisse nec diam ante. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Quisque in odio felis. Nam sed tempus odio, eu consectetur orci. Nullam dapibus, nulla ac rutrum eleifend, erat libero consequat elit, nec facilisis magna ligula in velit. Donec at congue purus, ac interdum lectus. Integer vitae urna eu sem blandit lobortis ut sit amet lectus. Integer tristique eros id.</p>

                                <p>Cras malesuada sapien et varius bibendum. Maecenas leo nisl, auctor sed sodales eget, blandit id augue. Fusce nec est tempor, rhoncus nulla eget, molestie massa. Proin fermentum scelerisque ultricies. Cras vel arcu vitae lectus elementum commodo. Proin ac lobortis est. In hac habitasse platea dictumst. Mauris nec orci sodales, imperdiet libero ullamcorper, tincidunt nibh. Sed purus neque, pretium rhoncus rutrum a, iaculis nec diam. Nam arcu nisi, convallis id aliquet tincidunt, congue eget odio. Fusce auctor hendrerit ullamcorper. </p>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal">fechar</button>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- fim Modal4 -->

                <!-- início Modal5 -->
                <div class="modal fade" id="doen1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                    <div class="modal-dialog modal-lg" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title" id="myModalLabel">Tratamento</h4>
                            </div>
                            <div class="modal-body">
                                <img src="images/modal.jpg" class="col-md-6" alt="image">
                                <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod
                                    tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,
                                    quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo
                                    consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse
                                    cillum dolore eu fugiat nulla pariatur.</p> 

                                <p>Morbi pellentesque id neque a malesuada. Suspendisse potenti. Phasellus sit amet fringilla sem. Suspendisse nec diam ante. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Quisque in odio felis. Nam sed tempus odio, eu consectetur orci. Nullam dapibus, nulla ac rutrum eleifend, erat libero consequat elit, nec facilisis magna ligula in velit. Donec at congue purus, ac interdum lectus. Integer vitae urna eu sem blandit lobortis ut sit amet lectus. Integer tristique eros id.</p>

                                <p>Cras malesuada sapien et varius bibendum. Maecenas leo nisl, auctor sed sodales eget, blandit id augue. Fusce nec est tempor, rhoncus nulla eget, molestie massa. Proin fermentum scelerisque ultricies. Cras vel arcu vitae lectus elementum commodo. Proin ac lobortis est. In hac habitasse platea dictumst. Mauris nec orci sodales, imperdiet libero ullamcorper, tincidunt nibh. Sed purus neque, pretium rhoncus rutrum a, iaculis nec diam. Nam arcu nisi, convallis id aliquet tincidunt, congue eget odio. Fusce auctor hendrerit ullamcorper. </p>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal">fechar</button>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- fim Modal5 -->

                <!-- início Modal6 -->
                <div class="modal fade" id="doen2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                    <div class="modal-dialog modal-lg" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title" id="myModalLabel">Tratamento</h4>
                            </div>
                            <div class="modal-body">
                                <img src="images/modal.jpg" class="col-md-6" alt="image">
                                <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod
                                    tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,
                                    quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo
                                    consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse
                                    cillum dolore eu fugiat nulla pariatur.</p> 

                                <p>Morbi pellentesque id neque a malesuada. Suspendisse potenti. Phasellus sit amet fringilla sem. Suspendisse nec diam ante. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Quisque in odio felis. Nam sed tempus odio, eu consectetur orci. Nullam dapibus, nulla ac rutrum eleifend, erat libero consequat elit, nec facilisis magna ligula in velit. Donec at congue purus, ac interdum lectus. Integer vitae urna eu sem blandit lobortis ut sit amet lectus. Integer tristique eros id.</p>

                                <p>Cras malesuada sapien et varius bibendum. Maecenas leo nisl, auctor sed sodales eget, blandit id augue. Fusce nec est tempor, rhoncus nulla eget, molestie massa. Proin fermentum scelerisque ultricies. Cras vel arcu vitae lectus elementum commodo. Proin ac lobortis est. In hac habitasse platea dictumst. Mauris nec orci sodales, imperdiet libero ullamcorper, tincidunt nibh. Sed purus neque, pretium rhoncus rutrum a, iaculis nec diam. Nam arcu nisi, convallis id aliquet tincidunt, congue eget odio. Fusce auctor hendrerit ullamcorper. </p>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal">fechar</button>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- fim Modal6 -->

                <!-- início Modal7 -->
                <div class="modal fade" id="doen3" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                    <div class="modal-dialog modal-lg" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title" id="myModalLabel">Tratamento</h4>
                            </div>
                            <div class="modal-body">
                                <img src="images/modal.jpg" class="col-md-6" alt="image">
                                <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod
                                    tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,
                                    quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo
                                    consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse
                                    cillum dolore eu fugiat nulla pariatur.</p> 

                                <p>Morbi pellentesque id neque a malesuada. Suspendisse potenti. Phasellus sit amet fringilla sem. Suspendisse nec diam ante. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Quisque in odio felis. Nam sed tempus odio, eu consectetur orci. Nullam dapibus, nulla ac rutrum eleifend, erat libero consequat elit, nec facilisis magna ligula in velit. Donec at congue purus, ac interdum lectus. Integer vitae urna eu sem blandit lobortis ut sit amet lectus. Integer tristique eros id.</p>

                                <p>Cras malesuada sapien et varius bibendum. Maecenas leo nisl, auctor sed sodales eget, blandit id augue. Fusce nec est tempor, rhoncus nulla eget, molestie massa. Proin fermentum scelerisque ultricies. Cras vel arcu vitae lectus elementum commodo. Proin ac lobortis est. In hac habitasse platea dictumst. Mauris nec orci sodales, imperdiet libero ullamcorper, tincidunt nibh. Sed purus neque, pretium rhoncus rutrum a, iaculis nec diam. Nam arcu nisi, convallis id aliquet tincidunt, congue eget odio. Fusce auctor hendrerit ullamcorper. </p>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal">fechar</button>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- fim Modal7 -->

                <!-- início Modal8 -->
                <div class="modal fade" id="doen4" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                    <div class="modal-dialog modal-lg" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title" id="myModalLabel">Tratamento</h4>
                            </div>
                            <div class="modal-body">
                                <img src="images/modal.jpg" class="col-md-6" alt="image">
                                <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod
                                    tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,
                                    quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo
                                    consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse
                                    cillum dolore eu fugiat nulla pariatur.</p> 

                                <p>Morbi pellentesque id neque a malesuada. Suspendisse potenti. Phasellus sit amet fringilla sem. Suspendisse nec diam ante. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Quisque in odio felis. Nam sed tempus odio, eu consectetur orci. Nullam dapibus, nulla ac rutrum eleifend, erat libero consequat elit, nec facilisis magna ligula in velit. Donec at congue purus, ac interdum lectus. Integer vitae urna eu sem blandit lobortis ut sit amet lectus. Integer tristique eros id.</p>

                                <p>Cras malesuada sapien et varius bibendum. Maecenas leo nisl, auctor sed sodales eget, blandit id augue. Fusce nec est tempor, rhoncus nulla eget, molestie massa. Proin fermentum scelerisque ultricies. Cras vel arcu vitae lectus elementum commodo. Proin ac lobortis est. In hac habitasse platea dictumst. Mauris nec orci sodales, imperdiet libero ullamcorper, tincidunt nibh. Sed purus neque, pretium rhoncus rutrum a, iaculis nec diam. Nam arcu nisi, convallis id aliquet tincidunt, congue eget odio. Fusce auctor hendrerit ullamcorper. </p>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal">fechar</button>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- fim Modal8 -->

                <!-- scrpit isotope(colocar em arquivo separado dps) -->
                <script type="text/javascript">
                    $(window).load(function () {
                        var $container = $('.portfolioContainer');
                        $container.isotope({
                            filter: '*',
                            animationOptions: {
                                duration: 750,
                                easing: 'linear',
                                queue: false
                            }
                        });

                        $('.portfolioFilter a').click(function () {
                            $('.portfolioFilter .current').removeClass('current');
                            $(this).addClass('current');

                            var selector = $(this).attr('data-filter');
                            $container.isotope({
                                filter: selector,
                                animationOptions: {
                                    duration: 750,
                                    easing: 'linear',
                                    queue: false
                                }
                            });
                            return false;
                        });
                    });
                </script>


            </article>
        </div>
        <%@include file="include/footer.jsp"%>

    </body>
</html>