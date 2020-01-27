$(function(){
	// Altera o estado do menu lateral
	$('.info-menu').on('click touchstart', function(e){
		$('html').toggleClass('menu-ativo');
		e.preventDefault();
	});

	$('.info-menu').tooltip();
});