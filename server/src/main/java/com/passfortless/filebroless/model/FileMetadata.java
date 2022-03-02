package com.passfortless.filebroless.model;

import java.util.Date;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Value;

@Value
@RegisterForReflection
public class FileMetadata {

        private String name;
        private boolean directory;
        private boolean read;
        private boolean write;
        private boolean execute;
        private long length;
        private Date lastModified;
}
