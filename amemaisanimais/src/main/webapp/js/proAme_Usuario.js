$(function () {
    'use strict';

    // Validação do campo de confirmação de senha
    $('#pswSenha, #pswSenhaRep').bind('change', function () {
        var $field = $('#pswSenha'),
                $fieldRep = $('#pswSenhaRep');

        if ($field.val() !== $fieldRep.val()) {
            $.changeFieldValidity({
                elements: [$field, $fieldRep],
                message: 'As senhas não são correspondentes!'
            });
        } else {
            $.changeFieldValidity({
                elements: [$field, $fieldRep],
                message: ''
            });
        }
    });

    // Validação dos campos de username, não inclui os campos de filtragem de resultados e de login
    $('#txtUsername').not('.proForm__input--filter, .proForm__input--login').bind('blur', function () {
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
                data: {'acao': 'existeUsuario', 'txtID': $id, 'txtUsername': $this.val()},
                url: '/amemaisanimais/private/usuarios',
                timeout: 5000,
                success: function (data) {
                    if (!$.isEmpty(data)) {
                        $.changeFieldValidity({
                            elements: [$this],
                            message: 'Este username já existe na base de dados!'
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