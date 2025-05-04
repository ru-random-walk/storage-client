package ru.random.walk.model;

import com.amazonaws.services.s3.model.ObjectMetadata;

public enum FileExtension {
    PNG,
    JPG;

    public ObjectMetadata getMetadata() {
        var metadata = new ObjectMetadata();
        metadata.setContentType("image/%s".formatted(this.name().toLowerCase()));
        return metadata;
    }
}
