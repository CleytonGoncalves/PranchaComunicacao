"use strict";

//TODO: https://www.w3.org/wiki/JavaScript_best_practices#Avoid_globals
//TODO: INCLUIR ETAPA DE SELEÇÃO DE TEMPO VERBAL!

/* ########## CONFIGURAÇÃO ########## */

var CONFIG_TEMPO_SELETOR = 1000; // Tempo em ms para o movimento do seletor
var CONFIG_FALAR_CADA_PALAVRA = false; // Se todo item pelo qual o seletor passar deve ser falado

/* ########## FIM DA CONFIGURAÇÃO ########## */


/* ### CONSTANTES DO SCRIPT ### */

/* Sintetizador de Voz */
var sintetizador = new Sintetizador();

/* CSS */

/* Classes que definem quais são selecionáveis pelo seletor */
var CLASSE_SECAO_SELECIONAVEL = "secao-selecionavel";
var CLASSE_ITEM_SELECIONAVEL = "item-selecionavel";

/* Classes que aplicam o realce (seleção) */
var CLASSE_SECAO_REALCE = "secao-realce";
var CLASSE_ITEM_REALCE = "item-realce";

/* Classe que definem quais itens realizam alguma ação */
var CLASSE_ITEM_ACAO = "item-acao";

/* Classe que define os itens que foram selecionados*/
var CLASSE_ITEM_SELECIONADO = "item-selecionado";

/* Classe que define o texto dos itens */
var CLASSE_ITEM_TEXTO = "texto-item";

/* IDs das seções, para usar na montagem do fluxo */
var ID_SECAO_FORMACAO = "secao-formacao";
var ID_SECAO_SUJEITO = "secao-sujeito";
var ID_SECAO_VERBO = "secao-verbo";
var ID_SECAO_COMPLEMENTO = "secao-complemento";
var ID_SECAO_DIVERSO = "secao-diverso";
var ID_SECAO_ACAO = "secao-acao";
var ID_SECAO_TEMPO = "secao-tempo";

/* IDS dos itens de tempo */
var ID_TEMPO_PASSADO = "tempo-passado";
var ID_TEMPO_PRESENTE = "tempo-presente";
var ID_TEMPO_FUTURO = "tempo-futuro";

/* IDs dos itens de ação do painel de ação */
var ID_ITEM_FALAR = "acao-falar";
var ID_ITEM_APAGAR_ULTIMO = "acao-apagar-ultimo";
var ID_ITEM_VOLTAR = "acao-voltar";
var ID_ITEM_RECOMECAR = "acao-recomecar";

/* IDs dos itens de voltar de cada painel */
var ID_ITEM_VOLTAR_SUJEITO = "acao-voltar-sujeito";
var ID_ITEM_VOLTAR_VERBO = "acao-voltar-verbo";
var ID_ITEM_VOLTAR_COMPLEMENTO = "acao-voltar-complemento";
var ID_ITEM_VOLTAR_DIVERSO = "acao-voltar-diverso";


/* Enum de estados do seletor */

var EstadoSeletorEnum = {
    SELETOR_PARADO: "SELETOR_PARADO",
    SELETOR_SECAO: "SELETOR_SECAO",
    SELETOR_ITEM: "SELETOR_ITEM"
};

if (Object.freeze) {
    Object.freeze(EstadoSeletorEnum); // Torna imutável se suportar ES5
}

/* ##### DEFINICAO FLUXO DA PRANCHA ##### */

$(window).ready(function () {
    setFluxoSVC();
    proximaEtapaFluxo();
});

/* ##### SCRIPT DO SELETOR ##### */

var listaSujeito = [];
var listaVerbo = [];
var listaComplemento = [];
var listaDiverso = [];

var estadoSeletor = EstadoSeletorEnum.SELETOR_PARADO;
var seletorIntervalId = undefined;

var fluxoFuncoes = null;
var etapaFluxoAtual = null;

function proximaEtapaFluxo() {
    desativarTodosRealces();
    
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

/* Clique */

$("body").not("#topo").click(selecionarAtual); // Qualquer parte da página, exceto pelo navbar

function selecionarAtual() {
    if (estadoSeletor === EstadoSeletorEnum.SELETOR_SECAO) {
        rodarSeletorItem();
    } else if (estadoSeletor === EstadoSeletorEnum.SELETOR_ITEM) {
        pararSeletor();

        var itemRealcado = getItemRealcado();
        if (itemRealcado.hasClass(CLASSE_ITEM_ACAO)) {
            fazerAcao(itemRealcado);
        } else {
            selecionarItem(itemRealcado);
            proximaEtapaFluxo();
        }
    }
}

function fazerAcao(item) {
    var itemId = item.prop("id");
    
    if (itemId === ID_ITEM_FALAR) {
        sintetizador.falar(getTextoFormado());
        //TODO: ENVIAR FRASE FORMADA PARA O SERVIDOR
    } else if (itemId === ID_ITEM_VOLTAR) {
        desativarTodosRealces();
        rodarSeletorSecao();
    } else if (itemId === ID_ITEM_RECOMECAR) {
        window.location.reload(true);
    }
}

/* Seletor */

function rodarSeletorSecao() {
    pararSeletor();

    var secoes = getSecoesVisiveisSelecionaveis();
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

    // Executa a função e retorna a si própria, a fim de que
    // a primeira execução seja feita antes do intervalo
    seletorIntervalId = setInterval(function loop() {
        var posAnterior = calcPosCircular(posAtual - 1, qntd);
        
        if (estadoSeletor === EstadoSeletorEnum.SELETOR_ITEM && CONFIG_FALAR_CADA_PALAVRA) {
            sintetizador.falar($(lista[posAtual]).find("." + CLASSE_ITEM_TEXTO).text());
        }
        
        funcaoDesativarRealce(lista[posAnterior]);
        funcaoAtivarRealce(lista[posAtual]);

        posAtual = calcPosCircular(posAtual + 1, qntd);
        
        return loop;
    }(), CONFIG_TEMPO_SELETOR);
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

/* ### Fluxo ### */

/* Fluxo Sujeito -> Verbo -> Complemento (SVC) */
function setFluxoSVC() {
    
    function setApenasSujeitoVisivel() {
        esconderTodasSecoesExceto("#{0}, #{1}".f(ID_SECAO_SUJEITO, ID_SECAO_ACAO));
    }
    
    function setApenasVerboEAcaoVisivel() {
        esconderTodasSecoesExceto("#{0}, #{1}".f(ID_SECAO_VERBO, ID_SECAO_ACAO));
    }
    
    function setApenasTempoVerbalVisivel() {
        esconderTodasSecoesExceto("#{0}, #{1}".f(ID_SECAO_TEMPO, ID_SECAO_ACAO));
    }
    
    function setApenasComplementoEAcaoVisivel() {
        esconderTodasSecoesExceto("#{0}, #{1}".f(ID_SECAO_COMPLEMENTO, ID_SECAO_ACAO))
    }
    
    fluxoFuncoes = [setApenasSujeitoVisivel, setApenasVerboEAcaoVisivel, setApenasTempoVerbalVisivel, setApenasComplementoEAcaoVisivel];
}

function esconderTodasSecoesExceto(excecaoSeletor) {
    var seletorGeral = $("." + CLASSE_SECAO_SELECIONAVEL);
    var seletorEsconder = seletorGeral.not(excecaoSeletor);
    
    seletorGeral.show(); // Exibe todos
    seletorEsconder.hide(); // Esconde os não desejados
}

/* ### Seletor ### */

function getSecoesVisiveisSelecionaveis() {
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

    item.removeClass(CLASSE_ITEM_SELECIONAVEL); // Deixa de ser selecionavel
    item.addClass(CLASSE_ITEM_SELECIONADO);
    
    var itemId = item.prop("id").split("-");
    var tipo = itemId[0], numeroId = itemId[1];
    
    switch (tipo) {
        case "sujeito":
            listaSujeito.push(numeroId);
            break;
        case "verbo":
            listaVerbo.push(numeroId);
            break;
        case "complemento":
            listaComplemento.push(numeroId);
            break;
        case "diverso":
            listaDiverso.push(numeroId);
            break;
    }

    moverParaSecaoFormacao(item);
}

function moverParaSecaoFormacao(item) {
    item.appendTo("#" + ID_SECAO_FORMACAO).scrollView();
}

/* ### Texto ### */

function getTextoFormado() {
    var texto = "";
    
    $(".{0} .{1}".f(CLASSE_ITEM_SELECIONADO, CLASSE_ITEM_TEXTO)).each(function (idx) {
        if (idx > 0) { texto += " "; }
        
        texto += $(this).text();
    });
    
    return texto;
}

/* ### Geral ### */

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
