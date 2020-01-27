$(function() {
    'use strict';

    $('body').tooltip({ selector: '[data-toggle=tooltip]' });

    $('#modalMensagem').modal('show');
    $('#modalException').modal('show');

    $('#btnFiltrar').bind('click', function() {
        $('#formFiltrar').submit();
    });

    $('#selNatFato').on('blur', function() {
        $.ajax({
            url: '../naturezas_cad/',
            timeout: 10000,
            type: 'GET',
            data: { 'acao': 'getDescrFato', 'idFato': $('#selNatFato').val() },
            success: function(data) {
                $('#txtDescricao').val(data);
            },
            /*error: function(jqXHR, textStatus, errorThrown) {
                
            },
            complete: function(jqXHR, textStatus) {
                
            }*/
        });
    });
});