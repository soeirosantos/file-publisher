package br.com.soeirosantos.publisher.core.service;

import br.com.soeirosantos.publisher.api.FileDetails;
import br.com.soeirosantos.publisher.api.FileDetailsMapper;
import br.com.soeirosantos.publisher.client.StorageService;
import br.com.soeirosantos.publisher.core.entity.FileMetadata;
import br.com.soeirosantos.publisher.db.FileMetadataDao;
import org.modelmapper.ModelMapper;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FileMetadataService {

    private final FileMetadataDao fileMetadataDao;
    private final FileDetailsMapper mapper = new FileDetailsMapper(new ModelMapper());
    private final StorageService storageService;

    public FileMetadataService(FileMetadataDao fileMetadataDao, StorageService storageService) {
        this.fileMetadataDao = fileMetadataDao;
        this.storageService = storageService;
    }

    public FileMetadata publish(InputStream file, FileDetails details) {
        FileMetadata metadata = mapper.toEntity(details);
        metadata.setId(UUID.randomUUID().toString());
        fileMetadataDao.save(metadata);
        storageService.save(file, metadata);
        return metadata;
    }

    public Optional<FileMetadata> get(String id) {
        return fileMetadataDao.findById(id);
    }

    public List<FileMetadata> all() {
        return fileMetadataDao.list();
    }
}
