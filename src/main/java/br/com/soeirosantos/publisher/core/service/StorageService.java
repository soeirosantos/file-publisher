package br.com.soeirosantos.publisher.core.service;

import br.com.soeirosantos.publisher.core.entity.FileMetadata;

import java.io.InputStream;

public interface StorageService {

    void save(InputStream file, FileMetadata fileMetadata);

    InputStream get(String id);
}
