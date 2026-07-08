package com.pulcini.meubusao.itg.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record PosicaoLinhaResponseDto(
        @JsonProperty("hr")
        String horarioConsulta, // O formato de resposta da SPTrans é apenas HH:mm (ex: "15:49")

        @JsonProperty("vs")
        List<VeiculoResponseDto> veiculos
) { }
