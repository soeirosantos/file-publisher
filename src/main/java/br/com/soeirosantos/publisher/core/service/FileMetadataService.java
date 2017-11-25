package br.com.soeirosantos.publisher.core.service;

import br.com.soeirosantos.publisher.api.FileDetails;
import br.com.soeirosantos.publisher.core.entity.FileMetadata;
import br.com.soeirosantos.publisher.db.FileMetadataDao;

import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

public class FileMetadataService {

    private final FileMetadataDao fileMetadataDao;

    public FileMetadataService(FileMetadataDao fileMetadataDao) {
        this.fileMetadataDao = fileMetadataDao;
    }

    public FileMetadata publish(InputStream file, FileDetails details) {
        FileMetadata metadata = new FileMetadata();
        metadata.setId(UUID.randomUUID().toString());
        metadata.setOriginalName(details.getOriginalName());
        return fileMetadataDao.save(metadata);
    }

    public Optional<FileMetadata> get(String id) {
        return fileMetadataDao.findById(id);
    }
}
