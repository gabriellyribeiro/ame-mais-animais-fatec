<%@page import="util.App"%>
<!DOCTYPE html>
<html lang="en" class="no-js">
    <head>
        <meta charset="UTF-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
        <meta name="viewport" content="width=device-width, initial-scale=1.0"> 
        <title>Ame+ Ani+ - Página cartilha digital</title>
        <link rel="icon" href="<%=App.BASE_URL%>/images/icon.png" type="image/x-icon" />
        <meta name="description" content="Fullscreen Pageflip Layout with BookBlock" />
        <meta name="keywords" content="fullscreen pageflip, booklet, layout, bookblock, jquery plugin, flipboard layout, sidebar menu" />
        <meta name="author" content="Codrops" />
        <link rel="shortcut icon" href="../favicon.ico"> 
        <link rel="stylesheet" type="text/css" href="<%=App.BASE_URL%>/css/jquery.jscrollpane.custom.css" />
        <link rel="stylesheet" type="text/css" href="<%=App.BASE_URL%>/css/bookblock.css" />
        <link rel="stylesheet" type="text/css" href="<%=App.BASE_URL%>/css/custom.css" />        
        <script src="<%=App.BASE_URL%>/js/modernizr.custom.79639.js"></script>
    </head>
    <body>
        <div id="container" class="container">	
            <div class="menu-panel">
                <h3>Ame+ Ani+ Cartilha</h3>
                <ul id="menu-toc" class="menu-toc">
                    <li class="menu-toc-current"><a href="#item1">Capa</a></li>
                    <li><a href="#item2">Somos todos animais</a></li>
                    <li><a href="#item3">Guarda Responsável</a></li>
                    <li><a href="#item4">Antes de levar um bichinho para casa, Reflita!</a></li>
                    <li><a href="#item5">Acões que fazem a diferença</a></li>
                    <li><a href="#item6">Castração</a></li>
                    <li><a href="#item7">E os animais Silvestres?</a></li>
                    <li><a href="#item8">Mitos e verdades</a></li>
                    <li><a href="#item9">Créditos</a></li>

                </ul>
            </div>

            <div class="bb-custom-wrapper">
                <div id="bb-bookblock" class="bb-bookblock">
                    <div class="bb-item" id="item1">
                        <div class="content">
                            <img class="capa" src="images/cartilha/capa.jpg">
                        </div>
                    </div>
                    <div class="bb-item" id="item2">
                        <div class="content">
                            <div class="scroller">
                                <h2>Somos todos animais</h2>
                                <p>Na escola aprendemos que somos animais racionais da espécie <strong>Homo sapiens</strong> e que a diferença entre nós e os outros animais é que pensamos enquanto os demais agem por instinto.</p>

                                <p>Dividimos e compartilhamos com todos o planeta onde vivemos. Como seres racionais, precisamos pensar na <strong>sustentabilidade</strong>, equilíbrio ecológico e agir corretamente para que todos os animais que seguem seus instintos também tenham direito a uma vida digna.</p>

                                <img class="imagem" src="images/cartilha/img1.jpg">

                                <p class="alert alert-success" role="alert">A vida é muito importante e dependemos de um sistema integrado e equilibrado que chamamos de <strong>ecossistema.</strong></p>

                                <p>Quando o homem se considera superior e não respeita o direito dos outros animais viverem chamamos de <strong>especismo</strong>. O homem não deve ser <strong>antropocêntrico</strong> e precisa valorizar a vida dos outros animais sem dominá-los ou sujeitá-los.</p>

                                <p>Os animais, tanto domésticos como silvestres, também precisam de cuidado e carinho. Por isso devemos preservar a natureza, respeitar a todos os seres cuidando deles e os protegendo.</p>

                                <p>Agindo assim seremos pessoas boas e éticas.</p>

                                <p class="alert alert-info">
                                    Dica:
                                    Converse com seu professor sobre as palavras que estão em negrito.
                                </p>
                            </div>
                        </div>
                    </div>
                    <div class="bb-item" id="item3">
                        <div class="content">
                            <div class="scroller">
                                <h2>Guarda Responsável</h2>
                                <p class="alert alert-danger">Para ser um bom guardião é preciso um conjunto de atitudes do proprietário, com a finalidade principal de garantir o bem-estar físico e emocional dos animais de companhia.</p>

                                <img class="imagem2" src="images/cartilha/img2.jpg">

                                <p>Ninguém é obrigado a ter um animal de estimação. Porém, a partir do momento em que essa escolha e feita, a pessoa que se propôs a ser o guardião do animal deve assumir a responsabilidade de zelar por sua qualidade de vida.</p>

                                <p>É preciso disponibilizar os elementos necessários para que o animalzinho tenha uma existência digna e saudável.</p>

                                <div class="alert alert-success" role="alert">
                                    <p class="cor-fonte1"><strong>Atenção!</strong></p>
                                    <p>Os animais domésticos não tem medo da presença do homem, pelo contrário, gostam da nossa companhia e de receber carinho. Mas, se fizermos coisas de que não gostam, eles podem se assustar, atacando as pessoas que estão por perto, pois essa á sua forma natural de defesa.</p>
                                </div>

                                <img src="images/cartilha/gato.jpg">

                                <div class="alert alert-info2" role="alert">   
                                    <h3> "Tu te tornas eternamente responsável por aquilo que cativas."</h3> 
                                    <h3>Antoine de Saint-Exupery</h3>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="bb-item" id="item4" >
                        <div class="content">
                            <div class="scroller">
                                <img class="capa" src="images/cartilha/img4e5.jpg">
                            </div>
                        </div>
                    </div>
                    <div class="bb-item" id="item5">
                        <div class="content">
                            <div class="scroller">
                                <h2>Ações que fazem a diferença</h2>
                                <p><strong>O melhor ensino é o exemplo</strong></p>                                                     
                                <p>A bondade, a justiça e a compaixão são virtudes que aprendemos por meio das relações pessoais.
                                    Quando as crianças crescem vendo bons exemplos, o mais provável é que se tornem adultos com capacidade de se colocarem no lugar do outro. Essa formação de caráter resulta em homens e mulheres que respeitam as leis e são cuidadosos com o outro.
                                    Algumas ações e reações podem fazer a diferença. Vamos citar algumas delas:</p>

                                <p class="alert alert-success">Valorizar a vida dos animais de estimação da família: ser paciente com eles.</p>

                                <p class="alert alert-info">Assegurar que tenham água, comida de qualidade, um lugar confortável para dormir.</p>

                                <p class="alert alert-warning">Garantir que sejam castrados para evitar crias indesejadas ou abandono e que visitem regularmente o veterinário.</p>

                                <p class="alert alert-danger">Nunca bater num animal ou agredi-lo fisicamente.</p>

                                <p class="alert alert-success">Referir-se aos animais de maneira respeitosa.</p>

                                <p class="alert alert-info">Ao presenciar atitudes hostis, de má-fé ou não, contra animais de quaisquer espécies, deve-se interferir com firmeza, corrigindo o agressor.</p>

                                <p class="alert alert-danger">Não frequentar locais onde os animais sirvam de entretenimento, subjugando sua natureza em favor do divertimento humano.</p>

                            </div>
                        </div>
                    </div>

                    <div class="bb-item" id="item6">
                        <div class="content">
                            <div class="scroller">
                                <img src="images/cartilha/img8e9.jpg">
                            </div>
                        </div>
                    </div>

                    <div class="bb-item" id="item7">
                        <div class="content">
                            <div class="scroller">
                                <img src="images/cartilha/img10e11.jpg">
                            </div>
                        </div>
                    </div>

                    <div class="bb-item" id="item8">
                        <div class="content">
                            <div class="scroller">
                                <img src="images/cartilha/img12e13.jpg">
                            </div>
                        </div>
                    </div>

                    <div class="bb-item" id="item9">
                        <div class="content">
                            <div class="scroller">
                                <img src="images/cartilha/img14e15.jpg">
                            </div>
                        </div>
                    </div>


                </div>
                <nav>
                    <span id="bb-nav-prev">&larr;</span>
                    <span id="bb-nav-next">&rarr;</span>
                </nav>

                <span id="tblcontents" class="menu-button">Table of Contents</span>
            </div>

        </div><!-- /container -->
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
        <script src="<%=App.BASE_URL%>/js/jquery.mousewheel.js"></script>
        <script src="<%=App.BASE_URL%>/js/jquery.jscrollpane.min.js"></script>
        <script src="<%=App.BASE_URL%>/js/jquerypp.custom.js"></script>
        <script src="<%=App.BASE_URL%>/js/jquery.bookblock.js"></script>
        <script src="<%=App.BASE_URL%>/js/page.js"></script>
        <script>
            $(function () {
                Page.init();
            });
        </script>
        <link rel="stylesheet" type="text/css" href="<%=App.BASE_URL%>/css/cartilha.css">
    </body>
</html>
