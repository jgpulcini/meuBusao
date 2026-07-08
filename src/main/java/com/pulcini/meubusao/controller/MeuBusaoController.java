package com.pulcini.meubusao.controller;

import com.pulcini.meubusao.dto.FindLineDto;
import com.pulcini.meubusao.itg.dto.PosicaoLinhaResponseDto;
import com.pulcini.meubusao.service.MeuBusaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transporte")
public class MeuBusaoController {

    private final MeuBusaoService meuBusaoService;

    public MeuBusaoController(MeuBusaoService meuBusaoService) {
        this.meuBusaoService = meuBusaoService;
    }

    @GetMapping("/authorization")
    public ResponseEntity<Boolean> authorization() {
        Boolean resultado = meuBusaoService.authorization();

        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/find/line")
    public ResponseEntity<List<FindLineDto>> buscarLinhas() {
        final String bus = "2714";
        List<FindLineDto> resultado = meuBusaoService.findLine(bus);

        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/find/buslive")
    @CrossOrigin(origins = "*")
    public ResponseEntity<PosicaoLinhaResponseDto> buscarBusaoVivo() {
        final int bus = 33695;
        PosicaoLinhaResponseDto resultado = meuBusaoService.findLine(bus);

        return ResponseEntity.ok(resultado);
    }

}
