package com.lumiere.domain.readmodels;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface UserInfoView {
    @JsonProperty("name")
    String getAuthName();

    @JsonProperty("userId")
    UUID getId();
}
