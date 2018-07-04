var SECOES_IDS = ["secao-sujeitos", "secao-verbos", "secao-complementos", "secao-diversos"];

var listaPalavrasSelecionadas = [];
var idxSecaoAtual = 0;

$(document).ready(function () {
    $("#form-cadastro").submit(onEnviarForm);
    $("#btn-anterior-categoria").prop("disabled", true);
    exibirSecaoAtual();
});

$("#btn-anterior-categoria").on('click', function(e) {
    if (idxSecaoAtual > 0) {
        idxSecaoAtual--;
        exibirSecaoAtual();
    
        $("#btn-proxima-categoria").prop("disabled", false);
        
        if (idxSecaoAtual === 0) {
            $(this).prop("disabled", true);
        }
    }
});

$("#btn-proxima-categoria").on('click', function(e) {
    if (idxSecaoAtual < SECOES_IDS.length - 1) {
        idxSecaoAtual++;
        exibirSecaoAtual();
    
        $("#btn-anterior-categoria").prop("disabled", false);
        
        if (idxSecaoAtual === SECOES_IDS.length - 1) {
            $(this).prop("disabled", true);
        }
    }
});

$("input[type='checkbox']").on('change', function(e) {
    var id = $(this).prop("id");
    
    if (listaPalavrasSelecionadas.indexOf(id) === -1) {
        listaPalavrasSelecionadas.push(id);
    } else {
        listaPalavrasSelecionadas = listaPalavrasSelecionadas.filter(function (value, index, self) {
            return value !== id;
        });
    }
});

function exibirSecaoAtual() {
    $(".secao-palavra").hide();
    
    var secaoAtual = $("#{0}".f(SECOES_IDS[idxSecaoAtual]));
    secaoAtual.show();
    
    secaoAtual.find("table").DataTable(DATATABLES_CONFIG_PADRAO);
}

function onEnviarForm(e) {
    var botaoEnvio = $("#botao-enviar-form");
    
    var form = $('#form-cadastro')[0];
    var dados = new FormData(form);
    dados.append('palavrasIds', JSON.stringify(listaPalavrasSelecionadas));
    
    e.preventDefault();
    botaoEnvio.prop("disabled", true); // Desabilita o botão de envio
    
    $.post({
        url: window.origin + "/pastaPaciente/salvar",
        data: dados,
        enctype: 'multipart/form-data',
        processData: false,
        contentType: false,
        cache: false
    })
    .done(function () {
        window.location = window.origin + "/pastaPaciente";
    })
    .fail(function () {
        alert("Falha ao salvar o cadastro. Confira os campos e tente novamente.");
        botaoEnvio.prop('disabled', false); // Reabilita o botão de envio
    });
}
