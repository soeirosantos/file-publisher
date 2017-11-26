package br.com.soeirosantos.publisher.client.impl;

import br.com.soeirosantos.publisher.client.StorageService;
import br.com.soeirosantos.publisher.core.entity.FileMetadata;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;

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
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(fileMetadata.getSize());
        amazonS3.putObject(bucketName, fileMetadata.getId(), file, objectMetadata);
    }

    @Override
    public InputStream get(String id) {
        S3Object s3object = amazonS3.getObject(bucketName, id);
        return s3object.getObjectContent();
    }
}
