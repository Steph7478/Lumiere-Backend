package com.lumiere.application.dtos.admin.command.modify;

import java.util.Optional;

public record ModifyProductRequestData(Optional<String> name, Optional<String> description) {
    public boolean isCompleteUpdate() {
        return name.isPresent() &&
                description.isPresent();
    }

    public boolean hasUpdates() {
        return name.isPresent() ||
                description.isPresent();
    }
}
