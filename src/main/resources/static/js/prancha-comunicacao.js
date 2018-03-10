/* ########## CONFIGURAÇÃO ########## */

var TEMPO_SELETOR = 1000; // Tempo em ms para o movimento do seletor

/* ########## FIM DA CONFIGURAÇÃO ########## */


/* ### CONSTANTES DO SCRIPT ### */

/* CSS */

var CLASSE_SECAO_SELECIONAVEL = "secao-selecionavel";
var CLASSE_ITEM_SELECIONAVEL = "item-selecionavel";

var CLASSE_SECAO_REALCE = "secao-realce";
var CLASSE_ITEM_REALCE = "item-realce";

var CLASSE_ITEM_ACAO = "item-acao";
var CLASSE_ITEM_SELECIONADO = "item-selecionado";

var ID_SECAO_FORMACAO = "secao-formacao";


/* Enum de estados do seletor */

var EstadoSeletorEnum = {
    SELETOR_PARADO: "SELETOR_PARADO",
    SELETOR_SECAO: "SELETOR_SECAO",
    SELETOR_ITEM: "SELETOR_ITEM"
};

if (Object.freeze) {
    Object.freeze(EstadoSeletorEnum); // Torna imutável se suportar ES5
}


/* ### SCRIPT DO SELETOR ### */

var estadoSeletor = EstadoSeletorEnum.SELETOR_PARADO;
var seletorIntervalId = undefined;

$(window).ready(rodarSeletorSecao);


/* Clique */

$("body").not("#topo").click(clickFeito); // Qualquer parte da página, exceto pelo navbar

function clickFeito() {
    if (estadoSeletor === EstadoSeletorEnum.SELETOR_SECAO) {
        rodarSeletorItem();
    } else if (estadoSeletor === EstadoSeletorEnum.SELETOR_ITEM) {
        pararSeletor();

        var itemRealcado = getItemRealcado();
        if (itemRealcado.hasClass(CLASSE_ITEM_ACAO)) {
            //fazerAcao(itemRealcado);
        } else {
            selecionarItem(itemRealcado);
        }

        desativarTodosRealces();
        rodarSeletorSecao();
    }
}

/* Seletor */

function rodarSeletorSecao() {
    if (estadoSeletor !== EstadoSeletorEnum.SELETOR_PARADO) {
        pararSeletor();
    }

    var secoes = getSecoesSelecionaveis();
    estadoSeletor = EstadoSeletorEnum.SELETOR_SECAO;
    iniciarSeletor(secoes, ativarRealceSecao, desativarRealceSecao);
}

function rodarSeletorItem() {
    if (estadoSeletor !== EstadoSeletorEnum.SELETOR_PARADO) {
        pararSeletor();
    }

    var itensSecaoEscolhida = getItensSelecionaveis();
    estadoSeletor = EstadoSeletorEnum.SELETOR_ITEM;
    iniciarSeletor(itensSecaoEscolhida, ativarRealceItem, desativarRealceItem);
}

function iniciarSeletor(lista, funcaoAtivarRealce, funcaoDesativarRealce) {
    var posAtual = 0;
    var qntd = lista.length;

    seletorIntervalId = setInterval(function () {
        var posAnterior = calcPosCircular(posAtual - 1, qntd);

        funcaoDesativarRealce(lista[posAnterior]);
        funcaoAtivarRealce(lista[posAtual]);

        posAtual = calcPosCircular(posAtual + 1, qntd);
    }, TEMPO_SELETOR);
}

function pararSeletor() {
    estadoSeletor = EstadoSeletorEnum.SELETOR_PARADO;

    if (seletorIntervalId) {
        clearInterval(seletorIntervalId);
        seletorIntervalId = null;
    }
}

function calcPosCircular(pos, qntdSecao) {
    // Posicao circular: pos -1 -> 3; pos 0 -> 0; ...; pos qntdSecao -> 0; pos qntdSecao + 1 -> 1;
    return (pos + qntdSecao) % qntdSecao; //
}

/* Realce */

function ativarRealceSecao(secao) {
    secao.classList.add(CLASSE_SECAO_REALCE);
}

function desativarRealceSecao(secao) {
    secao.classList.remove(CLASSE_SECAO_REALCE);
}

function ativarRealceItem(item) {
    item.classList.add(CLASSE_ITEM_REALCE);
}

function desativarRealceItem(item) {
    item.classList.remove(CLASSE_ITEM_REALCE);
}

function desativarTodosRealces() {
    $("." + CLASSE_SECAO_REALCE).removeClass(CLASSE_SECAO_REALCE);
    $("." + CLASSE_ITEM_REALCE).removeClass(CLASSE_ITEM_REALCE);
}

/* Funções Ajudantes */

function getSecoesSelecionaveis() {
    return $("." + CLASSE_SECAO_SELECIONAVEL).filter(":visible");
}

function getItensSelecionaveis() {
    var selector = "." + CLASSE_SECAO_REALCE + " ." + CLASSE_ITEM_SELECIONAVEL;
    return $(selector).filter(":visible");
}

function getItemRealcado() {
    return $("." + CLASSE_SECAO_REALCE + " ." + CLASSE_ITEM_REALCE).filter(":visible");
}

function selecionarItem(item) {
    item.detach();

    // Deixa de ser selecionavel
    item.removeClass(CLASSE_ITEM_SELECIONAVEL);
    item.addClass(CLASSE_ITEM_SELECIONADO);

    moverParaSecaoFormacao(item);
}

function moverParaSecaoFormacao(item) {
    item.appendTo("#" + ID_SECAO_FORMACAO).scrollView();
}

$.fn.scrollView = function () {
    return this.each(function () {
        $('html, body').animate({
            scrollTop: $(this).offset().top - 20
        }, 500);
    });
};