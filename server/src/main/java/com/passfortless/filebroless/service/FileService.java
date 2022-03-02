package com.passfortless.filebroless.service;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;

import com.passfortless.filebroless.config.ParametersConfig;
import com.passfortless.filebroless.model.FileMetadata;
import com.passfortless.filebroless.utils.StringUtils;

import org.jboss.logging.Logger;

@ApplicationScoped
public class FileService {

    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass());

    private final Path rootPath;

    public FileService(ParametersConfig parametersConfig) {
        this.rootPath = Paths.get(parametersConfig.getWorkingDirectory());
    }

    public Optional<List<FileMetadata>> listDirectory(String relativePath) {
        LOGGER.debug(String.format("Retrieve files for directory [%s]", relativePath));
        try {
            Path directory = rootPath.resolve(relativePath);
            // allowed only for working directory and subfolders of working directory
            if (isSameOrSubfolder(rootPath, directory) && Files.exists(directory)) {
                LOGGER.debug(String.format("Listing files, directory [%s]",
                        directory.toAbsolutePath().normalize().toString()));
                List<FileMetadata> files = new ArrayList<FileMetadata>();
                // add parent directory only for subfolders of working directory
                if (StringUtils.isNotBlank(relativePath) && !Files.isSameFile(rootPath, directory)) {
                    Path parent = Path.of("..");
                    files.add(fileToMetadata(parent.toFile()));
                }
                List<FileMetadata> listing = Files.list(directory).map(f -> fileToMetadata(f.toFile()))
                        .collect(Collectors.toList());
                files.addAll(listing);
                return Optional.of(files);
            }
        } catch (IOException e) {
            LOGGER.error("Failed to read files", e);
        }
        return Optional.empty();
    }

    public Optional<File> getFile(String filePath) {
        Objects.nonNull(filePath);
        LOGGER.debug(String.format("Retrieving file [%s]", filePath));
        Path path = rootPath.resolve(filePath);
        if (Files.exists(path) && Files.isRegularFile(path)) {
            return Optional.of(path.toFile());
        }
        return Optional.empty();
    }

    private boolean isSameOrSubfolder(Path parentFolder, Path directory) throws IOException {
        Path parentPath = parentFolder.toRealPath().normalize();
        Path childPath = directory.toRealPath().normalize();
        return childPath.getNameCount() >= parentPath.getNameCount() && childPath.startsWith(parentPath);
    }

    private FileMetadata fileToMetadata(File file) {
        return new FileMetadata(file.getName(), file.isDirectory(), file.canRead(), file.canWrite(), file.canExecute(),
                file.length(), new Date(file.lastModified()));
    }
}
