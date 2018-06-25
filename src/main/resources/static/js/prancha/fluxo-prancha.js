/* ##### Define os diferentes fluxos que a Prancha pode ter ##### */

/* ##### OBJETO GERENCIADOR DO FLUXO ##### */

var FluxoPrancha = function () {
    /* Retorna o fluxo que a Prancha irá usar */
    this.getFluxoPaciente = function () {
        
        switch (CONFIG_FLUXO_PRANCHA) {
            case "SUJEITO-VERBO-COMPLEMENTO":
                return getFluxoSujeitoVerboComplemento();
            case "FLUXOLIVRE":
                return getFluxoLivre();
            default:
                console.log("Fluxo de Prancha inválido!");
        }
        
    }
};


/* ##### DEFINIÇÃO DE FLUXOS ##### */

/* ### Fluxo Sujeito -> Verbo -> Complemento (SVC) ### */
function getFluxoSujeitoVerboComplemento() {
    
    function setSujeitoEAcaoVisivel() {
        esconderTodasSecoesExceto("#{0}, #{1}".f(ID_SECAO_SUJEITO, ID_SECAO_ACAO));
    }
    
    function setVerboEAcaoVisivel() {
        esconderTodasSecoesExceto("#{0}, #{1}".f(ID_SECAO_VERBO, ID_SECAO_ACAO));
    }
    
    function setTempoEAcaoVisivel() {
        esconderTodasSecoesExceto("#{0}, #{1}".f(ID_SECAO_TEMPO, ID_SECAO_ACAO));
    }
    
    function setComplementoEAcaoVisivel() {
        esconderTodasSecoesExceto("#{0}, #{1}".f(ID_SECAO_COMPLEMENTO, ID_SECAO_ACAO));
    }
    
    var fluxoFuncoes = [setSujeitoEAcaoVisivel, setVerboEAcaoVisivel, setTempoEAcaoVisivel,
        setComplementoEAcaoVisivel]; // O fluxo terá a mesma ordem do vetor
    
    return fluxoFuncoes;
}

/* ### Fluxo Livre (todas palavras visiveís) ### */
function getFluxoLivre() {
    
    function setTodasPalavrasVisiveis() {
        var selSecaoPalavraNaoVazia = ".secao-selecionavel:has(ul li[data-item-tipo='palavra'])";
        esconderTodasSecoesExceto("{0}, #{1}".f(selSecaoPalavraNaoVazia, ID_SECAO_ACAO));
    }
    
    function setTempoEAcaoVisivelSeVerbo(ultimoItemSelecionado) {
        
        var dataDict = ultimoItemSelecionado.data();
        if (dataDict[ATR_ITEM_TIPO] === VAL_TIPO_PALAVRA &&
            dataDict[ATR_PALAVRA_CATEGORIA] === VAL_PALAVRA_CATEGORIA_VERBO) {
            // Se foi escolhido 'Verbo', mostar seção Tempo
            esconderTodasSecoesExceto("#{0}, #{1}".f(ID_SECAO_TEMPO, ID_SECAO_ACAO));
        } else {
            etapaFluxoAtual = 0; // Não avança no fluxo
        }
    }
    
    function setRetornaEtapaInicial() {
        etapaFluxoAtual = 0; // Não avança no fluxo
        setTodasPalavrasVisiveis();
    }
    
    return [setTodasPalavrasVisiveis, setTempoEAcaoVisivelSeVerbo, setRetornaEtapaInicial];
}


/* ##### FUNÇÕES AJUDANTES ##### */

function esconderTodasSecoesExceto(excecaoSeletor) {
    var seletorGeral = $(".{0}".f(CLASSE_SECAO_SELECIONAVEL));
    var seletorEsconder = seletorGeral.not(excecaoSeletor);
    
    seletorGeral.show(); // Exibe todos
    seletorEsconder.hide(); // Esconde os não desejados
}
