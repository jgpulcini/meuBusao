package com.pulcini.meubusao.controller;

import com.pulcini.meubusao.dto.PontoOnibusDto;
import com.pulcini.meubusao.dto.PontoOnibusPassagemRequestDto;
import com.pulcini.meubusao.dto.RegistroPassagemDto;
import com.pulcini.meubusao.service.PontoOnibusService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transporte")
public class PontoOnibusController {

    private final PontoOnibusService pontoOnibusService;

    public PontoOnibusController(PontoOnibusService pontoOnibusService) {
        this.pontoOnibusService = pontoOnibusService;
    }

    @PostMapping("/pontos")
    public ResponseEntity<PontoOnibusDto> cadastrarPonto(@RequestBody PontoOnibusDto pontoOnibusDto) {
        return ResponseEntity.ok(pontoOnibusService.cadastrarPonto(pontoOnibusDto));
    }

    @PostMapping("/pontos/{pontoId}/passagens")
    public ResponseEntity<Boolean> registrarPassagem(
            @PathVariable Integer pontoId,
            @RequestBody PontoOnibusPassagemRequestDto request) {

        boolean gravou = pontoOnibusService.registrarPassagem(
                pontoId,
                request.codigoOnibus(),
                request.latitude(),
                request.longitude());

        return ResponseEntity.ok(gravou);
    }

    @GetMapping("/pontos/{pontoId}/passagens")
    public ResponseEntity<List<RegistroPassagemDto>> buscarPassagens(@PathVariable Integer pontoId) {
        return ResponseEntity.ok(pontoOnibusService.buscarPassagens(pontoId));
    }
}
