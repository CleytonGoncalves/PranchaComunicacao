"use strict";
/* ##### Wrapper do Sintetizador de Voz ##### */

var VOZ_PADRAO = "Brazilian Portuguese Female";
var VELOCIDADE_PADRAO = 1.0;

var Sintetizador = function () {
    this.sintetizarLocal = function (texto) {
        if ('speechSynthesis' in window && !!window.chrome) { // Navegador chrome
            var fala = new SpeechSynthesisUtterance(texto);
            fala.lang = 'pt-BR';
            window.speechSynthesis.speak(fala);
        } else {
            console.error(
                "Seu navegador não suporta sintetizar as palavras pelo qual o seletor está passando.\n" +
                "Para usar tal funcionalidade, por favor utilize a última versão disponível do navegador Google Chrome.");
            
            CONFIG_FALAR_CADA_PALAVRA = false;
        }
    };
    
    this.sintetizarNuvem = function (texto) {
        if (responsiveVoice && responsiveVoice.voiceSupport()) {
            responsiveVoice.speak(texto, VOZ_PADRAO, {rate: VELOCIDADE_PADRAO});
            return true;
        }
        
        return false;
    };
    
    this.sintetizarServidor = function (texto, funcaoCallback) {
        $.post({
            url: location.origin + "/api/sintetizador",
            data: JSON.stringify({palavrasIds: listaPalavrasSelecionadas, tempoVerbal: tempoVerbalSelecionado}),
            contentType: 'application/json;charset=UTF-8',
            processData: false
        })
        .done(function (response) {
            var audio = new Audio(window.location.origin + '/' + response);
            audio.addEventListener('ended', funcaoCallback);
            audio.play();
        })
        .fail(function () {
            alert("Falha ao sintetizar o texto. Verifique sua conexão ou tente novamente mais tarde.");
            funcaoCallback();
        });
    }
    
};

