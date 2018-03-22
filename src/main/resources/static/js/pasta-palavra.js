"use strict";

var TAMANHO_MAX_IMAGEM = 2000; // Comprimento/Largura em px
var TAMANHO_MIN_IMAGEM = 100; // Comprimento/Largura em px
var PESO_MAX_IMAGEM = 2000; // Peso em KB

$(document).ready(function () {
    $("#botao-novo-cadastro").click(clickNovoCadastro);
    aplicarTabelaListeners();
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
    var paiDoForm = $("#modal-conteudo");
    var form = $("#form-cadastro");
    substituirFragmento(paiDoForm, form, novoFormHtml);

    $('#modal-cadastro').modal('show'); // Exibe o modal

    aplicarModalListeners();
}

function validarImagem(pesoKB) {
    var altura = this.height;
    var largura = this.width;

    if (altura < TAMANHO_MIN_IMAGEM || largura < TAMANHO_MIN_IMAGEM) {
        alert("A imagem é muito pequena. As dimensões devem estar entre "
            + TAMANHO_MIN_IMAGEM + "px por " + TAMANHO_MIN_IMAGEM + "px.");
        return false;
    }

    if (altura > TAMANHO_MAX_IMAGEM || largura > TAMANHO_MAX_IMAGEM) {
        alert("A imagem é muito grande. As dimensões devem estar entre "
            + TAMANHO_MAX_IMAGEM + "px por " + TAMANHO_MAX_IMAGEM + "px.");
        return false;
    }

    if (pesoKB > PESO_MAX_IMAGEM) {
        alert("A imagem é muito pesada. Peso máximo permitido é de " +
            PESO_MAX_IMAGEM + "KB.");
        return false;
    }

    return true;
}

/* Pré-Visualização da imagem escolhida no formulário */
function carregarImagemPreview(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        var arquivo = input.files[0];

        reader.onload = function (e) {
            var imagemTag = $('#imagem-palavra');
            imagemTag.attr("src", e.target.result);

            //TODO: Ativar validação de tamanho de imagem
            //var pesoKB = Math.round(arquivo.size / 1024);
            //imagemTag.onload = function() {
            //  validarImagem(pesoKB);
            //}
        };

        reader.readAsDataURL(arquivo);
    }
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
    /* Listener para habilitar pré-visualização ao selecionar imagem */
    $("#input-imagem-real").change(function () {
        carregarImagemPreview(this);
    });

    /* Repassa o click no botão falso de seleção de imagem para o botão real */
    $("#input-imagem-exibido").click(function () {
        $('#input-imagem-real').click()
    });

    /* Listener para fazer o envio do formulário */
    $("#form-cadastro").submit(onEnviarForm);
}
