package repository;

import model.Writer;

import java.util.Optional;

public interface WriterRepository extends GenericRepository<Writer, Integer> {
    Writer getWriterByName(String firstName, String lastName);

    Optional<Writer> getWriterByEmail(String email);
}
