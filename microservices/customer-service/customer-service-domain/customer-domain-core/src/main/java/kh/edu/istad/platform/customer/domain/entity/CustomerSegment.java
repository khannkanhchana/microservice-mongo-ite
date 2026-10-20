package kh.edu.istad.platform.customer.domain.entity;

import java.util.Locale;
import java.util.UUID;

public class CustomerSegment {

    private UUID id;
    private String name;
    private String code;
    private String description;
    private boolean active;

    public CustomerSegment(
            UUID id,
            String name,
            String code,
            String description,
            boolean active
    ) {
        this.id = id;
        this.name = name;
        this.code = normalizeCode(code);
        this.description = description;
        this.active = active;
    }

    public void create() {
        validate();

        if (id != null) {
            throw new IllegalArgumentException(
                    "Segment ID must be null when creating"
            );
        }

        id = UUID.randomUUID();
        active = true;
    }

    public void update(
            String name,
            String code,
            String description
    ) {
        this.name = name;
        this.code = normalizeCode(code);
        this.description = description;
        validate();
    }

    public void activate() {
        active = true;
    }

    public void deactivate() {
        active = false;
    }

    private void validate() {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                    "Segment name is required"
            );
        }

        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException(
                    "Segment code is required"
            );
        }
    }

    private static String normalizeCode(String code) {
        return code == null
                ? null
                : code.trim().toUpperCase(Locale.ROOT);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return active;
    }
}