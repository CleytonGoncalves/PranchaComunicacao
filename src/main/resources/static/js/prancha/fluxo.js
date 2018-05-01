/* ##### Define os diferentes fluxos que a Prancha pode ter ##### */

var Fluxo = function () {
    
    /* Retorna o fluxo que a Prancha irá usar */
    this.getFluxoPaciente = function () {
        
        switch (CONFIG_FLUXO_PRANCHA) {
            case "Sujeito-Verbo-Complemento":
                return getFluxoSujeitoVerboComplemento();
            default:
                console.log("Fluxo de Prancha inválido!");
        }
        
    }
    
};

/* Fluxo Sujeito -> Verbo -> Complemento (SVC) */

function getFluxoSujeitoVerboComplemento() {
    
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
    
    var fluxoFuncoes = [setApenasSujeitoVisivel, setApenasVerboEAcaoVisivel, setApenasTempoVerbalVisivel,
        setApenasComplementoEAcaoVisivel]; // O fluxo terá a mesma ordem do vetor
    
    return fluxoFuncoes;
}

function esconderTodasSecoesExceto(excecaoSeletor) {
    var seletorGeral = $("." + CLASSE_SECAO_SELECIONAVEL);
    var seletorEsconder = seletorGeral.not(excecaoSeletor);
    
    seletorGeral.show(); // Exibe todos
    seletorEsconder.hide(); // Esconde os não desejados
}
