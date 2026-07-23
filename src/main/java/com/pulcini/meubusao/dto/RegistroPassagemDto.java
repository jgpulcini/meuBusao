package com.pulcini.meubusao.dto;

import java.time.Instant;

public record RegistroPassagemDto(
        Integer pontoId,
        Integer codigoOnibus,
        Instant horarioPassagem,
        Double latitude,
        Double longitude,
        Double distanciaEmMetros
) {
}
