package repository;

import model.Writer;
import repository.GenericRepository;

public interface WriterRepository extends GenericRepository<Writer, Integer> {
    Writer getWriterByName(String firstName, String lastName);
    boolean isExisting(String firstName, String lastName);
}
