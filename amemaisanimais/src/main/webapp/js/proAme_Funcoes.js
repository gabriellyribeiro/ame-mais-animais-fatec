$(function () {
    'use strict';

    // Validação customizada para campos de formulário
    $.changeFieldValidity = function (pOptions) {
        var label;

        for (var x in pOptions.elements) {
            label = pOptions.elements[x].siblings('.proForm__label');

            if ((pOptions.message !== '') && (!label.hasClass('.proForm__label--error'))) {
                label.addClass('proForm__label--error');
            } else if (pOptions.message === '') {
                label.removeClass('proForm__label--error');
            }

            pOptions.elements[x].get(0).setCustomValidity(pOptions.message);
        }
    };

    $.changeLabelSpinnerStatus = function (pInput, pStatus) {
        pInput.siblings('.proForm__label').children('.proForm__spinner').css('visibility', pStatus);
        $('.proForm .proButton[type=\'submit\']').attr('disabled', (pStatus === 'visible'));
    };

    // Retorna ao jQuery o elemento pai ('num' vezes) de um elemento HTML. Evita trechos como '$(this).parent().parent().parent()'...
    jQuery.fn.getNTHParent = function (num) {
        var last = this[0];

        for (var i = 0; i < num; i++) {
            last = last.parentNode;
        }

        return jQuery(last);
    };

    // Verifica se um objeto está vazio, se sim retorna true;
    $.isEmpty = function (pObj) {
        for (var prop in pObj) {
            if (pObj.hasOwnProperty(prop)) {
                return false;
            }
        }

        return true;
    };

    // Validação de CNPJ
    $.cnpjValidation = function (pCNPJ) {
        var cnpj = pCNPJ.replace(/[^\d]+/g, ''),
                invalidos = ['', '00000000000000', '11111111111111', '22222222222222', '33333333333333', '44444444444444', '55555555555555', '66666666666666', '77777777777777', '88888888888888', '99999999999999'];

        if ((cnpj.length !== 14) || (invalidos.indexOf(cnpj) !== -1)) {
            return false;
        }

        // Valida DVs
        var tamanho = cnpj.length - 2,
                numeros = cnpj.substring(0, tamanho),
                digitos = cnpj.substring(tamanho),
                soma = 0,
                pos = tamanho - 7;

        for (var i = tamanho; i >= 1; i--) {
            soma += numeros.charAt(tamanho - i) * pos--;
            if (pos < 2) {
                pos = 9;
            }
        }

        var resultado = soma % 11 < 2 ? 0 : 11 - soma % 11;
        if (resultado != digitos.charAt(0)) {
            return false;
        }

        tamanho = tamanho + 1;
        numeros = cnpj.substring(0, tamanho);
        soma = 0;
        pos = tamanho - 7;

        for (i = tamanho; i >= 1; i--) {
            soma += numeros.charAt(tamanho - i) * pos--;
            if (pos < 2) {
                pos = 9;
            }
        }

        resultado = soma % 11 < 2 ? 0 : 11 - soma % 11;
        if (resultado != digitos.charAt(1)) {
            return false;
        }

        return true;
    };

    // Insere um well no wrapper para requisições do infinite scroll
    $.insertLoader = function (pTable) {
        $('.proTableError').remove();
        if ($('#' + pTable.attr('id') + '__loader').length) {
            throw 'Já existe um loader na tabela informada!';
        } else {
            var wellLoader = '<div class=\'proTableLoader well well-sm no-rounded-border text-center\' id=\'' + pTable.attr('id') + '__loader\'><p><i class=\'fa fa-spinner fa-spin fa-lg\'></i> Carregando registros...</p></div>';

            pTable.addClass('proTable--loading').getNTHParent('2').append(wellLoader);
        }
    };

    // Remove o loader do infinite scroll
    $.removeLoader = function (pTable) {
        $('#' + pTable.attr('id') + '__loader').remove();
        pTable.removeClass('proTable--loading');
    };

    // Well que informa ao usuário que todos os resultados do banco com a filtragem aplicada estão sendo apresentados na tabela
    $.insertTableEOF = function (pTable) {
        pTable.addClass('proTable--EOF').getNTHParent('2').append('<div class=\'proTableEOF well well-sm no-rounded-border text-center\' ><p>Não há mais registros.</p></div>');
    };

    // Alert que informa o usuário de algum erro ocorrido na requisição AJAX
    $.insertTableError = function (pTable, jqXHR) {
        pTable.getNTHParent('2').append('<div class=\'proTableError alert alert-danger text-center\'><button class=\'close\' data-dismiss=\'alert\'>&times;</button><p>Ops! <strong>Um erro (status ' + jqXHR.status + ') ocorreu ao requisitar as informações, tente refazer a operação em alguns instantes.</strong></p></div>');
    };

    // Insere os dados do infinite scroll na tabela
    $.insertTableLines = function (url, pTable, data) {
        var lines = '';

        if (/categorias/.test(url)) {
            $.each(data, function (key, categoria) {
                lines += '<tr><td class=\'text-center\' data-toggle=\'modal\' data-target=\'#modalExcluir\' data-backdrop=\'static\' data-row=\'{\'acao\': \'excluir\', \'id\': \'' + categoria.id + '\'}\' data-description=\'Deseja realmente excluir a categoria ' + categoria.id + ' - ' + categoria.descricao + '?\'><a class=\'proTooltip proButton--link\' data-toggle=\'tooltip\' data-original-title=\'Excluir\' href=\'#\'><i class=\'fa fa-trash-o fa-lg\'></i><span class=\'visible-sm visible-xs\'>Excluir</span></a></td>' +
                        '<td class=\'text-center\'><a class=\'proTooltip proButton--link\' data-toggle=\'tooltip\' data-original-title=\'Alterar\' href=\'?acao=alterar&id=' + categoria.id + '\'><i class=\'fa fa-edit fa-lg\'></i><span class=\'visible-sm visible-xs\'>Alterar</span></a></td>' +
                        '<td class=\'proTable__nomeUsuario\'>' + categoria.descricao + '</td>';
            });
        } else if (/usuarios/.test(url)) {
            $.each(data, function (key, usuario) {
                lines += '<tr><td class=\'text-center\' data-toggle=\'modal\' data-target=\'#modalExcluir\' data-backdrop=\'static\' data-row=\'{\'acao\': \'excluir\', \'id\': \'' + usuario.id + '\'}\' data-description=\'Deseja realmente excluir o usuário ' + usuario.idUsuario + ' - ' + usuario.nome + '?\'><a class=\'proTooltip proButton--link\' data-toggle=\'tooltip\' data-original-title=\'Excluir\' href=\'#\'><i class=\'fa fa-trash-o fa-lg\'></i><span class=\'visible-sm visible-xs\'>Excluir</span></a></td>' +
                        '<td class=\'text-center\'><a class=\'proTooltip proButton--link\' data-toggle=\'tooltip\' data-original-title=\'Alterar\' href=\'?acao=alterar&id=' + usuario.id + '\'><i class=\'fa fa-edit fa-lg\'></i><span class=\'visible-sm visible-xs\'>Alterar</span></a></td>' +
                        '<td class=\'proTable__nomeUsuario\'>' + usuario.nome + '</td>' +
                        '<td class=\'visible-lg\'>' + usuario.userName + '</td>' +
                        '<td>' + usuario.perfil.descricao + '</td>';
            });
        } else if (/legislacoes/.test(url)) {
            $.each(data, function (key, legislacao) {
                lines += '<tr><td class=\'text-center\' data-toggle=\'modal\' data-target=\'#modalExcluir\' data-backdrop=\'static\' data-row=\'{\'acao\': \'excluir\', \'id\': \'' + legislacao.id + '\'}\' data-description=\'Deseja realmente excluir a legislação ' + legislacao.id + ' - ' + legislacao.titulo + '?\'><a class=\'proTooltip proButton--link\' data-toggle=\'tooltip\' data-original-title=\'Excluir\' href=\'#\'><i class=\'fa fa-trash-o fa-lg\'></i><span class=\'visible-sm visible-xs\'>Excluir</span></a></td>' +
                        '<td class=\'text-center\'><a class=\'proTooltip proButton--link\' data-toggle=\'tooltip\' data-original-title=\'Alterar\' href=\'?acao=alterar&id=' + legislacao.id + '\'><i class=\'fa fa-edit fa-lg\'></i><span class=\'visible-sm visible-xs\'>Alterar</span></a></td>' +
                        '<td class=\'proTable__nomeUsuario\'>' + legislacao.titulo + '</td>';
            });
        } else if (/receitas/.test(url)) {
            $.each(data, function (key, receita) {
                lines += '<tr><td class=\'text-center\' data-toggle=\'modal\' data-target=\'#modalExcluir\' data-backdrop=\'static\' data-row=\'{\'acao\': \'excluir\', \'id\': \'' + receita.id + '\'}\' data-description=\'Deseja realmente excluir a receita ' + receita.id + ' - ' + receita.titulo + '?\'><a class=\'proTooltip proButton--link\' data-toggle=\'tooltip\' data-original-title=\'Excluir\' href=\'#\'><i class=\'fa fa-trash-o fa-lg\'></i><span class=\'visible-sm visible-xs\'>Excluir</span></a></td>' +
                        '<td class=\'text-center\'><a class=\'proTooltip proButton--link\' data-toggle=\'tooltip\' data-original-title=\'Alterar\' href=\'?acao=alterar&id=' + receita.id + '\'><i class=\'fa fa-edit fa-lg\'></i><span class=\'visible-sm visible-xs\'>Alterar</span></a></td>' +
                        '<td class=\'proTable__nomeUsuario\'>' + receita.titulo + '</td>';
            });
        }else if (/dicas/.test(url)) {
            $.each(data, function (key, dica) {
                lines += '<tr><td class=\'text-center\' data-toggle=\'modal\' data-target=\'#modalExcluir\' data-backdrop=\'static\' data-row=\'{\'acao\': \'excluir\', \'id\': \'' + dica.id + '\'}\' data-description=\'Deseja realmente excluir a dica ' + dica.id + ' - ' + dica.titulo + '?\'><a class=\'proTooltip proButton--link\' data-toggle=\'tooltip\' data-original-title=\'Excluir\' href=\'#\'><i class=\'fa fa-trash-o fa-lg\'></i><span class=\'visible-sm visible-xs\'>Excluir</span></a></td>' +
                        '<td class=\'text-center\'><a class=\'proTooltip proButton--link\' data-toggle=\'tooltip\' data-original-title=\'Alterar\' href=\'?acao=alterar&id=' + dica.id + '\'><i class=\'fa fa-edit fa-lg\'></i><span class=\'visible-sm visible-xs\'>Alterar</span></a></td>' +
                        '<td class=\'proTable__nomeUsuario\'>' + dica.titulo + '</td>';
            });
        }
        else if (/perfis/.test(url)) {
            $.each(data, function (key, perfil) {
                lines += '<td class=\'text-center\'><a class=\'proTooltip proButton--link\' data-toggle=\'tooltip\' data-original-title=\'Alterar\' href=\'?acao=alterar&id=' + perfil.id + '\'><i class=\'fa fa-edit fa-lg\'></i><span class=\'visible-sm visible-xs\'>Alterar</span></a></td>' +
                        '<td class=\'proTable__nomeUsuario\'>' + perfil.descricao + '</td>';
            });
        }

        pTable.children('tbody').append(lines);
    };
});