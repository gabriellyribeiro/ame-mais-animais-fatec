$(function () {
    'use strict';

    // Validação dos campos de descrição, não inclui os campos de filtragem de resultados
    $('#txtTitulo').not('.proForm__input--filter, .proForm__input--login').bind('blur', function () {
        if ($(this).val() !== '') {

            var $this = $(this),
                    $id = 0;

            if ($('#txtID').val().length) {
                $id = $('#txtID').val().val();
            }

            $.ajax({
                beforeSend: function () {
                    $.changeLabelSpinnerStatus($this, 'visible');
                },
                type: 'get',
                data: {'acao': 'existeDica', 'txtID': $id, 'txtTitulo': $this.val()},
                url: '/amemaisanimais/private/dicas',
                timeout: 5000,
                success: function (data) {
                    if (!$.isEmpty(data)) {
                        $.changeFieldValidity({
                            elements: [$this],
                            message: 'Está dica já existe na base de dados!'
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