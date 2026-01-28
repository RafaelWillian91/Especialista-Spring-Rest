package com.algaworks.algafood.domain.exception;

import com.algaworks.algafood.api.exceptionhandler.ProblemType;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.algaworks.algafood.api.exceptionhandler.ApiExceptionHandler.MSG_ERRO_GENERICA_USUARIO_FINAL;


//A forma mais comum de customizar a resposta padrão de erro no Spring Boot é estendendo DefaultErrorAttributes,
// permitindo alterar o payload retornado pelo endpoint /error sem interferir no fluxo normal da aplicação.
//Dessa forma nao preciso desabilitar a pasta static e forcar passar pelo handler,
/**
 * Customiza os atributos de erro retornados pelo Spring Boot
 * para cenários que não passam pelo ControllerAdvice,
 * como requisições para URLs inexistentes.
 * Essa classe garante que erros 404 de rota sigam o mesmo
 * padrão de resposta da API, evitando payloads genéricos
 * ou dependentes da estrutura interna do Spring.
 * evita que a pasta /static é desativada
    -  recursos estáticos param de funcionar
    -  Swagger, docs, assets, etc. podem quebrar
 */

@Component
public class ApiErrorAttributes extends DefaultErrorAttributes {

    public static final String RECURSO_INEXISTENTE = "O recurso %s, que você tentou acessar, é inexistente";

    @Override
    public Map<String, Object> getErrorAttributes(
            WebRequest webRequest,
            ErrorAttributeOptions options) {

        Map<String, Object> attrs =
                super.getErrorAttributes(webRequest, options);

        Integer status = (Integer) attrs.get("status");

        if (status != null && status == 404) {
            String path = (String) attrs.get("path");

            //Criamos um novo Map para evitar acoplamento à estrutura interna do Spring e garantir que apenas
            // os campos desejados componham a resposta final. Utilizamos LinkedHashMap para preservar a ordem de
            // inserção, tornando o JSON mais legível e previsível.
            Map<String, Object> problem = new LinkedHashMap<>();
            problem.put("status", HttpStatus.NOT_FOUND.value());
            problem.put("type", ProblemType.RECURSO_NAO_ENCONTRADO.getUri());
            problem.put("title", ProblemType.RECURSO_NAO_ENCONTRADO.getTitle());
            problem.put(
                    "detail",
                    String.format(
                            RECURSO_INEXISTENTE,
                            path
                    )
            );
            problem.put("userMesagem",String.format(RECURSO_INEXISTENTE, path)
            );

            return problem;
        }

        return attrs;
    }
}
