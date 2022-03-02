package com.passfortless.filebroless.api;

import java.io.File;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import com.passfortless.filebroless.model.FileMetadata;
import com.passfortless.filebroless.service.FileService;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.Logger;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.smallrye.mutiny.Uni;

@Path("/api/v1")
@Tag(name = "File API", description = "File API service")
public class FileV1Resource {

    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass());

    @Inject
    FileService fileService;

    @Operation(summary = "Return files in directory")
    @APIResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = FileMetadata.class, type = SchemaType.ARRAY)))
    @Counted(description = "Counts how many times the listFiles method has been invoked")
    @Timed(description = "Times how long it takes to invoke the listFiles method")
    @GET
    @Path("/files")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> listFiles(
            @Parameter(description = "Directory", required = false) @DefaultValue("") @QueryParam("directory") String directory) {
        Optional<List<FileMetadata>> optionalFiles = fileService.listDirectory(directory);
        if (optionalFiles.isPresent()) {
            return Uni.createFrom().item(Response.ok(optionalFiles.get()).build());
        }
        return Uni.createFrom().item(Response.status(Status.NOT_FOUND).build());
    }

    @Operation(summary = "Downloading file")
    @APIResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM, schema = @Schema(implementation = File.class, type = SchemaType.OBJECT)))
    @Counted(description = "Counts how many times the downloadFile method has been invoked")
    @Timed(description = "Times how long it takes to invoke the downloadFile method")
    @GET
    @Path("/files/{filePath}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Uni<Response> downloadFile(
            @Parameter(description = "File path", required = true) @PathParam("filePath") String filePath) {
        Optional<File> optionalFile = fileService.getFile(filePath);
        if (optionalFile.isPresent()) {
            LOGGER.debug(String.format("Downloading file [%s]", optionalFile.get().getAbsolutePath()));
            ResponseBuilder response = Response.ok(optionalFile.get());
            response.header("Content-Disposition", "attachment;filename=" + optionalFile.get().getName());
            return Uni.createFrom().item(response.build());
        }
        return Uni.createFrom().item(Response.status(Status.NOT_FOUND).build());
    }
}
