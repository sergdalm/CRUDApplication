package repository;

import model.Writer;

import java.util.Optional;

public interface WriterRepository extends GenericRepository<Writer, Integer> {
    Optional<Writer> getWriterByEmail(String email);
}
