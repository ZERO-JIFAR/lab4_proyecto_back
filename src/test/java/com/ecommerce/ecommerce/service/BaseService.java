//Base service copiado de rigoni

package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.entity.Base;
import com.ecommerce.ecommerce.repository.BaseRepository;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class BaseService<E extends Base, ID extends Serializable> {

    protected BaseRepository<E, ID> baseRepository;

    public BaseService(BaseRepository<E, ID> baseRepository) {
        this.baseRepository = baseRepository;
    }

    @Transactional(readOnly = true)
    public List<E> listar() throws Exception {
        try {
            return baseRepository.findAll();
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public Optional<E> buscarPorId(ID id) throws Exception {
        try {
            return baseRepository.findById(id);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    @Transactional
    public E crear(E entity) throws Exception {
        try {
            return baseRepository.save(entity);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    @Transactional
    public E actualizar(E entity) throws Exception {
        try {
            return baseRepository.save(entity);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    @Transactional
    public void eliminar(ID id) throws Exception {
        try {
            baseRepository.deleteById(id);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }
}
