package com.pulcini.meubusao.itg;

import com.pulcini.meubusao.itg.dto.FindLineItgDto;
import com.pulcini.meubusao.itg.dto.PosicaoLinhaResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.cookie.BasicCookieStore;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Slf4j
@Component
public class OlhoVivoClient {

    private final RestClient restClient;

    public OlhoVivoClient(@Value("${integracao.olhovivo.url}") String baseUrl) {

        BasicCookieStore cookieStore = new BasicCookieStore();

        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultCookieStore(cookieStore)
                .build();

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);

        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .requestFactory(factory)
                .build();
    }

    public Boolean authorization(String token) {
        return restClient.post()
                .uri("/Login/Autenticar?token={token}", token)
                .body("a")
                .retrieve()
                .body(Boolean.class);
    }

    public List<FindLineItgDto> findLine(String lineNumber) {
        return restClient.get()
                .uri("/Linha/Buscar?termosBusca={lineNumber}", lineNumber)
                .retrieve()
                .body(new ParameterizedTypeReference<List<FindLineItgDto>>() {});
    }

    public PosicaoLinhaResponseDto buscarPosicaoEmTempoReal(int codigoLinha) {
        return restClient.get()
                .uri("/Posicao/Linha?codigoLinha={codigo}", codigoLinha)
                .retrieve()
                .body(PosicaoLinhaResponseDto.class);
    }

}
