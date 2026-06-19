package com.algaworks.algafood.core.validation;

import org.springframework.http.MediaType;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FileContentTypeValidator implements ConstraintValidator<FileContentType, MultipartFile> {

    private List<String> TIPOS_PERMITIDOS;

    @Override
    public void initialize(FileContentType constraintAnnotation) {
        TIPOS_PERMITIDOS = Arrays.asList(constraintAnnotation.allowed());
    }

    @Override
    public boolean isValid(MultipartFile type, ConstraintValidatorContext constraintValidatorContext) {

        return type == null || TIPOS_PERMITIDOS.contains(type.getContentType());
    }
}
