/* Constante de Configuração */

var TEMPO_SELETOR = 1000;

/* jQuery Selectors */

// Encontra todos pelo qual o seletor deve passar
var JQ_SECAO_SELECIONAVEL = ".secao-selecionavel:visible";
var JQ_ITEM_SELECIONAVEL = ".item-selecionavel:visible";

// Encontra aquele que foi escolhido pelo usuário
var JQ_SECAO_ESCOLHIDA = ".secao-realce";
var JQ_ITEM_ESCOLHIDO = ".item-realce";

/* Status do Seletor */

var SELETOR_PARADO = "SELETOR_PARADO";
var SELETOR_SECAO = "SELETOR_SECAO";
var SELETOR_ITEM = "SELETOR_ITEM";

/* Script */

var statusSeletor = SELETOR_PARADO;
var seletorIntervalId = undefined;

$(window).ready(function () {
    rodarSeletorSecao();
});

$("body").not("#topo").click(clickFeito);

/* Clique */

function clickFeito() {
    console.log("Clicado");
    if (statusSeletor === SELETOR_SECAO) {
        console.log("rodar Item");
        rodarSeletorItem();
    } else if (statusSeletor === SELETOR_ITEM) {
        //    TODO: MOVER O ITEM SELECIONADO PARA A SECAO DE EXIBIÇÃO, RETIRAR DA SECAO ANTERIOR, DESATIVAR REALCE
        pararSeletor();
    }
}

function rodarSeletorSecao() {
    pararSeletor();

    var secoes = $(JQ_SECAO_SELECIONAVEL);
    statusSeletor = SELETOR_SECAO;
    iniciarSeletor(secoes, ativarRealceSecao, desativarRealceSecao);
}

function rodarSeletorItem() {
    pararSeletor();

    var itensSecaoEscolhida = $(JQ_SECAO_ESCOLHIDA + " " + JQ_ITEM_SELECIONAVEL);
    statusSeletor = SELETOR_ITEM;
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
    statusSeletor = SELETOR_PARADO;

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
    secao.classList.add("secao-realce");
}

function desativarRealceSecao(secao) {
    secao.classList.remove("secao-realce");
}

function ativarRealceItem(item) {
    item.classList.add("item-realce");
}

function desativarRealceItem(item) {
    item.classList.remove("item-realce");
}
