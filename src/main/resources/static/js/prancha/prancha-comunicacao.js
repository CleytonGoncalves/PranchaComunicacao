"use strict";

//TODO: Relacionar Tempo com o Verbo, e ao remover o Tempo, voltar p/ tela de seleção do Tempo
//TODO: Remover uso de IDs/Classes em favor do uso de Data-Attributes no JS e CSS
//TODO: https://www.w3.org/wiki/JavaScript_best_practices#Avoid_globals
//TODO: Criar objeto global que guarda o estado do app

/* ##### CONSTANTES DO SCRIPT ##### */

/* CSS */

/* Classes que definem quais são selecionáveis pelo seletor */
var CLASSE_SECAO_SELECIONAVEL = "secao-selecionavel";

/* Classes que aplicam o realce (seleção) */
var CLASSE_SECAO_REALCE = "secao-realce";
var CLASSE_ITEM_REALCE = "item-realce";

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

/* ### Atributos Data-* personalizados ### */

/* Chaves */
var ATR_ITEM_TIPO = "itemTipo";
var ATR_ITEM_SECAO = "itemSecao";

var ATR_PALAVRA_CATEGORIA = "palavraCategoria";
var ATR_PALAVRA_ID = "palavraId";

var ATR_ACAO_ID = "acaoId";

var ATR_TEMPO_ID = "tempoId";

/* Valores */
var VAL_TIPO_PALAVRA = "palavra";
var VAL_TIPO_ACAO = "acao";
var VAL_TIPO_TEMPO = "tempo";

var VAL_PALAVRA_CATEGORIA_VERBO = "verbo";

var VAL_ACAO_FALAR = "falar";
var VAL_ACAO_APAGAR_ULTIMO = "apagar-ultimo";
var VAL_ACAO_VOLTAR_SELECAO = "voltar-selecao";
var VAL_ACAO_RECOMECAR = "recomecar";

/* ### Enum de estados do seletor ### */

var EstadoSeletorEnum = {
    SELETOR_PARADO: "SELETOR_PARADO",
    SELETOR_SECAO: "SELETOR_SECAO",
    SELETOR_ITEM: "SELETOR_ITEM"
};


/* ##### INICIALIZAÇÃO DA PRANCHA ##### */

var sintetizador = new Sintetizador();
var fluxo = new FluxoPrancha();

var listaPalavrasSelecionadas = [];
var tempoVerbalSelecionado = CONFIG_TEMPO_VERBAL_PADRAO;

var estadoSeletor = EstadoSeletorEnum.SELETOR_PARADO;
var seletorIntervalId = undefined;

var fluxoFuncoes = null;
var etapaFluxoAtual = null;


/* ##### SCRIPT DO SELETOR ##### */

$(window).ready(function () {
    fluxoFuncoes = fluxo.getFluxoPaciente();
    proximaEtapaFluxo(null);
});

/* ### Seletor ### */

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

/* ### Ao Clicar ### */

$("body").not("#topo").click(selecionarAtual); // Qualquer parte da página, exceto pelo navbar

function selecionarAtual() {
    if (estadoSeletor === EstadoSeletorEnum.SELETOR_SECAO) {
        rodarSeletorItem();
    } else if (estadoSeletor === EstadoSeletorEnum.SELETOR_ITEM) {
        pararSeletor();
        
        var itemRealcado = getItemRealcado();
        if (itemRealcado.data(ATR_ITEM_TIPO) === VAL_TIPO_ACAO) {
            fazerAcao(itemRealcado);
        } else {
            selecionarItem(itemRealcado);
            proximaEtapaFluxo(itemRealcado);
        }
    }
}

function selecionarItem(item) {
    item.detach();
    
    // Deixa de ser selecionável, e se torna selecionado
    item.addClass(CLASSE_ITEM_SELECIONADO);
    
    var tipoItem = item.data(ATR_ITEM_TIPO);
    
    switch (tipoItem) {
        case VAL_TIPO_TEMPO:
            tempoVerbalSelecionado = item.data(ATR_TEMPO_ID);
            // Clone permite escolher o mesmo Tempo p/ verbos diferentes do mesmo fluxo, caso liberado
            item = item.clone(true, true);
            break;
        case VAL_TIPO_PALAVRA:
            listaPalavrasSelecionadas.push(item.data(ATR_PALAVRA_ID));
            break;
        default:
            console.error("Seleção de item inválido: {0}".f(item));
    }
    
    moverParaSecaoFormacao(item);
}

function deselecionarItem(item) {
    item.detach();
    
    // Deixa de ser selecionado, e se torna selecionável
    item.removeClass(CLASSE_ITEM_SELECIONADO);
    
    var tipoItem = item.data(ATR_ITEM_TIPO);
    
    switch (tipoItem) {
        case VAL_TIPO_TEMPO:
            tempoVerbalSelecionado = CONFIG_TEMPO_VERBAL_PADRAO;
            break;
        case VAL_TIPO_PALAVRA:
            var index = listaPalavrasSelecionadas.indexOf(item.data(ATR_PALAVRA_ID));
            if (index > -1)
                listaPalavrasSelecionadas = listaPalavrasSelecionadas.splice(index, 1); // Remove o ID da palavra atual da lista
            break;
        default:
            console.error("Deseleção de item inválido: {0}".f(item));
    }
    
    moverParaSecaoOriginal(item);
}

function fazerAcao(item) {
    var tipoAcao = item.data(ATR_ACAO_ID);
    
    switch (tipoAcao) {
        case VAL_ACAO_FALAR:
            fazerAcaoFalar();
            break;
        case VAL_ACAO_VOLTAR_SELECAO:
            desativarTodosRealces();
            rodarSeletorSecao();
            break;
        case VAL_ACAO_RECOMECAR:
            window.location.reload(true);
            break;
        case VAL_ACAO_APAGAR_ULTIMO:
            deselecionarItem($("#{0}".f(ID_SECAO_FORMACAO)).scrollView().children().last());
            voltarEtapaFluxo();
            break;
        default:
            console.error("Ação da prancha inválida: {0}".f(tipoAcao));
    }
}

function fazerAcaoFalar() {
    sintetizador.falar(getTextoFormado());
    
    //TODO: ENVIAR FRASE FORMADA PARA O SERVIDOR
    $.post({
        url:  location.origin + "/api/sintetizador",
        data: JSON.stringify({palavrasIds: listaPalavrasSelecionadas, tempoVerbal: tempoVerbalSelecionado}),
        dataType: 'json',
        contentType: 'application/json;charset=UTF-8',
        processData: false,
        cache: false
    })
    .done(function () {
        console.log("Sucesso ao enviar frase para o servidor");
    })
    .fail(function () {
        alert("Falha ao sintetizar o texto.");
    });
}

/* ### Fluxo ### */

function proximaEtapaFluxo(ultimoItemSelecionado) {
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
    funcaoAtualFluxo(ultimoItemSelecionado);
    
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


/* ##### FUNÇÕES AJUDANTES ##### */

/* ### Seletor ### */

function getSecoesVisiveisSelecionaveis() {
    return $("." + CLASSE_SECAO_SELECIONAVEL).filter(":visible");
}

function getItensSelecionaveis() {
    return $(".{0} ul li".f(CLASSE_SECAO_REALCE)).filter(":visible");
}

function getItemRealcado() {
    return $(".{0} .{1}".f(CLASSE_SECAO_REALCE, CLASSE_ITEM_REALCE)).filter(":visible");
}

function moverParaSecaoFormacao(item) {
    item.appendTo("#{0}".f(ID_SECAO_FORMACAO)).scrollView();
}

function moverParaSecaoOriginal(item) {
    item.appendTo("#{0} ul".f(item.data(ATR_ITEM_SECAO)));
}

/* ### Realce ### */

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

/* ### Texto ### */

function getTextoFormado() {
    var texto = "";
    
    $(".{0} .{1}".f(CLASSE_ITEM_SELECIONADO, CLASSE_ITEM_TEXTO)).each(function (idx) {
        if (idx > 0) { texto += " "; }
        
        texto += $(this).text();
    });
    
    return texto;
}
