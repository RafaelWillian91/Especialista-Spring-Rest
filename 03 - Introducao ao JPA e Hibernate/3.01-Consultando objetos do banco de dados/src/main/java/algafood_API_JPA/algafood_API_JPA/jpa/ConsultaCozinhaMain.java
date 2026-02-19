package algafood_API_JPA.algafood_API_JPA.jpa;

import algafood_API_JPA.algafood_API_JPA.AlgafoodApiJpaApplication;
import algafood_API_JPA.algafood_API_JPA.domain.model.Cozinha;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

public class ConsultaCozinhaMain {

    public static void main(String[] args) {
        //Cria um builder para inicializar a aplicação Spring Boot.
        //O parâmetro AlgafoodApiJpaApplication.class é a classe principal anotada com @SpringBootApplication.Ele define o ponto de entrada do contexto Spring.
        //.web(WebApplicationType.NONE) Diz ao Spring Boot que não é uma aplicação web.
        //Por padrão, o Boot tenta subir um servidor embutido (Tomcat/Jetty/Undertow).
        //Com NONE, você inicializa apenas o contexto (Beans, Repositórios, JPA, etc.) sem abrir portas HTTP.
        //Útil para rodar testes, batch jobs, ou scripts que só precisam de acesso ao banco.
        //Executa a aplicação de fato, processando os parâmetros de linha de comando (args).
        //Retorna um objeto do tipo ConfigurableApplicationContext, que é o container de IoC do Spring.
        //Esse contexto guarda todos os Beans gerenciados (Services, Repositories, etc.).
        //applicationContext.getBean(CadastroCozinha.class)
        //Recupera do contexto Spring a instância do Bean CadastroCozinha.
        //O Spring faz a injeção de dependências dentro desse bean (por exemplo, CozinhaRepository).
        ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiJpaApplication.class)
                .web(WebApplicationType.NONE).run(args);

        //applicationContext É o container de IoC (Inversão de Controle) criado pelo Spring.
        //Ele guarda todos os Beans registrados na aplicação (classes anotadas com @Component, @Service, @Repository, etc.).
        //getBean(CadastroCozinha.class)
        //Pede ao Spring: “Me devolva o Bean do tipo CadastroCozinha que você gerencia”.
        //O Spring procura no contexto um objeto que seja compatível com CadastroCozinha.
        //Como a classe CadastroCozinha foi anotada com @Component, ela já foi registrada automaticamente no contexto durante o scan do pacote.
        CadastroCozinha cadastroCozinha = applicationContext.getBean(CadastroCozinha.class);

        List<Cozinha> listCozinha = cadastroCozinha.listar();

        for(Cozinha cozinha : listCozinha){
            System.out.println(cozinha.getNome());
        }

    }



}
