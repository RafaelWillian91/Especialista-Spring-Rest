package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.api.exceptionhandler.FotoNaoEncontradaException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Optional;

@Service
public class CatalogoProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private FotoStorageService fotoStorageService;

    @Transactional
    public FotoProduto salvar(FotoProduto fotoProduto, InputStream dadosArquivo){

        Optional<FotoProduto> fotoExistente = produtoRepository.findFotoById(
                fotoProduto.getProduto().getRestaurante().getId(),
                fotoProduto.getProduto().getId());
                String nomeArquivoExistente = null;

                String nomeArquivo = fotoStorageService.gerarNomeArquivo(fotoProduto.getNomeArquivo());

        if(fotoExistente.isPresent()){
            nomeArquivoExistente = fotoExistente.get().getNomeArquivo();
            produtoRepository.delete(fotoExistente.get());
        }

        fotoProduto.setNomeArquivo(nomeArquivo);
        fotoProduto = produtoRepository.save(fotoProduto);
        produtoRepository.flush();

        FotoStorageService.NovaFoto novaFoto = FotoStorageService.NovaFoto.builder()
                .nomeArquivo(fotoProduto.getNomeArquivo())
                .inputStream(dadosArquivo).build();

        fotoStorageService.substituir(nomeArquivoExistente, novaFoto);

        return fotoProduto;
    }

    public FotoProduto buscarOuFalhar(Long restauranteId, Long produtoId){
        return produtoRepository.findFotoById(restauranteId, produtoId)
                .orElseThrow(() -> new FotoNaoEncontradaException(restauranteId, produtoId));
    }

}
