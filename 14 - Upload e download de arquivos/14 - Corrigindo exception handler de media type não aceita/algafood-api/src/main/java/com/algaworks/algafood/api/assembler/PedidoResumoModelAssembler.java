package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.dtos.PedidoDTO;
import com.algaworks.algafood.api.model.dtos.PedidoResumoDTO;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoResumoModelAssembler {


    @Autowired
    private ModelMapper modelMapper;

    public PedidoResumoDTO toModel (Pedido pedido){

        return modelMapper.map(pedido, PedidoResumoDTO.class);
    }

    public List<PedidoResumoDTO> toCollectionModel(List<Pedido> pedidos) {
        return pedidos.stream()
                .map(pedido -> toModel(pedido))
                .collect(Collectors.toList());
    }


}
