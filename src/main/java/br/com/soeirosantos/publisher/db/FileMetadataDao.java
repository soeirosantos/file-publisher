package br.com.soeirosantos.publisher.db;

import br.com.soeirosantos.publisher.core.entity.FileMetadata;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class FileMetadataDao extends AbstractDAO<FileMetadata> {

    public FileMetadataDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public FileMetadata save(FileMetadata metadata) {
        return persist(metadata);
    }

    public Optional<FileMetadata> findById(String id) {
        return Optional.ofNullable(get(id));
    }

    public List<FileMetadata> list() {
        return list(query("select distinct f from FileMetadata f"));
    }
}
