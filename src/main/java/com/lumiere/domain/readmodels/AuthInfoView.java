package com.lumiere.domain.readmodels;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface AuthInfoView {
    @JsonProperty("name")
    String getAuthName();

    @JsonProperty("isAdmin")
    boolean getAuthIsAdmin();

    @JsonProperty("userId")
    UUID getId();
}