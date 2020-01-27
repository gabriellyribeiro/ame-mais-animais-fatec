/**
    * Controla o tamanho das fontes
    *
    * @param STRING strSymbol  			// Simbolo que controla a operacao, ou seja, + para aumentar, - para diminuir a fonte e 0 para retornar o tamanho original
    * @param INTEGER intPercentMax		// Percentual maximo que se queira aumentar uma fonte (lembrando que a fonte inicial possui percentual de 100) - parametro opcional
    * @param INTEGER intPercentMin		// Percentual minimo que se queira diminuir uma fonte (lembrando que a fonte inicial possui percentual de 100) - parametro opcional
    * @param INTEGER intPercentOperation	// Percentual que eh trabalhado em cada operacao - parametro opcional (default = 5)
    * @return BOOLEAN
*/
var intPercentFontSizeGlobal = new Array();
function controlFontSize( strSymbol , intPercentMax , intPercentMin , intPercentOperation )
{
        // Inserindo valores padroes nos parametros
        if ( ( strSymbol == undefined ) || ( ( strSymbol != "+" ) && ( strSymbol != "-" ) ) ) strSymbol = "0";
        if ( intPercentOperation == undefined ) intPercentOperation = 1;

        // Definindo tags que terao o controle de tam
        var arrTagFontSize = new Array( new Array("P",14) , new Array("H1", 35 ), new Array("H2", 30 ), new Array("H3", 20 ) , new Array("A", 14 ) , new Array("LI", 14 ));

        
        for ( var i = 0 ; i < arrTagFontSize.length ; ++i )
        {
                var strTagFontSize = arrTagFontSize[ i ][ 0 ];
                
                if ( intPercentFontSizeGlobal[i] == undefined ) intPercentFontSizeGlobal[i] = arrTagFontSize[ i ][ 1 ];
                 // Realizando os devidos controles
                if ( strSymbol == "0" ){
                    var intPercentFontSize = arrTagFontSize[ i ][ 1 ];
                    intPercentFontSizeGlobal[i] = intPercentFontSize;
                } 
                else{
                    eval( "intPercentFontSizeGlobal["+i+"] = intPercentFontSizeGlobal["+i+"] " + strSymbol + " " + intPercentOperation + ";" );
                    intPercentFontSize = intPercentFontSizeGlobal[i];
                }
                
                if ( ( intPercentMax != undefined ) && ( intPercentFontSize > intPercentMax ) ) return ( false );
                if ( ( intPercentMin != undefined ) && ( intPercentFontSize < intPercentMin ) ) return ( false );			
                
                
                var arrTag = document.getElementsByTagName( strTagFontSize );
                for ( var j = 0 ; j < arrTag.length ; ++j )
                {
                        var objTag = arrTag[ j ];					
                        objTag.style.fontSize = intPercentFontSize + "px";
                }
        }

        // Retorno da funcao
        return ( true );
}	

$(document).ready(function(){	
	$('#scrollbar1').tinyscrollbar();
	
	$("a.AbreModal").fancybox({
		'padding': '0',
		'titleShow': false,
		'autoDimensions': false,
		'width': 970,
		'height': 'auto',
		'hideOnContentClick': true,
		'autoScale': false		
	});	
	
	// inicio contraste
    
    $('#contrasteTabl').click(function(){
    $('.contraste').css('background-color', 'black').css('color', 'blue');
         $('.mdcor1').css('color', 'green');
        $('.mdcor2').css('color', 'green');
    });
    
    $('#contrasteSmart').click(function(){
    $('.contraste').css('background-color', 'black').css('color', 'blue');
         $('.mdcor1').css('color', 'yellow');
        $('.mdcor2').css('color', 'yellow');
    });
    
    $('#bt_contraste').click(function(){
    $('.contraste').css('background-color', 'black').css('color', 'blue');
         $('.mdcor1').css('color', 'orange');
        $('.mdcor2').css('color', 'orange');
        $('a').css('color', 'blue');
		$('footer').css('background', 'white');
		$('nav').css('background', 'white');
    });
    
    // fim contraste
});