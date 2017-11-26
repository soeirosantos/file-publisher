package br.com.soeirosantos.publisher.client;

import br.com.soeirosantos.publisher.core.entity.FileMetadata;

import java.io.InputStream;

public interface StorageService {

    void save(InputStream file, FileMetadata fileMetadata);

}
