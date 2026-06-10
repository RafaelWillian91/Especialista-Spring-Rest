package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;

import java.util.List;

public interface VendaReportService {


    byte[] consultarVendasDiarias(VendaDiariaFilter vendaDiariaFilter, String timeOffset);


}
