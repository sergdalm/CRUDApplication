// Interface for all repositories

import java.util.Map;

public interface GenericRepository<T, ID> {

    T getById(ID id);

    T save(T obj);

    T update(ID id, T obj);

    Map<ID, T> getAll();

    void deleteById(ID id);

}
