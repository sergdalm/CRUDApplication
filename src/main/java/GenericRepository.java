// Interface for all repositories

import java.util.List;

public interface GenericRepository<T, ID> {

    T getById(ID id);

    T save(T obj);

    void update(T obj);

    List<T> getAll();

    void deleteById(ID id);

    // Save content of repository into json file
    void saveRepository();

}
