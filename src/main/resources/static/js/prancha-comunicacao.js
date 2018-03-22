"use strict";

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
var ID_SECAO_SUJEITO = "secao-sujeito";
var ID_SECAO_VERBO = "secao-verbo";
var ID_SECAO_COMPLEMENTO = "secao-complemento";
var ID_SECAO_DIVERSO = "secao-diverso";
var ID_SECAO_ACAO = "secao-acao";


/* Enum de estados do seletor */

var EstadoSeletorEnum = {
    SELETOR_PARADO: "SELETOR_PARADO",
    SELETOR_SECAO: "SELETOR_SECAO",
    SELETOR_ITEM: "SELETOR_ITEM"
};

if (Object.freeze) {
    Object.freeze(EstadoSeletorEnum); // Torna imutável se suportar ES5
}

/* ##### FLUXO DA PRANCHA ##### */

$(window).ready(function () {
    setFluxoSVC();
    proximaEtapaFluxo();
});

var fluxoFuncoes = null;
var etapaFluxoAtual = null;

/* Fluxo Sujeito -> Verbo -> Complemento (SVC) */
function setFluxoSVC() {
    fluxoFuncoes = [setApenasSujeitoVisivel, setApenasVerboEAcaoVisivel, setApenasComplementoEAcaoVisivel];
}

function proximaEtapaFluxo() {
    desativarTodosRealces();
    debugger;
    
    if (etapaFluxoAtual === null || etapaFluxoAtual < 0) {
        etapaFluxoAtual = 0;
    } else if (etapaFluxoAtual < fluxoFuncoes.length - 1) {
        etapaFluxoAtual++;
    } else {
        rodarSeletorSecao();
        return; // Mantém preso na última etapa, podendo selecionar quantos itens/ações quiser
    }
    
    var funcaoAtualFluxo = fluxoFuncoes[etapaFluxoAtual];
    funcaoAtualFluxo();
    
    rodarSeletorSecao();
}

function voltarEtapaFluxo() {
    desativarTodosRealces();
    
    if (etapaFluxoAtual === null || etapaFluxoAtual < 0) {
        etapaFluxoAtual = 0;
    } else if (etapaFluxoAtual >= 1) {
        etapaFluxoAtual--;
    } else {
        rodarSeletorSecao();
        return; // Mantém preso na primeira etapa
    }
    
    var funcaoAtualFluxo = fluxoFuncoes[etapaFluxoAtual];
    funcaoAtualFluxo();
    
    rodarSeletorSecao();
}

/* ### SCRIPT DO SELETOR ### */

var estadoSeletor = EstadoSeletorEnum.SELETOR_PARADO;
var seletorIntervalId = undefined;

/* Clique */

$("body").not("#topo").click(selecionarAtual); // Qualquer parte da página, exceto pelo navbar

function selecionarAtual() {
    if (estadoSeletor === EstadoSeletorEnum.SELETOR_SECAO) {
        rodarSeletorItem();
    } else if (estadoSeletor === EstadoSeletorEnum.SELETOR_ITEM) {
        pararSeletor();

        var itemRealcado = getItemRealcado();
        if (itemRealcado.hasClass(CLASSE_ITEM_ACAO)) {
            //fazerAcao(itemRealcado);
        } else {
            selecionarItem(itemRealcado);
            proximaEtapaFluxo();
        }
    }
}

/* Seletor */

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
    // Posicao circular: pos -1 -> qntdSecao-1; pos 0 -> 0; ...; pos qntdSecao -> 0; pos qntdSecao + 1 -> 1;
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

/* ##### Funções Ajudantes ##### */

/* Fluxo */

function setApenasSujeitoVisivel() {
    var seletorGeral = $(".{0}".f(CLASSE_SECAO_SELECIONAVEL));
    var seletorMostrar = seletorGeral.not("#{0}, #{1}".f(ID_SECAO_SUJEITO, ID_SECAO_ACAO));
    
    seletorGeral.show(); // Exibe todos
    seletorMostrar.hide(); // Esconde os não desejados
}

function setApenasVerboEAcaoVisivel() {
    var seletorGeral = $(".{0}".f(CLASSE_SECAO_SELECIONAVEL));
    var seletorMostrar = seletorGeral.not("#{0}, #{1}".f(ID_SECAO_VERBO, ID_SECAO_ACAO));
    
    seletorGeral.show(); // Exibe todos
    seletorMostrar.hide(); // Esconde os não desejados
}

function setApenasComplementoEAcaoVisivel() {
    var seletorGeral = $(".{0}".f(CLASSE_SECAO_SELECIONAVEL));
    var seletorMostrar = seletorGeral.not("#{0}, #{1}".f(ID_SECAO_COMPLEMENTO, ID_SECAO_ACAO));
    
    seletorGeral.show(); // Exibe todos
    seletorMostrar.hide(); // Esconde os não desejados
}

/* Seletor */

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

/* Geral */

$.fn.scrollView = function () {
    return this.each(function () {
        $('html, body').animate({
            scrollTop: $(this).offset().top - 20
        }, 500);
    });
};

String.prototype.format = String.prototype.f = function() {
    var s = this,
        i = arguments.length;
    
    while (i--) {
        s = s.replace(new RegExp('\\{' + i + '\\}', 'gm'), arguments[i]);
    }
    return s;
};
