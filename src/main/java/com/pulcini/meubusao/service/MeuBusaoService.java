package com.pulcini.meubusao.service;

import com.pulcini.meubusao.dto.FindLineDto;
import com.pulcini.meubusao.itg.OlhoVivoClient;
import com.pulcini.meubusao.itg.dto.PosicaoLinhaResponseDto;
import com.pulcini.meubusao.utils.BuilderUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MeuBusaoService {

    private final OlhoVivoClient olhoVivoClient;
    private final String token;


    public MeuBusaoService(OlhoVivoClient olhoVivoClient, @Value("${integracao.olhovivo.token}") String token) {
        this.olhoVivoClient = olhoVivoClient;
        this.token = token;
    }

    public Boolean authorization() {
        Boolean resultado = olhoVivoClient.authorization(this.token);
        return resultado;
    }

    public List<FindLineDto> findLine(final String lineNumber) {
        List<FindLineDto> resultado = BuilderUtils.buildFindLine(olhoVivoClient.findLine(lineNumber));
        log.info(resultado.toString());
        return resultado;
    }

    public PosicaoLinhaResponseDto findLine(final int codigoLinha) {
        final Boolean authorization = this.authorization();

        PosicaoLinhaResponseDto resultado = new PosicaoLinhaResponseDto(token, null);
        if(authorization){
        resultado = olhoVivoClient.buscarPosicaoEmTempoReal(codigoLinha);
        log.info(resultado.toString());
        }else {
        log.info("deu ruim papai");
        }
        return resultado;
    }

}
