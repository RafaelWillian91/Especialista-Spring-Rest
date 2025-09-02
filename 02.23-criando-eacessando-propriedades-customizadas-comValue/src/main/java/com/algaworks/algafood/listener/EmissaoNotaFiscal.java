package com.algaworks.algafood.listener;

import com.algaworks.algafood.di.service.ClienteAtivadoEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EmissaoNotaFiscal {

    @EventListener
    public void emissaoNotaFiscal(ClienteAtivadoEvent clienteAtivadoEvent){
        System.out.println("Nota fiscal emitida para o cliente " + clienteAtivadoEvent.getClienteAtivadoEvent());
    }

}
