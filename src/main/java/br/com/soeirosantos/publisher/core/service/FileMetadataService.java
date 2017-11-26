package br.com.soeirosantos.publisher.core.service;

import br.com.soeirosantos.publisher.api.FileDetails;
import br.com.soeirosantos.publisher.api.FileDetailsMapper;
import br.com.soeirosantos.publisher.client.NotificationService;
import br.com.soeirosantos.publisher.client.StorageService;
import br.com.soeirosantos.publisher.core.entity.FileMetadata;
import br.com.soeirosantos.publisher.db.FileMetadataDao;
import lombok.SneakyThrows;
import org.apache.tika.Tika;
import org.modelmapper.ModelMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FileMetadataService {

    private final FileMetadataDao fileMetadataDao;
    private final StorageService storageService;
    private final NotificationService notificationService;
    private final FileDetailsMapper mapper = new FileDetailsMapper(new ModelMapper());
    private final Tika tika = new Tika();

    public FileMetadataService(FileMetadataDao fileMetadataDao, StorageService storageService, NotificationService notificationService) {
        this.fileMetadataDao = fileMetadataDao;
        this.storageService = storageService;
        this.notificationService = notificationService;
    }

    @SneakyThrows(IOException.class)
    public FileMetadata publish(InputStream file, FileDetails details) {
        FileMetadata metadata = mapper.toEntity(details);
        metadata.setId(UUID.randomUUID().toString());
        metadata.setType(tika.detect(file));
        fileMetadataDao.save(metadata);
        storageService.save(file, metadata);
        notificationService.notify(metadata);
        return metadata;
    }

    public Optional<FileMetadata> get(String id) {
        return fileMetadataDao.findById(id);
    }

    public List<FileMetadata> all() {
        return fileMetadataDao.list();
    }
}
