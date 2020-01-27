$(function () {
    'use strict';

    // Infinite scroll
    $('.proTable--infinite-scroll').parent().on('scroll', function () {
        var $tabela = $(this).children('.proTable--infinite-scroll');

        if (($(this).scrollTop() + $(this).innerHeight() >= (this.scrollHeight - 1)) && (!$tabela.is('.proTable--loading, .proTable--EOF'))) {
            var reqData = {
                saida: 'json',
                pagina: $tabela.data('scroll-page')
            };

            if ($tabela.data('filter') !== undefined) {
                for (var x in $tabela.data('filter')) {
                    reqData[x] = $tabela.data('filter')[x];
                }
            }

           $.ajax({
                url: $tabela.data('url'),
                type: 'GET',
                data: reqData,
                timeout: 10000,
                beforeSend: function () { $.insertLoader($tabela); },
                success: function (data) {
                    if (!$.isEmpty(data)) {
                        $.insertTableLines($tabela.data('url'), $tabela, data);
                        $tabela.data('scroll-page', parseInt($tabela.data('scroll-page')) + 1);
                    } else {
                        $.insertTableEOF($tabela);
                    }
                },
                complete: function () { $.removeLoader($tabela); },
                error: function (jqXHR) { $.insertTableError($tabela, jqXHR); }
            });
        }
    });
});