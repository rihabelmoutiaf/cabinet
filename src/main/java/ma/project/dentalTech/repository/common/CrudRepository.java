package ma.project.dentalTech.repository.common;

import java.util.List;

public interface CrudRepository<T, ID> {

    T save(T entity);

    T update(T entity);

    boolean delete(ID id);

    T findById(ID id);

    List<T> findAll();
}
