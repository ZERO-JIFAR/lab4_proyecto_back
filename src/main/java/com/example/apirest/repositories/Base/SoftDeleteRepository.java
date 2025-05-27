
package com.example.apirest.repositories.Base;

import com.example.apirest.entities.Base;
import com.example.apirest.repositories.BaseRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

@NoRepositoryBean
public interface SoftDeleteRepository<T extends Base, ID extends Serializable> extends BaseRepository<T, ID> {
    List<T> findAllActive();
}
