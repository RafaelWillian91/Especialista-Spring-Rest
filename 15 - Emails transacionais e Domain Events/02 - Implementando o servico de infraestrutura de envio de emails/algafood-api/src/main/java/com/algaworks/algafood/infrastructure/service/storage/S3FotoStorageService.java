package com.algaworks.algafood.infrastructure.service.storage;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.service.FotoStorageService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URL;


public class S3FotoStorageService implements FotoStorageService {

    @Autowired
    private AmazonS3 amazonS3;
    @Autowired
    private StorageProperties storageProperties;
    @Override
    public FotoRecuperada recuperar(String nomeArquivo) {
        String caminhoArquivo = getCaminhoArquivo(nomeArquivo);
        URL url = amazonS3.getUrl(storageProperties.getS3().getBucket(), caminhoArquivo);

        return FotoRecuperada.builder()
                .url(url.toString())
                .build();
    }

    @Override
    public void armazenar(NovaFoto novaFoto) {
        try {
        String caminhoArquivo = getCaminhoArquivo(novaFoto.getNomeArquivo());

        var objectMetaData = new ObjectMetadata();
            objectMetaData.setContentType(novaFoto.getContentType());

        var putObjectRquest = new PutObjectRequest(
                storageProperties.getS3().getBucket(),
                caminhoArquivo,
                novaFoto.getInputStream(),
                objectMetaData);

        amazonS3.putObject(putObjectRquest);
        }catch (Exception e){
            throw new StorageException("Não foi possível enviar arquivo para amazon S3", e);
        }
    }


    @Override
    public void remover(String nomeArquivo) {
        try {
            var deleteObjectRequest = new DeleteObjectRequest(

                    storageProperties.getS3().getBucket(), getCaminhoArquivo(nomeArquivo));

            amazonS3.deleteObject(deleteObjectRequest);
        }catch (Exception e){
            throw new StorageException("Não foi possível deletar arquivo na amazon S3", e);
        }

    }
    private String getCaminhoArquivo(String nomeArquivo) {
        return String.format("%s/%s", storageProperties.getS3().getDiretorioFotos(), nomeArquivo);
    }
}
