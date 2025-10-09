package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.RestauranteNaoencontradoException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.algaworks.algafood.domain.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Consumer;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @Autowired
    private RestauranteService restauranteService;

    @GetMapping("/listaRestaurantes")
    public List<Restaurante> listaRestaurante(){

        List<Restaurante> listaRestaurante = restauranteService.listaRestaurante();

        return listaRestaurante;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscaID(@PathVariable Long id){
        try {
            Restaurante r1 = restauranteService.buscaRestauranteID(id);
            return ResponseEntity.status(HttpStatus.OK).body(r1);
        }catch (RestauranteNaoencontradoException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getLocalizedMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> adicionarRestaurante(@RequestBody Restaurante restaurante){

        try{
            cadastroRestauranteService.salvar(restaurante);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(restaurante);
        }catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getLocalizedMessage());

        }
    }

}
