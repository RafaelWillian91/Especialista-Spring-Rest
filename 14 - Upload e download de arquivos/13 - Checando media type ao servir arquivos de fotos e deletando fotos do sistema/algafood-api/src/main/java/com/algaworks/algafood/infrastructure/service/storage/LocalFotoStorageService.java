package com.algaworks.algafood.infrastructure.service.storage;

import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.service.FotoStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class LocalFotoStorageService implements FotoStorageService {

    @Value("${algafood.storage.local.diretorios-fotos}")
    private Path diretorioFotos;

    @Override
    public InputStream recuperar(String nomeArquivo) {

        try {
        Path pathArquivo = getDiretorioFotos(nomeArquivo);
            return Files.newInputStream(pathArquivo);
        } catch (IOException e) {
            throw new StorageException("não foi possível recuperar arquivo", e);
        }
    }

    @Override
    public void armazenar(NovaFoto novaFoto) {

        Path arquivoPath = getDiretorioFotos(novaFoto.getNomeArquivo());

        try {
            Files.copy(novaFoto.getInputStream(), arquivoPath);
        } catch (IOException e) {
            throw new StorageException("Não foi possível armazenar arquivo." + e);
        }
    }

    @Override
    public void remover(String nomeArquivo) {
            try {
                Path arquivoPath = getDiretorioFotos(nomeArquivo);
                Files.deleteIfExists(arquivoPath);
            } catch (IOException e) {
                throw new StorageException("Não foi possível excluir arquivo", e);
            }
    }

    private Path getDiretorioFotos(String nomeArquivo){

        return diretorioFotos.resolve(Path.of(nomeArquivo));
    }
}
