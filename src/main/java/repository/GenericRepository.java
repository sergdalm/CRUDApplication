package repository;// Interface for all repositories

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface GenericRepository<T, K> {

    T getById(K id);

    T save(T obj);

    T update(T obj);

    List<T> getAll();

    boolean deleteById(K id);
}
