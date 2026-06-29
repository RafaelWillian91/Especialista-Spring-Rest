package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.FotoProdutoAssembler;
import com.algaworks.algafood.api.model.dtos.FotoProdutoDTO;
import com.algaworks.algafood.api.model.input.FotoProdutoInput;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CatalogoProdutoService;
import com.algaworks.algafood.domain.service.FotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {
    @Autowired
    private CadastroProdutoService cadastroProdutoService;
    @Autowired
    private CatalogoProdutoService catalogoProdutoService;
    @Autowired
    private FotoProdutoAssembler fotoProdutoAssembler;
    @Autowired
    private FotoStorageService fotoStorageService;

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FotoProdutoDTO atualizarFoto(@PathVariable Long restauranteId,
                                        @PathVariable Long produtoId, @Valid FotoProdutoInput fotoProdutoInput) throws IOException {

        Produto produto = cadastroProdutoService.buscarOuFalhar(restauranteId, produtoId);

        MultipartFile arquivo = fotoProdutoInput.getArquivo();

        FotoProduto fotoProduto = new FotoProduto();
        fotoProduto.setProduto(produto);
        fotoProduto.setDescricao(fotoProdutoInput.getDescricao());
        fotoProduto.setContentType(arquivo.getContentType());
        fotoProduto.setTamanho(arquivo.getSize());
        fotoProduto.setNomeArquivo(arquivo.getOriginalFilename());

        FotoProduto fotoSalva = catalogoProdutoService.salvar(fotoProduto, arquivo.getInputStream());

        return fotoProdutoAssembler.toModel(fotoSalva);
    }

    @DeleteMapping
    public void deletarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId){

        FotoProduto fotoProduto = catalogoProdutoService.buscarOuFalhar(restauranteId, produtoId);

        catalogoProdutoService.excluir(restauranteId, produtoId);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public FotoProdutoDTO buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId){

        FotoProduto fotoProduto = catalogoProdutoService.buscarOuFalhar(restauranteId, produtoId);

        return fotoProdutoAssembler.toModel(fotoProduto);
    }

    @GetMapping
    public ResponseEntity<InputStreamResource> servirFoto(@PathVariable Long restauranteId,
                                                          @PathVariable Long produtoId,
                                                          @RequestHeader (name = "accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException{

        try {
        FotoProduto fotoProduto = catalogoProdutoService.buscarOuFalhar(restauranteId, produtoId);

        MediaType mediaTypeFoto = MediaType.parseMediaType(fotoProduto.getContentType());
            List<MediaType> mediaTypesAceitas = MediaType.parseMediaTypes(acceptHeader);

        verificarCompatibilidade(mediaTypeFoto, mediaTypesAceitas);

        InputStream inputStream = fotoStorageService.recuperar(fotoProduto.getNomeArquivo());

        return ResponseEntity.ok()
                .contentType(mediaTypeFoto)
                .body(new InputStreamResource(inputStream));
        }catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.notFound().build();
        }
    }


    private void verificarCompatibilidade (MediaType mediaType, List<MediaType> mediaTypeAceitas) throws HttpMediaTypeNotAcceptableException {

            boolean compativel = mediaTypeAceitas.stream()
                    .anyMatch(mediaTypeAceita -> mediaTypeAceita.isCompatibleWith(mediaType));

            if(!compativel){
                throw new HttpMediaTypeNotAcceptableException(mediaTypeAceitas);
            }
    }
}
