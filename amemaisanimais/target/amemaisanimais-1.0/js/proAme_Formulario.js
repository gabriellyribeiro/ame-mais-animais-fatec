$(function () {
    'use strict';

    // Floating Labels
    $('.proForm__input').not('.proForm__input--search-description').on('keyup blur focus change', function (e) {
        var $this = $(this),
            $label = $this.siblings('.proForm__label');

        if (e.type === 'keyup') {
            if (($this.val() === '') || (($this.prop('tagName') === 'SELECT') && ($this.val() === null))) {
                $label.addClass('proForm__label--hidden');
            } else {
                $label.removeClass('proForm__label--hidden');
            }
        } else if (e.type === 'blur') {
            if (($this.val() === '') || (($this.prop('tagName') === 'SELECT') && ($this.val() === null))) {
                $label.addClass('proForm__label--hidden');
            } else {
                $label.removeClass('proForm__label--hidden').addClass('proForm__label--visible');
            }

            if (this.checkValidity()) {
                if ($this.hasClass('proForm__input--error')) {
                    $this.removeClass('proForm__input--error');
                }
            }
        } else if (e.type === 'focus') {
            if (($this.val() !== '') || (($this.prop('tagName') === 'SELECT') && ($this.val() !== null))) {
                $label.removeClass('proForm__label--visible');
            }
        } else if (e.type == 'change') {
            if (($this.prop('tagName') === 'SELECT') && ($this.val() === null)) {
                $label.addClass('proForm__label--hidden');
            } else {
                $label.removeClass('proForm__label--hidden');
            }
        }
    });

    // Máscaras
    $('.maskID').inputmask('9999', {numericInput: true, 'placeholder': '0'});
    $('.maskIDOperacao').inputmask('999999', {'placeholder': '0'});
    $('.maskCNPJ').inputmask('99.999.999/9999-99');
    $('.maskCPF').inputmask('999.999.999-99');
    $('.maskCEP').inputmask('99999-999');
    $('.maskTelefone').inputmask('(99) 9999-9999');
    $('.maskCelular').inputmask('(99) 99999-9999');
    $('.maskPorcentagem').inputmask('99,99', {'placeholder': '0'});
    $('.maskNumEndereco').inputmask('999999', {'placeholder': ''});
    $('.maskData').inputmask('99/99/9999');
    $('.maskTaxa').inputmask('9,999999', {'placeholder': '0'});

    $('.maskCNPJ').not('.proForm__input--filter').bind('blur', function () {
        var $input = $(this);
        
        if ($.cnpjValidation($input.val())) {
            var $reqData = {'acao': 'existeDocumento'};

            if ($input.data('document') !== undefined) {
                for (var x in $input.data('document')) {
                    $reqData[x] = $input.data('document')[x];
                }
            }

            $reqData.txtCNPJ = $input.val();

            $.ajax({
                url: $input.data('document-url'),
                type: 'GET',
                data: $reqData,
                timeout: 10000,
                beforeSend: function () { $.changeLabelSpinnerStatus($input, 'visible'); },
                success:  function (data) {
                    $.changeFieldValidity({
                        elements: [$input],
                        message: (!$.isEmpty(data)) ? 'O CNPJ informado já existe na base de dados!' : ''
                    });
                },
                error: function () {},
                complete: function () { $.changeLabelSpinnerStatus($input, 'hidden'); }
            });
        } else {
            $.changeFieldValidity({
                elements: [$input],
                message: 'CNPJ inválido!'
            });
        }
    });

    // Busca de endereço através do CEP
    $('.maskCEP').bind('blur', function () {
        var $this = $(this),
            $nativeThis = this,
            $cep = $this.inputmask('unmaskedvalue');

        if ($cep.length === 8) {
            $.ajax({
                url: 'http://jlrv.com.br:8084/WSCep/cep/getEnderecoPorCep/' + $cep,
                dataType: 'json',
                beforeSend: function () { $.changeLabelSpinnerStatus($this, 'visible'); },
                success: function (data) {
                    if (!$.isEmpty(data)) {
                        $('#txtLogradouro').val(data.tipoLogradouro+ ' ' +data.logradouro).siblings('.proForm__label').removeClass('proForm__label--hidden').addClass('proForm__label--visible');
                        $('#txtBairro').val(data.bairro).siblings('.proForm__label').removeClass('proForm__label--hidden').addClass('proForm__label--visible');
                        $('#txtCidade').val(data.cidade).siblings('.proForm__label').removeClass('proForm__label--hidden').addClass('proForm__label--visible');
                        $('#selUf').val(data.uf).siblings('.proForm__label').removeClass('proForm__label--hidden').addClass('proForm__label--visible');                        
                        $('#numNumero').focus();
                    } else {
                        $('#txtLogradouro').val('').siblings('.proForm__label').removeClass('proForm__label--visible').addClass('proForm__label--hidden');
                        $('#txtBairro').val('').siblings('.proForm__label').removeClass('proForm__label--visible').addClass('proForm__label--hidden');
                        $('#txtCidade').val('').siblings('.proForm__label').removeClass('proForm__label--visible').addClass('proForm__label--hidden');
                        $('#selUf').val('').siblings('.proForm__label').removeClass('proForm__label--visible').addClass('proForm__label--hidden');
                    }

                    $.changeFieldValidity({
                        elements: [$this],
                        message: ''
                    });
                },
                complete: function() {
                    $.changeLabelSpinnerStatus($this, 'hidden');
                }
            });
        } else {
            $('#txtLogradouro').val('').siblings('.proForm__label').removeClass('proForm__label--visible').addClass('proForm__label--hidden');
            $('#txtBairro').val('').siblings('.proForm__label').removeClass('proForm__label--visible').addClass('proForm__label--hidden');
            $('#txtCidade').val('').siblings('.proForm__label').removeClass('proForm__label--visible').addClass('proForm__label--hidden');
            $('#selUf').val('').siblings('.proForm__label').removeClass('proForm__label--visible').addClass('proForm__label--hidden');
            $('#txtLogradouro').focus();
            $.changeFieldValidity({
                elements: [$this],
                message: 'CEP inválido!'
            });
        }
    });

    // Verificação de validação dos campos do formulário, mudando o estado do label de normal para error nos que não irão validar
    $('.proForm .proButton[type=\'submit\']').bind('click', function () {
        $('.proForm__input').each(function () {
            if (!this.checkValidity()) {
                if (!$(this).hasClass('proForm__input--error')) {
                    $(this).addClass('proForm__input--error');
                }
            }
        });
    });

    // Percorre todos os campos de formulários existentes em uma página que possui filtragem ou que esta sendo editada
    if ($('html').hasClass('visible-labels')) {
        $('.proForm__field').each(function () {
            if ($(this).children('.proForm__input').val() !== '') {
                $(this).children('.proForm__label').removeClass('proForm__label--hidden').addClass('proForm__label--visible');
            }
        });
    }

    $('#modalExcluir').on('show.bs.modal', function (event) {
        $(this).find('.proModal__description').text(event.relatedTarget.getAttribute('data-description'));
        $(this).find('.proButton--confirm').data('row', event.relatedTarget.getAttribute('data-row'));
    });

    $('#modalExcluir').on('hidden.bs.modal', function () {
        $(this).find('.proModal__description').empty().parent().removeClass('bg-danger bg-success');
        $(this).find('.proButton--confirm').show().button('reset');
        $(this).find('.proButton--cancel').text('Cancelar').attr('data-dismiss', 'modal');
    });

    $('#modalExcluir').delegate('.proButton--confirm', 'click', function () {
        var $btn = $(this),
            $modal = $('#modalExcluir');

        $.ajax({
            type: 'GET',
            url: $btn.data('url'),
            data: JSON.parse($btn.data('row')),
            timeout: 120000,
            beforeSend: function () {
                $btn.button('loading').siblings('.proButton--cancel').attr('disable', true);
            },
            success: function (data) {
                $modal.find('.proModal__description').text((data) ? 'O registro foi excluído com sucesso!': 'Ocorreu um erro ao excluir o registro, tente refazer a operação em alguns instantes.').parent().addClass((data) ? 'bg-success' : 'bg-danger');
            },
            error: function () {
                $modal.find('.proModal__description').text('Ocorreu um erro ao excluir o registro, tente refazer a operação em alguns instantes.'). parent().addClass('bg-danger');
            },
            complete: function () {
                $btn.hide().siblings('.proButton--cancel').attr('disable', false).text('Fechar').attr('href', $btn.data('url')).removeAttr('data-dismiss');
            }
        });
    });
});