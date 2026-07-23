package com.pulcini.meubusao.service;

import com.pulcini.meubusao.dto.PontoOnibusDto;
import com.pulcini.meubusao.dto.RegistroPassagemDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class PontoOnibusService {

    private static final Logger log = LoggerFactory.getLogger(PontoOnibusService.class);
    private static final double PONTO_LATITUDE = -23.527060074874345;
    private static final double PONTO_LONGITUDE = -46.53046981701503;
    private static final int RAIO_METROS = 200;

    private final Map<Integer, PontoOnibusDto> pontos = new ConcurrentHashMap<>();
    private final Map<Integer, List<RegistroPassagemDto>> registros = new ConcurrentHashMap<>();
    private final Map<Integer, Double> ultimaDistanciaPorOnibus = new ConcurrentHashMap<>();
    private final Map<Integer, Instant> ultimaPassagemPorOnibus = new ConcurrentHashMap<>();
    private final AtomicInteger sequencia = new AtomicInteger(1);

    public PontoOnibusDto cadastrarPonto(PontoOnibusDto ponto) {
        if (ponto.getId() == null) {
            ponto.setId(sequencia.getAndIncrement());
        }

        pontos.put(ponto.getId(), ponto);
        registros.computeIfAbsent(ponto.getId(), id -> new CopyOnWriteArrayList<>());

        return ponto;
    }

    public boolean registrarPassagem(Integer pontoId, Integer codigoOnibus, Double latitude, Double longitude) {
        if (codigoOnibus == null || latitude == null || longitude == null) {
            return false;
        }

        double distancia = calcularDistanciaEmMetros(latitude, longitude, PONTO_LATITUDE, PONTO_LONGITUDE);
        Double distanciaAnterior = ultimaDistanciaPorOnibus.get(codigoOnibus);

        boolean entrouNoPonto = distancia <= RAIO_METROS
                && (distanciaAnterior == null || distanciaAnterior > RAIO_METROS);

        if (!entrouNoPonto) {
            ultimaDistanciaPorOnibus.put(codigoOnibus, distancia);
            return false;
        }

        Instant agora = Instant.now();
        Instant ultimaPassagem = ultimaPassagemPorOnibus.get(codigoOnibus);
        if (ultimaPassagem != null && Duration.between(ultimaPassagem, agora).compareTo(Duration.ofSeconds(30)) < 0) {
            ultimaDistanciaPorOnibus.put(codigoOnibus, distancia);
            return false;
        }

        RegistroPassagemDto registro = new RegistroPassagemDto(
                pontoId,
                codigoOnibus,
                agora,
                latitude,
                longitude,
                distancia
        );

        log.info("Veículo {} passou no meu ponto às {} (lat={}, lng={}, distância={}m)",
                codigoOnibus,
                agora,
                latitude,
                longitude,
                distancia);

        registros.computeIfAbsent(pontoId, id -> new CopyOnWriteArrayList<>()).add(registro);
        ultimaPassagemPorOnibus.put(codigoOnibus, agora);
        ultimaDistanciaPorOnibus.put(codigoOnibus, distancia);
        return true;
    }

    public List<RegistroPassagemDto> buscarPassagens(Integer pontoId) {
        return registros.getOrDefault(pontoId, List.of());
    }

    private double calcularDistanciaEmMetros(double latitudeA, double longitudeA, double latitudeB, double longitudeB) {
        final double raioTerra = 6371000;

        double lat1 = Math.toRadians(latitudeA);
        double lat2 = Math.toRadians(latitudeB);
        double deltaLat = Math.toRadians(latitudeB - latitudeA);
        double deltaLon = Math.toRadians(longitudeB - longitudeA);

        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return raioTerra * c;
    }
}
