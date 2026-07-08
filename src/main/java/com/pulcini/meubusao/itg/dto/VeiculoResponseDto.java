package com.pulcini.meubusao.itg.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;

public record VeiculoResponseDto(

        @JsonProperty("p")
        String prefixo,

        @JsonProperty("a")
        boolean acessivel,

        @JsonProperty("ta")
        Instant horarioGps,// Mapeia a string ISO (ex: 2026-06-22T18:49:56Z) direto para Instant da API de data do Java 8+

        @JsonProperty("py")
        double latitude,

        @JsonProperty("px")
        double longitude,

        @JsonProperty("sv")
        Boolean sentidoVeiculo, // Mantido como objeto/wrapper caso venha nulo

        @JsonProperty("is")
        Boolean indicadorStatus // Mantido como objeto/wrapper caso ve
) {

}
