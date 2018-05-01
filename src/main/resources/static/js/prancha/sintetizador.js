"use strict";
/* ##### Wrapper do Sintetizador de Voz ##### */
/* https://responsivevoice.org/api/ */

var VOZ_PADRAO = "Brazilian Portuguese Female";
var VELOCIDADE_PADRAO = 1.0;

var Sintetizador = function () {
    this.falar = function (texto) {
        if (responsiveVoice && responsiveVoice.voiceSupport()) {
            responsiveVoice.speak(texto, VOZ_PADRAO, {rate: VELOCIDADE_PADRAO});
        } else {
            alert(
                "Sintetizador de voz não suportado. Por favor contate o administrador."
            );
        }
    };
};

