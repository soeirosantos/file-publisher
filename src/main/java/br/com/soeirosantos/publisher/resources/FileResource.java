package br.com.soeirosantos.publisher.resources;

import br.com.soeirosantos.publisher.api.FileDetails;
import br.com.soeirosantos.publisher.core.entity.FileMetadata;
import br.com.soeirosantos.publisher.core.service.FileMetadataService;
import com.codahale.metrics.annotation.Timed;
import io.dropwizard.hibernate.UnitOfWork;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.Optional;

@Slf4j
@Path("/files")
@Produces(MediaType.APPLICATION_JSON)
public class FileResource {

    private final FileMetadataService fileMetadataService;

    public FileResource(FileMetadataService fileMetadataService) {
        this.fileMetadataService = fileMetadataService;
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @UnitOfWork
    @Timed
    public Response publish(@FormDataParam("details") FileDetails details,
                            @FormDataParam("file") InputStream file,
                            @FormDataParam("file") FormDataContentDisposition fileDisposition) {

        details.setName(fileDisposition.getFileName());
        details.setCreated(fileDisposition.getCreationDate());
        details.setLastModified(fileDisposition.getModificationDate());
        details.setSize(fileDisposition.getSize());
        FileMetadata metadata = fileMetadataService.publish(file, details);
        return Response.ok(metadata).build();
    }

    @GET
    @Path("/{fileId}")
    @UnitOfWork
    public Response get(@PathParam("fileId") String id) {
        Optional<FileMetadata> fileMetadata = fileMetadataService.get(id);
        if (fileMetadata.isPresent()) {
            return Response.ok(fileMetadata.get()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/{fileId}/download")
    @UnitOfWork
    @Timed
    public Response download(@PathParam("fileId") String id) {
        Optional<FileMetadata> metadata = fileMetadataService.get(id);
        if (metadata.isPresent()) {
            InputStream content = fileMetadataService.getContent(id);
            Response.ResponseBuilder response = Response.ok(content);
            response.header(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename=" + metadata.get().getName());
            return response.build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @UnitOfWork
    public Response list() {
        return Response.ok(fileMetadataService.all()).build();
    }
}
