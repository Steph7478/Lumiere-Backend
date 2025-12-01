package com.lumiere.application.dtos.admin.command.add;

import java.util.List;

public record AddProductInput(List<AddProductRequestData> items) {
}
