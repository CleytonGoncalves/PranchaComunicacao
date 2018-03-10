/* ########## CONFIGURAÇÃO ########## */

var TEMPO_SELETOR = 1000; // Tempo em ms para o movimento do seletor

/* ########## FIM DA CONFIGURAÇÃO ########## */


/* ### CONSTANTES DO SCRIPT ### */

/* Classes CSS */

var CLASSE_SECAO_SELECIONAVEL = "secao-selecionavel";
var CLASSE_ITEM_SELECIONAVEL = "item-selecionavel";

var CLASSE_SECAO_SELECIONADA = "secao-realce";
var CLASSE_ITEM_SELECIONADO = "item-realce";


/* Enum de estados do seletor */

var EstadoSeletorEnum = {
    SELETOR_PARADO: "SELETOR_PARADO",
    SELETOR_SECAO: "SELETOR_SECAO",
    SELETOR_ITEM: "SELETOR_ITEM"
};

if (Object.freeze) {
    Object.freeze(EstadoSeletorEnum);
} // Torna imutável se suportar ES5


/* ### SCRIPT DO SELETOR ### */

var estadoSeletor = EstadoSeletorEnum.SELETOR_PARADO;
var seletorIntervalId = undefined;

$(window).ready(function () {
    rodarSeletorSecao();
});

$("body").not("#topo").click(clickFeito); // Qualquer parte da página, exceto pelo navbar

/* Clique */

function clickFeito() {
    if (estadoSeletor === EstadoSeletorEnum.SELETOR_SECAO) {
        rodarSeletorItem();
    } else if (estadoSeletor === EstadoSeletorEnum.SELETOR_ITEM) {
        //    TODO: MOVER O ITEM SELECIONADO PARA A SECAO DE EXIBIÇÃO, RETIRAR DA SECAO ANTERIOR, DESATIVAR REALCE
        pararSeletor();
    }
}

function rodarSeletorSecao() {
    pararSeletor();

    var secoes = getSecoesSelecionaveis();
    estadoSeletor = EstadoSeletorEnum.SELETOR_SECAO;
    iniciarSeletor(secoes, ativarRealceSecao, desativarRealceSecao);
}

function rodarSeletorItem() {
    pararSeletor();

    var itensSecaoEscolhida = getItensSelecionaveis();
    estadoSeletor = EstadoSeletorEnum.SELETOR_ITEM;
    iniciarSeletor(itensSecaoEscolhida, ativarRealceItem, desativarRealceItem);
}

/* Seletor */

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
        seletorIntervalId = undefined;
    }
}

function calcPosCircular(pos, qntdSecao) {
    // Posicao circular: pos -1 -> 3; pos 0 -> 0; ...; pos qntdSecao -> 0; pos qntdSecao + 1 -> 1;
    return (pos + qntdSecao) % qntdSecao; //
}

/* Realce */

function ativarRealceSecao(secao) {
    secao.classList.add(CLASSE_SECAO_SELECIONADA);
}

function desativarRealceSecao(secao) {
    secao.classList.remove(CLASSE_SECAO_SELECIONADA);
}

function ativarRealceItem(item) {
    item.classList.add(CLASSE_ITEM_SELECIONADO);
}

function desativarRealceItem(item) {
    item.classList.remove(CLASSE_ITEM_SELECIONADO);
}

/* Funções Ajudantes */

function getSecoesSelecionaveis() {
    return $("." + CLASSE_SECAO_SELECIONAVEL + ":visible");
}

function getItensSelecionaveis() {
    var selector = "." + CLASSE_SECAO_SELECIONADA + " ." + CLASSE_ITEM_SELECIONAVEL + ":visible";
    return $(selector);
}