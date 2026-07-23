package com.pulcini.meubusao.dto;

public record PontoOnibusPassagemRequestDto(
        Integer codigoOnibus,
        Double latitude,
        Double longitude
) {
}
