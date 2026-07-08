package com.pulcini.meubusao.utils;

import com.pulcini.meubusao.dto.FindLineDto;
import com.pulcini.meubusao.itg.dto.FindLineItgDto;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@UtilityClass
public final class BuilderUtils {

    public static FindLineDto buildFindLine(final FindLineItgDto findLineItg) {
        return FindLineDto.builder()
                .codigoDaLinha(findLineItg.getCl())
                .circular(findLineItg.getLc())
                .numeroDaLinha(findLineItg.getLt())
                .finalDaLinha(findLineItg.getTl())
                .sentidoDaLinha(findLineItg.getSl())
                .sentidoPrincipal(findLineItg.getTp())
                .sentidoSecundario(findLineItg.getTs())
                .build();
    }

    public static List<FindLineDto> buildFindLine(final List<FindLineItgDto> findLineItg) {
        // Se a lista mapeada vier nula, evita NullPointerException retornando uma lista vazia
        if (findLineItg == null) {
            return List.of();
        }

        return findLineItg.stream()
                .map(itg -> FindLineDto.builder()
                        .codigoDaLinha(itg.getCl()) // Note que agora chamamos 'itg.getCl()' do elemento individual
                        .circular(itg.getLc())
                        .numeroDaLinha(itg.getLt())
                        .finalDaLinha(itg.getTl())
                        .sentidoDaLinha(itg.getSl())
                        .sentidoPrincipal(itg.getTp())
                        .sentidoSecundario(itg.getTs())
                        .build())
                .toList(); // No Java 16+ você pode usar .toList() diretamente. Se for Java mais antigo, use .collect(Collectors.toList())
    }



}
