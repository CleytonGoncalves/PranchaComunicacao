"use strict";

//TODO: Fazer com que ao APAGAR ÚLTIMO  após Tempo Verbal, retorne a seleção de tempo
//TODO: Remover uso de IDs/Classes em favor do uso de Data-Attributes no JS e CSS
//TODO: https://www.w3.org/wiki/JavaScript_best_practices#Avoid_globals

/* ### Enum de estados do seletor ### */

var EstadoSeletorEnum = {
    SELETOR_PARADO: "SELETOR_PARADO",
    SELETOR_SECAO: "SELETOR_SECAO",
    SELETOR_ITEM: "SELETOR_ITEM"
};


/* ##### INICIALIZAÇÃO DA PRANCHA ##### */

var sintetizador = new Sintetizador();
var fluxo = new FluxoPrancha(CONFIG_FLUXO_PRANCHA);

var listaPalavrasSelecionadas = [];
var tempoVerbalSelecionado = CONFIG_TEMPO_VERBAL_PADRAO;

var estadoSeletor = EstadoSeletorEnum.SELETOR_PARADO;
var seletorIntervalId = undefined;

/* ##### SCRIPT DO SELETOR ##### */

$(window).ready(function () {
    proximaEtapaFluxo();
});

/* ### Seletor ### */

function rodarSeletorSecao() {
    pararSeletor();
    desativarTodosRealces();
    
    var secoes = getSecoesVisiveisSelecionaveis();
    estadoSeletor = EstadoSeletorEnum.SELETOR_SECAO;
    rodarSeletor(secoes, ativarRealceSecao, desativarRealceSecao);
}

function rodarSeletorItem() {
    pararSeletor();
    
    var itensSecaoEscolhida = getItensSelecionaveis();
    estadoSeletor = EstadoSeletorEnum.SELETOR_ITEM;
    rodarSeletor(itensSecaoEscolhida, ativarRealceItem, desativarRealceItem);
}

function rodarSeletor(lista, funcaoAtivarRealce, funcaoDesativarRealce) {
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
    var tipoItem = item.data(ATR_ITEM_TIPO);
    
    if (tipoItem === VAL_TIPO_TEMPO) {
        tempoVerbalSelecionado = item.data(ATR_TEMPO_ID);
        // Clone permite escolher o mesmo Tempo p/ verbos diferentes do mesmo fluxo, caso liberado
        item = item.clone(true, true);
    }
    
    item.detach();
    item.addClass(CLASSE_ITEM_SELECIONADO);
    
    if (tipoItem === VAL_TIPO_PALAVRA) {
        listaPalavrasSelecionadas.push(item.data(ATR_PALAVRA_ID));
    }
    
    moverParaSecaoFormacao(item);
}

function deselecionarItem(item) {
    item.detach();
    item.removeClass(CLASSE_ITEM_SELECIONADO);
    
    var tipoItem = item.data(ATR_ITEM_TIPO);
    
    if (tipoItem === VAL_TIPO_TEMPO) {
        tempoVerbalSelecionado = CONFIG_TEMPO_VERBAL_PADRAO;
        return;
    }
    
    if (tipoItem === VAL_TIPO_PALAVRA) {
        listaPalavrasSelecionadas = listaPalavrasSelecionadas.filter(function (e) {
            return e !== item.data(ATR_PALAVRA_ID);
        }); // Remove o ID da palavra atual da lista
        moverParaSecaoOriginal(item);
        return;
    }
    
    console.error("Deseleção de item inválido: {0}".f(item));
}

function fazerAcao(item) {
    var tipoAcao = item.data(ATR_ACAO_ID);
    
    switch (tipoAcao) {
        case VAL_ACAO_FALAR:
            fazerAcaoFalar();
            rodarSeletorSecao();
            break;
        case VAL_ACAO_VOLTAR_SELECAO:
            rodarSeletorSecao();
            break;
        case VAL_ACAO_RECOMECAR:
            window.location.reload(true);
            break;
        case VAL_ACAO_APAGAR_ULTIMO:
            voltarEtapaFluxo(item);
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
    fluxo.avancar(ultimoItemSelecionado);
    rodarSeletorSecao();
}

function voltarEtapaFluxo(ultimoItemSelecionado) {
    deselecionarItem($("#{0}".f(ID_SECAO_FORMACAO)).scrollView().children().last());
    fluxo.voltar(ultimoItemSelecionado);
    rodarSeletorSecao();
}


/* ##### FUNÇÕES AJUDANTES ##### */

/* ### Seletor ### */

function calcPosCircular(pos, qntdSecao) {
    // Posicao circular: pos -1 -> qntdSecao-1; pos 0 -> 0; ...; pos qntdSecao -> 0; pos qntdSecao + 1 -> 1;
    return (pos + qntdSecao) % qntdSecao; //
}

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
    // TODO: Voltar o item para mesma posição anterior, ou ao menos coloca-lo antes do Voltar Seleção
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
