package com.algaworks.algafood.di.service;

import com.algaworks.algafood.di.modelo.Cliente;
import org.springframework.stereotype.Component;


public class ClienteAtivadoEvent {

    private Cliente cliente;

    public ClienteAtivadoEvent(Cliente c){
        cliente = c;
    }

    public Cliente getClienteAtivadoEvent() {
        return cliente;
    }
    
    
}
