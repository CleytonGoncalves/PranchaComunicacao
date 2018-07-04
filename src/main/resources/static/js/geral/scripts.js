/* JS GLOBAL */
"use strict";

/* ##### CONSTANTES ##### */

var TAMANHO_MAX_IMAGEM = 2000; // Comprimento/Largura em px
var TAMANHO_MIN_IMAGEM = 100; // Comprimento/Largura em px
var PESO_MAX_IMAGEM = 2000; // Peso em KB

var DATATABLES_PT_LANG = {
    "sEmptyTable": "Nenhum registro encontrado",
    "sInfo": "Mostrando registros _START_-_END_ de _TOTAL_",
    "sInfoEmpty": "Nenhum registro encontrado",
    "sInfoFiltered": "(Filtrados de _MAX_ registros)",
    "sInfoPostFix": "",
    "sInfoThousands": ".",
    "sLengthMenu": "_MENU_ resultados por página",
    "sLoadingRecords": "Carregando...",
    "sProcessing": "Processando...",
    "sZeroRecords": "Nenhum registro encontrado",
    "sSearch": "Pesquisar",
    "oPaginate": {
        "sNext": "Próximo",
        "sPrevious": "Anterior",
        "sFirst": "Primeiro",
        "sLast": "Último"
    },
    "oAria": {
        "sSortAscending": ": Ordenar colunas de forma ascendente",
        "sSortDescending": ": Ordenar colunas de forma descendente"
    }
};

var DATATABLES_CONFIG_PADRAO = {
    "language": DATATABLES_PT_LANG,
    "pagingType": "numbers",
    responsive: true,
    retrieve: true,
    "info": false,
    "lengthChange": false
};

/* ##### FUNÇÕES ##### */

/* ### Upload Imagem ### */

/* Listener para habilitar pré-visualização ao selecionar imagem */
$('body').on('change', '.input-imagem-real', function () {
    carregarImagemPreview(this);
});

/* Repassa o click no botão falso de seleção de imagem para o botão real */
$('body').on('click', '.input-imagem-exibido', function () {
    $('.input-imagem-real').click()
});

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
            var imagemTag = $('.imagem-preview');
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

/* ### Funções úteis auxiliares ### */

$.fn.scrollView = function () {
    return this.each(function () {
        $('html, body').animate({
            scrollTop: $(this).offset().top - 20
        }, 500);
    });
};

String.prototype.format = String.prototype.f = function () {
    var s = this,
        i = arguments.length;
    
    while (i--) {
        s = s.replace(new RegExp('\\{' + i + '\\}', 'gm'), arguments[i]);
    }
    return s;
};
