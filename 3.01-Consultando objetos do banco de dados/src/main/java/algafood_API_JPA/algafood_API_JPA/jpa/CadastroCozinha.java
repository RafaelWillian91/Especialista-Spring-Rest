package algafood_API_JPA.algafood_API_JPA.jpa;

import algafood_API_JPA.algafood_API_JPA.domain.model.Cozinha;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Component;

import java.util.List;

//@Component → diz ao Spring que essa classe é um Bean gerenciado e deve ser registrada no ApplicationContext.
@Component
public class CadastroCozinha {

    //pede ao container JPA (no caso, Hibernate via Spring Boot) para injetar uma instância do EntityManager.
    @PersistenceContext
    //O EntityManager é a interface de baixo nível que manipula o ciclo de vida das entidades (persist, merge, remove, find, createQuery, etc.).
    //Ele é configurado automaticamente pelo Spring Boot quando você tem spring-boot-starter-data-jpa e um DataSource.
    private EntityManager manager;
    public List<Cozinha> listar(){
        //Cria uma query JPQL (Java Persistence Query Language)
        //É uma interface da JPA (Java Persistence API) usada para executar consultas JPQL ou SQL nativo de forma tipada.
        //Query (sem tipo) → retorna resultados genéricos (Object, Object[]…), você precisa fazer cast manual.
        //TypedQuery<T> → já retorna uma lista/objeto do tipo especificado (T), sem precisar de cast.
        TypedQuery<Cozinha> query = manager.createQuery("from Cozinha", Cozinha.class);
        //devolve diretamente List<Cozinha>
        return query.getResultList();
    }


}
