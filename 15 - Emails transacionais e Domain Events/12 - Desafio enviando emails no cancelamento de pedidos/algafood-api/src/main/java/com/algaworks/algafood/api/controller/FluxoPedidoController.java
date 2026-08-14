package com.algaworks.algafood.api.controller;


import com.algaworks.algafood.domain.service.FluxoPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos/{codigoPedido}")
public class FluxoPedidoController {

    @Autowired
    private FluxoPedidoService fluxoPedidoService;

    @PutMapping("/confirmacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirmar(@PathVariable String codigoPedido){

        fluxoPedidoService.confirmar(codigoPedido);
    }

    @PutMapping("/cancelamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelar(@PathVariable String codigoPedido){

        fluxoPedidoService.cancelarPedido(codigoPedido);
    }

    @PutMapping("/pedidoEntregue")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void pedidoEntregue(@PathVariable String codigoPedido){

        fluxoPedidoService.entregar(codigoPedido);
    }
}
