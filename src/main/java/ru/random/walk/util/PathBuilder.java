package ru.random.walk.util;

import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor(staticName = "init")
public class PathBuilder {
    private final List<String> folders = new ArrayList<>();

    public PathBuilder add(String folder) {
        folders.add(folder);
        return this;
    }

    public PathBuilder add(Key key, String value) {
        folders.add(key.name().toLowerCase());
        folders.add(value);
        return this;
    }

    public PathBuilder add(Key key, UUID value) {
        return add(key, value.toString());
    }

    public String build() {
        return folders.stream()
                .reduce("%s/%s"::formatted)
                .orElseThrow(() -> new IllegalArgumentException("Path must not be empty!"));
    }

    public enum Key {
        CLUB_ID,
        USER_ID,
        MESSAGE_ID,
        CHAT_ID,
        APPOINTMENT_ID,
    }
}
