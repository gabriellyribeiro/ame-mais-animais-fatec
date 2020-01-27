$(function () {
    'use strict';

    // Validação dos campos de descrição, não inclui os campos de filtragem de resultados
    $('#txtDescricao').not('.proForm__input--filter, .proForm__input--login').bind('blur', function () {
        if ($(this).val() !== '') {

            var $this = $(this),
                    $id = 0;

            if ($('#txtID').val().length) {
                $id = $('#txtID').val();
            }

            $.ajax({
                beforeSend: function () {
                    $.changeLabelSpinnerStatus($this, 'visible');
                },
                type: 'get',
                data: {'acao': 'existeCategoria', 'txtID': $id, 'txtDescricao': $this.val()},
                url: '/amemaisanimais/private/categorias',
                timeout: 5000,
                success: function (data) {
                    if (!$.isEmpty(data)) {
                        $.changeFieldValidity({
                            elements: [$this],
                            message: 'Está categoria já existe na base de dados!'
                        });
                    }
                    else {
                        $.changeFieldValidity({
                            elements: [$this],
                            message: ''
                        });
                    }
                },
                complete: function () {
                    $.changeLabelSpinnerStatus($this, 'hidden');
                },
            });
        }
    });
});