package com.pulcini.meubusao.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PontoOnibusDto {

    private Integer id;
    private String nome;
    private Double latitude;
    private Double longitude;
    private Integer raioMetros;
}
