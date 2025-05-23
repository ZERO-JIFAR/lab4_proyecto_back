
package com.example.apirest.repositories.Base;

import com.example.apirest.entities.Base;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import java.io.Serializable;
import java.util.List;

public class SoftDeleteRepositoryImpl<T extends Base, ID extends Serializable> extends SimpleJpaRepository<T, ID>
        implements SoftDeleteRepository<T, ID> {

    private final EntityManager entityManager;
    private final Class<T> domainClass;

    public SoftDeleteRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
        this.domainClass = entityInformation.getJavaType();
    }

    @Override
    public List<T> findAllActive() {
        String jpql = "SELECT e FROM " + domainClass.getSimpleName() + " e WHERE e.eliminado = false";
        return entityManager.createQuery(jpql, domainClass).getResultList();
    }
}
