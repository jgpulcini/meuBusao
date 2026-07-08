package com.pulcini.meubusao.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FindLineDto {

    private Integer codigoDaLinha;
    private Boolean circular;
    private String numeroDaLinha;
    private Integer sentidoDaLinha;
    private Integer finalDaLinha;
    private String sentidoPrincipal;
    private String sentidoSecundario;
}
