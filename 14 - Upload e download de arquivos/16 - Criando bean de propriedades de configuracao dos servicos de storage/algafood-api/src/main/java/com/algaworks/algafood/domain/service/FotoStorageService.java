package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.FotoProduto;
import lombok.Builder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.UUID;


public interface FotoStorageService {
    InputStream recuperar(String nomeArquivo);
    void armazenar(NovaFoto novaFoto);
    void remover (String nomeArquivo);

    default void substituir(String nomeArquivoAntigo, NovaFoto novaFoto){
        this.armazenar(novaFoto);

        if(nomeArquivoAntigo != null){
            this.remover(nomeArquivoAntigo);
        }
    }
    default String gerarNomeArquivo (String nomeArquivo){
        return UUID.randomUUID().toString() + "__" + nomeArquivo;
    }
    @Getter
    @Builder
    class NovaFoto {

        private String nomeArquivo;
        private InputStream inputStream;

    }
}
