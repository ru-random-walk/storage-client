package ru.random.walk.model;

import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum FileType {
    PNG("image", "png"),
    JPG("image", "jpeg"),
    HTML("text", "html"),
    PDF("application", "pdf"),
    CSV("text", "csv"),
    JSON("application", "json"),
    XML("application", "xml"),
    GIF("image", "gif"),
    MP3("audio", "mpeg"),
    MP4("video", "mp4"),
    SVG("image", "svg+xml"),
    ZIP("application", "zip"),
    TEXT("text", "plain");

    private final String mimeType;
    private final String mimeSubtype;

    public ObjectMetadata getMetadata() {
        var metadata = new ObjectMetadata();
        metadata.setContentType("%s/%s".formatted(this.mimeType, this.mimeSubtype));
        return metadata;
    }
}
