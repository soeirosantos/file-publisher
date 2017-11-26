package br.com.soeirosantos.publisher.core.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
public class FileMetadata {

    @Id
    private String id;
    private String name;
    private String originalName;
    private String type;
    private Long size;
    private LocalDateTime created;
    private LocalDateTime lastModified;

}
