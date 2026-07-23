package com.pulcini.meubusao;

import com.pulcini.meubusao.dto.RegistroPassagemDto;
import com.pulcini.meubusao.service.PontoOnibusService;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PontoOnibusServiceTest {

    @Test
    void deveRegistrarPassagemQuandoOnibusEntrarNaAreaDoPonto() {
        PontoOnibusService service = new PontoOnibusService();

        boolean entrouNaArea = service.registrarPassagem(1, 33695, -23.527060074874345, -46.53046981701503);
        boolean saiuDaArea = service.registrarPassagem(1, 33695, -23.526000000000000, -46.529000000000000);

        List<RegistroPassagemDto> passagens = service.buscarPassagens(1);

        assertTrue(entrouNaArea);
        assertFalse(saiuDaArea);
        assertFalse(passagens.isEmpty());
        assertEquals(1, passagens.size());
        assertEquals(33695, passagens.get(0).codigoOnibus());
    }

    @Test
    void deveRegistrarPassagemQuandoOnibusEntrarNaAreaDoPontoMesmoSemLeituraAnteriorDentroDoRaio() {
        PontoOnibusService service = new PontoOnibusService();

        boolean primeiraLeituraForaDaArea = service.registrarPassagem(1, 33695, -23.526000000000000, -46.529000000000000);
        boolean segundaLeituraDentroDaArea = service.registrarPassagem(1, 33695, -23.527060074874345, -46.53046981701503);

        List<RegistroPassagemDto> passagens = service.buscarPassagens(1);

        assertFalse(primeiraLeituraForaDaArea);
        assertTrue(segundaLeituraDentroDaArea);
        assertFalse(passagens.isEmpty());
        assertEquals(1, passagens.size());
        assertEquals(33695, passagens.get(0).codigoOnibus());
    }

    @Test
    void deveRegistrarPassagemQuandoCoordenadaRealEstiverAte52MetrosDoPonto() {
        PontoOnibusService service = new PontoOnibusService();

        boolean gravou = service.registrarPassagem(1, 33695, -23.526591, -46.530523);

        List<RegistroPassagemDto> passagens = service.buscarPassagens(1);

        assertTrue(gravou);
        assertFalse(passagens.isEmpty());
        assertEquals(1, passagens.size());
        assertEquals(33695, passagens.get(0).codigoOnibus());
    }

    @Test
    void devePermitirNovaPassagemAposUmMinutoMesmoDentroDoRaio() throws Exception {
        PontoOnibusService service = new PontoOnibusService();

        Field ultimaPassagemPorOnibusField = PontoOnibusService.class.getDeclaredField("ultimaPassagemPorOnibus");
        ultimaPassagemPorOnibusField.setAccessible(true);
        @SuppressWarnings("unchecked")
        java.util.Map<Integer, Instant> ultimaPassagemPorOnibus = (java.util.Map<Integer, Instant>) ultimaPassagemPorOnibusField.get(service);
        ultimaPassagemPorOnibus.put(33695, Instant.now().minusSeconds(61));

        boolean gravou = service.registrarPassagem(1, 33695, -23.527060074874345, -46.53046981701503);

        List<RegistroPassagemDto> passagens = service.buscarPassagens(1);

        assertTrue(gravou);
        assertFalse(passagens.isEmpty());
        assertEquals(1, passagens.size());
    }
}
