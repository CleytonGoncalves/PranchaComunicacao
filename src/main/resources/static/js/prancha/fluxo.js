/* ##### OBJETO GERENCIADOR DO FLUXO ##### */

var FluxoPrancha = function (fluxoId) {
    var fluxoFuncoes = null;
    var pilhaEtapaFluxo = [];
    
    switch (fluxoId) {
        case "SUJEITO-VERBO-COMPLEMENTO":
            fluxoFuncoes = getFluxoSujeitoVerboComplemento();
            break;
        case "FLUXOLIVRE":
            fluxoFuncoes =  getFluxoLivre();
            break;
        default:
            console.error("Fluxo de Prancha inválido!");
    }
    
    this.avancar = function (ultimoItemSelecionado) {
        //TODO: Receber se deve avançar etapa (e.g. caso do Verbo/Tempo, ou caso do Complemento no meio de um fluxo
        var etapaAtual = pilhaEtapaFluxo.slice(-1)[0]; // Pega último item, sem removê-lo
        
        if (etapaAtual === null || etapaAtual === undefined) {
            etapaAtual = 0; // Vai para etapa inicial
        } else if (etapaAtual < fluxoFuncoes.length - 1) {
            etapaAtual++; // Avança para próxima etapa
        } else {
            // Mantém preso na última etapa, podendo selecionar quantos itens/ações quiser
        }
    
        pilhaEtapaFluxo.push(etapaAtual);
        
        var funcaoAtualFluxo = fluxoFuncoes[etapaAtual];
        funcaoAtualFluxo(ultimoItemSelecionado);
    };
    
    this.voltar = function (ultimoItemSelecionado) {
        //TODO: Caso chamar aqui na primeira execução?
        var etapaAtual = pilhaEtapaFluxo.slice(-1)[0]; // Pega último item, sem removê-lo
    
        if (etapaAtual === null || etapaAtual === undefined) {
            etapaAtual = 0;
            pilhaEtapaFluxo.push(0); // Vai para etapa inicial
        } else {
            pilhaEtapaFluxo.pop();
            etapaAtual = pilhaEtapaFluxo.slice(-1)[0]; // Pega último item, sem removê-lo
        }
    
        var funcaoAtualFluxo = fluxoFuncoes[etapaAtual];
        funcaoAtualFluxo(ultimoItemSelecionado);
    };
    
    function irParaEtapa(etapa, ultimoItemSelecionado) {
        var etapaAtual = pilhaEtapaFluxo.slice(-1)[0]; // Pega último item, sem removê-lo
        
        if (etapaAtual !== etapa) {
            pilhaEtapaFluxo.push(etapa);
            
            var funcaoAtualFluxo = fluxoFuncoes[etapa];
            funcaoAtualFluxo(ultimoItemSelecionado);
        }
    }
    
    
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
            var selSecaoPalavraNaoVazia = ".{0}:has(ul li[data-item-tipo='{1}'])".f(CLASSE_SECAO_SELECIONAVEL, VAL_TIPO_PALAVRA);
            esconderTodasSecoesExceto("{0}, #{1}".f(selSecaoPalavraNaoVazia, ID_SECAO_ACAO));
        }
        
        function setTempoEAcaoVisivelSeVerbo(ultimoItemSelecionado) {
            var dataDict = ultimoItemSelecionado.data();
            
            var isVerbo = dataDict[ATR_PALAVRA_CATEGORIA] === VAL_PALAVRA_CATEGORIA_VERBO;
            var isApagarUltimo = dataDict[ATR_ACAO_ID] === VAL_ACAO_APAGAR_ULTIMO;
            
            if (! isVerbo) { // && ! isApagarUltimo) {
                irParaEtapa(0);
                return;
            }
            
            // Se o último foi 'Verbo', mostrar seção Tempo
            esconderTodasSecoesExceto("#{0}, #{1}".f(ID_SECAO_TEMPO, ID_SECAO_ACAO));
        }
        
        function setRetornaEtapaInicial(ultimoItemSelecionado) {
            if (ultimoItemSelecionado.data(ATR_ACAO_ID) === VAL_ACAO_APAGAR_ULTIMO) {
                irParaEtapa(1, ultimoItemSelecionado);
                return;
            }
            
            irParaEtapa(0);
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
    
};
