package com.algaworks.algafood.api.controller;


import com.algaworks.algafood.api.assembler.ProdutoAssembler;
import com.algaworks.algafood.api.assembler.ProdutoDisassembler;
import com.algaworks.algafood.api.model.dtos.ProdutoDTO;
import com.algaworks.algafood.api.model.input.ProdutoInput;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoAssembler produtoAssembler;
    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;
    @Autowired
    private CadastroProdutoService cadastroProduto;
    @Autowired
    private ProdutoDisassembler produtoDisassembler;

    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping
    public List<ProdutoDTO> listar (@PathVariable Long restauranteId,
                                    @RequestParam(required = false) boolean incluirInativos){
        Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);
        List<Produto> todosProdutos = null;
        if(incluirInativos){
             todosProdutos = produtoRepository.findByRestaurante(restaurante);
        }else {
            todosProdutos = produtoRepository.findByAtivoTrueAndRestaurante(restaurante);
        }


        return produtoAssembler.toCollectionModel(todosProdutos);
    }


    @GetMapping("/{produtoId}")
    public ProdutoDTO buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        Produto produto = cadastroProduto.buscarOuFalhar(restauranteId, produtoId);

        return produtoAssembler.toModel(produto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoDTO adicionarProduto(@RequestBody @Valid ProdutoInput produtoInput, @PathVariable Long restauranteId){

       Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);
       Produto produto = produtoDisassembler.toDomainObject(produtoInput);

       produto.setRestaurante(restaurante);

       cadastroProduto.salvar(produto, restauranteId);

       return produtoAssembler.toModel(produto);
    }

    @PutMapping("/{produtoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ProdutoDTO atualizarProduto(@PathVariable Long produtoId, @PathVariable Long restauranteId, @RequestBody
                                @Valid ProdutoInput produtoInput){

        Produto produto = cadastroProduto.buscarOuFalhar(restauranteId, produtoId);
        produtoDisassembler.copyToDomainObject(produtoInput, produto);

        cadastroProduto.salvar(produto, restauranteId);

        return produtoAssembler.toModel(produto);
    }

}
