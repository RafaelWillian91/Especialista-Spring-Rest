package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.input.FotoProdutoInput;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void atualizarFoto(@PathVariable Long restauranteId,
                              @PathVariable Long produtoId, FotoProdutoInput fotoProdutoInput) {

            var nomeArquivo = UUID.randomUUID().toString() + " - " + fotoProdutoInput.getArquivo().getOriginalFilename();

            var arquivoFoto = Path.of("D:/Documentos/Curso AlgaWorks - Especialista Spring Rest", nomeArquivo);

        System.out.println("Descricao: " + fotoProdutoInput.getDescricao());
        System.out.println("Arquivo Foto: " + fotoProdutoInput.getArquivo());
        System.out.println("ContentType: " + fotoProdutoInput.getArquivo().getContentType());

            try {
                fotoProdutoInput.getArquivo().transferTo(arquivoFoto);
            }catch (Exception e){
                throw new RuntimeException(e);
            }
    }


}
