package com.algaworks.algafood.domain.listener;

import com.algaworks.algafood.core.email.EmailProperties;
import com.algaworks.algafood.domain.event.PedidoConfirmadoEvent;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class NotificacaoClientePedidoConfirmadoListener {
        @Autowired
        private EnvioEmailService envioEmailService;
        @TransactionalEventListener
        public void aoConfirmarPedido(PedidoConfirmadoEvent event){

                Pedido pedido = event.getPedido();

                var mensagem = EnvioEmailService.Mensagem.builder()
                        .assunto(pedido.getRestaurante().getNome() + " - Status Pedido - "+ pedido.getStatus().getDescricao())
                        .corpo("pedido-confirmado.html")
                        .variavel("pedido", pedido)
                        .destinatario(pedido.getCliente().getEmail())
                        .build();
                envioEmailService.enviar(mensagem);
        }

}
