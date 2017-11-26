package br.com.soeirosantos.publisher.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FileDetails {

    @Length(max = 500)
    private final String description;

    @JsonIgnore
    private String type;

    @JsonIgnore
    private Long size;

    @JsonIgnore
    private Date created;

    @JsonIgnore
    private Date lastModified;

    @JsonIgnore
    private String name;
}
