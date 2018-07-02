"use strict";

$(document).ready(function () {
    $("#botao-novo-cadastro").click(clickNovoCadastro);
    
    var dataTablesConfig = jQuery.extend({ "initComplete": aplicarTabelaListeners }, DATATABLES_CONFIG_PADRAO);
    
    $("#tabela-palavras").DataTable(dataTablesConfig);
});

/* Pede pro servidor criar uma nova palavra, recebendo o modal  */
function clickNovoCadastro(e) {
    e.preventDefault();

    $.get({
        url: window.location + "/novo",
        cache: false
    })
        .done(adicionarFormulario)
        .fail(function () {
            alert("Falha ao criar novo cadastro.");
        });
}

/* Pede pro servidor a palavra clicada, recebendo o modal  */
function clickEditarCadastro(e) {
    e.preventDefault();
    var palavraId = $(this).closest("button").attr("id");

    $.get({
        url: window.location + "/" + palavraId,
        cache: false
    })
        .done(adicionarFormulario)
        .fail(function () {
            alert("Falha ao editar o cadastro.");
        });
}

function adicionarFormulario(novoFormHtml) {
    var paiDoForm = $(".modal-content");
    var form = $("#form-cadastro");
    substituirFragmento(paiDoForm, form, novoFormHtml);
    
    $('#modal-cadastro').modal('show'); // Exibe o modal

    aplicarModalListeners();
}

/* Envia o formulário pro servidor, recebendo a tabela de palavras atualizada */
function onEnviarForm(e) {
    var botaoEnvio = $("#botao-enviar-form");

    var form = $('#form-cadastro')[0];
    var dados = new FormData(form);

    e.preventDefault();
    botaoEnvio.prop("disabled", true); // Desabilita o botão de envio

    $.post({
        url: window.location + "/salvar",
        data: dados,
        enctype: 'multipart/form-data',
        processData: false,
        contentType: false,
        cache: false
    })
        .done(function () {
            window.location.reload(true); // Recarrega a página
        })
        .fail(function () {
            alert("Falha ao salvar o cadastro. Confira os campos e tente novamente.");
            botaoEnvio.prop('disabled', false); // Reabilita o botão de envio
        });
}


/* -- Funções Auxiliares -- */

function substituirFragmento(elementoPai, elementoAtual, fragmentoHtml) {
    elementoAtual.remove(); // Remove se já existir (antigo)
    elementoPai.append(fragmentoHtml); // Adiciona o fragmento de HTML no elemento pai
}

function aplicarTabelaListeners() {
    $(".botao-editar-cadastro").click(clickEditarCadastro);
}

function aplicarModalListeners() {
    /* Listener para fazer o envio do formulário */
    $("#form-cadastro").submit(onEnviarForm);
}
