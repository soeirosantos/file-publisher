package br.com.soeirosantos.publisher.core.service;

import br.com.soeirosantos.publisher.core.entity.FileMetadata;
import lombok.Data;

import java.time.LocalDateTime;

public interface NotificationService {

    void notify(FileMetadata fileMetadata);

    @Data
    class NewFileEvent {
        private final String id;
        private final String name;
        private final Long size;
        private final LocalDateTime publishedAt;
    }
}
