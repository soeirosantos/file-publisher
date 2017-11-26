package br.com.soeirosantos.publisher.client.impl;

import br.com.soeirosantos.publisher.client.StorageService;
import br.com.soeirosantos.publisher.core.entity.FileMetadata;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;

import java.io.InputStream;

public class S3Client implements StorageService {

    private final String bucketName;
    private final AmazonS3 amazonS3;

    public S3Client(AmazonS3 amazonS3, String bucketName) {
        this.bucketName = bucketName;
        this.amazonS3 = amazonS3;
    }

    @Override
    public void save(InputStream file, FileMetadata fileMetadata) {
        ObjectMetadata objectMetada = new ObjectMetadata();
        objectMetada.setContentLength(fileMetadata.getSize());
        amazonS3.putObject(bucketName, fileMetadata.getId(), file, objectMetada);
    }
}
