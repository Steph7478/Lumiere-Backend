package com.lumiere.application.dtos.product.query;

import jakarta.validation.constraints.*;

public record ProductFindAllCriteria(@NotNull int page,
                @NotNull int size,
                @NotBlank String sortBy) {

}
