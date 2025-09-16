package com.algaworks.algafood.infrastructure.repository;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class RestauranteRepositoryImpl implements RestauranteRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Restaurante> listaRestaurante() {

        return entityManager.createQuery("from Restaurante", Restaurante.class).getResultList();
    }

    @Override
    @Transactional
    public Restaurante addRestaurante(Restaurante restaurante) {

        return entityManager.merge(restaurante);
    }

    @Override
    public Restaurante buscarRestauranteId(Long id) {

        return entityManager.find(Restaurante.class, id);
    }

    @Transactional
    @Override
    public void removerRestaurante() {
        entityManager.remove(buscarRestauranteId(1L));
    }
}
