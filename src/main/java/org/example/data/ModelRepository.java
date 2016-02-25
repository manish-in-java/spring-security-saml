package org.example.data;

import org.example.domain.Model;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Contract for data access operations on {@link Model}.
 */
@NoRepositoryBean
public interface ModelRepository<T extends Model> extends CrudRepository<T, Long>
{
}
